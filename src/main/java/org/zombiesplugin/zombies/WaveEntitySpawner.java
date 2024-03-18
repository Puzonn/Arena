package org.zombiesplugin.zombies;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WaveEntitySpawner {
    public final List<Wave> Waves = new ArrayList<Wave>();
    public final List<WaveEntity> AllEntities = new ArrayList<WaveEntity>();
    private Random rnd = new Random();

    public WaveEntitySpawner() {
        AllEntities.add(new WaveEntity(EntityType.ZOMBIE, 1));
        AllEntities.add(new WaveEntity(EntityType.SPIDER, 4));
        AllEntities.add(new WaveEntity(EntityType.SPIDER, 5, true));
        AllEntities.add(new WaveEntity(EntityType.SKELETON, 7));
    }

    public Wave CreateWave(int wave) {
        double growthFactor = 1.2;
        int minSpawn = 2;
        int maxSpawn = 4;
        int mobsToSpawn = (int) (minSpawn + (maxSpawn - minSpawn) * (Math.pow(growthFactor, wave - 1)));

        List<WaveEntity> possibleEntities = AllEntities.stream().filter(x -> x.FromWave <= wave).collect(Collectors.toList());

        Wave cWave = new Wave();
        for(int i=0;i<mobsToSpawn;i++){
            WaveEntity entity = possibleEntities.get(rnd.nextInt(0, possibleEntities.size()));
            cWave.SpawnableEntities.add(entity);
        }

        return cWave;
    }
}
