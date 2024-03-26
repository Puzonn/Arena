package org.zombiesplugin.zombies.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;
import org.zombiesplugin.zombies.BedWars;
import org.zombiesplugin.zombies.Bosses.TestIslandBoss;
import org.zombiesplugin.zombies.Lobby.IslandMap;
import org.zombiesplugin.zombies.Zombies;

public class BedWarsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args[0].equals("start")) {
            new BedWars(((Player)commandSender).getWorld()).Start();
        }
        else if(args[0].equals("spawn")) {
            Player player = (Player)commandSender;
            Villager villager = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
            villager.setAI(false);
            villager.setInvulnerable(true);
            villager.setCustomName(ChatColor.YELLOW + ""+ ChatColor.BOLD+ "Team Upgrades");
            villager.setCustomNameVisible(true);
            villager.setMetadata("villager_upgrade", new FixedMetadataValue(Zombies.Instance, true));
        }
        else if(args[0].equals("boss")) {
            new TestIslandBoss(((Player)commandSender).getLocation());
        }
        else if(args[0].equals("create")) {
            String mapName = args[1];
            IslandMap.Maps.putIfAbsent(mapName, new IslandMap());
        }
        else if(args[0].equals("add")) {
            String mapName = args[1];
            IslandMap map = IslandMap.Maps.get(mapName);
        }
        return false;
    }
}
