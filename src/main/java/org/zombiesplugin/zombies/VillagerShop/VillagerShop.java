package org.zombiesplugin.zombies.VillagerShop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.zombiesplugin.zombies.Zombies;

public class VillagerShop {
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

        meta.getPersistentDataContainer().set(new NamespacedKey(Zombies.Instance, "shop_button"), PersistentDataType.BOOLEAN, true);
        meta.getPersistentDataContainer().set(new NamespacedKey(Zombies.Instance, "action_tag"), PersistentDataType.STRING, tag);
        item.setItemMeta(meta);

        IVillagerShopAction.Actions.putIfAbsent(tag, action);

        return item;
    }
}
