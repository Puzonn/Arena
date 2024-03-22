package org.zombiesplugin.zombies.VillagerShop;

import java.util.HashMap;
import java.util.Map;

public interface IVillagerShopAction {
    /**
     * Singleton. Stores all function that are associated with @action_tag.
     */
    public static Map<String, IVillagerShopAction> Actions = new HashMap<>();

    /**
     * Called when shop button gets clicked.
     * @param event contains Player, Bed and Item that has been clicked.
     */
    void Action(VillagerShopActionEvent event);
}
