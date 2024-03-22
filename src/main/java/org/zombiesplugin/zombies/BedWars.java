package org.zombiesplugin.zombies;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.*;

public class BedWars {
    /**
     *  Contains all active wars
     * */
    public final static Map<UUID, BedWars> ActiveBedWars = new HashMap<>();

    /**
     * Contains active players in current bed war
     * **/
    private final List<Player> Players = new ArrayList<>();

    private final World MainWorld;

    public BedWars(World world) {
        MainWorld = world;
    }

    public void Start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                SpawnCurrency(CurrencyType.Coal);
            }
        }.runTaskTimer(Zombies.Instance, 0, 100L);
    }

    /**
     * Spawns currency at currency spawn location
     * **/
    private void SpawnCurrency(CurrencyType type) {
        Location spawnLocation =  new Location(MainWorld, 251.5d, 36.5d, 181.5d);

        @Nullable
        ItemStack item = null;
        /**
         * Set item to its currency type
         * **/
        switch (type){
            case Coal: {
                item = new ItemStack(Material.COAL);
                break;
            }
        }

        if(item == null) {
            return;
        }

        spawnLocation.getWorld().dropItem(spawnLocation, item);
    }
}
