package org.zombiesplugin.zombies.Items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.zombiesplugin.zombies.Zombies;

import javax.annotation.Nullable;
import java.util.*;

public class ArenaItem {
    public final static List<ArenaItem> ArenaItems = new ArrayList<>();

    public Player owner;
    public ItemStack item;
    public UUID UniqueId;

    public final Map<String, ArenaItemMeta> Metas = new Hashtable<>();

    public ArenaItem(Player owner, ItemStack item) {
        this.owner = owner;
        this.item = item;

        SetUniqueId();
    }

    public void SetMetas(HashSet<ArenaItemMeta> metas) {
        for(ArenaItemMeta entry : metas) {
            Metas.put(entry.MetaName, entry.Create());
        }

        SetLora();
    }

    private void SetLora() {
        List<String> lore = item.getItemMeta().getLore();

        if(lore == null) {
            lore = new ArrayList<>();
        }

        for(ArenaItemMeta arenaMeta : Metas.values()) {
            lore.add(String.format("%s %.2f", arenaMeta.DisplayName, arenaMeta.MetaValue));
        }

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        Bukkit.getPlayer("Puzonne").sendMessage(lore.toString());
    }

    public static Optional<ArenaItem> Get(ItemStack item) {
        String uniqueId = item.getItemMeta().getPersistentDataContainer().getOrDefault(new NamespacedKey(Zombies.Instance, "arena_unique_id"), PersistentDataType.STRING, "");
        return ArenaItems.stream().filter(x -> x.UniqueId.toString().equals(uniqueId)).findFirst();
    }

    public static void Register(ArenaItem item) {
        ArenaItems.add(item);
    }

    private void SetUniqueId() {
        ItemMeta meta = item.getItemMeta();
        UUID uniqueId = UUID.randomUUID();

        meta.getPersistentDataContainer().set(new NamespacedKey(Zombies.Instance, "arena_unique_id"), PersistentDataType.STRING, uniqueId.toString());
        meta.getPersistentDataContainer().set(new NamespacedKey(Zombies.Instance, "arena_item_calc"), PersistentDataType.BOOLEAN, true);

        item.setItemMeta(meta);
        UniqueId = uniqueId;
    }
}
