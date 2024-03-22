package org.zombiesplugin.zombies;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.zombiesplugin.zombies.Commands.BedWarsCommand;

public final class Zombies extends JavaPlugin {
    public static Plugin Instance;

    @Override
    public void onEnable() {
        Instance = this;
        getServer().getPluginManager().registerEvents(new BedWarsListener(), this);
        getCommand("bed").setExecutor(new BedWarsCommand());
    }
}
