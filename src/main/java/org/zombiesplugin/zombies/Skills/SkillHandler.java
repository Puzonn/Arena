package org.zombiesplugin.zombies.Skills;

import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.zombiesplugin.zombies.Zombies;

public class SkillHandler {
    public void onBlockPlaced(BlockPlaceEvent event) {
         if(event.getBlock().getType() == Material.RED_WOOL){
            HellBomb hellBomb = new HellBomb();
            MetadataValue value = new FixedMetadataValue(Zombies.Instance, true);
            event.getBlock().getState().setMetadata("IsSkill", value);
            ISkill.ActivatedSkills.add(hellBomb);
            hellBomb.Activate(event.getBlock().getLocation(), event.getBlock().getWorld());
        }
        else if(event.getBlock().getType() == Material.GREEN_WOOL){
            VinesSkill vinesSkill = new VinesSkill();
            MetadataValue value = new FixedMetadataValue(Zombies.Instance, true);
            event.getBlock().getState().setMetadata("IsSkill", value);
            ISkill.ActivatedSkills.add(vinesSkill);
            vinesSkill.Activate(event.getBlock().getLocation(), event.getBlock().getWorld());
        }
        else if(event.getBlock().getType() == Material.YELLOW_WOOL){
            BoostSkill boostSkill = new BoostSkill();
            MetadataValue value = new FixedMetadataValue(Zombies.Instance, true);
            event.getBlock().getState().setMetadata("IsSkill", value);
            ISkill.ActivatedSkills.add(boostSkill);
            boostSkill.Activate(event.getBlock().getLocation(), event.getBlock().getWorld());
        }
    }
}
