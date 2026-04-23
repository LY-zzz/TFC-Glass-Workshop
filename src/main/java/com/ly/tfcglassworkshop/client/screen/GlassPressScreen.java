package com.ly.tfcglassworkshop.client.screen;

import com.ly.tfcglassworkshop.menu.GlassPressMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GlassPressScreen extends AbstractContainerScreen<GlassPressMenu> {
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
        guiGraphics.hLine(left + 100, left + 111, top + 43, 0xFF404040);
    }

    private static void renderSlotBackground(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.fill(x, y, x + 18, y + 18, 0xFF373737);
        guiGraphics.fill(x + 1, y + 1, x + 17, y + 17, 0xFFE0E0E0);
    }
}
