package org.zombiesplugin.zombies.Boss;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import java.util.Random;

public class TestAttack extends BossAttack {

    public TestAttack(LivingEntity bossEntity) {
        super(bossEntity);
    }

    final double xRadius = 15d;
    final double zRadius = 15d;
    final double yRadius = 15d;

    private int MaxIteration = 12;
    private int iteration = MaxIteration;

    private Location EngagedLocation;

    @Override
    public boolean IsAttackComplete() {
        return iteration == 0;
    }

    @Override
    public void ResetAttack() {
        iteration = MaxIteration;
    }

    @Override
    public boolean Attack() {
        iteration --;

        if(iteration == 2) {
            Player[] players = BossEntity.getNearbyEntities(xRadius, yRadius, zRadius).stream().filter(e -> e instanceof Player)
                    .map(e -> (Player)e)
                    .toArray(Player[]::new);

            Random rnd = new Random();

            EngagedLocation = players[rnd.nextInt(0, players.length)].getLocation();
        }

        if(IsAttackComplete()) {
            if(EngagedLocation == null){
                return false;
            }

            EngagedLocation.getWorld().playSound(EngagedLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0.7f);
            BossEntity.teleport(EngagedLocation);
        }

        return true;
    }
}
