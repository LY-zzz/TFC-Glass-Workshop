package com.ly.tfcglassworkshop.item;

import net.minecraft.world.item.Item;

public class CeramicMoldItem extends Item {
    public static final int DEFAULT_DURABILITY = 32;

    public CeramicMoldItem() {
        super(new Item.Properties().stacksTo(1).durability(DEFAULT_DURABILITY));
    }
}
