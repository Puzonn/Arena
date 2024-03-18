package org.zombiesplugin.zombies.Mechanics;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.zombiesplugin.zombies.Zombies;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Forge {
    private final Inventory inventory;
    private final Player player;
    private final Random random = new Random();
    private ItemStack ForgingItem;
    private ItemStack ForgingMaterial;

    public Forge(Inventory forgeInventory, Player forgingPlayer) {
        inventory = forgeInventory;
        player = forgingPlayer;
    }

    public Forge(Player forgingPlayer) {
        player = forgingPlayer;
        inventory = CreateInventory();
    }

    public void TransformItem(ItemStack item) {
        if (item.getAmount() > 1) {
            throw new IllegalArgumentException("Transformed item has more than 1 amount");
        }

        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
    }

    public void Forge() {
        Optional<ItemStack> forgingItem = GetForgingItem();
        Optional<ItemStack> forgingMaterial = GetForgingMaterial();

        if (!forgingMaterial.isPresent() || !forgingItem.isPresent()) {
            return;
        }

        TransformItem(forgingItem.get());

        List<String> lore = new ArrayList<>();
        lore.add("Physical Damage: " + random.nextFloat(1, 10));

        ItemMeta forgingMeta = forgingItem.get().getItemMeta();

        forgingMeta.setLore(lore);

        forgingItem.get().setItemMeta(forgingMeta);

        SetForgingItem(forgingItem.get());
    }

    public Optional<ItemStack> GetForgingItem() {
        ItemStack item = inventory.getItem(10);

        if (item == null || item.getType() == Material.AIR) {
            return Optional.empty();
        }

        return Optional.of(item);
    }

    public void SetForgingItem(ItemStack item) {
        inventory.setItem(10, item);
    }

    public Optional<ItemStack> GetForgingMaterial() {
        ItemStack item = inventory.getItem(16);

        if (item == null || item.getType() == Material.AIR) {
            return Optional.empty();
        }

        return Optional.of(item);
    }

    public void OpenForge() {
        player.openInventory(inventory);
    }

    private Inventory CreateInventory() {
        Inventory inventory = Bukkit.createInventory(player, InventoryType.CHEST, "Reforger");

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
        }

        ItemStack btn_forge = new ItemStack(Material.ANVIL);
        ItemMeta meta = btn_forge.getItemMeta();
        meta.setDisplayName("§c Forge");
        meta.getPersistentDataContainer().set(new NamespacedKey(Zombies.Instance, "button_forge"), PersistentDataType.BOOLEAN, true);
        btn_forge.setItemMeta(meta);

        inventory.setItem(22, btn_forge);
        inventory.setItem(16, new ItemStack(Material.AIR));
        inventory.setItem(10, new ItemStack(Material.AIR));

        return inventory;
    }
}
