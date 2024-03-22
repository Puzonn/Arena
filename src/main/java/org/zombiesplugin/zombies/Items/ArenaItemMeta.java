package org.zombiesplugin.zombies.Items;

import org.bukkit.Bukkit;

import java.util.Random;

public class ArenaItemMeta {
    public final String DisplayName;
    public final String MetaName;
    public final double MinMetaValue;
    public final double MaxMetaValue;
    public double MetaValue;

    public ArenaItemMeta(String displayName, String metaName, double minValue, double maxValue) {
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
        MetaValue = rnd.nextDouble(MinMetaValue, MaxMetaValue);
    }
}
