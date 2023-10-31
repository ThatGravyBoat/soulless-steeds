package tech.thatgravyboat.soullesssteeds.mixins;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import tech.thatgravyboat.soullesssteeds.common.entities.ZombieTrapGoal;
import tech.thatgravyboat.soullesssteeds.common.entities.ZombieTrapHorse;

@Mixin(ZombieHorse.class)
public abstract class ZombieHorseMixin extends AbstractHorse implements ZombieTrapHorse {

    @Unique
    private final ZombieTrapGoal soullesssteeds$trapGoal = new ZombieTrapGoal((ZombieHorse)(Object)this, this);

    @Unique
    private int soullesssteeds$trapTime = 0;

    protected ZombieHorseMixin(EntityType<? extends AbstractHorse> p_30531_, Level p_30532_) {
        super(p_30531_, p_30532_);
    }

    @Override
    public void soullesssteeds$setTrap(boolean trap) {
        boolean currentTrap = this.getPersistentData().getBoolean("SoullessSteedsTrap");
        if (trap != currentTrap) {
            this.getPersistentData().putBoolean("SoullessSteedsTrap", trap);
            if (trap) {
                this.goalSelector.addGoal(1, this.soullesssteeds$trapGoal);
            } else {
                this.goalSelector.removeGoal(this.soullesssteeds$trapGoal);
            }
        }
    }

    @Override
    public void soullesssteeds$updateGoal() {
        boolean currentTrap = this.getPersistentData().getBoolean("SoullessSteedsTrap");
        this.goalSelector.removeGoal(this.soullesssteeds$trapGoal);
        if (currentTrap) {
            this.goalSelector.addGoal(1, this.soullesssteeds$trapGoal);
        }
    }

    @Override
    public boolean soullesssteeds$isTrap() {
        return this.getPersistentData().getBoolean("SoullessSteedsTrap");
    }

    @Override
    public int soullesssteeds$getAndIncrementTrapTime() {
        return this.soullesssteeds$trapTime++;
    }
}
