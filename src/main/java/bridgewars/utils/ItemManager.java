package bridgewars.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import org.bukkit.Bukkit;

import bridgewars.Main;
import bridgewars.items.Axe;
import bridgewars.items.BasicBoots;
import bridgewars.items.BasicChestplate;
import bridgewars.items.BasicHelmet;
import bridgewars.items.BasicLeggings;
import bridgewars.items.BasicSword;
import bridgewars.items.BlackHole;
import bridgewars.items.BottomlessLavaBucket;
import bridgewars.items.BottomlessWaterBucket;
import bridgewars.items.Bow;
import bridgewars.items.BridgeEgg;
import bridgewars.items.ChanceTime;
import bridgewars.items.DisguiseKit;
import bridgewars.items.Eraser;
import bridgewars.items.Fireball;
import bridgewars.items.ForceFieldGenerator;
import bridgewars.items.HeartContainer;
import bridgewars.items.HomeRunBat;
import bridgewars.items.LifeforcePotion;
import bridgewars.items.MagicStopwatch;
import bridgewars.items.MysteryPill;
import bridgewars.items.ParkourCheckpoint;
import bridgewars.items.ParkourQuitter;
import bridgewars.items.ParkourResetter;
import bridgewars.items.ParkourTeleporter;
import bridgewars.items.PitfallTrap;
import bridgewars.items.PortableDoinkHut;
import bridgewars.items.Railgun;
import bridgewars.items.SadTear;
import bridgewars.items.Shears;
import bridgewars.items.SignalJammer;
import bridgewars.items.UltimastPortal;
import bridgewars.items.UnoReverse;
import bridgewars.items.WoolBlocks;

public class ItemManager {
	
    private static File config = new File("./plugins/bridgewars/itempool.ini");
    private static ArrayList<ICustomItem> allItems = new ArrayList<>();
    private static ItemPool activeItems;
    
    //item odds
    //white: 70%
    //green: 20%
    //red: 9%
    //blue: 1%
    private static final float greenPercent = 0.2f;
    private static final float redPercent = 0.09f;
    private static final float bluePercent = 0.01f;
    
    public static void Initialize(Main plugin){
    	
        allItems.add(new ForceFieldGenerator(plugin));
        allItems.add(new BlackHole(plugin));
        allItems.add(new BridgeEgg(plugin));
        allItems.add(new LifeforcePotion(plugin));
        allItems.add(new DisguiseKit(plugin));
        allItems.add(new PortableDoinkHut(plugin));
        allItems.add(new Fireball(plugin));
        allItems.add(new HomeRunBat(plugin));
        allItems.add(new SadTear(plugin));
        allItems.add(new BottomlessWaterBucket(plugin));
        allItems.add(new BottomlessLavaBucket(plugin));
        allItems.add(new Railgun(plugin));
        allItems.add(new Eraser(plugin));
        allItems.add(new UltimastPortal(plugin));
        allItems.add(new UnoReverse(plugin));
        allItems.add(new PitfallTrap(plugin));
        allItems.add(new HeartContainer(plugin));
        allItems.add(new MysteryPill(plugin));
        allItems.add(new MagicStopwatch(plugin));
        allItems.add(new ChanceTime(plugin));
        allItems.add(new SignalJammer(plugin));
        
        allItems.add(new Axe());
        allItems.add(new BasicBoots());
        allItems.add(new BasicChestplate());
        allItems.add(new BasicHelmet());
        allItems.add(new BasicLeggings());
        allItems.add(new BasicSword());
        allItems.add(new Bow());
        allItems.add(new Shears());
        allItems.add(new WoolBlocks());
        allItems.add(new ParkourTeleporter());
        allItems.add(new ParkourResetter());
        allItems.add(new ParkourQuitter());
        allItems.add(new ParkourCheckpoint());
        allItems.sort(Comparator.comparing(ICustomItem::getRarity).thenComparing(o -> o.getClass().getSimpleName()));
        try {
            validateConfig();
        }catch (IOException ioe){
            Bukkit.getLogger().severe(ioe.getMessage());
        }
        generateItemPool(greenPercent, redPercent, bluePercent);
    }
    
    public static void generateItemPool(float greenPercent, float redPercent, float bluePercent){
        activeItems = getActivePool(greenPercent, redPercent, bluePercent);
    }
    
    private static class ItemPool {
        private final float greenPercent;
        private final float redPercent;
        private final float bluePercent;

        ArrayList<ICustomItem> blueItems;
        ArrayList<ICustomItem> redItems;
        ArrayList<ICustomItem> greenItems;
        ArrayList<ICustomItem> whiteItems;
        public ItemPool(float greenPercent, float redPercent, float bluePercent) {
            this.greenPercent = greenPercent;
            this.redPercent = redPercent;
            this.bluePercent = bluePercent;

            whiteItems = new ArrayList<>();
            greenItems = new ArrayList<>();
            redItems = new ArrayList<>();
            blueItems = new ArrayList<>();
        }
        
        public void addItem(ICustomItem item){
            switch (item.getRarity()){
                case WHITE:
                    whiteItems.add(item);
                    break;
                case GREEN:
                    greenItems.add(item);
                    break;
                case RED:
                    redItems.add(item);
                    break;
                case BLUE:
                    blueItems.add(item);
                    break;
                case NONE:
                	return;
            }
        }
        
        private ICustomItem getFromGroup(ArrayList<ICustomItem> itemGroup){
            if(itemGroup.size() <= 0) { return null; }
            return itemGroup.get(Utils.rand(itemGroup.size()));
        }
        
        public ICustomItem getRandomItem(){
            double val = Math.random();
            ICustomItem out;
            if(val < bluePercent){
                out = getFromGroup(blueItems);
            } else if(val < redPercent){
                out = getFromGroup(redItems);
            } else if(val < greenPercent){
                out = getFromGroup(greenItems);
            } else{
                out = getFromGroup(whiteItems);
            }
            return out;
        }
    }
    
    private static void validateConfig() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(config));
        BufferedReader br = new BufferedReader(new FileReader(config));
        String[] names = br.lines().toArray(String[]::new);

        outerLoop:
        for (ICustomItem i : allItems)
        {
            String name = i.getClass().getSimpleName();
            for (String n : names
            ) {
                if(n.substring(1).equals(name)) {
                    continue outerLoop;
                }
            }
            //append this item if not found
            bw.write("1"+name);
            bw.newLine();
        }
        bw.close();
        br.close();
    }
    
    private static ItemPool getActivePool(float greenPercent, float redPercent, float bluePercent){
        ItemPool out = new ItemPool(greenPercent, redPercent, bluePercent);
        try {
            BufferedReader br = new BufferedReader(new FileReader(config));
            String line;
            nextLine:
            while ((line = br.readLine()) != null)
            {
                if(line.charAt(0) == '1'){
                    for(ICustomItem i : allItems){
                        if(i.getClass().getSimpleName().equals(line.substring(1))){
                            out.addItem(i);
                            continue nextLine;
                        }
                    }
                    Bukkit.getLogger().info("Adding item: " + line.substring(1));
                }
            }
            br.close();
        } catch (IOException ioe) {
            Bukkit.getLogger().severe("Error while reading item pool config file!");
        }
        return out;
    }
    
    public static ICustomItem getRandomItem(){
        return activeItems.getRandomItem();
    }
    
    public static ArrayList<String> getItemNames(){
        ArrayList<String> out = new ArrayList<>();
        for (ICustomItem allItem : allItems) {
            out.add(allItem.getClass().getSimpleName());
        }
        return out;
    }
    
    public static ICustomItem getItem(int index){
        return allItems.get(index);
    }
    
    public static ICustomItem getItem(String name){
        for (ICustomItem i : allItems)
            if(i.getClass().getSimpleName().equalsIgnoreCase(name))
                return i;
        
        Bukkit.getLogger().severe("ERROR getting item: " + name);
        return new bridgewars.items.Error();
    }
}