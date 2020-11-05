package eu.revamp.spigot.plugin;

import eu.revamp.spigot.config.Settings;
import eu.revamp.spigot.inventory.PhasmatosBukkitInventoryAPI;
import eu.revamp.spigot.inventory.PhasmatosClickableStableInventory;
import eu.revamp.spigot.utils.chat.color.CC;
import eu.revamp.spigot.utils.item.ItemBuilder;
import net.techcable.tacospigot.TacoSpigotConfig;
import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.paperspigot.PaperSpigotConfig;
import org.spigotmc.SpigotConfig;

import java.io.File;

public class RevampSettingsPlugin {
    private static JavaPlugin instance;

    public static PhasmatosClickableStableInventory mainInventory;

    public void enable() {
        instance = new JavaPlugin("RevampSettings") {
            public void onEnable() {
            }
        };
        postEnable();
    }

    public void postEnable() {
        mainInventory = new PhasmatosClickableStableInventory("&cRevampSpigot settings (Main)", 9);
        Settings.SETTINGS.PERFORMANCE performance = Settings.IMP.SETTINGS.PERFORMANCE;
        PhasmatosClickableStableInventory performanceSettings = new PhasmatosClickableStableInventory("&cRevampSpigot settings (Performance)", 36);
        performanceSettings.addItem(performanceSettings.getSize() - 1, (new ItemBuilder(Material.WOOL))
                .setDurability((short) 14)
                .setName("&cBack to the main gui")
                .setLore("&8 > &7Click to go to the main menu").toItemStack());
        performanceSettings.addItemAction(performanceSettings.getSize() - 1, player -> {
            player.closeInventory();
            mainInventory.open(player);
        });
        PhasmatosClickableStableInventory enderpearlSettings = new PhasmatosClickableStableInventory("&cRevampSpigot settings (Enderpearl)", 27);
        enderpearlSettings.addItem(enderpearlSettings.getSize() - 1, (new ItemBuilder(Material.WOOL))
                .setDurability((short) 14)
                .setName("&cBack to the performance gui")
                .setLore("&8 > &7Click to go to the performance menu").toItemStack());
        enderpearlSettings.addItemAction(enderpearlSettings.getSize() - 1, player -> {
            player.closeInventory();
            enderpearlSettings.open(player);
        });
        enderpearlSettings.addItem(12, (new ItemBuilder(Material.ENDER_PEARL))
                .setName("&cAllow Pearl Through CobWeb")
                .setLore("&8 > &7Click to disable/enable &cAllow Pearl Through CobWeb").toItemStack());
        enderpearlSettings.addItemAction(12, player -> {
            if (Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_COBWEB) {
                Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_COBWEB = false;
                player.sendMessage(CC.translate("&8> &7Allow Pearl Through CobWeb has been &cdisabled"));
                return;
            }
            Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_COBWEB = true;
            player.sendMessage(CC.translate("&8> &7Allow Pearl Through CobWe has been &aenabled"));
        });
        enderpearlSettings.addItem(13, (new ItemBuilder(Material.ENDER_PEARL))
                .setName("&cAllow Pearl Through Fence Gate")
                .setLore("&8 > &7Click to disable/enable &cAllow Pearl Through Fence Gate").toItemStack());
        enderpearlSettings.addItemAction(13, player -> {
            if (Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_FENCE_GATE) {
                Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_FENCE_GATE = false;
                player.sendMessage(CC.translate("&8> &7Allow Pearl Through Fence Gate has been &cdisabled"));
                return;
            }
            Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_FENCE_GATE = true;
            player.sendMessage(CC.translate("&8> &7Allow Pearl Through Fence Gate has been &aenabled"));
        });
        enderpearlSettings.addItem(14, (new ItemBuilder(Material.ENDER_PEARL))
                .setName("&cAllow Pearl Through Fence Slab")
                .setLore("&8 > &7Click to disable/enable &cAllow Pearl Through Fence Slab").toItemStack());
        enderpearlSettings.addItemAction(14, player -> {
            if (Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_SLAB) {
                Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_SLAB = false;
                player.sendMessage(CC.translate("&8> &7Allow Pearl Through Fence Slab has been &cdisabled"));
                return;
            }
            Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_SLAB = true;
            player.sendMessage(CC.translate("&8> &7Allow Pearl Through Fence Slab has been &aenabled"));
        });
        enderpearlSettings.addItem(15, (new ItemBuilder(Material.ENDER_PEARL))
                .setName("&cAllow Pearl Through Fence String")
                .setLore("&8 > &7Click to disable/enable &cAllow Pearl Through Fence String").toItemStack());
        enderpearlSettings.addItemAction(15, player -> {
            if (Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_STRING) {
                Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_STRING = false;
                player.sendMessage(CC.translate("&8> &7Allow Pearl Through Fence String has been &cdisabled"));
                return;
            }
            Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_STRING = true;
            player.sendMessage(CC.translate("&8> &7Allow Pearl Through Fence String has been &aenabled"));
        });
        enderpearlSettings.addItem(16, (new ItemBuilder(Material.ENDER_PEARL))
                .setName("&cEndermite Spawning")
                .setLore("&8 > &7Click to disable/enable &cEndermite Spawning").toItemStack());
        enderpearlSettings.addItemAction(16, player -> {
            if (Settings.IMP.SETTINGS.ENDER_PEARL.ENDERMITE_SPAWNING) {
                Settings.IMP.SETTINGS.ENDER_PEARL.ENDERMITE_SPAWNING = false;
                player.sendMessage(CC.translate("&8> &7Endermite Spawning has been &cdisabled"));
                return;
            }
            Settings.IMP.SETTINGS.ENDER_PEARL.ENDERMITE_SPAWNING = true;
            player.sendMessage(CC.translate("&8> &7Endermite Spawning has been &aenabled"));
        });
        performanceSettings.addItem(10, (new ItemBuilder(Material.MONSTER_EGG)).setDurability((short) 3)
                .setName("&cMobAI")
                .setLore("&8 > &7Click to disable/enable &cMobAI").toItemStack());
        performanceSettings.addItemAction(10, player -> {
            if (performance.DISABLE_MOB_ABI) {
                performance.DISABLE_MOB_ABI = false;
                player.sendMessage(CC.translate("&8> &7MobAI has been &aenabled"));
                return;
            }
            performance.DISABLE_MOB_ABI = true;
            player.sendMessage(CC.translate("&8> &7MobAI has been &cdisabled"));
        });
        performanceSettings.addItem(11, (new ItemBuilder(Material.SAND))
                .setName("&cFalling Sand Visibility")
                .setLore("&8 > &7Click to disable/enable &cFalling Sand", "&8 > &7Players will not see falling sad", "&8 > &7Optimizes FPS").toItemStack());
        performanceSettings.addItemAction(11, player -> {
            if (performance.SHOW_FALLING_SAND) {
                performance.SHOW_FALLING_SAND = false;
                player.sendMessage(CC.translate("&8> &7FallingSand visibility has been &cdisabled"));
                return;
            }
            performance.SHOW_FALLING_SAND = true;
            player.sendMessage(CC.translate("&8> &7FallingSand visibility has been &aenabled"));
        });
        performanceSettings.addItem(12, (new ItemBuilder(Material.TNT))
                .setName("&cTNT Particles visibility")
                .setLore("&8 > &7Click to disable/enable &cTNT Particles&7", "&8 > &7Players will not see tnt particles", "&8 > &7Optimizes FPS").toItemStack());
        performanceSettings.addItemAction(12, player -> {
            if (performance.TNT.SHOW_TNT_PARTICLES) {
                performance.TNT.SHOW_TNT_PARTICLES = false;
                player.sendMessage(CC.translate("&8> &7TnT Particles visibility has been &cdisabled"));
                return;
            }
            performance.TNT.SHOW_TNT_PARTICLES = true;
            player.sendMessage(CC.translate("&8> &7TnT Particles visibility has been &aenabled"));
        });
        performanceSettings.addItem(13, (new ItemBuilder(Material.TNT))
                .setName("&cTNT Explosions visibility")
                .setLore("&8 > &7Click to disable/enable &cTNT Particles&7", "&8 > &7Players will not see tnt explosions", "&8 > &7Optimizes FPS").toItemStack());
        performanceSettings.addItemAction(13, player -> {
            if (performance.TNT.DO_NOT_SHOW_TNT_EXPLOSIONS) {
                performance.TNT.DO_NOT_SHOW_TNT_EXPLOSIONS = false;
                player.sendMessage(CC.translate("&8> &7TnT Explosion visibility has been &cdisabled"));
                return;
            }
            performance.TNT.DO_NOT_SHOW_TNT_EXPLOSIONS = true;
            player.sendMessage(CC.translate("&8> &7TnT Explosion visibility has been &aenabled"));
        });
        performanceSettings.addItem(14, (new ItemBuilder(Material.TNT))
                .setName("&cTNT Movement Optimizatio")
                .setLore("&8 > &7Click to disable/enable &cTNT Movement Optimization&7").toItemStack());
        performanceSettings.addItemAction(14, player -> {
            if (performance.TNT.OPTIMIZE_TNT_MOVEMENT) {
                performance.TNT.OPTIMIZE_TNT_MOVEMENT = false;
                player.sendMessage(CC.translate("&8> &7TnT Movement Optimization has been &cdisabled"));
                return;
            }
            performance.TNT.DO_NOT_SHOW_TNT_EXPLOSIONS = true;
            player.sendMessage(CC.translate("&8> &7TnT Movement Optimization has been &aenabled"));
        });
        performanceSettings.addItem(15, (new ItemBuilder(Material.COBBLESTONE))
                .setName("&cFix East West cannons")
                .setLore("&8 > &7Click to disable/enable &cFixing East West Canoons&7").toItemStack());
        performanceSettings.addItemAction(15, player -> {
            if (performance.TNT.FIX_EAST_WEST_CANOONS) {
                performance.TNT.FIX_EAST_WEST_CANOONS = false;
                player.sendMessage(CC.translate("&8> &7Fixing East West Canoons has been &cdisabled"));
                return;
            }
            performance.TNT.FIX_EAST_WEST_CANOONS = true;
            player.sendMessage(CC.translate("&8> &7Fixing East West Canoons has been &aenabled"));
        });
        performanceSettings.addItem(16, (new ItemBuilder(Material.REDSTONE_BLOCK))
                .setName("&cBlock Physics of Redstone")
                .setLore("&8 > &7Click to disable/enable &cBlock Physics of Redstone&7", "&8 > &7Set to false for optimization").toItemStack());
        performanceSettings.addItemAction(16, player -> {
            if (performance.DISABLE_BLOCK_PHYSICS_FOR_REDSTONE) {
                performance.DISABLE_BLOCK_PHYSICS_FOR_REDSTONE = false;
                player.sendMessage(CC.translate("&8> &7Block Physics of Redstone has been &aenabled"));
                return;
            }
            performance.DISABLE_BLOCK_PHYSICS_FOR_REDSTONE = true;
            player.sendMessage(CC.translate("&8> &7Block Physics of Redstone has been &cdisabled"));
        });
        performanceSettings.addItem(20, (new ItemBuilder(Material.ENDER_PEARL))
                .setName("&cEnderPearl settings")
                .setLore("&8 > &7Click to show &cender pearl settings").toItemStack());
        performanceSettings.addItemAction(20, player -> {
            player.closeInventory();
            enderpearlSettings.open(player);
        });
        performanceSettings.addItem(21, (new ItemBuilder(Material.TORCH))
                .setName("&cLoad chunks for light checks")
                .setLore("&8 > &7Click to disable/enable &cLoading chunks for light checks&7", "&8 > &7Set to false for optimization").toItemStack());
        performanceSettings.addItemAction(21, player -> {
            if (performance.LOAD_CHUNKS_FOR_LIGHT_CHECKS) {
                performance.LOAD_CHUNKS_FOR_LIGHT_CHECKS = false;
                player.sendMessage(CC.translate("&8> &7Loading chunks for light checks has been &cdisabled"));
                return;
            }
            performance.LOAD_CHUNKS_FOR_LIGHT_CHECKS = true;
            player.sendMessage(CC.translate("&8> &7Loading chunks for light checks has been &aenabled"));
        });
        performanceSettings.addItem(22, (new ItemBuilder(Material.DIAMOND_ORE))
                .setName("&cAntiXRay")
                .setLore("&8 > &7Click to disable/enable &cAntiXRay&7", "&8 > &7Set to false for optimization").toItemStack());
        performanceSettings.addItemAction(22, player -> {
            if (performance.DISABLE_ANTI_XRAY) {
                performance.DISABLE_ANTI_XRAY = false;
                ((CraftPlayer) player).entity.world.spigotConfig.antiXray = true;
                player.sendMessage(CC.translate("&8> &7AntiXRay has been &aenabled"));
                return;
            }
            ((CraftPlayer) player).entity.world.spigotConfig.antiXray = false;
            performance.DISABLE_ANTI_XRAY = true;
            player.sendMessage(CC.translate("&8> &7AntiXRay has been &cdisabled"));
        });
        performanceSettings.addItem(23, (new ItemBuilder(Material.FEATHER))
                .setName("&cPlayerMoveEvent")
                .setLore("&8 > &7Click to disable/enable &cPlayerMoveEvent&7", "&8 > &7Set to false for optimization", "&8 > &7Disabled kind of move api, so some plugins cannot work").toItemStack());
        performanceSettings.addItemAction(23, player -> {
            if (performance.FIRE_MOVE_EVENTS) {
                performance.FIRE_MOVE_EVENTS = false;
                player.sendMessage(CC.translate("&8> &7PlayerMoveEvent has been &cdisabled"));
                return;
            }
            performance.FIRE_MOVE_EVENTS = true;
            player.sendMessage(CC.translate("&8> &7PlayerMoveEvent has been &aenabled"));
        });/*
        performanceSettings.addItem(24, (new ItemBuilder(Material.REDSTONE_COMPARATOR))
                .setName("&cBlock Redstone Clocks")
                .setLore("&8 > &7Click to disable/enable &cRedstone blocks blocker&7", "&8 > &7Block redstone mechanics that lags").toItemStack());
        performanceSettings.addItemAction(24, player -> {
            if (performance.ANTIREDSTONE_CLOCK) {
                performance.ANTIREDSTONE_CLOCK = false;
                player.sendMessage(CC.translate("&8> &7AntiRedstoneClock has been &cdisabled"));
                return;
            }
            performance.ANTIREDSTONE_CLOCK = true;
            player.sendMessage(CC.translate("&8> &7AntiRedstoneClock has been &aenabled"));
        });*/
        performanceSettings.addItem(performanceSettings.getSize() - 1, (new ItemBuilder(Material.WOOL))
                .setDurability((short) 14)
                .setName("&cBack to the main gui")
                .setLore("&8 > &7Click to go to the main menu").toItemStack());
        performanceSettings.addItemAction(performanceSettings.getSize() - 1, player -> {
            player.closeInventory();
            mainInventory.open(player);
        });
        ItemStack empty = (new ItemBuilder(Material.STAINED_GLASS_PANE))
                .setDurability((short) 3).setName("&7 ").toItemStack();
        for (int i = 0; i < performanceSettings.getSize(); i++) {
            if (performanceSettings.getItems().get(i) == null)
                performanceSettings.addItem(i, empty);
        }
        mainInventory.addItem(3, (new ItemBuilder(Material.HOPPER))
                .setName("&cPerformance settings")
                .setLore(" &8> &7Click to open &cperformance &7settings").toItemStack());
        mainInventory.addItemAction(3, player -> {
            player.closeInventory();
            //mainInventory.open(player);
            performanceSettings.open(player);

        });
        Settings.SETTINGS.ASYNC async = Settings.IMP.SETTINGS.ASYNC;
        PhasmatosClickableStableInventory asyncInventory = new PhasmatosClickableStableInventory("&cRevampSpigot settings (Async)", 27);
        asyncInventory.addItem(12, (new ItemBuilder(Material.DIAMOND_SWORD))
                .setName("&cAsync Hit Detection")
                .setLore("&8 > &7Click to disable/enable &cAsyncHitDetection&7", "&8 > &7Set to true for optimization", "&8 > &7Hits will be not in main thread, which means smooth pvp!").toItemStack());
        asyncInventory.addItemAction(12, player -> {
            if (async.ASYNC_HIT_DETECTION) {
                async.ASYNC_HIT_DETECTION = false;
                player.sendMessage(CC.translate("&8> &7Async Hit Detection has been &cdisabled"));
                return;
            }
            async.ASYNC_HIT_DETECTION = true;
            player.sendMessage(CC.translate("&8> &7Async Hit Detection has been &aenabled"));
        });
        asyncInventory.addItem(14, (new ItemBuilder(Material.STICK))
                .addEnchant(Enchantment.KNOCKBACK, 2)
                .setName("&cAsync Knockback")
                .setLore("&8 > &7Click to disable/enable &cAsyncKnockback&7", "&8 > &7Set to true for optimization", "&8 > &7Knockback will be not in main thread, which means smooth pvp and movement!").toItemStack());
        asyncInventory.addItemAction(14, player -> {
            if (async.ASYNC_KNOCKBACK_DETECTION) {
                async.ASYNC_KNOCKBACK_DETECTION = false;
                player.sendMessage(CC.translate("&8> &7Async Knockback has been &cdisabled"));
                return;
            }
            async.ASYNC_KNOCKBACK_DETECTION = true;
            player.sendMessage(CC.translate("&8> &7Async Knockback has been &aenabled"));
        });
        asyncInventory.addItem(13, (new ItemBuilder(Material.GRASS))
                .setName("&cConcurrent World Support")
                .setLore("&8 > &7Click to disable/enable &cConcurrentWorldSupport&7", "&8 > &7Set to true if your plugins change world in async").toItemStack());
        asyncInventory.addItemAction(13, player -> {
            if (async.CONCURRENT_WORLD_SUPPORT) {
                async.CONCURRENT_WORLD_SUPPORT = false;
                player.sendMessage(CC.translate("&8> &7Concurrent World Support has been &cdisabled"));
                return;
            }
            async.CONCURRENT_WORLD_SUPPORT = true;
            player.sendMessage(CC.translate("&8> &7Concurrent World Support has been &aenabled"));
        });
        asyncInventory.addItem(asyncInventory.getSize() - 1, (new ItemBuilder(Material.WOOL))
                .setDurability((short) 14)
                .setName("&cBack to the main gui")
                .setLore("&8 > &7Click to go to the main menu").toItemStack());
        asyncInventory.addItemAction(asyncInventory.getSize() - 1, player -> {
            player.closeInventory();
            mainInventory.open(player);
        });
        for (int j = 0; j < asyncInventory.getSize(); j++) {
            if (asyncInventory.getItems().get(j) == null)
                asyncInventory.addItem(j, empty);
        }
        mainInventory.addItem(4, (new ItemBuilder(Material.IRON_SWORD))
                .setName("&cAsync settings")
                .setLore(" &8> &7Click to open &casync &7settings", "&8 > &7Async hit detection etc..").toItemStack());
        mainInventory.addItemAction(4, player -> {
            player.closeInventory();
            //mainInventory.open(player);
            asyncInventory.open(player);
        });
        PhasmatosClickableStableInventory reloadInventory = new PhasmatosClickableStableInventory("&cRevampSpigot settings (Reload)", 9);
        reloadInventory.addItem(reloadInventory.getSize() - 1, (new ItemBuilder(Material.WOOL))
                .setDurability((short) 14)
                .setName("&cBack to the main gui")
                .setLore("&8 > &7Click to go to the main menu").toItemStack());
        reloadInventory.addItemAction(reloadInventory.getSize() - 1, player -> {
            player.closeInventory();
            mainInventory.open(player);
        });
        reloadInventory.addItem(2, (new ItemBuilder(Material.INK_SACK))
                .setDurability((short) 2)
                .setName("&cReload RevampSpigot config")
                .setLore(" &8> &7Click to reload &crevampspigot config").toItemStack());
        reloadInventory.addItemAction(2, player -> {
            Settings.IMP.save(new File("RevampSpigot", "config.yml"));
            player.sendMessage(CC.translate("&8> &7Reloaded &erevampspigot config.yml&7!"));
        });
        reloadInventory.addItem(3, (new ItemBuilder(Material.INK_SACK))
                .setDurability((short) 3)
                .setName("&cReload PaperSpigot config")
                .setLore(" &8> &7Click to reload &cpaperspigot config").toItemStack());
        reloadInventory.addItemAction(3, player -> {
            PaperSpigotConfig.init(new File("paper.yml"));
            player.sendMessage(CC.translate("&8> &7Reloaded &cpaper.yml&7!"));
        });
        reloadInventory.addItem(5, (new ItemBuilder(Material.INK_SACK))
                .setDurability((short) 5)
                .setName("&cReload Spigot config")
                .setLore(" &8> &7Click to reload &cspigot config").toItemStack());
        reloadInventory.addItemAction(5, player -> {
            SpigotConfig.init(new File("spigot.yml"));
            player.sendMessage(CC.translate("&8> &7Reloaded &cspigot.yml&7!"));
        });
        reloadInventory.addItem(6, (new ItemBuilder(Material.INK_SACK))
                .setDurability((short) 6)
                .setName("&cReload Taco config")
                .setLore(" &8> &7Click to reload &ctaco config").toItemStack());
        reloadInventory.addItemAction(6, player -> {
            TacoSpigotConfig.init(new File("taco.yml"));
            player.sendMessage(CC.translate("&8> &7Reloaded &ctaco.yml&7!"));
        });
        mainInventory.addItem(5, (new ItemBuilder(Material.PAPER))
                .setName("&cReload settings")
                .setLore(" &8> &7Click to open &creloading menu").toItemStack());
        mainInventory.addItemAction(5, player -> {
            player.closeInventory();
            //mainInventory.open(player);
            reloadInventory.open(player);
        });
        int k;
        for (k = 0; k < mainInventory.getSize(); k++) {
            if (mainInventory.getItems().get(k) == null)
                mainInventory.addItem(k, empty);
        }
        for (k = 0; k < reloadInventory.getSize(); k++) {
            if (reloadInventory.getItems().get(k) == null)
                reloadInventory.addItem(k, empty);
        }
        PhasmatosBukkitInventoryAPI inventoryAPI = new PhasmatosBukkitInventoryAPI();
        inventoryAPI.addInventory(mainInventory);
        inventoryAPI.addInventory(performanceSettings);
        inventoryAPI.addInventory(asyncInventory);
        inventoryAPI.addInventory(reloadInventory);
        inventoryAPI.addInventory(enderpearlSettings);
        inventoryAPI.register(instance);
    }
}
