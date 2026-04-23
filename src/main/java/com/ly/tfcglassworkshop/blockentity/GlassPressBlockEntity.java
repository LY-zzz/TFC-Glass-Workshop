package com.ly.tfcglassworkshop.blockentity;

import com.ly.tfcglassworkshop.TFCGlassWorkshop;
import com.ly.tfcglassworkshop.registry.ModBlockEntities;
import com.ly.tfcglassworkshop.menu.GlassPressMenu;
import com.ly.tfcglassworkshop.recipe.PressingRecipe;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;

import com.ly.tfcglassworkshop.registry.ModRecipeTypes;
import com.ly.tfcglassworkshop.registry.ModTags;

public class GlassPressBlockEntity extends BlockEntity implements MenuProvider {
    public static final int PRESS_TIME = 20;
    public static final int INPUT_SLOT = 0;
    public static final int MOLD_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;
    public static final int SLOT_COUNT = 3;

    private final ItemStackHandler inventory = new ItemStackHandler(SLOT_COUNT) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (slot == OUTPUT_SLOT) {
                return false;
            }
            if (slot == MOLD_SLOT) {
                return stack.is(ModTags.Items.PRESS_MOLDS);
            }
            return !stack.is(ModTags.Items.PRESS_MOLDS);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> inventoryCapability = LazyOptional.of(() -> inventory);
    private int progress;

    public GlassPressBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GLASS_PRESS.get(), pos, state);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public Optional<PressingRecipe> getMatchingRecipe() {
        if (level == null) {
            return Optional.empty();
        }

        SimpleContainer container = new SimpleContainer(2);
        container.setItem(INPUT_SLOT, inventory.getStackInSlot(INPUT_SLOT));
        container.setItem(MOLD_SLOT, inventory.getStackInSlot(MOLD_SLOT));
        return level.getRecipeManager().getRecipeFor(ModRecipeTypes.PRESSING_TYPE.get(), container, level);
    }

    public float getInputTemperature() {
        return HeatCapability.getTemperature(inventory.getStackInSlot(INPUT_SLOT));
    }

    public int getProgress() {
        return progress;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GlassPressBlockEntity blockEntity) {
        Optional<PressingRecipe> recipe = blockEntity.getMatchingRecipe();
        if (recipe.isPresent() && blockEntity.canProcess(recipe.get())) {
            blockEntity.progress++;
            if (blockEntity.progress >= PRESS_TIME) {
                blockEntity.craft(recipe.get());
                blockEntity.progress = 0;
            }
            setChanged(level, pos, state);
        } else if (blockEntity.progress != 0) {
            blockEntity.progress = 0;
            setChanged(level, pos, state);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.tfc_glass_workshop.glass_press");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new GlassPressMenu(containerId, playerInventory, this);
    }

    public void dropContents(Level level, BlockPos pos) {
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                Containers.dropItemStack(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack.copy());
                inventory.setStackInSlot(slot, ItemStack.EMPTY);
            }
        }
    }

    private boolean canProcess(PressingRecipe recipe) {
        ItemStack inputStack = inventory.getStackInSlot(INPUT_SLOT);
        ItemStack moldStack = inventory.getStackInSlot(MOLD_SLOT);
        ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);
        ItemStack recipeResult = recipe.getResult();

        if (inputStack.isEmpty() || moldStack.isEmpty()) {
            return false;
        }
        if (!recipe.matches(inputStack, moldStack)) {
            return false;
        }
        if (!HeatCapability.has(inputStack) || HeatCapability.getTemperature(inputStack) < recipe.getMinTemperature()) {
            return false;
        }
        if (outputStack.isEmpty()) {
            return true;
        }
        if (!ItemStack.isSameItemSameTags(outputStack, recipeResult)) {
            return false;
        }

        return outputStack.getCount() + recipeResult.getCount() <= outputStack.getMaxStackSize();
    }

    private void craft(PressingRecipe recipe) {
        ItemStack inputStack = inventory.getStackInSlot(INPUT_SLOT);
        ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);
        ItemStack recipeResult = recipe.getResult();

        inputStack.shrink(1);
        if (outputStack.isEmpty()) {
            inventory.setStackInSlot(OUTPUT_SLOT, recipeResult);
        } else {
            outputStack.grow(recipeResult.getCount());
            inventory.setStackInSlot(OUTPUT_SLOT, outputStack);
        }

        setChanged();
        TFCGlassWorkshop.LOGGER.debug("Glass press completed recipe {}", recipe.getId());
    }

    @Override
    public void onLoad() {
        super.onLoad();
        inventoryCapability = LazyOptional.of(() -> inventory);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inventoryCapability.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        inventoryCapability = LazyOptional.of(() -> inventory);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
            return inventoryCapability.cast();
        }

        return super.getCapability(capability, side);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.putInt("Progress", progress);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("Inventory"));
        progress = tag.getInt("Progress");
    }
}
