package org.zombiesplugin.zombies.Boss;

import org.bukkit.entity.LivingEntity;

public class BossAttack implements IBossAttack {
    public LivingEntity BossEntity;

    public boolean Attack() {
        return false;
    }

    @Override
    public void ResetAttack() {}

    @Override
    public boolean IsAttackComplete() { return false; }

    public BossAttack(LivingEntity bossEntity) {
        BossEntity = bossEntity;
    }
}
