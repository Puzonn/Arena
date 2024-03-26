package org.zombiesplugin.zombies.Lobby;

import org.bukkit.Location;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IslandMap {
    public static Map<String, IslandMap> Maps = new HashMap<>();
    public Map<IslandMapLocation, List<Location>> Locations = new HashMap<>();

    public void AddLocation(Location location, IslandMapLocation type) {
        if(Locations.containsKey(type)) {
            Locations.get(type).add(location);
        }
        else {
            List<Location> _ = new ArrayList<>();
            _.add(location);
            Locations.put(type, _);
        }
    }
}