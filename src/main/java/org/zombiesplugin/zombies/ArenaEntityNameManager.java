package org.zombiesplugin.zombies;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

public class ArenaEntityNameManager {
    public static void UpdateName(LivingEntity entity, double damage) {
        int entityMaxHealth = Math.round((float)entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        int entityHealth = Math.round((float)entity.getHealth() - (float)damage);
        entity.setCustomName(String.format("%d / %d", entityHealth, entityMaxHealth));
    }
}