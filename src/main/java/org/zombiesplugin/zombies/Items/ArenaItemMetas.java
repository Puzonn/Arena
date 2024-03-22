package org.zombiesplugin.zombies.Items;

import org.zombiesplugin.zombies.PlayerMeta.ArenaPlayerMetas;

public class ArenaItemMetas {
    public static ArenaItemMeta PhysicalDamage = new ArenaItemMeta("§ Physical Damage: ", ArenaPlayerMetas.PhysicalDamage, 1d, 3d);
    public static ArenaItemMeta ElementalDamage = new ArenaItemMeta("§ Elemental Damage: ",ArenaPlayerMetas.ElementalDamage, 1d, 3d);
    public static ArenaItemMeta IncreasedSkillSize = new ArenaItemMeta("§ Increased Skill Size: ", ArenaPlayerMetas.IncreasedSkillSize, 1.2d, 2.5d);
}