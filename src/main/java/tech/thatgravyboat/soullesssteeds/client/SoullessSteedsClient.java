package tech.thatgravyboat.soullesssteeds.client;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tech.thatgravyboat.soullesssteeds.SoullessSteeds;

public class SoullessSteedsClient {

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(SoullessSteedsClient::registerRenderers);
    }

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SoullessSteeds.WITHER_SKELETON_HORSE.get(), WitherSkeletonHorseRenderer::new);
    }
}
