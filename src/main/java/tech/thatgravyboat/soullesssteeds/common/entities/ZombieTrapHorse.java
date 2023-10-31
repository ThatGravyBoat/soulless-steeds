package tech.thatgravyboat.soullesssteeds.common.entities;

public interface ZombieTrapHorse {

    void soullesssteeds$setTrap(boolean trap);

    void soullesssteeds$updateGoal();

    boolean soullesssteeds$isTrap();

    int soullesssteeds$getAndIncrementTrapTime();
}
