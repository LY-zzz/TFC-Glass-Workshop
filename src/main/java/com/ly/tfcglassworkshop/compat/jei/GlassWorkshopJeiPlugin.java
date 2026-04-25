package com.ly.tfcglassworkshop.compat.jei;

import com.ly.tfcglassworkshop.TFCGlassWorkshop;
import com.ly.tfcglassworkshop.client.screen.GlassPressScreen;
import com.ly.tfcglassworkshop.recipe.PressingRecipe;
import com.ly.tfcglassworkshop.registry.ModBlocks;
import com.ly.tfcglassworkshop.registry.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@JeiPlugin
public final class GlassWorkshopJeiPlugin implements IModPlugin {
    public static final RecipeType<PressingRecipe> PRESSING = new RecipeType<>(
            ResourceLocation.fromNamespaceAndPath(TFCGlassWorkshop.MOD_ID, "pressing"),
            PressingRecipe.class
    );

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(TFCGlassWorkshop.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new PressingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return;
        }

        List<PressingRecipe> recipes = minecraft.level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.PRESSING_TYPE.get());
        registration.addRecipes(PRESSING, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocks.GLASS_PRESS.get(), PRESSING);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(GlassPressScreen.class, 99, 36, 14, 14, PRESSING);
    }
}
