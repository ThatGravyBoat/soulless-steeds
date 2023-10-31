package tech.thatgravyboat.soullesssteeds.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.soullesssteeds.common.entities.ZombieTrapHorse;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Redirect(
            method = "tickChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/Entity;",
                    ordinal = 0
            )
    )
    private Entity createEntity(EntityType<?> instance, Level level, @Share("spawnedHorse") LocalBooleanRef argRef) {
        boolean spawnSkeleton = level.getRandom().nextBoolean();
        argRef.set(spawnSkeleton);
        return spawnSkeleton ? instance.create(level) : null;
    }

    @Inject(
            method = "tickChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/Entity;",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void onEntitySpawned(LevelChunk p_8715_, int p_8716_, CallbackInfo ci, @Local(ordinal = 0) BlockPos blockpos, @Share("spawnedHorse") LocalBooleanRef argRef) {
        if (!argRef.get()) {
            ServerLevel level = (ServerLevel)(Object)this;
            ZombieHorse horse = EntityType.ZOMBIE_HORSE.create(level);
            if (horse == null) return;
            ((ZombieTrapHorse)horse).soullesssteeds$setTrap(true);
            horse.setAge(0);
            horse.setPos(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            level.addFreshEntity(horse);
        }
    }

}
