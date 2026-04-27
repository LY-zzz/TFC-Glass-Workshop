package com.ly.tfcglassworkshop.client;

import com.ly.tfcglassworkshop.TFCGlassWorkshop;
import com.ly.tfcglassworkshop.client.screen.GlassPressScreen;
import com.ly.tfcglassworkshop.registry.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = TFCGlassWorkshop.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEventHandler {
    private ClientEventHandler() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(ModMenuTypes.GLASS_PRESS.get(), GlassPressScreen::new));
    }
}
