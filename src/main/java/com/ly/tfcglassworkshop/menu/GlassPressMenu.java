package com.ly.tfcglassworkshop.menu;

import com.ly.tfcglassworkshop.blockentity.GlassPressBlockEntity;
import com.ly.tfcglassworkshop.registry.ModBlocks;
import com.ly.tfcglassworkshop.registry.ModMenuTypes;
import com.ly.tfcglassworkshop.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class GlassPressMenu extends AbstractContainerMenu {
    private static final int MACHINE_SLOT_COUNT = GlassPressBlockEntity.SLOT_COUNT;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = 27;
    private static final int PLAYER_HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_SLOT_COUNT = PLAYER_INVENTORY_SLOT_COUNT + PLAYER_HOTBAR_SLOT_COUNT;
    private static final int PLAYER_INVENTORY_START = MACHINE_SLOT_COUNT;
    private static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + PLAYER_SLOT_COUNT;

    private final GlassPressBlockEntity blockEntity;
    private final ContainerLevelAccess access;
    private final ContainerData data;

    public GlassPressMenu(int containerId, Inventory playerInventory, FriendlyByteBuf data) {
        this(containerId, playerInventory, getBlockEntity(playerInventory, data.readBlockPos()), new SimpleContainerData(GlassPressBlockEntity.DATA_COUNT));
    }

    public GlassPressMenu(int containerId, Inventory playerInventory, GlassPressBlockEntity blockEntity) {
        this(containerId, playerInventory, blockEntity, blockEntity.getContainerData());
    }

    private GlassPressMenu(int containerId, Inventory playerInventory, GlassPressBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.GLASS_PRESS.get(), containerId);
        this.blockEntity = blockEntity;
        this.access = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());
        this.data = data;

        addDataSlots(data);

        addMachineSlots();
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public GlassPressBlockEntity getBlockEntity() {
        return blockEntity;
    }

    public int getProgress() {
        return data.get(GlassPressBlockEntity.DATA_PROGRESS);
    }

    public int getScaledProgress(int pixels) {
        int progress = getProgress();
        int pressTime = data.get(GlassPressBlockEntity.DATA_PRESS_TIME);
        if (progress <= 0 || pressTime <= 0) {
            return 0;
        }
        return progress * pixels / pressTime;
    }

    public int getCurrentTemperature() {
        return data.get(GlassPressBlockEntity.DATA_INPUT_TEMPERATURE);
    }

    public int getRequiredTemperature() {
        return data.get(GlassPressBlockEntity.DATA_REQUIRED_TEMPERATURE);
    }

    public boolean hasRecipe() {
        return getRequiredTemperature() > 0;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.GLASS_PRESS.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack originalStack = stack.copy();

        if (index < MACHINE_SLOT_COUNT) {
            if (!moveItemStackTo(stack, PLAYER_INVENTORY_START, PLAYER_INVENTORY_END, true)) {
                return ItemStack.EMPTY;
            }
        } else if (stack.is(ModTags.Items.PRESS_MOLDS)) {
            if (!moveItemStackTo(stack, GlassPressBlockEntity.MOLD_SLOT, GlassPressBlockEntity.MOLD_SLOT + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!moveItemStackTo(stack, GlassPressBlockEntity.INPUT_SLOT, GlassPressBlockEntity.INPUT_SLOT + 1, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (stack.getCount() == originalStack.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);
        return originalStack;
    }

    private void addMachineSlots() {
        addSlot(new InputSlot(blockEntity, 44, 35));
        addSlot(new MoldSlot(blockEntity, 80, 35));
        addSlot(new OutputSlot(blockEntity, 116, 35));
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
        }
    }

    private static GlassPressBlockEntity getBlockEntity(Inventory playerInventory, BlockPos pos) {
        BlockEntity blockEntity = playerInventory.player.level().getBlockEntity(pos);
        if (blockEntity instanceof GlassPressBlockEntity glassPress) {
            return glassPress;
        }

        throw new IllegalStateException("Expected glass press block entity at " + pos);
    }

    private static class OutputSlot extends SlotItemHandler {
        OutputSlot(GlassPressBlockEntity blockEntity, int x, int y) {
            super(blockEntity.getInventory(), GlassPressBlockEntity.OUTPUT_SLOT, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }
    }

    private static class InputSlot extends SlotItemHandler {
        InputSlot(GlassPressBlockEntity blockEntity, int x, int y) {
            super(blockEntity.getInventory(), GlassPressBlockEntity.INPUT_SLOT, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return !stack.is(ModTags.Items.PRESS_MOLDS);
        }
    }

    private static class MoldSlot extends SlotItemHandler {
        MoldSlot(GlassPressBlockEntity blockEntity, int x, int y) {
            super(blockEntity.getInventory(), GlassPressBlockEntity.MOLD_SLOT, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.is(ModTags.Items.PRESS_MOLDS);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    }
}
