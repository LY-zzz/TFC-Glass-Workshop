package com.ly.tfcglassworkshop.blockentity;

import com.ly.tfcglassworkshop.TFCGlassWorkshop;
import com.ly.tfcglassworkshop.item.CeramicMoldItem;
import com.ly.tfcglassworkshop.menu.GlassPressMenu;
import com.ly.tfcglassworkshop.recipe.PressingRecipe;
import com.ly.tfcglassworkshop.registry.ModBlockEntities;
import net.dries007.tfc.common.capabilities.glass.GlassOperation;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.dries007.tfc.common.blockentities.rotation.RotationSinkBlockEntity;
import net.dries007.tfc.util.rotation.NetworkAction;
import net.dries007.tfc.util.rotation.Node;
import net.dries007.tfc.util.rotation.Rotation;
import net.dries007.tfc.util.rotation.SinkNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ly.tfcglassworkshop.registry.ModRecipeTypes;
import com.ly.tfcglassworkshop.registry.ModTags;

public class GlassPressBlockEntity extends BlockEntity implements MenuProvider, RotationSinkBlockEntity {
    private static final float CERAMIC_MOLD_BREAK_CHANCE = 0.1F;
    private static final float REFERENCE_ROTATION_SPEED = Mth.PI / 40F;
    private static final ResourceLocation TFC_GLASS_BOTTLES_TAG = ResourceLocation.fromNamespaceAndPath("tfc", "glass_bottles");
    public static final int PRESS_TIME = 60;
    public static final int DATA_PROGRESS = 0;
    public static final int DATA_PRESS_TIME = 1;
    public static final int DATA_INPUT_TEMPERATURE = 2;
    public static final int DATA_REQUIRED_TEMPERATURE = 3;
    public static final int DATA_COUNT = 4;
    public static final int INPUT_SLOT = 0;
    public static final int MOLD_SLOT = 1;
    public static final int FIRST_POWDER_SLOT = 2;
    public static final int POWDER_SLOT_COUNT = 3;
    public static final int OUTPUT_SLOT = FIRST_POWDER_SLOT + POWDER_SLOT_COUNT;
    public static final int SLOT_COUNT = OUTPUT_SLOT + 1;

    private final ItemStackHandler inventory = new ItemStackHandler(SLOT_COUNT) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (slot == OUTPUT_SLOT) {
                return false;
            }
            if (slot == MOLD_SLOT) {
                return stack.is(ModTags.Items.PRESS_MOLDS);
            }
            if (isPowderSlot(slot)) {
                return isGlassworkingPowder(stack);
            }
            return !stack.is(ModTags.Items.PRESS_MOLDS) && !isGlassworkingPowder(stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final Node rotationNode;
    private LazyOptional<IItemHandler> inventoryCapability = LazyOptional.of(() -> inventory);
    private float progress;
    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case DATA_PROGRESS -> Mth.floor(progress);
                case DATA_PRESS_TIME -> PRESS_TIME;
                case DATA_INPUT_TEMPERATURE -> (int) getInputTemperature();
                case DATA_REQUIRED_TEMPERATURE -> getRequiredTemperature();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == DATA_PROGRESS) {
                progress = value;
            }
        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }
    };

    public GlassPressBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GLASS_PRESS.get(), pos, state);
        rotationNode = new SinkNode(pos, Direction.UP) {};
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public Optional<PressingRecipe> getMatchingRecipe() {
        if (level == null) {
            return Optional.empty();
        }

        SimpleContainer container = new SimpleContainer(SLOT_COUNT);
        container.setItem(INPUT_SLOT, inventory.getStackInSlot(INPUT_SLOT));
        container.setItem(MOLD_SLOT, inventory.getStackInSlot(MOLD_SLOT));
        for (int slot = FIRST_POWDER_SLOT; slot < OUTPUT_SLOT; slot++) {
            container.setItem(slot, inventory.getStackInSlot(slot));
        }
        return level.getRecipeManager().getRecipeFor(ModRecipeTypes.PRESSING_TYPE.get(), container, level);
    }

    public static boolean isPowderSlot(int slot) {
        return slot >= FIRST_POWDER_SLOT && slot < OUTPUT_SLOT;
    }

    public static boolean isGlassworkingPowder(ItemStack stack) {
        return !stack.isEmpty() && GlassOperation.getByPowder(stack) != null;
    }

    public static List<ItemStack> getPowderStacks(Container container) {
        List<ItemStack> stacks = new ArrayList<>(POWDER_SLOT_COUNT);
        for (int slot = FIRST_POWDER_SLOT; slot < OUTPUT_SLOT; slot++) {
            stacks.add(slot < container.getContainerSize() ? container.getItem(slot) : ItemStack.EMPTY);
        }
        return stacks;
    }

    public float getInputTemperature() {
        return HeatCapability.getTemperature(inventory.getStackInSlot(INPUT_SLOT));
    }

    public int getRequiredTemperature() {
        return getMatchingRecipe()
                .map(recipe -> (int) recipe.getMinTemperature())
                .orElse(0);
    }

    public int getProgress() {
        return Mth.floor(progress);
    }

    public ContainerData getContainerData() {
        return containerData;
    }

    @Override
    public Node getRotationNode() {
        return rotationNode;
    }

    public float getRotationSpeed() {
        Rotation rotation = rotationNode.rotation();
        return rotation == null ? 0F : rotation.positiveSpeed();
    }

    public boolean hasMechanicalPower() {
        return getRotationSpeed() > 0F;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GlassPressBlockEntity blockEntity) {
        Optional<PressingRecipe> recipe = blockEntity.getMatchingRecipe();
        if (recipe.isPresent() && blockEntity.canProcess(recipe.get()) && blockEntity.hasMechanicalPower()) {
            blockEntity.progress += blockEntity.getProgressIncrement();
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
        if (!recipe.matches(inputStack, moldStack, getPowderStacks())) {
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

    private float getProgressIncrement() {
        return getRotationSpeed() / REFERENCE_ROTATION_SPEED;
    }

    private void craft(PressingRecipe recipe) {
        ItemStack inputStack = inventory.getStackInSlot(INPUT_SLOT);
        ItemStack moldStack = inventory.getStackInSlot(MOLD_SLOT);
        ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);
        ItemStack recipeResult = prepareRecipeResult(recipe.getResult());

        inputStack.shrink(1);
        consumePowders();
        damageMold(moldStack);
        if (outputStack.isEmpty()) {
            inventory.setStackInSlot(OUTPUT_SLOT, recipeResult);
        } else {
            outputStack.grow(recipeResult.getCount());
            inventory.setStackInSlot(OUTPUT_SLOT, outputStack);
        }

        setChanged();
        TFCGlassWorkshop.LOGGER.debug("Glass press completed recipe {}", recipe.getId());
    }

    private ItemStack prepareRecipeResult(ItemStack result) {
        if (!result.is(ItemTags.create(TFC_GLASS_BOTTLES_TAG))) {
            return result;
        }

        result.removeTagKey("fluid");
        result.removeTagKey("tfc:glass_work_data");
        if (result.getTag() != null && result.getTag().isEmpty()) {
            result.setTag(null);
        }
        return result;
    }

    private List<ItemStack> getPowderStacks() {
        List<ItemStack> stacks = new ArrayList<>(POWDER_SLOT_COUNT);
        for (int slot = FIRST_POWDER_SLOT; slot < OUTPUT_SLOT; slot++) {
            stacks.add(inventory.getStackInSlot(slot));
        }
        return stacks;
    }

    private void consumePowders() {
        for (int slot = FIRST_POWDER_SLOT; slot < OUTPUT_SLOT; slot++) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                stack.shrink(1);
                inventory.setStackInSlot(slot, stack);
            }
        }
    }

    private void damageMold(ItemStack moldStack) {
        if (!(moldStack.getItem() instanceof CeramicMoldItem)) {
            return;
        }

        if (level != null && level.random.nextFloat() < CERAMIC_MOLD_BREAK_CHANCE) {
            inventory.setStackInSlot(MOLD_SLOT, ItemStack.EMPTY);
            level.playSound(null, worldPosition, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        inventoryCapability = LazyOptional.of(() -> inventory);
        if (level != null && !level.isClientSide) {
            performNetworkAction(NetworkAction.ADD);
        }
    }

    @Override
    public void setRemoved() {
        if (level != null && !level.isClientSide) {
            performNetworkAction(NetworkAction.REMOVE);
        }
        super.setRemoved();
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
        tag.putFloat("Progress", progress);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        loadInventory(tag.getCompound("Inventory"));
        progress = tag.getFloat("Progress");
    }

    private void loadInventory(CompoundTag tag) {
        if (tag.getInt("Size") == SLOT_COUNT) {
            inventory.deserializeNBT(tag);
            return;
        }

        ListTag items = tag.getList("Items", Tag.TAG_COMPOUND);
        for (int slot = 0; slot < SLOT_COUNT; slot++) {
            inventory.setStackInSlot(slot, ItemStack.EMPTY);
        }
        for (int i = 0; i < items.size(); i++) {
            CompoundTag itemTag = items.getCompound(i);
            int savedSlot = itemTag.getInt("Slot");
            int targetSlot = savedSlot == 2 ? OUTPUT_SLOT : savedSlot;
            if (targetSlot >= 0 && targetSlot < SLOT_COUNT) {
                inventory.setStackInSlot(targetSlot, ItemStack.of(itemTag));
            }
        }
    }
}
