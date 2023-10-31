package tech.thatgravyboat.soullesssteeds.client;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.jetbrains.annotations.NotNull;

public class WitherSkeletonHorseRenderer extends UndeadHorseRenderer {
    public WitherSkeletonHorseRenderer(EntityRendererProvider.Context p_174432_) {
        super(p_174432_, ModelLayers.SKELETON_HORSE);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull AbstractHorse horse) {
        return new ResourceLocation("soulless_steeds", "textures/entity/horse/horse_wither_skeleton.png");
    }
}
