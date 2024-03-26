package org.zombiesplugin.zombies;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.zombiesplugin.zombies.Commands.BedWarsCommand;

public final class Zombies extends JavaPlugin {
    public static Plugin Instance;

    @Override
    public void onEnable() {
        Instance = this;
        getServer().getPluginManager().registerEvents(new BedWarsListener(), this);
        getServer().getPluginManager().registerEvents(new CoreListener(), this);
        
        getCommand("bed").setExecutor(new BedWarsCommand());
        
        new Teams();
        
        CoreMenager core = new CoreMenager();
        core.PlaceCore(Bukkit.getPlayer("Xwaw").getLocation());
    }
}
