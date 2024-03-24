package org.zombiesplugin.zombies.Bosses;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.zombiesplugin.zombies.Zombies;
import java.util.*;

public class TestIslandBoss {
    private final LivingEntity BossEntity;
    private List<IBossAttack> Attacks = new ArrayList<>();

    private IBossAttack currentAttack;

    public TestIslandBoss(Location spawnLocation) {
        BossEntity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);

        Attacks.add(new TestAttack(BossEntity));
        Attacks.add(new TestAttack2(BossEntity));

        currentAttack = RollAttack();

        new BukkitRunnable() {
            @Override
            public void run() {
                if(currentAttack.IsAttackComplete()) {
                    currentAttack.ResetAttack();

                    currentAttack = RollAttack();
                }

                currentAttack.Attack();
            }
        }.runTaskTimer(Zombies.Instance, 0L, 10L);
    }

    private IBossAttack RollAttack() {
        Random rnd = new Random();
        return Attacks.get(rnd.nextInt(0, Attacks.size()));
    }
}
