package org.zombiesplugin.zombies.VillagerShop;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.zombiesplugin.zombies.Zombies;

public class VillagerShop {
    public static final String META_VILLAGER_UPGRADE = "villager_upgrade";
    public static final String META_SHOP_BUTTON= "shop_button";
    public static final String META_ACTION_TAG = "action_tag";

    /**
     * Player who interacted with villager.
     */
    private final Player owner;

    /**
     * @param owner Player who interacted with villager.
     */
    public VillagerShop(Player owner) {
        this.owner = owner;
    }

    /**
     * Creates and opens shop.
     */
    public void OpenInventory() {
        Inventory inventory = Bukkit.createInventory(owner, InventoryType.CHEST, "Team Upgrades");

        /**
         * Add here shop buttons.
         * @example inventory.setItem(0, CreateShopButton("tag", Material, IVillagerShopAction));
         */
        owner.openInventory(inventory);
    }

    /**
     * Creates ItemStack that cannot be picked any dropped from shop. Indicates button in shop.
     * @param material Material of this button.
     * @return ItemStack as shop button.
     */
    private ItemStack CreateShopButton(String tag, Material material, IVillagerShopAction action) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(new NamespacedKey(Zombies.Instance, META_SHOP_BUTTON), PersistentDataType.BOOLEAN, true);
        meta.getPersistentDataContainer().set(new NamespacedKey(Zombies.Instance, META_ACTION_TAG), PersistentDataType.STRING, tag);
        item.setItemMeta(meta);

        IVillagerShopAction.Actions.putIfAbsent(tag, action);

        return item;
    }

    /**
     * Spawns villager
     * @param location
     */
    public void SpawnVillager(Location location) {
        Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Team Upgrades");
        villager.setCustomNameVisible(true);
        villager.setMetadata(META_VILLAGER_UPGRADE, new FixedMetadataValue(Zombies.Instance, true));
    }
}
