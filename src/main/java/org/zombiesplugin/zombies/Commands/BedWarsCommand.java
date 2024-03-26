package org.zombiesplugin.zombies.Commands;

import net.minecraft.network.protocol.game.*;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
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
        if (args[0].equals("start")) {
            new BedWars(((Player) commandSender).getWorld()).Start();
        } else if (args[0].equals("spawn")) {
            Player player = (Player) commandSender;

        } else if (args[0].equals("boss")) {
            new TestIslandBoss(((Player) commandSender).getLocation());
        } else if (args[0].equals("create")) {
            String mapName = args[1];
            IslandMap.Maps.putIfAbsent(mapName, new IslandMap());
        } else if (args[0].equals("add")) {
            String mapName = args[1];
            IslandMap map = IslandMap.Maps.get(mapName);
        } else if (args[0].equals("d")) {
            String name = args[1];
            Player player = (Player) commandSender;
            CraftPlayer craftPlayer = ((CraftPlayer) player);
            ClientboundPlayerInfoUpdatePacket d = new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.a.a, craftPlayer.getHandle());
            player.setPlayerListName(name);
        } else if (args[0].equals("s")) {
            Player player = (Player)commandSender;
            player.sendTitle(ChatColor.RED+"A","");
        }

        return  true;
    }
}
