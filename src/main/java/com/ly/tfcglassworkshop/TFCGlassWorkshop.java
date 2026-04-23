package com.ly.tfcglassworkshop;

import com.ly.tfcglassworkshop.registry.ModBlocks;
import com.ly.tfcglassworkshop.registry.ModBlockEntities;
import com.ly.tfcglassworkshop.registry.ModCreativeTabs;
import com.ly.tfcglassworkshop.registry.ModItems;
import com.ly.tfcglassworkshop.registry.ModMenuTypes;
import com.ly.tfcglassworkshop.registry.ModRecipeTypes;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(TFCGlassWorkshop.MOD_ID)
public class TFCGlassWorkshop {
    public static final String MOD_ID = "tfc_glass_workshop";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TFCGlassWorkshop(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipeTypes.register(modEventBus);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Initializing TerraFirmaCraft: Glass Workshop");
    }
}
