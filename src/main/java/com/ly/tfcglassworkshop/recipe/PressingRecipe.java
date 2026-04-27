package com.ly.tfcglassworkshop.recipe;

import com.ly.tfcglassworkshop.blockentity.GlassPressBlockEntity;
import com.ly.tfcglassworkshop.registry.ModRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.common.component.glass.GlassOperation;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PressingRecipe implements Recipe<PressingRecipe.Input> {
    public static final int MAX_POWDER_OPERATIONS = 3;

    private static final Codec<ResourceLocation> OPERATION_ID_CODEC = Codec.STRING.xmap(PressingRecipe::operationId, ResourceLocation::toString);

    private final Ingredient input;
    private final Ingredient mold;
    private final List<ResourceLocation> powderOperations;
    private final float minTemperature;
    private final ItemStack result;

    public PressingRecipe(Ingredient input, Ingredient mold, List<ResourceLocation> powderOperations, float minTemperature, ItemStack result) {
        if (powderOperations.size() > MAX_POWDER_OPERATIONS) {
            throw new IllegalArgumentException("Pressing recipe cannot require more than " + MAX_POWDER_OPERATIONS + " powders");
        }
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
        Map<ResourceLocation, Integer> required = countOperations(powderOperations);
        Map<ResourceLocation, Integer> present = new HashMap<>();

        for (ItemStack stack : powderStacks) {
            if (stack.isEmpty()) {
                continue;
            }

            GlassOperation operation = GlassOperation.getByPowder(stack);
            ResourceLocation operationId = operation == null ? null : GlassOperation.REGISTRY.getKey(operation);
            if (operationId == null) {
                return false;
            }
            present.merge(operationId, 1, Integer::sum);
        }

        return present.equals(required);
    }

    @Override
    public boolean matches(Input recipeInput, Level level) {
        return matches(recipeInput.input(), recipeInput.mold(), recipeInput.powders());
    }

    @Override
    public ItemStack assemble(Input recipeInput, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
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

    public List<ResourceLocation> getPowderOperations() {
        return powderOperations;
    }

    public NonNullList<Ingredient> getPowderIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (ResourceLocation operationId : powderOperations) {
            ingredients.add(getPowderIngredient(operationId));
        }
        return ingredients;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public ItemStack getResult() {
        return result.copy();
    }

    private static Map<ResourceLocation, Integer> countOperations(List<ResourceLocation> operations) {
        Map<ResourceLocation, Integer> counts = new HashMap<>();
        for (ResourceLocation operation : operations) {
            counts.merge(operation, 1, Integer::sum);
        }
        return counts;
    }

    private static Ingredient getPowderIngredient(ResourceLocation operationId) {
        List<ItemStack> stacks = new ArrayList<>();
        for (Map.Entry<Item, GlassOperation> entry : GlassOperation.POWDERS.get().entrySet()) {
            if (operationId.equals(GlassOperation.REGISTRY.getKey(entry.getValue()))) {
                stacks.add(new ItemStack(entry.getKey()));
            }
        }
        return stacks.isEmpty() ? Ingredient.EMPTY : Ingredient.of(stacks.toArray(ItemStack[]::new));
    }

    private static ResourceLocation operationId(String name) {
        return name.indexOf(':') >= 0 ? ResourceLocation.parse(name) : ResourceLocation.fromNamespaceAndPath("tfc", name);
    }

    public record Input(ItemStack input, ItemStack mold, List<ItemStack> powders) implements RecipeInput {
        public Input {
            powders = List.copyOf(powders);
        }

        @Override
        public ItemStack getItem(int index) {
            if (index == GlassPressBlockEntity.INPUT_SLOT) {
                return input;
            }
            if (index == GlassPressBlockEntity.MOLD_SLOT) {
                return mold;
            }
            int powderIndex = index - GlassPressBlockEntity.FIRST_POWDER_SLOT;
            if (powderIndex >= 0 && powderIndex < powders.size()) {
                return powders.get(powderIndex);
            }
            return ItemStack.EMPTY;
        }

        @Override
        public int size() {
            return GlassPressBlockEntity.SLOT_COUNT;
        }
    }

    public static class Serializer implements RecipeSerializer<PressingRecipe> {
        private static final MapCodec<PressingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(PressingRecipe::getInput),
                Ingredient.CODEC_NONEMPTY.fieldOf("mold").forGetter(PressingRecipe::getMold),
                OPERATION_ID_CODEC.listOf().optionalFieldOf("powders", List.of()).forGetter(PressingRecipe::getPowderOperations),
                Codec.FLOAT.fieldOf("min_temp").forGetter(PressingRecipe::getMinTemperature),
                ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
        ).apply(instance, PressingRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, PressingRecipe> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

        @Override
        public MapCodec<PressingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, PressingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
