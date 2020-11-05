package eu.revamp.spigot.commands;

import eu.revamp.spigot.plugin.RevampSettingsPlugin;
import eu.revamp.spigot.utils.chat.color.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RevampSpigotCommand extends Command{

    public RevampSpigotCommand(String name) {
        super(name);
        this.description = "Shows info about the spigot version";
        this.usageMessage = "/revampspigot";
        setAliases(Arrays.asList("spigot", "paperspigot", "tacospigot"));
    }

    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (sender.hasPermission("revampspigot.settings") && sender instanceof Player) {
            RevampSettingsPlugin.mainInventory.open((Player)sender);
            return true;
        }
        sender.sendMessage(CC.translate("&f&m+---------------------------------------------------+"));
        sender.sendMessage(CC.translate("&b» &7This server is running &fRevampSpigot."));
        sender.sendMessage(CC.translate("&b» &7Version&b: &f2.0"));
        sender.sendMessage(CC.translate("&b» &7Author&b: &fR3XET&b - &fR3XET#0852"));
        sender.sendMessage(CC.translate("&f&m+---------------------------------------------------+"));
        return true;
    }
}
