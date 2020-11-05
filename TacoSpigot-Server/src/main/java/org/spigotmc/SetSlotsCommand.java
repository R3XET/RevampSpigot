package org.spigotmc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;

public class SetSlotsCommand extends Command {

    public SetSlotsCommand(String name) {
        super(name);
        this.description = "Set server slots";
        this.usageMessage = "§7/setslots <amount>";
        this.setPermission("bukkit.command.setslots");
    }
    private void setMaxPlayers(int amount) throws ReflectiveOperationException {
        final Object playerlist = Class.forName("org.bukkit.craftbukkit.CraftServer").getDeclaredMethod("getHandle", (Class<?>[])null).invoke(Bukkit.getServer(), (Object[])null);
        final Field maxplayers = playerlist.getClass().getSuperclass().getDeclaredField("maxPlayers");
        maxplayers.setAccessible(true);
        maxplayers.set(playerlist, amount);
        Properties.setServerProperty(Properties.ServerProperty.MAX_PLAYERS, amount);
        Properties.savePropertiesFile();
    }
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(this.usageMessage);
            return true;
        }
        if (!isInt(args[0])) {
            sender.sendMessage(this.usageMessage);
            return true;
        }
        int slots = Integer.parseInt(args[0]);
        try {
            setMaxPlayers(slots);
        } catch (Exception e) {
            e.printStackTrace( );
        }
        sender.sendMessage("§6Slots updated to §a" + slots);
        return false;
    }

    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
