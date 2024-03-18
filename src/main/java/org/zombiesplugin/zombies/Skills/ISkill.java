package org.zombiesplugin.zombies.Skills;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.zombiesplugin.zombies.ISpawnable;
import org.zombiesplugin.zombies.UpgradeType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ISkill extends ISpawnable {
    public final static Set<ISkill> Skills = new HashSet<>();
    public final static List<ISkill> ActivatedSkills = new ArrayList<>();

    public Location skillLocation;
    public void Activate(Location location, World world){}

    protected BukkitRunnable runner;
    private ArmorStand textComponent;

    public ISkill() {
        this.Type = UpgradeType.Skill;
    }

    public void RemoveTextComponent() {
        if(textComponent != null){
            textComponent.remove();
        }
    }

    public void DestroyEntity() {
        skillLocation.getBlock().setType(Material.AIR);
    }

    public void DestroySkill() {
        RemoveTextComponent();
        DestroyEntity();
        ActivatedSkills.remove(this);
        runner.cancel();
    }

    protected void CreateTextComponent(String withText) {
        Location offsetLocation = new Location(skillLocation.getWorld(), skillLocation.getX()+0.5f, skillLocation.getY()+1, skillLocation.getZ()+0.5f);

        textComponent = (ArmorStand)skillLocation.getWorld().spawnEntity(offsetLocation, EntityType.ARMOR_STAND);
        textComponent.setVisible(false);
        textComponent.setCustomNameVisible(true);
        textComponent.setCustomName(withText);
        textComponent.setGravity(false);
        textComponent.setSmall(true);

        textComponent.setCollidable(false);

        textComponent.setMarker(true);
    }

    protected void ChangeTextComponent(String withText){
        if(textComponent != null){
            textComponent.setCustomName(withText);
        }
    }
}
