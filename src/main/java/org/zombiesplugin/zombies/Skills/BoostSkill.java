package org.zombiesplugin.zombies.Skills;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.zombiesplugin.zombies.ISpawnable;
import org.zombiesplugin.zombies.UpgradeRarity;
import org.zombiesplugin.zombies.Zombies;

import java.util.Collection;

public class BoostSkill extends ISkill {
    private int iteration = 0;

    public BoostSkill() {
        this.Name = "Boost";
        this.Rarity = UpgradeRarity.Common;
    }

    @Override
    public void Activate(Location location, World world) {
        skillLocation = location;
        CreateTextComponent(this.Name);

        runner = new BukkitRunnable() {
            @Override
            public void run() {
                if(iteration % 2 == 0){
                    DoBoost();
                }
                if(iteration == 20){
                    DestroySkill();
                    cancel();
                }
                iteration ++;
            }
        };

        runner.runTaskTimer(Zombies.Instance, 0L, 35L);
    }

    private void DoBoost() {
        Collection<Entity> entities = skillLocation.getWorld().getNearbyEntities(skillLocation, 8, 5, 8);

        for(Entity entity : entities) {
            if(entity instanceof Player){
                Player player = (Player)entity;
                player.spawnParticle(Particle.GUST, player.getLocation(), 20, 0, 0, 0);
                PotionEffect damageResistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 0, true, true);
                PotionEffect damageBoost = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 80, 0, true, true);
                player.addPotionEffect(damageResistance);
                player.addPotionEffect(damageBoost);
            }
        }
    }

    @Override
    public void Apply(Player player) {
        ItemStack item = new ItemStack(Material.YELLOW_WOOL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        player.getInventory().addItem(item);
    }
}
