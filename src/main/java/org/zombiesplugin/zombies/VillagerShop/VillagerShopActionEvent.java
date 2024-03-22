package org.zombiesplugin.zombies.VillagerShop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zombiesplugin.zombies.BedWars;

public class VillagerShopActionEvent {
    /**
     * Indicates who clicked button.
     */
    public Player player;
    /**
     * Indicates what item/button have been clicked.
     */
    public ItemStack item;

    /**
     * Indicates bed that player owns.
     */
    public BedWars bed;

    public VillagerShopActionEvent(Player player, ItemStack item, BedWars bed) {
        this.player = player;
        this.item = item;
        this.bed = bed;
    }
}
