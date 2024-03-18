package org.zombiesplugin.zombies;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.*;

public class Arena {
    private final Random rnd = new Random();
    private boolean ArenaStarted = false;

    private final List<LivingEntity> WaveEntities = new ArrayList<LivingEntity>();
    public final List<Player> ArenaPlayers = new ArrayList<>();

    private final ArmorStand SpawnTextInfo;

    private World world;

    private int MaxWave = 0;
    private int CurrentWave = 0;
    private int WaveEntityKilledCount = 0;

    public final ArenaLocations Locations;
    public final Upgrader upgrader;

    public Arena(World arenaWorld) {
        world = arenaWorld;
        Locations = new ArenaLocations(new Location(world, 242, 38, 209), new Location(world, 324, 43 ,196));
        Locations.AddEntitySpawnLocation(new Location(world, 225, 38, 198));
        SpawnTextInfo = CreateSpawnText();
        upgrader = new Upgrader(this);
    }

    public List<LivingEntity> GetWaveEntities() {
        return WaveEntities;
    }

    public void AddEntity(LivingEntity entity){
        if(!WaveEntities.contains(entity)) {
            Bukkit.getLogger().info("Adding entity count: "+WaveEntities.size());
            WaveEntities.add(entity);
        }
    }

    public void OnWaveEntityKilled(LivingEntity entity) {
        WaveEntityKilledCount++;

        ArenaBroadcastMessage(String.format("Entities: %d / %d Wave: %d", WaveEntityKilledCount, WaveEntities.size(), MaxWave));

        if(WaveEntityKilledCount == WaveEntities.size()){
            OnArenaClear();
        }
    }

    public void SpawnEntities() {
        if(!ArenaStarted){
            return;
        }

        WaveEntitySpawner spawner = new WaveEntitySpawner();

        Wave wave = spawner.CreateWave(CurrentWave);

        for(WaveEntity waveEntity : wave.SpawnableEntities){
            Location location = Locations.GetRandomEntitySpawnLocation();
            LivingEntity entity = (LivingEntity) world.spawnEntity(location, waveEntity.Type);
            Monster monster = (Monster)entity;
            entity.setAI(true);
            monster.setTarget(ArenaPlayers.get(0));
            double baseHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            double healthMultiplier = 1.1;
            double scaledHealth = baseHealth * Math.pow(healthMultiplier, CurrentWave - 1);
            entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(scaledHealth);
            entity.setHealth(scaledHealth);
            entity.setCustomNameVisible(true);
            AddEntity(entity);
            SetGlowingEffect(entity);
            UpdateEntity(entity);
        }
    }

    public void UpdateEntity(LivingEntity entity) {
        if(!WaveEntities.contains(entity)){
            return;
        }

        int entityMaxHealth = Math.round((float)entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        int entityHealth = Math.round((float)entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());

        entity.setCustomName(String.format("%d / %d", entityHealth, entityMaxHealth));
    }

    public void UpdateEntityOnDamage(LivingEntity entity, double damage) {
        if(!WaveEntities.contains(entity)){
            return;
        }

        double entityMaxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double entityHealth = entity.getHealth() - damage;

        entity.setCustomName(String.format("§7 %d / %d", Math.round(entityHealth), Math.round(entityMaxHealth)));
    }

    private void SetGlowingEffect(LivingEntity entity) {
        PotionEffect glowing = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, true, false);
        entity.addPotionEffect(glowing);
    }

    public void StartNextWave() {
        ArenaBroadcastMessage("Starting next round");
        UpdateSpawnText();

        /* PlayerDeath have lower priority to be called over this. Check if any LivingEntity is alive which would bse unusual */
        WaveEntities.clear();

        CurrentWave++;
        WaveEntityKilledCount = 0;

        SpawnEntities();
    }

    private void OnArenaClear() {
        if(CurrentWave % 1 == 0){
            upgrader.StartUpgrade();

            return;
        }

        StartNextWave();
    }

    private void ArenaBroadcastMessage(String message){
        for (Player player : ArenaPlayers) {
            player.sendMessage(message);
        }
    }

    public Arena OnArenaStart(Player player) {
        WaveEntities.clear();

        player.teleport(Locations.Spawn);
        ArenaStarted = true;

        if(!ArenaPlayers.contains(player)){
            ArenaPlayers.add(player);
        }

        world = player.getWorld();

        CurrentWave = 1;
        MaxWave = 10;
        WaveEntityKilledCount = 0;

        ArenaBroadcastMessage(String.format("Wave %d / %d", CurrentWave, MaxWave));
        SpawnEntities();

        return this;
    }

    public void OnPlayerDeath(PlayerDeathEvent event) {
        ArenaStarted = false;

        String username = event.getEntity().getName();
        String killer = event.getEntityType().name();
        ArenaBroadcastMessage(String.format("%s died by %s", username, killer));
        Bukkit.getLogger().info("Killing all entites < ");

        Bukkit.getPlayer("Puzonne").sendMessage("Count: "+WaveEntities.size());
        List<LivingEntity> _copy = new ArrayList<>(WaveEntities);
        Bukkit.getPlayer("Puzonne").sendMessage("Count copy: "+_copy.size());

        for(LivingEntity entity : _copy) {
            WaveEntities.remove(entity);
            LivingEntity _e = (LivingEntity) Bukkit.getEntity(entity.getUniqueId());
            _e.damage(_e.getHealth());
        }
    }

    private ArmorStand CreateSpawnText() {
        ArmorStand armorStand = (ArmorStand)Locations.Spawn.getWorld().spawnEntity(Locations.Spawn, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(String.format("Wave %d / %d", CurrentWave, MaxWave));
        armorStand.setGravity(false);
        armorStand.setSmall(true);

        armorStand.setCollidable(false);

        armorStand.setMarker(true);

        return armorStand;
    }

    public void SetWave(int wave) {
        CurrentWave = wave;
    }

    public boolean HasEntity(LivingEntity entity) {
        return WaveEntities.contains(entity);
    }

    private void UpdateSpawnText() {
        SpawnTextInfo.setCustomName(String.format("Wave %d / %d", CurrentWave, MaxWave));
    }
}