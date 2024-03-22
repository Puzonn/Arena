package org.zombiesplugin.zombies.Skills;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.zombiesplugin.zombies.ISpawnable;
import org.zombiesplugin.zombies.PlayerMeta.ArenaPlayer;
import org.zombiesplugin.zombies.UpgradeType;
import org.zombiesplugin.zombies.Zombies;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ISkill extends ISpawnable {
    public final static Set<ISkill> Skills = new HashSet<>();
    public final static List<ISkill> ActivatedSkills = new ArrayList<>();

    public void Activate(){}

    protected BukkitRunnable runner;
    protected final Item item;
    protected final ArenaPlayer player;

    private ArmorStand textComponent;

    public ISkill(final Item item, final ArenaPlayer player) {
        this.item = item;
        this.player = player;
        this.Type = UpgradeType.Skill;

        if(item != null) {
            ItemMeta meta = item.getItemStack().getItemMeta();
            meta.getPersistentDataContainer().set(new NamespacedKey(Zombies.Instance, "arena_skill"), PersistentDataType.BOOLEAN, true);
            item.getItemStack().setItemMeta(meta);
        }
    }

    public void RemoveTextComponent() {
        if(textComponent != null){
            textComponent.remove();
        }
    }

    public void DestroySkill() {
        RemoveTextComponent();
        ActivatedSkills.remove(this);
        runner.cancel();
        item.remove();
    }

    protected void ChangeTextComponent(String withText){
        if(textComponent != null){
            textComponent.setCustomName(withText);
        }
    }
}
