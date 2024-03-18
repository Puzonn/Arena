package org.zombiesplugin.zombies.Items;

import org.bukkit.Bukkit;

import java.util.Random;

public class ArenaItemMeta {
    public final String DisplayName;
    public final String MetaName;
    public final float MinMetaValue;
    public final float MaxMetaValue;
    public float MetaValue;

    public ArenaItemMeta(String displayName, String metaName, float minValue, float maxValue) {
        DisplayName = displayName;
        MetaName = metaName;
        MaxMetaValue = maxValue;
        MinMetaValue = minValue;

        SetMetaValue();
    }

    public ArenaItemMeta(ArenaItemMeta meta) {
        DisplayName = meta.DisplayName;
        MetaName = meta.MetaName;
        MinMetaValue = meta.MinMetaValue;
        MaxMetaValue = meta.MaxMetaValue;

        SetMetaValue();
    }

    public ArenaItemMeta Create() {
        return new ArenaItemMeta(this);
    }

    private void SetMetaValue() {
        Random rnd = new Random();
        MetaValue = rnd.nextFloat(MinMetaValue, MaxMetaValue);
        Bukkit.getPlayer("Puzonne").sendMessage("New value: " +MetaValue);
    }
}
