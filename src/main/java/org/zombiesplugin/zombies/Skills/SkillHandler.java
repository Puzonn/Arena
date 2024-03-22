package org.zombiesplugin.zombies.Skills;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.zombiesplugin.zombies.PlayerMeta.ArenaPlayer;
import org.zombiesplugin.zombies.Zombies;

public class SkillHandler {
    public void HandleSkill(Item item, ArenaPlayer player) {
        if(item.getItemStack().getType() == Material.RED_WOOL) {
            HellBomb hellBomb = new HellBomb(item, player);
            ISkill.ActivatedSkills.add(hellBomb);
            hellBomb.Activate();
        }
        else if(item.getItemStack().getType() == Material.GREEN_WOOL) {
            VinesSkill vineSkill = new VinesSkill(item, player);
            ISkill.ActivatedSkills.add(vineSkill);
            vineSkill.Activate();
        }
        else if(item.getItemStack().getType() == Material.YELLOW_WOOL) {
            BoostSkill boostSkill = new BoostSkill(item, player);
            ISkill.ActivatedSkills.add(boostSkill);
            boostSkill.Activate();
        }
    }
}
