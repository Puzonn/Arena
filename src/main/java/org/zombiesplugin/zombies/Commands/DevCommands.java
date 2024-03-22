package org.zombiesplugin.zombies.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zombiesplugin.zombies.ArenasManager;
import org.zombiesplugin.zombies.ItemBuilder;
import org.zombiesplugin.zombies.Items.ArenaItem;
import org.zombiesplugin.zombies.Items.ArenaItemMetas;
import org.zombiesplugin.zombies.Mechanics.Forge;
import org.zombiesplugin.zombies.Arena;
import org.zombiesplugin.zombies.PlayerMeta.ArenaPlayer;
import java.util.Map;
import java.util.Optional;

public class DevCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args[0].equals("info")){ // "info" != "info"
            Optional<Arena> arena = ArenasManager.GetArena(((Player)sender).getPlayer());

            if(arena.isPresent()) {
                long aliveCount = arena.get().GetWaveEntities().stream().filter(x->!x.isDead()).count();

                sender.sendMessage("Alive Arena Entities: "+aliveCount);
            }

            ArenaPlayer arenaPlayer = ArenaPlayer.Get(((Player) sender).getPlayer());

            for(Map.Entry<String, Object> entry : arenaPlayer.Metas.entrySet()) {
                sender.sendMessage(String.format("%s %.2f", entry.getKey(), ((double) entry.getValue())));
            }
        }
        else if(args[0].equals("start")) {
            Optional<Arena> arena = ArenasManager.GetArena(((Player) sender).getPlayer());
            if(arena.isPresent()){
                arena.get().OnArenaStart(((Player) sender).getPlayer());

            }
            else{
                ArenasManager.CreateArena(((Player) sender).getWorld())
                        .OnArenaStart(((Player) sender).getPlayer());
            }
        }
        else if(args[0].equals("s")){
            Location location = new Location(Bukkit.getPlayer("Puzonne").getWorld(), 325, 43.7, 197.07);

            ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

            armorStand.setBasePlate(false);
            armorStand.setArms(true);
            armorStand.setGravity(false);
            armorStand.setInvisible(true);

            ItemStack itemStack = new ItemStack(Material.WHITE_WOOL);

            armorStand.getEquipment().setItemInMainHand(itemStack);
            armorStand.setCustomNameVisible(true);
        }
        else if(args[0].equals("e")) {
            Player player = (Player)sender;

            Forge forge = new Forge(player);
            forge.OpenForge();
        }
        else if(args[0].equals("item") && args[1].equals("0")) {
            Player player = (Player) sender;

            ArenaItem arenaItem = new ItemBuilder(new ItemStack(Material.WOODEN_SWORD))
                    .WithName("Warstaff", true)
                    .WithBasePrefixMeta(ArenaItemMetas.ElementalDamage)
                    .WithBasePrefixMeta(ArenaItemMetas.IncreasedSkillSize)
                    .Get(player);

            player.getInventory().addItem(arenaItem.item);
        }
        else if(args[0].equals("c")) {
            Player player = (Player)sender;
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("generic.attackSpeed", 100, Operation.ADD_NUMBER));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", 1, Operation.ADD_NUMBER));
            item.setItemMeta(meta);
        }
        return true;
    }
}
