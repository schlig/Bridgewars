package bridgewars.utils;


import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ICustomItem {
	
    enum Rarity {
    	
        NONE(0,0,0),
        WHITE(255,255,255),
        GREEN(0,255,0),
        RED(255,0,0),
        BLUE(0,0,255);
    	
        public final int r;
        public final int g;
        public final int b;
        
        Rarity(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }
    
    Rarity getRarity();
    ItemStack createItem(Player p);
}