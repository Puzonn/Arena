package org.zombiesplugin.zombies.PlayerMeta;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ArenaPlayer {
    private static final Map<Player, ArenaPlayer> ArenaPlayers = new HashMap<>();

    public final Map<String, Object> Metas = new HashMap<>();
    public final Player player;

    private ArenaPlayer(Player player) {
        this.player = player;

        InitMetas();
    }

    public static ArenaPlayer Get(Player player) {
        @Nullable
        ArenaPlayer arenaPlayer = ArenaPlayers.get(player);

        if(arenaPlayer == null) {
            return new ArenaPlayer(player);
        }

        return arenaPlayer;
    }

    public Object GetMeta(String key) {
        return Metas.get(key);
    }

    public void SetMeta(String key, Object value) {
        Metas.put(key, value);
    }

    /* Does not check if player already exist in ArenaPlayers. Use only on main onEnable() */
    public static ArenaPlayer Create(Player player) {
        return new ArenaPlayer(player);
    }

    public static Boolean Contains(Player player) {
        return ArenaPlayers.get(player) != null;
    }

    public void AddArenaPlayer() {
        if(ArenaPlayers.get(player) == null) {
            ArenaPlayers.put(player, this);
        }
    }

    public void InitMetas() {
        Metas.put(ArenaPlayerMetas.PhysicalDamage, 1f);
        Metas.put(ArenaPlayerMetas.ElementalDamage, 1f);

        Metas.put(ArenaPlayerMetas.Intelligence, 0f);
        Metas.put(ArenaPlayerMetas.Strength, 0f);

        Metas.put(ArenaPlayerMetas.SkillSize, 1f);
    }
}
