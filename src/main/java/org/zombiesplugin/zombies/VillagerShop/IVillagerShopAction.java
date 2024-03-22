package org.zombiesplugin.zombies.VillagerShop;

import java.util.HashMap;
import java.util.Map;

public interface IVillagerShopAction {
    public static Map<String, IVillagerShopAction> Actions = new HashMap<>();

    void Action(VillagerShopActionEvent event);
}
