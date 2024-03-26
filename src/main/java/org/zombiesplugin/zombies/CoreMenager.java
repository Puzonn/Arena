package org.zombiesplugin.zombies;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.joml.Vector3i;

public class CoreMenager {
	public static List<Vector3i> Cores = new ArrayList<>();
	public void PlaceCore(Location location) {
		location.getWorld().getBlockAt(location).setType(Material.REDSTONE_BLOCK);
		Cores.add(new Vector3i(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
		Bukkit.getPlayer("Xwaw").sendMessage(location.toString());
	}
}
