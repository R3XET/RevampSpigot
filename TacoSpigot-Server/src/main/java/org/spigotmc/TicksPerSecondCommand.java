package org.spigotmc;

import eu.revamp.spigot.RevampSpigot;
import eu.revamp.spigot.utils.chat.color.CC;
import eu.revamp.spigot.utils.date.DateUtils;
import net.jafama.FastMath;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

public class TicksPerSecondCommand extends Command
{

    private final SystemInfo systemInfo = new SystemInfo();

    private final DecimalFormat format = new DecimalFormat("#.##");

    public TicksPerSecondCommand(String name) {
        super(name);
        this.description = "Gets the current ticks per second for the server";
        this.usageMessage = "/tps";
        this.setPermission("bukkit.command.tps");
    }

    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) {
            sender.sendMessage(CC.translate("&cYou do not have permission to perform this command."));
            return true;
        }
        double[] tps = Bukkit.spigot().getTPS();
        String[] tpsAvg = new String[tps.length];
        for (int i = 0; i < tps.length; i++)
            tpsAvg[i] = this.formatTps(tps[i]);
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory() / 1048576L;
        long usedMemory = runtime.totalMemory() / 1048576L;

        try {
            OperatingSystem system = this.systemInfo.getOperatingSystem();
            int processId = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
            OSProcess process = system.getProcess(processId);
            long upTime = process.getUpTime();
            double cpuUsage = Double.parseDouble(this.format.format(process.calculateCpuPercent()).replace(",", "."));
            sender.sendMessage(CC.CHAT_BAR);
            sender.sendMessage(CC.BLUE + "Server Info:");
            sender.sendMessage("");
            sender.sendMessage(CC.GREEN + "  Tps from last 5s, 1m, 5m, 15m: " + StringUtils.join(tpsAvg, ", "));
            sender.sendMessage(CC.AQUA + "» Online players: " + ChatColor.GREEN + Bukkit.getOnlinePlayers().size());
            sender.sendMessage(CC.AQUA + "» Loaded chunks: " + ChatColor.GREEN + (Bukkit.getWorlds().get(0).getLoadedChunks()).length);
            sender.sendMessage("");
            sender.sendMessage(CC.BLUE + "System Info:");
            sender.sendMessage(CC.AQUA + "» Uptime: " + CC.WHITE + ChatColor.GREEN + DateUtils.formatTimeMillis(upTime));
            sender.sendMessage(CC.AQUA + "» CPU Usage: " + CC.WHITE + this.format(cpuUsage, 100.0D) + "%");
            sender.sendMessage(CC.AQUA + "» Ram usage: " + this.format(usedMemory, maxMemory) + "MB" + ChatColor.GRAY + " / " + ChatColor.AQUA + maxMemory + "MB");
            sender.sendMessage("");
            sender.sendMessage(CC.BLUE + "Worlds Info:");
            sender.sendMessage(CC.CHAT_BAR);
            Bukkit.getWorlds().forEach(world -> sender.sendMessage(CC.translate("&7* &3" + world.getName() + " &a(&bLoaded Chunks&7: &3" + world.getLoadedChunks().length + "&7, &bEntities&7: &3" + world.getEntities().size() + "&7, &bLiving Entities&7: &3" + world.getLivingEntities().size() + "&a)")));
            return true;
        } catch (Exception ex) {
            RevampSpigot.getInstance().getLogger().warning("/tps command exception - " + ex.getMessage());
        }
        return true;
    }
        private String formatTps(double tps) {
        return ((tps > 18.0D) ? ChatColor.GREEN : ((tps > 16.0D) ? ChatColor.YELLOW : ChatColor.RED)).toString() + (
                (tps > 20.0D) ? "*" : "") + FastMath.min(FastMath.round(tps * 100.0D) / 100.0D, 20.0D);
    }

    private String format(double used, double max) {
        int half = (int)(max * 50.0D / 100.0D);
        int almostFull = (int)(max * 75.0D / 100.0D);
        int full = (int)(max * 85.0D / 100.0D);
        ChatColor color = ChatColor.GREEN;
        if (used >= full) {
            color = ChatColor.DARK_RED;
        } else if (used >= almostFull) {
            color = ChatColor.RED;
        } else if (used >= half) {
            color = ChatColor.YELLOW;
        }
        return color + String.valueOf(used);
    }
}
