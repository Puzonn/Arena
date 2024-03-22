package org.zombiesplugin.zombies;

import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArenasManager {
    public final static List<Arena> Arenas = new ArrayList<>();

    public static Arena CreateArena(World world) {
        Arena arena = new Arena(world);
        Arenas.add(arena);

        return arena;
    }

    public static void onMobKilledHandler(EntityDeathEvent event){
        if(event.getEntity().getKiller() == null || !(event.getEntity().getKiller() instanceof Player)){
            Optional<Arena> arena = GetArena(event.getEntity());
            if(arena.isPresent()){
                arena.get().OnWaveEntityKilled((LivingEntity) event.getEntity());
            }
            return;
        }

        Player killer = event.getEntity().getKiller();

        Optional<Arena> arena = GetArena(event.getEntity().getKiller());

        if(arena.isPresent()){
            arena.get().OnWaveEntityKilled(event.getEntity());
        }
    }

    public static void onArenaPlayerKilledHandler(PlayerDeathEvent event) {
        Optional<Arena> arena = GetArena(event.getEntity().getPlayer());

        if(arena.isPresent()){
           arena.get().OnPlayerDeath(event);
        }
    }

    public static void onArenaEntityDamagedHandler(EntityDamageEvent event) {
        for(Arena arena : Arenas) {
            if(arena.HasEntity((LivingEntity) event.getEntity())){
                ArenaEntityNameManager.UpdateName((LivingEntity) event.getEntity(), event.getDamage());
                break;
            }
        }
    }

    public static Optional<Arena> GetArena(Player player) {
        return Arenas.stream().filter(x -> x.ArenaPlayers.contains(player)).findFirst();
    }

    public static Optional<Arena> GetArena(LivingEntity entity) {
        return Arenas.stream().filter(x -> x.HasEntity(entity)).findFirst();
    }
}
