package org.zombiesplugin.zombies;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArenaLocations {
    public final Location Spawn;
    public final Location Upgrade;
    public final List<Location> EntitiesSpawnLocations = new ArrayList<>();

    public ArenaLocations(Location spawn, Location upgrade) {
        Spawn = spawn;
        Upgrade = upgrade;

    }

    public void AddEntitySpawnLocation(Location location) {
        EntitiesSpawnLocations.add(location);
    }

    public Location GetRandomEntitySpawnLocation() {
        if(EntitiesSpawnLocations.isEmpty()){
            Bukkit.getLogger().warning("EntitiesSpawnLocations is empty in GetRandomEntitySpawnLocation");
        }
        
        Random rnd = new Random();
        return EntitiesSpawnLocations.get(rnd.nextInt(0, EntitiesSpawnLocations.size()));
    }
}
