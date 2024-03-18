package org.zombiesplugin.zombies;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zombiesplugin.zombies.Passives.IPassive;

import java.util.*;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class PlayerUpgradesManager {
    public final static PlayerUpgradesManager Instance = new PlayerUpgradesManager();

    public Dictionary<Player, List<IPassive>> Passives = new Hashtable<>();

    public void RegisterPassive(Player player, IPassive passive) {
       @Nullable List<IPassive> passives = Passives.get(player);

        if(passives == null){
            passives = new ArrayList<>();
        }
    }

    private void RegisterEventBus(IPassive passive) {

    }
}
