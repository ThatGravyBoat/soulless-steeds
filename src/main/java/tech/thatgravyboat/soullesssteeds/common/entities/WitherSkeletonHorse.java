package tech.thatgravyboat.soullesssteeds.common.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.soullesssteeds.SoullessSteeds;

import javax.annotation.Nullable;

public class WitherSkeletonHorse extends SkeletonHorse {

    private final WitherSkeletonTrapGoal skeletonTrapGoal = new WitherSkeletonTrapGoal(this);
    private boolean isTrap;

    public WitherSkeletonHorse(EntityType<? extends WitherSkeletonHorse> p_30894_, Level p_30895_) {
        super(p_30894_, p_30895_);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return createBaseHorseAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    public boolean isTrap() {
        return this.isTrap;
    }

    @Override
    public void setTrap(boolean p_30924_) {
        if (p_30924_ != this.isTrap) {
            this.isTrap = p_30924_;
            if (p_30924_) {
                this.goalSelector.addGoal(1, this.skeletonTrapGoal);
            } else {
                this.goalSelector.removeGoal(this.skeletonTrapGoal);
            }

        }
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return SoullessSteeds.WITHER_SKELETON_HORSE.get().create(level);
    }
}