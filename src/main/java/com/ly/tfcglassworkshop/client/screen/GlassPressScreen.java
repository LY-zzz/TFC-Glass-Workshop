package com.ly.tfcglassworkshop.client.screen;

import com.ly.tfcglassworkshop.menu.GlassPressMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;

public class GlassPressScreen extends AbstractContainerScreen<GlassPressMenu> {
    private static final int PROGRESS_BAR_WIDTH = 12;
    private static final int PROGRESS_BAR_HEIGHT = 8;

    public GlassPressScreen(GlassPressMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 176;
        imageHeight = 166;
        inventoryLabelY = 72;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int left = leftPos;
        int top = topPos;

        guiGraphics.fill(left, top, left + imageWidth, top + imageHeight, 0xFFC6C6C6);
        guiGraphics.fill(left + 3, top + 3, left + imageWidth - 3, top + imageHeight - 3, 0xFF8B8B8B);
        guiGraphics.fill(left + 7, top + 7, left + imageWidth - 7, top + imageHeight - 7, 0xFFC6C6C6);

        renderSlotBackground(guiGraphics, left + 43, top + 34);
        renderSlotBackground(guiGraphics, left + 79, top + 34);
        renderSlotBackground(guiGraphics, left + 115, top + 34);

        guiGraphics.hLine(left + 64, left + 75, top + 43, 0xFF404040);
        renderProgressBar(guiGraphics, left + 100, top + 39);
    }

    private static void renderSlotBackground(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.fill(x, y, x + 18, y + 18, 0xFF373737);
        guiGraphics.fill(x + 1, y + 1, x + 17, y + 17, 0xFFE0E0E0);
    }

    private void renderProgressBar(GuiGraphics guiGraphics, int x, int y) {
        int progressWidth = menu.getScaledProgress(PROGRESS_BAR_WIDTH);
        guiGraphics.fill(x, y, x + PROGRESS_BAR_WIDTH, y + PROGRESS_BAR_HEIGHT, 0xFF3B3B3B);
        guiGraphics.fill(x + 1, y + 1, x + PROGRESS_BAR_WIDTH - 1, y + PROGRESS_BAR_HEIGHT - 1, 0xFFB0B0B0);

        if (progressWidth > 0) {
            guiGraphics.fill(x + 1, y + 1, x + 1 + progressWidth, y + PROGRESS_BAR_HEIGHT - 1, 0xFFD88A32);
        }
    }
}
