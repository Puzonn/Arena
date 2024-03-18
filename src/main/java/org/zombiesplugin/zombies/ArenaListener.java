package org.zombiesplugin.zombies;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.zombiesplugin.zombies.Items.ArenaItem;
import org.zombiesplugin.zombies.Items.ArenaItemMeta;
import org.zombiesplugin.zombies.Mechanics.Forge;
import org.zombiesplugin.zombies.Passives.IPassive;
import org.zombiesplugin.zombies.Passives.LightningDamagePassive;
import org.zombiesplugin.zombies.Passives.PassiveEventType;
import org.zombiesplugin.zombies.PlayerMeta.ArenaPlayer;
import org.zombiesplugin.zombies.PlayerMeta.ArenaPlayerMetas;
import org.zombiesplugin.zombies.Skills.*;

import java.util.*;

public class ArenaListener implements Listener {
    private SkillHandler skillHandler;

    private final List<IPassive> _passives = new ArrayList<IPassive>();

    public ArenaListener() {
        skillHandler = new SkillHandler();

        _passives.add(new LightningDamagePassive());

        ISkill.Skills.add(new BoostSkill());
        ISkill.Skills.add(new HellBomb());
        ISkill.Skills.add(new VinesSkill());

        RevaluatePassiveCache();
    }

    private void RevaluatePassiveCache() {
        for(IPassive passive : _passives){
            if(IPassive.Passives.containsKey(passive.PassiveType)){
                /* Safety check for random events */
               Set<IPassive> _passives = IPassive.Passives.get(passive.PassiveType);
               if(_passives.contains(passive)) {
                   continue;
               }
               _passives.add(passive);
            }
            else{
                Set<IPassive> _passives = new HashSet<>();
                _passives.add(passive);
                IPassive.Passives.put(passive.PassiveType, _passives);
            }
        }
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        skillHandler.onBlockPlaced(event);
    }

    @EventHandler
    private void onBlockDestroyed(BlockBreakEvent event){
        for(MetadataValue meta : event.getBlock().getState().getMetadata("IsSkill")){
            if(meta.getOwningPlugin().equals(Zombies.Instance)){
                Optional<ISkill> skill = ISkill.ActivatedSkills.stream().filter(x->x.skillLocation.equals(event.getBlock().getLocation())).findFirst();
                if(skill.isPresent()){
                    skill.get().RemoveTextComponent();
                }
            }
        }
    }

    @EventHandler
    private void onMobKilled(EntityDeathEvent event){
        ArenasManager.onMobKilledHandler(event);
    }

    @EventHandler
    private void onMobDamaged(EntityDamageEvent event) {
        DoPassive(PassiveEventType.OnDamage, event);
        ArenasManager.onArenaEntityDamagedHandler(event);
    }

    @EventHandler
    private void onMobDamagedByEntity(EntityDamageByEntityEvent event) {
        DoPassive(PassiveEventType.OnDamage, event);
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event){
        ArenasManager.onArenaPlayerKilledHandler(event);
    }

    @EventHandler
    private void onDropSkillUsed(PlayerDropItemEvent event) {
        Item item = event.getItemDrop();

        ArenaPlayer player = ArenaPlayer.Get(event.getPlayer());


        if(item.getItemStack().getType() == Material.RED_WOOL){
            double radius = 5 * (float)player.GetMeta(ArenaPlayerMetas.SkillSize);

            new BukkitRunnable() {
                @Override
                public void run() {
                    Location location = event.getItemDrop().getLocation();

                    for (int i = 0; i < 30; i++) {
                        double angleIncrement = 2 * Math.PI / 30;
                        double angle = i * angleIncrement;
                        double xOffset = radius * Math.cos(angle);
                        double zOffset = radius * Math.sin(angle);

                        double x = location.getX() + xOffset;
                        double z = location.getZ() + zOffset;

                        Location particleLoc = new Location(location.getWorld(), x, location.getY(), z);
                        particleLoc.getWorld().spawnParticle(Particle.LAVA, particleLoc, 1);
                    }
                }
            }.runTaskTimer(Zombies.Instance, 0L, 80L);
        }
    }

    @EventHandler
    private void onPlayerInventoryInteract(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if(item == null) {
            return;
        }

        if(item.getType() == Material.ANVIL) {
            Boolean isForgeButton = item.getItemMeta().getPersistentDataContainer().getOrDefault(new NamespacedKey(Zombies.Instance, "button_forge"), PersistentDataType.BOOLEAN, false);

            if(!isForgeButton){
                return;
            }

            Inventory forgeInventory = event.getInventory();

            Forge forge = new Forge(forgeInventory, player);
            forge.Forge();
        }
    }

    @EventHandler
    private void onPlayerMainHand(PlayerItemHeldEvent event) {
        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        String uniqueId = newItem.getItemMeta().getPersistentDataContainer().getOrDefault(new NamespacedKey(Zombies.Instance, "arena_unique_id"), PersistentDataType.STRING, "");

        if(newItem == null) {
            return;
        }

        Boolean isCalc = newItem.getItemMeta().getPersistentDataContainer().getOrDefault(new NamespacedKey(Zombies.Instance, "arena_item_calc"), PersistentDataType.BOOLEAN, false);

        if(isCalc) {
            Optional<ArenaItem> arenaItem = ArenaItem.Get(newItem);

            if(!arenaItem.isPresent()) {
                event.getPlayer().sendMessage(String.format("Item is not present - %s", uniqueId));
                return;
            }

            ArenaPlayer arenaPlayer = ArenaPlayer.Get(event.getPlayer());

            for(ArenaItemMeta meta : arenaItem.get().Metas.values()) {
                arenaPlayer.SetMeta(meta.MetaName, meta.MetaValue);
            }
        }
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) {
            return;
        }

        Location itemPedestal = new Location(event.getPlayer().getWorld(), 324, 44, 197);

        if(event.getClickedBlock().getLocation().equals(itemPedestal)) {
            Player player = event.getPlayer();
            Optional<Arena> arena = ArenasManager.GetArena(player);

            if(arena.isPresent()) {
                arena.get().upgrader.UpgradeClicked(player);
            }
        }
    }

    private void DoPassive(PassiveEventType type, Event event) {
        for(IPassive passive : IPassive.Passives.get(type)){
            passive.DoWork(type, event);
        }
    }
}
