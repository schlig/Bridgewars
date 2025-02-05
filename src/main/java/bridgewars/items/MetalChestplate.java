package bridgewars.items;

import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class MetalChestplate implements ICustomItem {
    @Override
    public Rarity getRarity() {
        return Rarity.NONE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "&rMetal Chestplate");
        ItemBuilder.setUnbreakable(item, true);
        ItemBuilder.setLeatherColor(item, Color.fromRGB(127, 127, 127));
		
		net.minecraft.server.v1_8_R3.ItemStack itemTags = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = itemTags.getTag();
		NBTTagList list = new NBTTagList();
		NBTTagCompound speed = new NBTTagCompound();
		speed.set("AttributeName", new NBTTagString("generic.knockbackResistance"));
		speed.set("Name", new NBTTagString("generic.knockbackResistance"));
		speed.set("Amount", new NBTTagDouble(1));
		speed.set("Operation", new NBTTagInt(0));
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