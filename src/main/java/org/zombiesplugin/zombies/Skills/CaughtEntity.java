package org.zombiesplugin.zombies.Skills;

import org.bukkit.entity.LivingEntity;

class CaughtEntity {
    public LivingEntity entity;
    public double BaseMovementSpeedValue;

    public CaughtEntity(LivingEntity entity, double baseMovementSpeedValue) {
        this.entity = entity;
        BaseMovementSpeedValue = baseMovementSpeedValue;
    }
}
