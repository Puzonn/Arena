package org.zombiesplugin.zombies;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.zombiesplugin.zombies.Commands.ArenaStartCommand;
import org.zombiesplugin.zombies.Commands.BedWarsCommand;
import org.zombiesplugin.zombies.Commands.DevCommands;
import org.zombiesplugin.zombies.PlayerMeta.ArenaPlayer;

public final class Zombies extends JavaPlugin {
    public static Plugin Instance;

    @Override
    public void onEnable() {
        Instance = this;
       // getServer().getPluginManager().registerEvents(new ArenaListener(), this);
        getServer().getPluginManager().registerEvents(new BedWarsListener(), this);
        getCommand("arena").setExecutor(new ArenaStartCommand());
        getCommand("bed").setExecutor(new BedWarsCommand());
        getCommand("dev").setExecutor(new DevCommands());

        for(Player player : Bukkit.getOnlinePlayers()) {
            ArenaPlayer.Create(player);
        }
    }
}
