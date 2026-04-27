package com.ly.tfcglassworkshop.registry;

import com.ly.tfcglassworkshop.TFCGlassWorkshop;
import com.ly.tfcglassworkshop.item.CeramicMoldItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, TFCGlassWorkshop.MOD_ID);

    public static final DeferredHolder<Item, Item> GLASS_PRESS = ITEMS.register("glass_press",
            () -> new BlockItem(ModBlocks.GLASS_PRESS.get(), new Item.Properties().stacksTo(32)));
    public static final DeferredHolder<Item, Item> PLATE_MOLD = registerMold("plate_mold");
    public static final DeferredHolder<Item, Item> BLOCK_MOLD = registerMold("block_mold");
    public static final DeferredHolder<Item, Item> BOTTLE_MOLD = registerMold("bottle_mold");
    public static final DeferredHolder<Item, Item> JAR_MOLD = registerMold("jar_mold");
    public static final DeferredHolder<Item, Item> LAMP_GLASS_MOLD = registerMold("lamp_glass_mold");
    public static final DeferredHolder<Item, Item> LENS_MOLD = registerMold("lens_mold");
    public static final DeferredHolder<Item, Item> STEEL_PLATE_MOLD = registerMold("steel_plate_mold");
    public static final DeferredHolder<Item, Item> STEEL_BLOCK_MOLD = registerMold("steel_block_mold");
    public static final DeferredHolder<Item, Item> STEEL_BOTTLE_MOLD = registerMold("steel_bottle_mold");
    public static final DeferredHolder<Item, Item> STEEL_JAR_MOLD = registerMold("steel_jar_mold");
    public static final DeferredHolder<Item, Item> STEEL_LAMP_GLASS_MOLD = registerMold("steel_lamp_glass_mold");
    public static final DeferredHolder<Item, Item> STEEL_LENS_MOLD = registerMold("steel_lens_mold");
    public static final DeferredHolder<Item, Item> UNFIRED_PLATE_MOLD = registerUnfiredMold("unfired_plate_mold");
    public static final DeferredHolder<Item, Item> UNFIRED_BLOCK_MOLD = registerUnfiredMold("unfired_block_mold");
    public static final DeferredHolder<Item, Item> UNFIRED_BOTTLE_MOLD = registerUnfiredMold("unfired_bottle_mold");
    public static final DeferredHolder<Item, Item> UNFIRED_JAR_MOLD = registerUnfiredMold("unfired_jar_mold");
    public static final DeferredHolder<Item, Item> UNFIRED_LAMP_GLASS_MOLD = registerUnfiredMold("unfired_lamp_glass_mold");
    public static final DeferredHolder<Item, Item> UNFIRED_LENS_MOLD = registerUnfiredMold("unfired_lens_mold");
    public static final DeferredHolder<Item, Item> CERAMIC_PLATE_MOLD = registerCeramicMold("ceramic_plate_mold");
    public static final DeferredHolder<Item, Item> CERAMIC_BLOCK_MOLD = registerCeramicMold("ceramic_block_mold");
    public static final DeferredHolder<Item, Item> CERAMIC_BOTTLE_MOLD = registerCeramicMold("ceramic_bottle_mold");
    public static final DeferredHolder<Item, Item> CERAMIC_JAR_MOLD = registerCeramicMold("ceramic_jar_mold");
    public static final DeferredHolder<Item, Item> CERAMIC_LAMP_GLASS_MOLD = registerCeramicMold("ceramic_lamp_glass_mold");
    public static final DeferredHolder<Item, Item> CERAMIC_LENS_MOLD = registerCeramicMold("ceramic_lens_mold");

    private ModItems() {
    }

    private static DeferredHolder<Item, Item> registerMold(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static DeferredHolder<Item, Item> registerUnfiredMold(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static DeferredHolder<Item, Item> registerCeramicMold(String name) {
        return ITEMS.register(name, CeramicMoldItem::new);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
