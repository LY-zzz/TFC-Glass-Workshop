package com.ly.tfcglassworkshop;

import com.ly.tfcglassworkshop.blockentity.GlassPressBlockEntity;
import com.ly.tfcglassworkshop.registry.ModBlocks;
import com.ly.tfcglassworkshop.registry.ModBlockEntities;
import com.ly.tfcglassworkshop.registry.ModCreativeTabs;
import com.ly.tfcglassworkshop.registry.ModItems;
import com.ly.tfcglassworkshop.registry.ModMenuTypes;
import com.ly.tfcglassworkshop.registry.ModRecipeTypes;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

@Mod(TFCGlassWorkshop.MOD_ID)
public class TFCGlassWorkshop {
    public static final String MOD_ID = "tfc_glass_workshop";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TFCGlassWorkshop(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCapabilities);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipeTypes.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Initializing TerraFirmaCraft: Glass Workshop");
    }

    private void registerCapabilities(final RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.GLASS_PRESS.get(), GlassPressBlockEntity::getItemHandler);
    }
}
