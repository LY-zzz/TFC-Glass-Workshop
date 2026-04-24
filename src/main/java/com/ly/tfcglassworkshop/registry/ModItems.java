package com.ly.tfcglassworkshop.registry;

import com.ly.tfcglassworkshop.TFCGlassWorkshop;
import com.ly.tfcglassworkshop.item.CeramicMoldItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TFCGlassWorkshop.MOD_ID);

    public static final RegistryObject<Item> GLASS_PRESS = ITEMS.register("glass_press",
            () -> new BlockItem(ModBlocks.GLASS_PRESS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLATE_MOLD = registerMold("plate_mold");
    public static final RegistryObject<Item> BLOCK_MOLD = registerMold("block_mold");
    public static final RegistryObject<Item> BOTTLE_MOLD = registerMold("bottle_mold");
    public static final RegistryObject<Item> JAR_MOLD = registerMold("jar_mold");
    public static final RegistryObject<Item> LAMP_GLASS_MOLD = registerMold("lamp_glass_mold");
    public static final RegistryObject<Item> LENS_MOLD = registerMold("lens_mold");
    public static final RegistryObject<Item> STEEL_PLATE_MOLD = registerMold("steel_plate_mold");
    public static final RegistryObject<Item> STEEL_BLOCK_MOLD = registerMold("steel_block_mold");
    public static final RegistryObject<Item> STEEL_BOTTLE_MOLD = registerMold("steel_bottle_mold");
    public static final RegistryObject<Item> STEEL_JAR_MOLD = registerMold("steel_jar_mold");
    public static final RegistryObject<Item> STEEL_LAMP_GLASS_MOLD = registerMold("steel_lamp_glass_mold");
    public static final RegistryObject<Item> STEEL_LENS_MOLD = registerMold("steel_lens_mold");
    public static final RegistryObject<Item> CERAMIC_PLATE_MOLD = registerCeramicMold("ceramic_plate_mold");
    public static final RegistryObject<Item> CERAMIC_BLOCK_MOLD = registerCeramicMold("ceramic_block_mold");
    public static final RegistryObject<Item> CERAMIC_BOTTLE_MOLD = registerCeramicMold("ceramic_bottle_mold");
    public static final RegistryObject<Item> CERAMIC_JAR_MOLD = registerCeramicMold("ceramic_jar_mold");
    public static final RegistryObject<Item> CERAMIC_LAMP_GLASS_MOLD = registerCeramicMold("ceramic_lamp_glass_mold");
    public static final RegistryObject<Item> CERAMIC_LENS_MOLD = registerCeramicMold("ceramic_lens_mold");

    private ModItems() {
    }

    private static RegistryObject<Item> registerMold(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static RegistryObject<Item> registerCeramicMold(String name) {
        return ITEMS.register(name, CeramicMoldItem::new);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
