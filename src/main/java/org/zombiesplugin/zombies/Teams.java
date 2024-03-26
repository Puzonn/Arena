package org.zombiesplugin.zombies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Teams {
	Map<TeamColors, List<Player>> teaMap = new HashMap<>();
	
	public Teams() {
		Player[] players = Bukkit.getOnlinePlayers().stream().map(e -> (Player)e)
                .toArray(Player[]::new);
		
		Random rnd = new Random();
		
		TeamColors pickColors = TeamColors.None;
		
		List<Player> _ = new ArrayList<>();
		for(int i = 0; i<16 ; i++) {
			if(players.length -1 < i) {
				break;
			}
			
			if(i%4 == 0) {
				pickColors = pickColors.GetNext();
			}
			
			switch (pickColors) {
			case Red:
				players[i].setCustomName(String.format("%s%s", "AHA",players[i].getName()));
				break;
			case Blue:
				players[i].setCustomName(String.format("%s%s", "AHA",players[i].getName()));
				break;
			case Yellow:
				players[i].setCustomName(String.format("%s%s", "AHA",players[i].getName()));
				break;
			case Green:
				players[i].setCustomName(String.format("%s%s", "AHA",players[i].getName()));
				break;
			

			default:
				break;
			}
			
			_.add(players[i]);
		}
		teaMap.put(pickColors, _);
		
		Bukkit.getPlayer("Xwaw").sendMessage(teaMap.toString());
	}
}
