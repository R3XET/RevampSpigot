package org.bukkit.command.defaults;

//import eu.revamp.spigot.enums.Lang;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class PluginsCommand extends BukkitCommand {
    public PluginsCommand(String name) {
        super(name);
        this.description = "Gets a list of plugins running on the server";
        this.usageMessage = "/plugins";
        this.setPermission("bukkit.command.plugins");
        this.setAliases(Arrays.asList("pl", "plugin"));
    }

    //TODO ADDED CODE
    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) {
            //sender.sendMessage(Lang.NO_PERMISSION.toString());
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&m+---------------------------------------------------+"));
            ComponentBuilder builder = new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', " &fPlugins&b: "));
            Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
            for (int i = 0; i < plugins.length; ++i) {
                if (i + 1 != plugins.length) {
                    builder.append(ChatColor.translateAlternateColorCodes('&', "&7" + plugins[i].getName() + "&b, \n"));
                }
                else {
                    builder.append(ChatColor.translateAlternateColorCodes('&', "&eand &7") + plugins[i].getName());
                }
                builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&fAuthors&b: &7" + plugins[i].getDescription().getAuthors() + "\n" + "&fVersion&b: &7" + plugins[i].getDescription().getVersion() + "\n" + "&fDescription&b: &7" + plugins[i].getDescription().getDescription())).create()));
            }
            sender.sendMessage(builder.create());
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&m+---------------------------------------------------+"));
        }
        return true;
    }
    //TODO ADDED CODE

    //TODO OLD CODE
    /*
    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        sender.sendMessage("Plugins " + getPluginList());
        return true;
    }

    private String getPluginList() {
        StringBuilder pluginList = new StringBuilder();
        Plugin[] plugins = Bukkit.getPluginManager().getPlugins();

        for (Plugin plugin : plugins) {
            if (pluginList.length() > 0) {
                pluginList.append(ChatColor.WHITE);
                pluginList.append(", ");
            }

            pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
            pluginList.append(plugin.getDescription().getName());
        }

        return "(" + plugins.length + "): " + pluginList.toString();
    }*/
    //TODO OLD CODE

    // Spigot Start
    @Override
    public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
    {
        return java.util.Collections.emptyList();
    }
    // Spigot End
}
