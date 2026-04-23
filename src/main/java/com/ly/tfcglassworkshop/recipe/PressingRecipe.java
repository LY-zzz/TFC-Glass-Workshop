package com.ly.tfcglassworkshop.recipe;

import com.google.gson.JsonObject;
import com.ly.tfcglassworkshop.blockentity.GlassPressBlockEntity;
import com.ly.tfcglassworkshop.registry.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class PressingRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final Ingredient input;
    private final Ingredient mold;
    private final float minTemperature;
    private final ItemStack result;

    public PressingRecipe(ResourceLocation id, Ingredient input, Ingredient mold, float minTemperature, ItemStack result) {
        this.id = id;
        this.input = input;
        this.mold = mold;
        this.minTemperature = minTemperature;
        this.result = result;
    }

    public boolean matches(ItemStack inputStack, ItemStack moldStack) {
        return input.test(inputStack) && mold.test(moldStack);
    }

    @Override
    public boolean matches(Container container, Level level) {
        return matches(container.getItem(GlassPressBlockEntity.INPUT_SLOT), container.getItem(GlassPressBlockEntity.MOLD_SLOT));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(input);
        ingredients.add(mold);
        return ingredients;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.PRESSING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.PRESSING_TYPE.get();
    }

    public Ingredient getInput() {
        return input;
    }

    public Ingredient getMold() {
        return mold;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public ItemStack getResult() {
        return result.copy();
    }

    public static class Serializer implements RecipeSerializer<PressingRecipe> {
        @Override
        public PressingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
            Ingredient mold = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "mold"));
            float minTemperature = GsonHelper.getAsFloat(json, "min_temp");
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new PressingRecipe(recipeId, input, mold, minTemperature, result);
        }

        @Override
        public PressingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            Ingredient mold = Ingredient.fromNetwork(buffer);
            float minTemperature = buffer.readFloat();
            ItemStack result = buffer.readItem();
            return new PressingRecipe(recipeId, input, mold, minTemperature, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, PressingRecipe recipe) {
            recipe.input.toNetwork(buffer);
            recipe.mold.toNetwork(buffer);
            buffer.writeFloat(recipe.minTemperature);
            buffer.writeItem(recipe.result);
        }
    }
}
