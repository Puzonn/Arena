package org.zombiesplugin.zombies.Skills;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.zombiesplugin.zombies.ISpawnable;
import org.zombiesplugin.zombies.PlayerMeta.ArenaPlayer;
import org.zombiesplugin.zombies.PlayerMeta.ArenaPlayerMetas;
import org.zombiesplugin.zombies.UpgradeRarity;
import org.zombiesplugin.zombies.UpgradeType;
import org.zombiesplugin.zombies.Zombies;

import java.util.Collection;

public class HellBomb extends ISkill {
    private int iteration = 0;

    public HellBomb(Item item, ArenaPlayer player) {
        super(item, player);
        this.Name = "Hell Bomb";
        this.Rarity = UpgradeRarity.Common;
    }

    @Override
    public void Activate() {
        runner = new BukkitRunnable() {

            @Override
            public void run() {
                double radius = 1d + (double)player.GetMeta(ArenaPlayerMetas.SkillSize) * (double)player.GetMeta(ArenaPlayerMetas.IncreasedSkillSize);
                double damage = 4d + (double)player.GetMeta(ArenaPlayerMetas.ElementalDamage) * (double)player.GetMeta(ArenaPlayerMetas.IncreasedElementalDamage);

                Location location = item.getLocation();

                for (int i = 0; i < 30; i++) {
                    double angleIncrement = 2 * Math.PI / 30;
                    double angle = i * angleIncrement;
                    double xOffset = radius * Math.cos(angle);
                    double zOffset = radius * Math.sin(angle);

                    double x = location.getX() + xOffset;
                    double z = location.getZ() + zOffset;

                    Location particleLoc = new Location(location.getWorld(), x, location.getY(), z);
                    particleLoc.getWorld().spawnParticle(Particle.LAVA, particleLoc, 1);
                    DoDamage(item.getLocation(), radius, damage);
                }

                iteration++;

                if(iteration == 8) {
                    cancel();
                }
            }
        };
        runner.runTaskTimer(Zombies.Instance, 20L, 70L);
    }

    private void DoDamage(Location skillLocation, double radius, double damage) {
        Collection<Entity> entities = skillLocation.getWorld().getNearbyEntities(skillLocation, radius, 5, radius);

        for(Entity entity : entities) {
            if(entity instanceof Player){
                continue;
            }
            if(entity instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity)entity;
                livingEntity.damage(damage);
            }
        }
    }

    @Override
    public void Apply(Player player) {
        ItemStack item = new ItemStack(Material.RED_WOOL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        player.getInventory().addItem(item);
    }
}
