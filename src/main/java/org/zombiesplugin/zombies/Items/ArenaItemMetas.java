package org.zombiesplugin.zombies.Items;

import org.zombiesplugin.zombies.PlayerMeta.ArenaPlayerMetas;

public class ArenaItemMetas {
    public static ArenaItemMeta PhysicalDamage = new ArenaItemMeta("§ Physical Damage: ", ArenaPlayerMetas.PhysicalDamage, 1f, 3f);
    public static ArenaItemMeta ElementalDamage = new ArenaItemMeta("§ Elemental Damage: ",ArenaPlayerMetas.ElementalDamage, 1f, 3f);
    public static ArenaItemMeta IncreasedSkillSize = new ArenaItemMeta("§ Increased Skill Size: ", ArenaPlayerMetas.IncreasedSkillSize, 1.2f, 2.5f);
}