package tech.thatgravyboat.soullesssteeds.mixins;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.soullesssteeds.common.entities.ZombieTrapHorse;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal {

    protected AbstractHorseMixin(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Inject(
        method = "aiStep",
        at = @At("TAIL")
    )
    private void soullesssteeds$onTick(CallbackInfo ci) {
        Object self = this;
        if (self instanceof ZombieTrapHorse trapHorse) {
            if (trapHorse.soullesssteeds$isTrap() && trapHorse.soullesssteeds$getAndIncrementTrapTime() >= 18000) {
                this.discard();
            }
        }
    }

    @Inject(
        method = "readAdditionalSaveData",
        at = @At("TAIL")
    )
    private void soullesssteeds$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        Object self = this;
        if (self instanceof ZombieTrapHorse trapHorse) {
            trapHorse.soullesssteeds$updateGoal();
        }
    }
}
