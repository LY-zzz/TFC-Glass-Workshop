package com.ly.tfcglassworkshop.registry;

import com.ly.tfcglassworkshop.TFCGlassWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TFCGlassWorkshop.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tfc_glass_workshop.main"))
            .icon(() -> ModItems.GLASS_PRESS.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.GLASS_PRESS.get());
                output.accept(ModItems.PLATE_MOLD.get());
                output.accept(ModItems.BLOCK_MOLD.get());
                output.accept(ModItems.BOTTLE_MOLD.get());
                output.accept(ModItems.JAR_MOLD.get());
                output.accept(ModItems.LAMP_GLASS_MOLD.get());
                output.accept(ModItems.LENS_MOLD.get());
                output.accept(ModItems.STEEL_PLATE_MOLD.get());
                output.accept(ModItems.STEEL_BLOCK_MOLD.get());
                output.accept(ModItems.STEEL_BOTTLE_MOLD.get());
                output.accept(ModItems.STEEL_JAR_MOLD.get());
                output.accept(ModItems.STEEL_LAMP_GLASS_MOLD.get());
                output.accept(ModItems.STEEL_LENS_MOLD.get());
                output.accept(ModItems.UNFIRED_PLATE_MOLD.get());
                output.accept(ModItems.UNFIRED_BLOCK_MOLD.get());
                output.accept(ModItems.UNFIRED_BOTTLE_MOLD.get());
                output.accept(ModItems.UNFIRED_JAR_MOLD.get());
                output.accept(ModItems.UNFIRED_LAMP_GLASS_MOLD.get());
                output.accept(ModItems.UNFIRED_LENS_MOLD.get());
                output.accept(ModItems.CERAMIC_PLATE_MOLD.get());
                output.accept(ModItems.CERAMIC_BLOCK_MOLD.get());
                output.accept(ModItems.CERAMIC_BOTTLE_MOLD.get());
                output.accept(ModItems.CERAMIC_JAR_MOLD.get());
                output.accept(ModItems.CERAMIC_LAMP_GLASS_MOLD.get());
                output.accept(ModItems.CERAMIC_LENS_MOLD.get());
            })
            .build());

    private ModCreativeTabs() {
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
