package qwezxc.asd;

import com.google.common.collect.Lists;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;


import java.util.*;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.potion.*;
import qwezxc.asd.Data.Database;
import qwezxc.asd.command.BalanceCommand;
import qwezxc.asd.command.teamcmd;
import qwezxc.asd.command.testcmd;
import qwezxc.asd.core.Economy;
import qwezxc.asd.core.KOTH;
import qwezxc.asd.core.PluginManager;
import qwezxc.asd.core.PluginScoreboardManager;
import qwezxc.asd.listener.OreRegeneration;
import qwezxc.asd.listener.PlayerJoinListener;
import qwezxc.asd.listener.TeamMenuListener;


public final class Asd extends JavaPlugin implements Listener {
    private static Asd instance;
    private Database database;
    private Economy economy;
    private PluginScoreboardManager scoreboardManager;
    public World world;

    private Map<UUID, Integer> minutesInCenter = new HashMap<>();
    private PluginManager pluginManager;
    public KOTH koth;
    public double kothRadius = 5.0;

    private OreRegeneration oreRegen;
    Teams teams = new Teams();
    public Map<UUID, Team> playerTeams = teams.getPlayers();
    @Override
    public void onLoad() {

        instance = this;
    }

    @Override
    public void onEnable() {
        checkWorldsOnServer();
        world = Bukkit.getWorld("world");
        this.pluginManager = PluginManager.getInstance();
        this.database = new Database();
        this.economy = new Economy();
        this.teams = new Teams();
        this.scoreboardManager = new PluginScoreboardManager();
        getServer().getPluginManager().registerEvents(new TeamMenuListener(teams), this);

        koth = new KOTH(this,teams);


        Bukkit.getPluginManager().registerEvents(this, this);
        oreRegen = new OreRegeneration(this);
        getServer().getPluginManager().registerEvents(oreRegen, this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);


        saveDefaultConfig();


        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("pickaxe").setExecutor(new teamcmd(this));
        getCommand("testcmd").setExecutor(new testcmd(this));
        boolean npcinworldtrader = getConfig().getBoolean("npcinworldtrader");
        for (NPC npc : Lists.newArrayList(CitizensAPI.getNPCRegistry())) {
            if (!npc.getName().equals("Trader")) {
                getConfig().set("npcinworldtrader", false);
            } else {
                getConfig().set("npcinworldtrader", true);
            }
        }
        if (npcinworldtrader == false) {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "Trader");
            npc.spawn(new Location(Bukkit.getWorld("world"), 0, 41, 80));
            getConfig().set("npcinworldtrader", true);
        }
        boolean npcinworldseller = getConfig().getBoolean("npcsellerinworld");
        for (NPC npc : Lists.newArrayList(CitizensAPI.getNPCRegistry())) {
            if (!npc.getName().equals("Seller")) {
                getConfig().set("npcsellerinworld", false);
            } else {
                getConfig().set("npcsellerinworld", true);
            }
        }
        if (npcinworldseller == false) {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "Seller");
            npc.spawn(new Location(Bukkit.getWorld("world"), 0, 41, 70));
            getConfig().set("npcsellerinworld", true);
        }
        saveConfig();

    }

    public void checkWorldsOnServer() {
        List<World> worlds = getServer().getWorlds();
        for (World world : worlds) {
            System.out.println("World on server: " + world.getName());
        }
    }

    @EventHandler
    public void onNPCInteract(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();
        Inventory plinv = player.getInventory();
        if (npc.getName().equals("Trader")) {


            Inventory inventory = Bukkit.createInventory(null, 54, "Trader Menu");

            ItemStack speedPotion = new ItemStack(Material.POTION);
            PotionMeta speedPotionItemMeta = (PotionMeta) speedPotion.getItemMeta();
            speedPotionItemMeta.setDisplayName(ChatColor.AQUA + "Speed Potion II");
            speedPotionItemMeta.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
            List<String> speedlore = new ArrayList<>();
            speedlore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            speedlore.add(ChatColor.GRAY + "x1 Speed II Potion");
            speedlore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            speedlore.add(ChatColor.WHITE + "Price: " + ChatColor.GREEN + "$10");
            speedPotionItemMeta.setLore(speedlore);
            speedPotion.setItemMeta(speedPotionItemMeta);

            ItemStack healPotion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta healPotionItemMeta = (PotionMeta) healPotion.getItemMeta();
            healPotionItemMeta.setDisplayName(ChatColor.GREEN + "Health Potion II");
            healPotionItemMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
            List<String> heallore = new ArrayList<>();
            heallore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            heallore.add(ChatColor.GRAY + "x1 Health Splash Potion");
            heallore.add(ChatColor.YELLOW + "- " + ChatColor.GREEN + "Right-click to fill your inventory");
            heallore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            heallore.add(ChatColor.WHITE + "Price: " + ChatColor.GREEN + "$5");
            healPotionItemMeta.setLore(heallore);
            healPotion.setItemMeta(healPotionItemMeta);

            ItemStack firePotion = new ItemStack(Material.POTION);
            PotionMeta firePotionItemMeta = (PotionMeta) firePotion.getItemMeta();
            firePotionItemMeta.setDisplayName(ChatColor.GOLD + "Fire Resistance Potion (3:00)");
            firePotionItemMeta.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, false, false));
            List<String> firelore = new ArrayList<>();
            firelore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            firelore.add(ChatColor.GRAY + "x1 Fire Resistance Potion");
            firelore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            firelore.add(ChatColor.WHITE + "Price: " + ChatColor.GREEN + "$25");
            firePotionItemMeta.setLore(firelore);
            firePotion.setItemMeta(firePotionItemMeta);

            ItemStack slownesspotion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta slownesspotionItemMeta = (PotionMeta) slownesspotion.getItemMeta();
            slownesspotionItemMeta.setDisplayName(ChatColor.DARK_GRAY + "Slowness Potion (1:07)");
            slownesspotionItemMeta.setBasePotionData(new PotionData(PotionType.SLOWNESS, false, false));
            List<String> slownesslore = new ArrayList<>();
            slownesslore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            slownesslore.add(ChatColor.GRAY + "x1 Slowness Splash Potion");
            slownesslore.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            slownesslore.add(ChatColor.WHITE + "Price: " + ChatColor.GREEN + "$50");

            slownesspotionItemMeta.setLore(slownesslore);
            slownesspotion.setItemMeta(slownesspotionItemMeta);

            //Diamond set + sword = 850$
            //Diamond sword 100$
            //Diamond Chestplate 275$
            //Diamond leggings 250
            //Diamond boots 125
            //Enmder Perl 1=25, 16 =400
            //Steak 16=75,64=300;
            //Invisiblity  Splash при дамаге не снимать инвиз = 1250$
            //Poison 50$ 33 sec
            //Lesser Invisiblite Potion 5 00 min 100$ при дамаге снять инвиз
            inventory.setItem(14, speedPotion);
            inventory.setItem(15, firePotion);
            inventory.setItem(24, healPotion);
            inventory.setItem(34, slownesspotion);

            player.openInventory(inventory);
        }
        if (npc.getName().equals("Seller")){
            Inventory inventory = Bukkit.createInventory(null, 9, "Seller Menu");
            ItemStack coal = new ItemStack(Material.COAL,1);
            ItemMeta coalmeta = coal.getItemMeta();
            coalmeta.setDisplayName(ChatColor.BLUE + "Sell Coal");
            int coalcount = 0;
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.COAL) {
                    coalcount += itemStack.getAmount();
                }
            }
            List<String> coaltext = new ArrayList<>();
            coaltext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            coaltext.add(ChatColor.GRAY + "Left click to sell 1 x Coal for 10$");
            int coalcena = 10*coalcount;
            coaltext.add(ChatColor.GRAY + "Right click to sell " + coalcount +  "x Coal for " + coalcena +"" +"$ ");
            coaltext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            coalmeta.setLore(coaltext);
            coal.setItemMeta(coalmeta);
            ItemStack iron = new ItemStack(Material.IRON_INGOT,1);
            ItemMeta ironmeta = iron.getItemMeta();
            ironmeta.setDisplayName(ChatColor.BLUE + "Sell Iron Ingot");
            int ironcount = 0;
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.IRON_INGOT) {
                    ironcount += itemStack.getAmount();
                }
            }
            List<String> irontext = new ArrayList<>();
            irontext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            irontext.add(ChatColor.GRAY + "Left click to sell 1 x Iron Ingot for 15$");
            int ironcena = 15*ironcount;
            irontext.add(ChatColor.GRAY + "Right click to sell " + ironcount +  "x Iron Ingot for " + ironcena + "$");
            irontext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            ironmeta.setLore(irontext);
            iron.setItemMeta(ironmeta);
            ItemStack diamond = new ItemStack(Material.DIAMOND,1);
            ItemMeta dimondmeta = diamond.getItemMeta();
            dimondmeta.setDisplayName(ChatColor.BLUE + "Sell Diamond");
            int diamondcount = 0;
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.DIAMOND) {
                    diamondcount += itemStack.getAmount();
                }
            }
            List<String> diamonttext = new ArrayList<>();
            diamonttext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            diamonttext.add(ChatColor.GRAY + "Left click to sell 1 x Diamond for 15$");
            int diamondcena = 10*diamondcount;
            diamonttext.add(ChatColor.GRAY + "Right click to sell " + ironcount +  "x Diamond for " + diamondcena + "$");
            diamonttext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            diamonttext.add(ChatColor.GRAY + "ТУТ НЕ ВЕРНАЯ ЦЕНА НАДО ИЗМЕНИТЬ");
            dimondmeta.setLore(diamonttext);
            diamond.setItemMeta(dimondmeta);
            ItemStack gold = new ItemStack(Material.GOLD_INGOT,1);
            ItemMeta goldmeta = gold.getItemMeta();
            goldmeta.setDisplayName(ChatColor.BLUE + "Sell Gold Ingot");
            int goldcount = 0;
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.GOLD_INGOT) {
                    goldcount += itemStack.getAmount();
                }
            }
            List<String> goldtext = new ArrayList<>();
            goldtext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            goldtext.add(ChatColor.GRAY + "Left click to sell 1 x Diamond for 15$");
            int goldcena = 10*goldcount;
            goldtext.add(ChatColor.GRAY + "Right click to sell " + goldcount +  "x Diamond for " + goldcena + "$");
            goldtext.add(ChatColor.GRAY + "―――――――――――――――――――――――――――");
            goldtext.add(ChatColor.GRAY + "ТУТ НЕ ВЕРНАЯ ЦЕНА НАДО ИЗМЕНИТЬ");
            goldmeta.setLore(goldtext);
            gold.setItemMeta(goldmeta);
            inventory.setItem(1,coal);
            inventory.setItem(3,iron);
            inventory.setItem(5,diamond);
            inventory.setItem(7,gold);
            player.openInventory(inventory);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        int emptySlots = -1;
        for (ItemStack i : player.getInventory().getStorageContents()) {
            if (i == null || i.getType() == Material.AIR) {
                emptySlots++;
            }
        }
        if (inventory.getTitle().equals("Trader Menu")) {

            event.setCancelled(true);



            if (item == null) return;

            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Speed Potion II")) {
                if(player.isOnline()) {
                    if (Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(uuid,10)) {
                        if (emptySlots == -1) {
                            player.sendMessage("Inventory is full");
                        } else {
                            Asd.getInstance().getPluginManager().getEconomy().removeBalance(uuid,10);
                            player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 8226));
                            player.sendMessage("You exchanged 1 gold for 1 diamond.");
                        }
                    }
                }
            }
            else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Health Potion II")) {
                if(player.isOnline()) {
                    if (Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(uuid,5)) {
                        if (emptySlots == -1) {
                            player.sendMessage("Inventory is full");
                        } else if (event.getClick() == ClickType.RIGHT) {
                            int rmbhill=0;
                            for (ItemStack i : player.getInventory().getStorageContents()) {
                                if (i == null || i.getType() == Material.AIR) {
                                    player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16421));
                                    player.sendMessage(String.valueOf(emptySlots));
                                    rmbhill++;
                                }
                            }
                            Asd.getInstance().getPluginManager().getEconomy().removeBalance(uuid,5*rmbhill);
                        } else {
                            Asd.getInstance().getPluginManager().getEconomy().removeBalance(uuid,5);
                            player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16421));
                            player.sendMessage("You exchanged 1 gold for 1 diamond.");
                        }
                    }
                }
            }
            else if (item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Fire Resistance Potion (3:00)")) {
                if(player.isOnline()) {
                    if (Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(uuid,25)) {
                        if (emptySlots == -1) {
                            player.sendMessage("Inventory is full");
                        } else {
                            Asd.getInstance().getPluginManager().getEconomy().removeBalance(uuid,25);
                            player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 8195));
                            player.sendMessage("You exchanged 1 gold for 1 diamond.");
                        }
                    }
                }
            }
            else if (item.getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Slowness Potion (1:07)")) {
                if(player.isOnline()) {
                    if (Asd.getInstance().getPluginManager().getEconomy().hasEnoughMoney(uuid,50)) {
                        if (emptySlots == -1) {
                            player.sendMessage("Inventory is full");
                        } else {
                            Asd.getInstance().getPluginManager().getEconomy().removeBalance(uuid,50);
                            player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16394));
                            player.sendMessage("You exchanged 1 gold for 1 diamond.");
                        }
                    }
                }
            }
            else if (item.getItemMeta().getDisplayName() == null){
                return;
            }
            else{
                return;
            }
            if(player.isOnline()){
                player.sendMessage("PROVERKA TI ONLINE");
            }
        }
        if (inventory.getTitle().equals("Seller Menu")) {
            event.setCancelled(true);
            if (item == null) return;
            if (player.isOnline()) {
                if (item.getType() == Material.COAL) {
                    if (player.getInventory().contains(Material.COAL)) {
                        if (event.getClick() == ClickType.RIGHT) {
                            int coalcountinsellerforrightclick = 0;
                            for (ItemStack itemStack : player.getInventory().getContents()) {
                                if (itemStack != null && itemStack.getType() == Material.COAL) {
                                    coalcountinsellerforrightclick += itemStack.getAmount();
                                }
                            }
                            player.getInventory().removeItem(new ItemStack(Material.COAL, coalcountinsellerforrightclick));
                            int addcoalbalance = coalcountinsellerforrightclick * 10;
                            Asd.getInstance().getPluginManager().getEconomy().addBalance(uuid, addcoalbalance);
                            player.sendMessage(String.valueOf(addcoalbalance));
                        } else if (event.getClick() == ClickType.LEFT) {
                            player.getInventory().removeItem(new ItemStack(Material.COAL, 1));
                            int addcoalbalance = 10;
                            Asd.getInstance().getPluginManager().getEconomy().addBalance(uuid, addcoalbalance);
                            player.sendMessage(String.valueOf(addcoalbalance));
                        }
                    }
                }
                if (item.getType() == Material.IRON_INGOT) {
                    if (player.getInventory().contains(Material.IRON_INGOT)) {
                        if (event.getClick() == ClickType.RIGHT) {
                            int ironcount = 0;
                            for (ItemStack itemStack : player.getInventory().getContents()) {
                                if (itemStack != null && itemStack.getType() == Material.IRON_INGOT) {
                                    ironcount += itemStack.getAmount();
                                }
                            }
                            player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, ironcount));
                            int addironbalance = ironcount * 15;
                            Asd.getInstance().getPluginManager().getEconomy().addBalance(uuid, addironbalance);
                            player.sendMessage(String.valueOf(addironbalance));
                        } else if (event.getClick() == ClickType.LEFT) {
                            player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, 1));
                            int addironbalance = 15;
                            Asd.getInstance().getPluginManager().getEconomy().addBalance(uuid, addironbalance);
                            player.sendMessage(String.valueOf(addironbalance));
                        }
                    }
                }
            }
        }

    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(item.getType() == Material.DIAMOND_BLOCK ) {
                event.setCancelled(true);
                Inventory menu = Bukkit.createInventory(null, 9, "Team Select");

                for (Team team : teams.getTeams().values()) {
                    menu.addItem(new ItemStack(team.getWoolBlock(), 1));
                }
                player.openInventory(menu);
            }
        }
    }
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        Player target = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();

        UUID attackerUUID = attacker.getUniqueId();
        UUID defenderUUID = target.getUniqueId();

        if (playerTeams.get(attackerUUID) == playerTeams.get(defenderUUID)) {
            event.setCancelled(true);
            attacker.sendMessage("You can't attack players from the same team.");
        }


    }

    public World getWorld(String name) {
        Iterator<World> worlds = Bukkit.getServer().getWorlds().iterator();
        while(worlds.hasNext()) {
            World w = worlds.next();
            String wName = w.getName().toLowerCase().trim();
            System.out.print(wName);
            System.out.print(name);
            if(wName.equalsIgnoreCase(name.toLowerCase().trim())) {
                System.out.print("debug");
                return w;
            }
        }

        return null;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLoc = player.getLocation();
        double captureRadius = 4.5;
        Location capturePoint = new Location( Bukkit.getWorld("world"), 10.5, 41.5, 10.5);
        if (Math.abs(playerLoc.getX()- capturePoint.getX()) <= captureRadius  &&
                Math.abs(playerLoc.getY() - capturePoint.getY()) <= captureRadius  &&
                Math.abs(playerLoc.getZ() - capturePoint.getZ()) <= captureRadius ) {
            koth.startCapture(player);
        }else{
            koth.stopCapture(player);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        koth.stopCapture(player);
    }


    public static Asd getInstance() {
        return instance;
    }


    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public void onDisable() {
        Asd.getInstance().getPluginManager().getDatabase().DisableDatabase();
    }
}