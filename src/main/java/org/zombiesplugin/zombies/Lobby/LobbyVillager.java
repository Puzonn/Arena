package org.zombiesplugin.zombies.Lobby;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;
import org.zombiesplugin.zombies.Zombies;

public class LobbyVillager {
    public static final String META = "villager_lobby";

    public LobbyVillager() {

    }

    public void SpawnLobbyVillager(Location location) {
        Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Click me to join");
        villager.setCustomNameVisible(true);
        villager.setMetadata(META, new FixedMetadataValue(Zombies.Instance, true));
    }
}
