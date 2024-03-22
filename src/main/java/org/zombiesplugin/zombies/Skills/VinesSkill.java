package org.zombiesplugin.zombies.Skills;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.zombiesplugin.zombies.PlayerMeta.ArenaPlayer;
import org.zombiesplugin.zombies.UpgradeRarity;
import org.zombiesplugin.zombies.Zombies;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class VinesSkill extends ISkill {
    private int iteration = 0;
    private final List<CaughtEntity> CaughtEntities = new ArrayList<CaughtEntity>();

    public VinesSkill(Item item, ArenaPlayer player) {
        super(item, player);
        this.Name = "Vines";
        this.Rarity = UpgradeRarity.Rare;
    }

    @Override
    public void Activate() {
        runner = new BukkitRunnable() {
            @Override
            public void run() {
                iteration ++;
                DoDamage(item.getLocation());

                if(iteration == 5){
                    for(CaughtEntity caughtEntity : CaughtEntities){
                        caughtEntity.entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(caughtEntity.BaseMovementSpeedValue);
                    }

                    DestroySkill();
                    cancel();
                }
            }
        };
        runner.runTaskTimer(Zombies.Instance, 10L, 80L);
    }

    private void DoDamage(Location skillLocation) {
        Collection<Entity> entities = Objects.requireNonNull(skillLocation.getWorld()).getNearbyEntities(skillLocation, 3, 5, 10);

        for(Entity entity : entities) {
            if(entity instanceof Player){
                continue;
            }

            if(entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                if(CaughtEntities.stream().noneMatch(x -> x.entity == livingEntity)){
                    double baseMovementSpeed = livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();
                    CaughtEntities.add(new CaughtEntity(livingEntity, baseMovementSpeed));
                }

                livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0d);
                livingEntity.getLocation().getWorld().spawnParticle(Particle.SLIME, livingEntity.getLocation(), 40, 0, 2, 0);
                Bukkit.getPlayer("Puzonne").sendMessage(entity.getName());
                livingEntity.damage(8f);
            }
        }
    }

    @Override
    public void Apply(Player player) {
        ItemStack item = new ItemStack(Material.GREEN_WOOL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY ,1);
        player.getInventory().addItem(item);
    }
}

