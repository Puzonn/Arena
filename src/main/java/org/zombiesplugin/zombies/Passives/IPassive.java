package org.zombiesplugin.zombies.Passives;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.zombiesplugin.zombies.ISpawnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class IPassive extends ISpawnable {
    public final static HashMap<PassiveEventType, Set<IPassive>> Passives = new HashMap<>();

    protected final Set<Player> Owners = new HashSet<>();

    public PassiveEventType PassiveType;

    public void DoWork(PassiveEventType type, Event event){
    }

    public final void RegisterPlayer(Player player) {
        Owners.add(player);
    }

    public final boolean IsOwner(Player player) {
        return Owners.contains(player);
    }
}


