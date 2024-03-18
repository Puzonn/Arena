package org.zombiesplugin.zombies.Skills;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.zombiesplugin.zombies.ISpawnable;
import org.zombiesplugin.zombies.UpgradeRarity;
import org.zombiesplugin.zombies.UpgradeType;
import org.zombiesplugin.zombies.Zombies;

import java.util.Collection;

public class HellBomb extends ISkill {
    private int iteration = 0;

    public HellBomb() {
       this.Name = "Hell Bomb";
       this.Rarity = UpgradeRarity.Common;
    }

    @Override
    public void Activate(Location location, World world) {
        skillLocation = location;
        CreateTextComponent(this.Name);

        runner = new BukkitRunnable() {
            @Override
            public void run() {
               world.spawnParticle(Particle.FLAME, skillLocation, 100, 0, 2, 0);
               DoDamage();
               iteration ++;

               if(iteration == 5){
                   DestroySkill();
                   cancel();
               }
            }
        };

        runner.runTaskTimer(Zombies.Instance, 0L, 140L);
    }

    private void DoDamage() {
        Collection<Entity> entities = skillLocation.getWorld().getNearbyEntities(skillLocation, 5, 5, 5);

        for(Entity entity : entities) {
            if(entity instanceof Player){
                continue;
            }
            if(entity instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity)entity;
                livingEntity.damage(12f);
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
