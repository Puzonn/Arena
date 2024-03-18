package org.zombiesplugin.zombies.Passives;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.zombiesplugin.zombies.UpgradeRarity;
import org.zombiesplugin.zombies.Zombies;

import java.util.Random;

public class LightningDamagePassive extends IPassive {
    private final Random rnd = new Random();

    public LightningDamagePassive() {
        Name = "Lightning Damage";
        PassiveType = PassiveEventType.OnDamage;
        Rarity = UpgradeRarity.Rare;
    }

    @Override
    public void DoWork(PassiveEventType type, Event event){
        if(!(event instanceof EntityDamageByEntityEvent)){
            return;
        }
        EntityDamageByEntityEvent handler = (EntityDamageByEntityEvent)event;
        if(handler.getDamager() instanceof Player){
            Player player = (Player)handler.getDamager();
            if(!IsOwner(player)){
                return;
            }

            OnDamage(player, handler);
        }
    }

    private void OnDamage(Player owner, EntityDamageByEntityEvent event) {
        if(event.getDamager() == owner) {
            if(rnd.nextInt(1, 10) >= 7){
                LightningStrike lightningStrike = event.getEntity().getWorld().strikeLightning(event.getEntity().getLocation());
                FixedMetadataValue damage = new FixedMetadataValue(Zombies.Instance, 1d);
                lightningStrike.setMetadata("damage", damage);
            }
        }
        else if(event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING){
            Entity lightning = (Entity) event.getDamager();
            if (lightning.hasMetadata("damage")) {
                double damage = lightning.getMetadata("damage").get(0).asDouble();
                event.setDamage(damage);
            }
        }
    }
}
