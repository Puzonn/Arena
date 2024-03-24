package org.zombiesplugin.zombies.Boss;

import org.bukkit.entity.LivingEntity;

public interface IBossAttack {
    /**
     * Tries to do attack if conditions are met
     * @return True if conditions are met
     */
    public boolean Attack();

    /**
     * Reset all variables to their initial values
     */
    public void ResetAttack();

    public boolean IsAttackComplete();

    public LivingEntity BossEntity = null;
}
