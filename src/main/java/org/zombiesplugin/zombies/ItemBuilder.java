package org.zombiesplugin.zombies;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zombiesplugin.zombies.Items.ArenaItem;
import org.zombiesplugin.zombies.Items.ArenaItemMeta;

import java.util.*;

public class ItemBuilder {
    private final ItemStack item;
    private final HashSet<ArenaItemMeta> Metas = new HashSet<>();

    public ItemBuilder(ItemStack base) {
        item = base;
    }

    public ItemBuilder WithName(String itemName, boolean calc) {
        ItemMeta meta = item.getItemMeta();

        if(calc) {
            meta.getPersistentDataContainer().set(new NamespacedKey(Zombies.Instance, "arena_item_calc"), PersistentDataType.BOOLEAN, true);
        }

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(itemName);

        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder WithBasePrefixMeta(ArenaItemMeta arenaMeta) {
        Metas.add(arenaMeta);

        return this;
    }

    public ArenaItem Get(Player owner) {
        ArenaItem arenaItem = new ArenaItem(owner, item);
        arenaItem.SetMetas(Metas);
        ArenaItem.Register(arenaItem);

        return arenaItem;
    }
}
