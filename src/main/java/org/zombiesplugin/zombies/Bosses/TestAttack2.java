package org.zombiesplugin.zombies.Bosses;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.zombiesplugin.zombies.Zombies;

import java.util.ArrayList;
import java.util.List;

public class TestAttack2 extends BossAttack {
    private BukkitTask runner;

    public TestAttack2(LivingEntity bossEntity) {
        super(bossEntity);
    }

    private int MaxIteration = 35;
    private int iteration = MaxIteration;
    private int radius = 2;
    private double genericSpeed = 0;

    @Override
    public boolean IsAttackComplete() {
        return iteration == 0;
    }

    @Override
    public void ResetAttack() {
        BossEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(genericSpeed);
        iteration = MaxIteration;
    }

    @Override
    public boolean Attack() {
        if(iteration == MaxIteration) {
            genericSpeed = BossEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
            BossEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(genericSpeed * 1.5d);

            runner = new BukkitRunnable() {
                @Override
                public void run() {
                    if(IsAttackComplete()) {
                        cancel();
                    }

                    Location location = BossEntity.getLocation();

                    Player[] players = BossEntity.getNearbyEntities(radius, 5, radius).stream().filter(e -> e instanceof Player)
                            .map(e -> (Player)e)
                            .toArray(Player[]::new);

                    for(Player player : players) {
                        player.damage(1d);
                    }

                    for (int i = 0; i < 8; i++) {
                        double angleIncrement = 2 * Math.PI / 8;
                        double angle = i * angleIncrement;
                        double xOffset = radius * Math.cos(angle);
                        double zOffset = radius * Math.sin(angle);

                        double x = location.getX() + xOffset;
                        double z = location.getZ() + zOffset;

                        Location particleLoc = new Location(location.getWorld(), x, location.getY(), z);
                        particleLoc.getWorld().spawnParticle(Particle.LAVA, particleLoc, 1);
                    }
                }
            }.runTaskTimer(Zombies.Instance, 0, 10);
        }

        iteration --;

        return true;
    }
}
