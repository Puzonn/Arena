package org.zombiesplugin.zombies;

import org.bukkit.entity.EntityType;

class WaveEntity {
    public final EntityType Type;
    public final int FromWave;
    public final boolean IsBoss;

    public WaveEntity(EntityType type, int fromWave, boolean isBoss){
        IsBoss = isBoss;
        Type = type;
        FromWave = fromWave;
    }

    public WaveEntity(EntityType type, int fromWave){
        IsBoss = false;
        Type = type;
        FromWave = fromWave;
    }
}