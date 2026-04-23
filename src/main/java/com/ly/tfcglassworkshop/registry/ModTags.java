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

        private Items() {
        }
    }
}
