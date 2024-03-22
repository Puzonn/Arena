package org.zombiesplugin.zombies;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BedWarsUpgradeShop {
    private final Player owner;
    /**
     * @param owner Player who interacted with villager.
     **/
    public BedWarsUpgradeShop(Player owner) {
        this.owner = owner;
    }

    /**
     * Creates and opens shop.
     */
    public void OpenInventory() {
        Inventory inventory = Bukkit.createInventory(owner, InventoryType.CHEST, "Team Upgrades");

        inventory.setItem(1, CreateShopButton(Material.COAL));
        inventory.setItem(3, CreateShopButton(Material.LEATHER_CHESTPLATE));

        owner.openInventory(inventory);
    }

    /**
     * Creates ItemStack that cannot be picked any dropped from shop. Indicates button in shop.
     * @param material Material of this button.
     * @return ItemStack as shop button.
     */
    private ItemStack CreateShopButton(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(Zombies.Instance, "shop_button"), PersistentDataType.BOOLEAN, true);
        item.setItemMeta(meta);

        return item;
    }
}
