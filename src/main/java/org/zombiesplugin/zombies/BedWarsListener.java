package org.zombiesplugin.zombies;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.zombiesplugin.zombies.VillagerShop.IVillagerShopAction;
import org.zombiesplugin.zombies.VillagerShop.VillagerShop;
import org.zombiesplugin.zombies.VillagerShop.VillagerShopActionEvent;

import java.util.List;

public class BedWarsListener implements Listener {

    @EventHandler
    private void onPlayerShopInteract(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        if(item == null) {
            return;
        }

        /**
         * Check if item that is being interacted has shop_button metadata. If it has, cancel current event.
         */
        Boolean isShopButton = item.getItemMeta().getPersistentDataContainer().getOrDefault(
                new NamespacedKey(Zombies.Instance, "shop_button"), PersistentDataType.BOOLEAN, false);

        if(isShopButton) {
            /**
             * Get the action tag used to call a function associated with a shop button item.
             */
            String actionTag = item.getItemMeta().getPersistentDataContainer().getOrDefault(
                    new NamespacedKey(Zombies.Instance, "action_tag"), PersistentDataType.STRING, "");

            /**
             * Call function associated with shop button.
             */
            IVillagerShopAction.Actions.get(actionTag).Action(new VillagerShopActionEvent((Player)event.getWhoClicked(), item, null));

            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onVillagerInteract(PlayerInteractAtEntityEvent event) {
        /**
         * Check if it's villager and has metadata that he is villager_upgrade indeed.
         */
        if(event.getRightClicked().getType() == EntityType.VILLAGER) {
            Villager villager = (Villager)event.getRightClicked();
            List<MetadataValue> villagerMetadata = villager.getMetadata("villager_upgrade");
            /**
             * Villager does not have any metadata at this key. We skip because we need with one.
             */
            if(villagerMetadata.size() == 0){
                return;
            }

            if(villagerMetadata.get(0).value() instanceof Boolean) {
                VillagerShop shop = new VillagerShop(event.getPlayer());
                shop.OpenInventory();
            }
        }
    }
}
