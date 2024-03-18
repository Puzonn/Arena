package org.zombiesplugin.zombies.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zombiesplugin.zombies.ArenasManager;
import org.zombiesplugin.zombies.Arena;

import java.util.Optional;

public class ArenaStartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length > 0) {
                if(args[0].equals("start")) {
                    Optional<Arena> arena = ArenasManager.GetArena(((Player) sender).getPlayer());
                    if(arena.isPresent()){
                        arena.get().OnArenaStart(((Player) sender).getPlayer());
                    }
                    else{
                        ArenasManager.CreateArena(((Player) sender).getWorld())
                                .OnArenaStart(((Player) sender).getPlayer());
                    }
                }
            }
            return true;
        }

        return false;
    }
}
