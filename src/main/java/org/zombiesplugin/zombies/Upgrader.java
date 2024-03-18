package org.zombiesplugin.zombies;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.zombiesplugin.zombies.Passives.IPassive;
import org.zombiesplugin.zombies.Passives.LightningDamagePassive;
import org.zombiesplugin.zombies.Skills.HellBomb;
import org.zombiesplugin.zombies.Skills.ISkill;
import org.zombiesplugin.zombies.Skills.VinesSkill;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

public class Upgrader {
    private final List<ArmorStand> TextEntities = new ArrayList<ArmorStand>();
    private final List<ISpawnable> Upgrades = new ArrayList<ISpawnable>();
    private final List<ISpawnable> PossibleUpgrades = new ArrayList<ISpawnable>();

    private final Random rnd = new Random();

    private final Arena arena;

    private final int MAX_UPGRADES = 1;

    public Upgrader(Arena arena) {
        this.arena = arena;

        InitPossibleUpgrades();
        RollUpgrades();
    }

    private void InitPossibleUpgrades() {
        Collection<Set<IPassive>> passives = IPassive.Passives.values();

        for (Set<IPassive> passiveSet : passives) {
            PossibleUpgrades.addAll(passiveSet);
        }

        PossibleUpgrades.addAll(ISkill.Skills);
    }

    private void RollUpgrades() {
        Upgrades.clear();

        for(int i = 0; i < MAX_UPGRADES; i++ ){
            ISpawnable rolled = PossibleUpgrades.get(rnd.nextInt(0, PossibleUpgrades.size()));
            Upgrades.add(rolled);
        }
    }

    private String GetRarityColor(UpgradeRarity rarity) {
        switch (rarity){
            case Common: return "§7";
            case Rare: return "§e";
        }

        return "";
    }

    public void UpgradeClicked(Player player) {
        ISpawnable rolledUpgrade = Upgrades.get(0);
        if(rolledUpgrade.Type == UpgradeType.Skill) {
            player.sendMessage(rolledUpgrade.Name);
            rolledUpgrade.Apply(player);
        }
        else {
            IPassive passive = (IPassive)Upgrades.get(0);
            passive.RegisterPlayer(player);
        }

        player.teleport(arena.Locations.Spawn);
        arena.StartNextWave();
    }

    private String GetUpgradeText(ISpawnable upgrade) {
        return String.format("%s %s", GetRarityColor(upgrade.Rarity), upgrade.Name);
    }

    private void SpawnUpgrade() {
        ISpawnable item = Upgrades.get(0);

        Location location = new Location(Bukkit.getPlayer("Puzonne").getWorld(), 325, 43.7, 197.07);

        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        armorStand.setBasePlate(false);
        armorStand.setArms(true);
        armorStand.setGravity(false);
        armorStand.setInvisible(true);
        armorStand.setCustomName(GetUpgradeText(item));
        ItemStack itemStack = new ItemStack(Material.WHITE_WOOL);

        armorStand.getEquipment().setItemInMainHand(itemStack);
        armorStand.setCustomNameVisible(true);

        TextEntities.add(armorStand);
    }

    public void StartUpgrade() {
        RollUpgrades();
        SpawnUpgrade();

        for(Player player : arena.ArenaPlayers) {
            player.teleport(arena.Locations.Upgrade);
        }
    }
 }
