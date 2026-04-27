package com.ly.tfcglassworkshop.registry;

import com.ly.tfcglassworkshop.TFCGlassWorkshop;
import com.ly.tfcglassworkshop.recipe.PressingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, TFCGlassWorkshop.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TFCGlassWorkshop.MOD_ID);

    public static final RegistryObject<RecipeType<PressingRecipe>> PRESSING_TYPE = RECIPE_TYPES.register("pressing",
            () -> new RecipeType<PressingRecipe>() {
                @Override
                public String toString() {
                    return TFCGlassWorkshop.MOD_ID + ":pressing";
                }
            });

    public static final RegistryObject<RecipeSerializer<PressingRecipe>> PRESSING_SERIALIZER = RECIPE_SERIALIZERS.register("pressing", PressingRecipe.Serializer::new);

    private ModRecipeTypes() {
    }

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
