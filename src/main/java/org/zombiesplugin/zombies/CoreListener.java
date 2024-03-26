package org.zombiesplugin.zombies;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.joml.Vector3i;

public class CoreListener implements Listener{
	@EventHandler
	private void onBreakCore(BlockBreakEvent event) {
		if(event.getBlock().getType()!=Material.REDSTONE_BLOCK) {
			return;
		}
		Location location = event.getBlock().getLocation(); 
		if(CoreMenager.Cores.contains(new Vector3i(location.getBlockX(), location.getBlockY(), location.getBlockZ()))) {
		}
	}
}
