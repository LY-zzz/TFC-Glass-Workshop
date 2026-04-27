package com.ly.tfcglassworkshop.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.ly.tfcglassworkshop.blockentity.GlassPressBlockEntity;
import com.ly.tfcglassworkshop.registry.ModRecipeTypes;
import net.dries007.tfc.common.component.glass.GlassOperation;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PressingRecipe implements Recipe<Container> {
    public static final int MAX_POWDER_OPERATIONS = 3;

    private final ResourceLocation id;
    private final Ingredient input;
    private final Ingredient mold;
    private final List<GlassOperation> powderOperations;
    private final float minTemperature;
    private final ItemStack result;

    public PressingRecipe(ResourceLocation id, Ingredient input, Ingredient mold, List<GlassOperation> powderOperations, float minTemperature, ItemStack result) {
        this.id = id;
        this.input = input;
        this.mold = mold;
        this.powderOperations = List.copyOf(powderOperations);
        this.minTemperature = minTemperature;
        this.result = result;
    }

    public boolean matches(ItemStack inputStack, ItemStack moldStack, List<ItemStack> powderStacks) {
        return input.test(inputStack) && mold.test(moldStack) && matchesPowders(powderStacks);
    }

    private boolean matchesPowders(List<ItemStack> powderStacks) {
        Map<GlassOperation, Integer> required = countOperations(powderOperations);
        Map<GlassOperation, Integer> present = new EnumMap<>(GlassOperation.class);

        for (ItemStack stack : powderStacks) {
            if (stack.isEmpty()) {
                continue;
            }

            GlassOperation operation = GlassOperation.getByPowder(stack);
            if (operation == null) {
                return false;
            }
            present.merge(operation, 1, Integer::sum);
        }

        return present.equals(required);
    }

    @Override
    public boolean matches(Container container, Level level) {
        return matches(
                container.getItem(GlassPressBlockEntity.INPUT_SLOT),
                container.getItem(GlassPressBlockEntity.MOLD_SLOT),
                GlassPressBlockEntity.getPowderStacks(container)
        );
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
        ingredients.addAll(getPowderIngredients());
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

    public List<GlassOperation> getPowderOperations() {
        return powderOperations;
    }

    public NonNullList<Ingredient> getPowderIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (GlassOperation operation : powderOperations) {
            ingredients.add(getPowderIngredient(operation));
        }
        return ingredients;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public ItemStack getResult() {
        return result.copy();
    }

    private static Map<GlassOperation, Integer> countOperations(List<GlassOperation> operations) {
        Map<GlassOperation, Integer> counts = new EnumMap<>(GlassOperation.class);
        for (GlassOperation operation : operations) {
            counts.merge(operation, 1, Integer::sum);
        }
        return counts;
    }

    private static Ingredient getPowderIngredient(GlassOperation operation) {
        List<ItemStack> stacks = new ArrayList<>();
        for (Map.Entry<Item, GlassOperation> entry : GlassOperation.POWDERS.get().entrySet()) {
            if (entry.getValue() == operation) {
                stacks.add(new ItemStack(entry.getKey()));
            }
        }
        return stacks.isEmpty() ? Ingredient.EMPTY : Ingredient.of(stacks.toArray(ItemStack[]::new));
    }

    private static List<GlassOperation> readPowderOperations(JsonObject json) {
        if (!json.has("powders")) {
            return List.of();
        }

        JsonArray array = GsonHelper.getAsJsonArray(json, "powders");
        if (array.size() > MAX_POWDER_OPERATIONS) {
            throw new IllegalArgumentException("Pressing recipe cannot require more than " + MAX_POWDER_OPERATIONS + " powders");
        }

        List<GlassOperation> operations = new ArrayList<>();
        for (JsonElement element : array) {
            String name = GsonHelper.convertToString(element, "powder");
            operations.add(GlassOperation.valueOf(name.toUpperCase(Locale.ROOT)));
        }
        return operations;
    }

    public static class Serializer implements RecipeSerializer<PressingRecipe> {
        @Override
        public PressingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
            Ingredient mold = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "mold"));
            List<GlassOperation> powderOperations = readPowderOperations(json);
            float minTemperature = GsonHelper.getAsFloat(json, "min_temp");
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new PressingRecipe(recipeId, input, mold, powderOperations, minTemperature, result);
        }

        @Override
        public PressingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            Ingredient mold = Ingredient.fromNetwork(buffer);
            int powderCount = buffer.readVarInt();
            List<GlassOperation> powderOperations = new ArrayList<>(powderCount);
            for (int i = 0; i < powderCount; i++) {
                powderOperations.add(buffer.readEnum(GlassOperation.class));
            }
            float minTemperature = buffer.readFloat();
            ItemStack result = buffer.readItem();
            return new PressingRecipe(recipeId, input, mold, powderOperations, minTemperature, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, PressingRecipe recipe) {
            recipe.input.toNetwork(buffer);
            recipe.mold.toNetwork(buffer);
            buffer.writeVarInt(recipe.powderOperations.size());
            for (GlassOperation operation : recipe.powderOperations) {
                buffer.writeEnum(operation);
            }
            buffer.writeFloat(recipe.minTemperature);
            buffer.writeItem(recipe.result);
        }
    }
}
