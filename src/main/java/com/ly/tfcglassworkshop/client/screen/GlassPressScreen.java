package com.ly.tfcglassworkshop.client.screen;

import com.ly.tfcglassworkshop.TFCGlassWorkshop;
import com.ly.tfcglassworkshop.menu.GlassPressMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GlassPressScreen extends AbstractContainerScreen<GlassPressMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(TFCGlassWorkshop.MOD_ID, "textures/gui/glass_press.png");
    private static final int PROGRESS_BAR_WIDTH = 12;
    private static final int PROGRESS_BAR_HEIGHT = 8;
    private static final int PROGRESS_BAR_TEXTURE_X = 176;
    private static final int PROGRESS_BAR_TEXTURE_Y = 0;

    public GlassPressScreen(GlassPressMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 176;
        imageHeight = 166;
        inventoryLabelY = 72;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int left = leftPos;
        int top = topPos;

        guiGraphics.blit(TEXTURE, left, top, 0, 0, imageWidth, imageHeight);
        renderProgressBar(guiGraphics, left + 100, top + 39);
    }

    private void renderProgressBar(GuiGraphics guiGraphics, int x, int y) {
        int progressWidth = menu.getScaledProgress(PROGRESS_BAR_WIDTH);
        if (progressWidth > 0) {
            guiGraphics.blit(TEXTURE, x, y, PROGRESS_BAR_TEXTURE_X, PROGRESS_BAR_TEXTURE_Y, progressWidth, PROGRESS_BAR_HEIGHT);
        }
    }
}
