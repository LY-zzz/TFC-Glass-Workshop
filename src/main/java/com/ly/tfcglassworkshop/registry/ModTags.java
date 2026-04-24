package com.ly.tfcglassworkshop.registry;

import com.ly.tfcglassworkshop.TFCGlassWorkshop;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModTags {
    private ModTags() {
    }

    public static final class Items {
        public static final TagKey<Item> PRESS_MOLDS = ItemTags.create(ResourceLocation.fromNamespaceAndPath(TFCGlassWorkshop.MOD_ID, "press_molds"));
        public static final TagKey<Item> PLATE_MOLDS = create("plate_molds");
        public static final TagKey<Item> BLOCK_MOLDS = create("block_molds");
        public static final TagKey<Item> BOTTLE_MOLDS = create("bottle_molds");
        public static final TagKey<Item> JAR_MOLDS = create("jar_molds");
        public static final TagKey<Item> LAMP_GLASS_MOLDS = create("lamp_glass_molds");
        public static final TagKey<Item> LENS_MOLDS = create("lens_molds");

        private Items() {
        }

        private static TagKey<Item> create(String path) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(TFCGlassWorkshop.MOD_ID, path));
        }
    }
}
