package eu.revamp.spigot.config;

import java.io.File;

public class Settings extends Config {
    @Ignore
    public static final Settings IMP = new Settings();

    @Comment({"RevampSpigot", "Made by R3XET, Discord: R3XET#0852", "The best TacoSpigot fork ever."})
    @Create
    public SETTINGS SETTINGS;

    @Create
    public PACKET_LIMITER PACKET_LIMITER;

    @Create
    public MESSAGES MESSAGES;

    @Comment({"Just settings, configure it however you want"})
    public static class SETTINGS {

        public boolean KNOCKBACK_PROFILES_ENABLED = true;

        @Comment({"Should we block worlddownloader mod that steals maps/worlds?"})
        public boolean ANTI_WORLD_DOWNLOADER = true;

        @Comment({"Should we autorespawn players after they die?"})
        public boolean AUTO_RESPAWN = true;

        @Comment({"Should we kick players on server stop?"})
        public boolean KICK_PLAYERS_ON_STOP = true;

        @Create
        public ANTICRASH ANTICRASH;

        @Create
        public ENDER_PEARL ENDER_PEARL;

        @Create
        public PERFORMANCE PERFORMANCE;

        @Create
        public DEVELOPER_SETTINGS DEVELOPER_SETTINGS;

        @Create
        public ASYNC ASYNC;

        @Create
        public COMMANDS COMMANDS;

        @Create
        public ENTITY ENTITY;

        @Create
        public MISCELLANOUS MISCELLANOUS;

        public static class ANTICRASH {
            @Comment({"This is optional option, please do not change packet length limits if not required!"})
            public int MIN_FIRST_LOGIN_PACKET_LENGTH = 7;

            public int MAX_FIRST_LOGIN_PACKET_LENGTH = 1300;

            public int MIN_SECOND_LOGIN_PACKET_LENGTH = 3;

            public int MAX_SECOND_LOGIN_PACKET_LENGTH = 66;
        }

        public static class ENDER_PEARL {
            public boolean ALLOW_PEARL_THROUGH_STRING = true;

            public boolean ALLOW_PEARL_THROUGH_FENCE_GATE = true;

            public boolean ALLOW_PEARL_THROUGH_COBWEB = true;

            public boolean ALLOW_PEARL_THROUGH_SLAB = true;

            public boolean ENDERMITE_SPAWNING = true;
        }

        public static class PERFORMANCE {

            @Create
            public TICKS TICKS;

            @Create
            public TNT TNT;

            @Comment({"Should we fire PlayerMoveEvent? It is fired everytime player moves his mouse!", "Disabling this thing can really optimize your server performance!"})
            public boolean FIRE_MOVE_EVENTS = false;

            @Comment({"Should we show falling blocks like sand?", "Disabling it can really optimize players fps, and does not have any influence on their game, they will just not see animation of falling blocks.", "Disabling it is just like weaker graphics option that very optimizes FPS"})
            public boolean SHOW_FALLING_SAND = false;

            @Comment({"Kepping it true can optimize server performance (redstone mechanics)."})
            public boolean DISABLE_BLOCK_PHYSICS_FOR_REDSTONE = true;

            @Comment({"Kepping it false can optimize server performance."})
            public boolean LOAD_CHUNKS_FOR_LIGHT_CHECKS = false;

            @Comment({"If there was an issue loading the chunk from region, stage1 will fail and stage2 will load it sync"})
            public boolean LOAD_CHUNK_AGAIN_IF_NULL = true;

            public boolean DISABLE_ANTI_XRAY = true;

            @Comment({"Want to see falling blocks? Reduces fps if true."})
            public boolean DO_NOT_SHOW_FALLING_BLOCKS = true;

            @Comment({"Disables mobs AI - can optimize server performance if true."})
            public boolean DISABLE_MOB_ABI = true;

            public boolean ONLY_DISABLE_MOB_AI_FOR_SPAWNER_MOB;

            @Comment({"Should we use PandaWire redstone algorithm?"})
            public boolean ALTERNATIVE_REDSTONE_ALGORITHM = true;

            public boolean FIRE_TNT_DISPENSE_EVENT = false;

            @Comment({"Should we optimize regions? - can optimize server performance if true."})
            public boolean OPTIMIZE_REGION = true;

            @Comment({"Can leaves decay? - can optimize server performance if false."})
            public boolean LEAVES_DECAY = true;

            @Comment({"Should we enable lava to cobblestone?"})
            public boolean LAVA_TO_COBBLE = true;

            @Comment({"Should we enable chunk unloading?"})
            public boolean DO_CHUNK_UNLOAD = true;

            @Comment({"Should we disable nether portal creation?"})
            public boolean DISABLE_NETHER_PORTALS_CREATION = true;

            @Comment({"Should we duse non randomize dispensers?"})
            public boolean USE_NON_RANDOMIZE_DISPENSER = true;

            @Comment({"Should we disable physics on block place?"})
            public boolean DISABLE_PHYSICS_PLACE = true;

            @Comment({"Should we disable physics update?"})
            public boolean DISABLE_PHYSICS_UPDATE = true;

            public static class TICKS {
                public int TICKS_BETWEEN_ENTITY_DESPAWN_CHECKS = 6;

                public int TICKS_BETWEEN_HOPPER_SEARCH = 10;

                public int TICKS_BETWEEN_ITEM_MERGE_SEARCH = 25;

                public int TICKS_BETWEEN_ITEM_BURN_CHECK = 25;

                public int TICKS_BETWEEN_ENTITY_STUCK_DAMAGE_CHECK = 3;

                public int TICKS_BETWEEN_ENTITY_WATER_CHECKS = 3;

                //public int TICKS_BETWEEN_DISABLED_ENTITY_CHECK = 30;
            }

            public static class TNT {

                @Comment({"Should tnt be invisible?"})
                public boolean INVIS_TNT = true;

                @Comment({"Should sand be invisible?"})
                public boolean INVIS_SAND = true;

                @Comment({"Should we combine TNT?"})
                public boolean COMBINE_TNT = true;

                @Comment({"Should we use old liquid flow mechanics TNT?"})
                public boolean USE_OLD_LIQUID_FLOW_MECHANICS = true;

                @Comment({"Should we use old better sponge mechanics?"})
                public boolean BETTER_SPONGE_MECHANICS = true;

                public int SPONGE_RADIUS = 3;

                @Comment({"Can TNT be merged?"})
                public boolean TNT_MERGE = true;

                @Comment({"Can water break redstone items?"})
                public boolean WATER_BREAKS_REDSTONE_ITEMS = true;

                @Comment({"Can fluids generate blocks?"})
                public boolean BLOCK_GENERATION_FROM_LIQUIDS = true;

                @Comment({"Should we show tnt particles to players?", "Disabling it really optimizes players FPS drops while tnt explosions.", "Disabling it just does not particles etc. only the broken blocks, it is just like weaker graphics."})
                public boolean SHOW_TNT_PARTICLES = false;

                public boolean FORCE_DISABLE_TNT_LIMIT = true;

                public boolean DISABLE_LEFT_SHOOTING = true;

                public double LEFT_SHOOTING_AXEIS_LOCK_DISTANCE = 80.0;

                public boolean DESTROY_TNT_AND_FALLING_BLOCKS_OUTSIDE_BORDER = true;

                public int TICKS_BEFORE_FALLING_BLOCK_36_ARE_REMOVED = 400;

                public boolean INFINITE_WATER_SOURCES = true;

                @Comment({"Should we disable roof cannons? Set to enable if you are using bannerboard!!!"})
                public boolean DISABLE_ROOF_CANNONS = true;

                @Comment({"Should we disable fencegates sand plates? Set to enable if you are using bannerboard!!!"})
                public boolean DISABLE_FENCEGATES_SAND_PLATES = true;

                public boolean TNT_PRIME_SOUND = false;

                public boolean DISPENSER_DISPENSE_SOUND = false;

                public boolean DISPENSER_DISPENSE_PARTICLES = false;

                @Comment({"Want to see tnt explosions? Reduces fps if true."})
                public boolean DO_NOT_SHOW_TNT_EXPLOSIONS = true;

                @Comment({"Should we optimize tnt movement?"})
                public boolean OPTIMIZE_TNT_MOVEMENT = true;

                public boolean FIX_EAST_WEST_CANOONS = true;
            }
        }

        public static class DEVELOPER_SETTINGS {

            public boolean THREADS_USE_AVAILABLE_RUNTIME_PROCESSORS = true;

            public int THREADS = 20;

            public int TPS = 20;

            public int THREAD_TPS = 20;

            public int NETTY_THREADS = 4;

            public int CHUNK_THREADS = 2;

            public int CHUNK_UNLOAD_DELAY = 5000;

            public boolean TCP_NO_DELAY = true;

            public boolean IP_ADDRESSES_HIDDEN = true;

            public boolean ASYNC_CATCHER_ENABLED = true;
        }

        public static class ASYNC {
            public boolean ASYNC_HIT_DETECTION = true;

            public boolean ASYNC_KNOCKBACK_DETECTION = true;

            public boolean AFFINITY_THREADS = false;

            public boolean ALLOW_ASYNC_DISPATCH_COMMANDS = true;

            public boolean CONCURRENT_WORLD_SUPPORT = false;

            public boolean ASYNC_BANS_AND_PROFILES_LOAD = true;
        }

        public static class COMMANDS {
            @Comment({"Should we add commands like /kill, /gamemode etc?"})
            public boolean REGISTER_VANILLA_COMMANDS = true;

            public boolean REGISTER_OP_AND_DEOP_COMMANDS = true;

            @Comment({"Should we add our /killentities command? Permission bukkit.command.killentities"})
            public boolean ADD_KILL_ENTITIES_COMMAND = true;

            @Comment({"Should we add our /ping command? Permission revamppigot.command.ping"})
            public boolean ADD_PING_COMMAND = true;

            @Comment({"Should we add our /setslots command? Permission bukkit.command.setslots"})
            public boolean ADD_SET_SLOTS_COMMAND = true;

            @Comment({"Should we add our /fps command?"})
            public boolean ADD_FPS_COMMAND = true;
        }

        public static class ENTITY {
            public int ENTITY_ACTIVATION_RANGE_DELAY = 5;

            public boolean SPAWNER_BLOCK_CHECKS = true;

            @Comment({"Should spawners act as sponges?"})
            public boolean SPAWNERS_ACTS_AS_SPONGES = true;

            @Comment({"Should we prevent entity spawn in water?"})
            public boolean WATER_PREVENT_SPAWN_CHECK = true;

            public boolean PISTON_BREAK_PUMPKIN_MELON = true;

        }

        public static class MISCELLANOUS {

            public boolean USE_MORE_EFFICIENT_HOPPER_SEARCH;

            public boolean DISABLE_MERGE_CHECK_ON_ITEM_MOVE;

            public boolean DISABLE_ITEM_BURN_CHECK_ON_MOVE;

            public boolean DISABLE_ITEM_WATER_PARTICLES_CHECK;

            public boolean DISABLE_MERGE_SEARCH_FOR_MAX_STACKED_ITEMS;

            //public boolean CREEPER_DAMAGE_OBSIDIAN;

            public boolean DO_ENTITIES_CREATE_WATER_SPLASH_PARTICLES;

            public boolean DO_ENTITIES_PICK_UP_ITEMS;

        }
    }



    public static class PACKET_LIMITER {
        @Create
        public TNT_SAND TNT_SAND;

        public boolean ENABLED = true;

        public int PACKETS_PER_SECOND = 500;

        @Comment({"Should we limit arm animation packets?"})
        public boolean ARM_ANIM_ENABLED = false;

        public boolean ALLOW_SCHEMATIC_PRINTER = false;

        public boolean LEVER_TIMESTAMP_ENABLED = false;

        @Comment({"In milliseconds. 1500ms is 1.5 second"})
        public int LEVER_TIMESTAMP = 1500;

        public String LEVER_MESSAGE = "&cYou are using lever too fast! Wait a few seconds please.";

        public static class TNT_SAND {
            public boolean TNT_SAND_PACKET_LIMITER_ENABLED = true;
            public long MAX_TNT_AND_SAND_PPS = 300L;
        }
    }

    public static class MESSAGES {
        @Comment({"/ping command message"})
        public String PING_COMMAND_YOURSELF = "&cYour ping is: &3{PING}";

        @Comment({"/ping command message"})
        public String PING_COMMAND_OTHER = "&cPing of &3{PLAYER}&c is &3{PING}";

        public String PING_COMMAND_PLAYER_IS_OFFLINE = "&cPlayer &3{PLAYER} &cis offline!";

        //@Comment({"Message when player joins the server. Set to empty to display nothing."})
        //public String JOIN_MESSAGE = "&cPlayer &3{PLAYER} &chas joined!";

        //@Comment({"Message when player quits the server. Set to empty to display nothing."})
        //public String QUIT_MESSAGE = "&cPlayer &3{PLAYER} &chas left!";


        @Comment({"Disable join / leave messages?"})
        public boolean DISABLE_JOIN_LEAVE_MESSAGES = true;

        @Comment({"Disable death messages?"})
        public boolean DISABLE_DEATH_MESSAGES = true;

        @Comment({"Message when the servers shut down."})
        public String SHUTDOWN_MESSAGE = "&c&lThe server is restarting...";

    }

    public void reload(File file) {
        load(file);
        save(file);
    }
}

