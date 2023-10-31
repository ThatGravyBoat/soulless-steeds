package tech.thatgravyboat.soullesssteeds;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.thatgravyboat.soullesssteeds.client.SoullessSteedsClient;
import tech.thatgravyboat.soullesssteeds.common.entities.WitherSkeletonHorse;

@Mod(SoullessSteeds.MODID)
public class SoullessSteeds {

    public static final String MODID = "soulless_steeds";
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<EntityType<WitherSkeletonHorse>> WITHER_SKELETON_HORSE =
            ENTITIES.register("wither_skeleton_horse", () ->
                    EntityType.Builder.of(WitherSkeletonHorse::new, MobCategory.CREATURE)
                            .sized(1.3964844F, 1.6F)
                            .clientTrackingRange(10)
                            .build("wither_skeleton_horse")
            );
    public static final RegistryObject<ForgeSpawnEggItem> WITHER_SKELETON_HORSE_SPAWN_EGG =
            ITEMS.register("wither_skeleton_horse_spawn_egg", () -> new ForgeSpawnEggItem(WITHER_SKELETON_HORSE, 1315860, 4672845, new Item.Properties()));

    public SoullessSteeds() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(SoullessSteeds::onMobHitByLightning);
        ENTITIES.register(bus);
        ITEMS.register(bus);
        bus.addListener(SoullessSteeds::addAttributesEvent);
        bus.addListener(SoullessSteeds::addCreativeTabEvent);

        if (FMLEnvironment.dist.isClient()) {
            SoullessSteedsClient.init();
        }
    }

    public static void addCreativeTabEvent(BuildCreativeModeTabContentsEvent event) {
        if (CreativeModeTabs.SPAWN_EGGS.equals(event.getTabKey())) {
            event.accept(WITHER_SKELETON_HORSE_SPAWN_EGG);
        }
    }

    public static void addAttributesEvent(EntityAttributeCreationEvent event) {
        event.put(WITHER_SKELETON_HORSE.get(), WitherSkeletonHorse.createAttributes().build());
    }

    public static void onMobHitByLightning(EntityStruckByLightningEvent event) {
        if (event.getEntity() instanceof SkeletonHorse horse && !(horse instanceof WitherSkeletonHorse)) {
            event.setCanceled(true);
            Level level = horse.level();
            WitherSkeletonHorse witherSkeletonHorse = WITHER_SKELETON_HORSE.get().create(level);
            if (witherSkeletonHorse != null) {
                witherSkeletonHorse.moveTo(horse.getX(), horse.getY(), horse.getZ(), horse.getYRot(), horse.getXRot());
                witherSkeletonHorse.setTrap(horse.isTrap());
                ForgeEventFactory.onLivingConvert(horse, witherSkeletonHorse);
                level.addFreshEntity(witherSkeletonHorse);
                event.getEntity().discard();
            }
        }
    }
}
