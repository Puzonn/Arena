package org.zombiesplugin.zombies;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ISpawnable {
    public String Name = "";
    public UpgradeRarity Rarity = UpgradeRarity.Common;
    public String Description = "";
    public UpgradeType Type = UpgradeType.None;
    public void Apply(Player player){}
}

