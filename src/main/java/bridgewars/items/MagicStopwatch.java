package bridgewars.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class MagicStopwatch implements ICustomItem, Listener {
	
	public static Map<Player, Double> speedModifier = new HashMap<>();
	
    public MagicStopwatch(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.BLUE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.WATCH, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Magic Stopwatch");
        ItemBuilder.setLore(item, Arrays.asList(
                Chat.color("&r&7Increases movement speed"),
                Chat.color("&r&7Disabled while disguised"),
                Chat.color("&r&7Permanent upgrade")));
        return item;
    }
    
    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR
        || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())
            && GameState.isState(GameState.ACTIVE)) {
            	Player p = e.getPlayer();
            	p.playSound(p.getLocation(), Sound.ANVIL_USE, 1F, 2F);
            	
            	if(!speedModifier.containsKey(p))
            		speedModifier.put(p, 0.0);
            	
            	speedModifier.put(p, speedModifier.get(p) + 0.05);
            	
            	ItemStack boots = p.getInventory().getBoots();
            	boots = addSpeed(boots, p);
            	p.getInventory().setBoots(boots);
            	
            	Utils.subtractItem(p);
            }
        }
    }
	
	public static ItemStack addSpeed(ItemStack item, Player p) {
		
		net.minecraft.server.v1_8_R3.ItemStack itemTags = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = itemTags.getTag();
		NBTTagList list = new NBTTagList();
		NBTTagCompound speed = new NBTTagCompound();
		speed.set("AttributeName", new NBTTagString("generic.movementSpeed"));
		speed.set("Name", new NBTTagString("generic.movementSpeed"));
		speed.set("Amount", new NBTTagDouble(speedModifier.get(p)));
		speed.set("Operation", new NBTTagInt(1));
		UUID uuid = UUID.randomUUID();
		speed.set("UUIDLeast", new NBTTagLong(uuid.getLeastSignificantBits()));
		speed.set("UUIDMost", new NBTTagLong(uuid.getMostSignificantBits()));
		list.add(speed);
		compound.set("AttributeModifiers", list);
		itemTags.setTag(compound);
		item = CraftItemStack.asBukkitCopy(itemTags);
		
		return item;
	}
}
