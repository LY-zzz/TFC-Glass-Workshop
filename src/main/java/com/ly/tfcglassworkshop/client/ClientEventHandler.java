package com.ly.tfcglassworkshop.client;

import com.ly.tfcglassworkshop.TFCGlassWorkshop;
import com.ly.tfcglassworkshop.client.screen.GlassPressScreen;
import com.ly.tfcglassworkshop.registry.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = TFCGlassWorkshop.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEventHandler {
    private ClientEventHandler() {
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.GLASS_PRESS.get(), GlassPressScreen::new);
    }
}
