package eu.revamp.spigot;

import eu.revamp.spigot.config.Settings;
import eu.revamp.spigot.handler.AsyncMovementHandler;
import eu.revamp.spigot.handler.AsyncPacketHandler;
import eu.revamp.spigot.knockback.KnockbackManager;
import eu.revamp.spigot.knockback.RevampThread;
import eu.revamp.spigot.logger.RevampLogger;
import jline.console.ConsoleReader;
import org.bukkit.ChatColor;
import org.fusesource.jansi.AnsiConsole;
import org.spigotmc.AsyncCatcher;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class RevampSpigot {

	public static final String NAME = "RevampSpigot 2.0";

	private final Set<AsyncMovementHandler> movementHandlers = new HashSet<>();

	private final Set<AsyncPacketHandler> packetHandlers = new HashSet<>();

	public static final String INFO = ChatColor.translateAlternateColorCodes('&',
			"&7Powerful performance boost and advanced server protection against crash clients and exploits &8| &aRevampSpigot 2.0");


	//private LanguageFile languageFile;
	private final File mainDirectory = new File("RevampSpigot");
	private RevampLogger logger;

	private RevampThread thread;

	private KnockbackManager knockbackManager;

	public RevampSpigot() {
		//languageFile = new LanguageFile();
		//loadLanguages();
	}
/*
	private void loadLanguages() {
		if (this.languageFile == null) {
			return;
		}
		Arrays.stream(Lang.values()).forEach(language -> {
			if (this.languageFile.getString(language.getPath()) == null) {
				if (language.getValue() != null) {
					this.languageFile.set(language.getPath(), language.getValue());
				} else if (language.getListValue() != null && this.languageFile.getStringList(language.getPath()) == null) {
					this.languageFile.set(language.getPath(), language.getListValue());
				}
			}
		});
		this.languageFile.save();
		this.languageFile.load();
	}*/


	public void enable() {
		this.mainDirectory.mkdirs();
		AnsiConsole.systemInstall();
		try {
			ConsoleReader consoleReader = new ConsoleReader();
			consoleReader.setExpandEvents(false);
			File oldLogs = new File(this.mainDirectory, "revamp-logs.log");
			if (oldLogs.exists())
				oldLogs.renameTo(new File(this.mainDirectory, "old-logs.log"));
			File newLogs = new File(this.mainDirectory, "revamp-logs.log");
			newLogs.delete();
			newLogs.createNewFile();
			this.logger = new RevampLogger("RevampSpigot", "RevampSpigot/revamp-logs.log", consoleReader);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Settings.IMP.reload(new File(this.mainDirectory, "config.yml"));
		this.thread = new RevampThread(Settings.IMP.SETTINGS.DEVELOPER_SETTINGS.THREADS_USE_AVAILABLE_RUNTIME_PROCESSORS ? (
				Runtime.getRuntime().availableProcessors() * 2) : Settings.IMP.SETTINGS.DEVELOPER_SETTINGS.THREADS);
		this.thread.loadAsyncThreads();
		this.knockbackManager = new KnockbackManager();
		AsyncCatcher.enabled = Settings.IMP.SETTINGS.DEVELOPER_SETTINGS.ASYNC_CATCHER_ENABLED;
		System.setProperty("io.netty.eventLoopThreads", Integer.toString(Settings.IMP.SETTINGS.DEVELOPER_SETTINGS.NETTY_THREADS));
	}

	public void addMovementHandler(AsyncMovementHandler movementHandler) {
		this.movementHandlers.add(movementHandler);
	}

	public void addPacketHandler(AsyncPacketHandler packetHandler) {
		this.packetHandlers.add(packetHandler);
	}

	public Set<AsyncMovementHandler> getMovementHandlers() {
		return this.movementHandlers;
	}

	public Set<AsyncPacketHandler> getPacketHandlers() {
		return this.packetHandlers;
	}

	public RevampThread getThread() {
		return this.thread;
	}

	public File getMainDirectory() {
		return this.mainDirectory;
	}

	public RevampLogger getLogger() {
		return this.logger;
	}

	public KnockbackManager getKnockbackManager() {
		return this.knockbackManager;
	}

	public static RevampSpigot getInstance() {
		return RevampSpigotHelper.INSTANCE;
	}

	private static final class RevampSpigotHelper {
		private static final RevampSpigot INSTANCE = new RevampSpigot();
	}
}
