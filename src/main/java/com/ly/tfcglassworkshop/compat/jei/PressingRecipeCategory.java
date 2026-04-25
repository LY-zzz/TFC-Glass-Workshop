package com.ly.tfcglassworkshop.compat.jei;

import com.ly.tfcglassworkshop.recipe.PressingRecipe;
import com.ly.tfcglassworkshop.registry.ModBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public final class PressingRecipeCategory implements IRecipeCategory<PressingRecipe> {
    private static final int WIDTH = 128;
    private static final int HEIGHT = 34;

    private final IDrawable icon;
    private final IDrawableStatic arrow;

    public PressingRecipeCategory(IGuiHelper guiHelper) {
        icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.GLASS_PRESS.get()));
        arrow = guiHelper.getRecipeArrow();
    }

    @Override
    public mezz.jei.api.recipe.RecipeType<PressingRecipe> getRecipeType() {
        return GlassWorkshopJeiPlugin.PRESSING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.tfc_glass_workshop.pressing");
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PressingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 7, 8)
                .setStandardSlotBackground()
                .addIngredients(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.INPUT, 35, 8)
                .setStandardSlotBackground()
                .addIngredients(recipe.getMold());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 103, 8)
                .setOutputSlotBackground()
                .addItemStack(recipe.getResult());
    }

    @Override
    public void draw(PressingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, 69, 8);
    }
}
