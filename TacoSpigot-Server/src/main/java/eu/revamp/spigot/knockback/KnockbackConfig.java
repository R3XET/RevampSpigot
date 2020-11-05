package eu.revamp.spigot.knockback;

import com.google.common.base.Throwables;
import eu.revamp.spigot.RevampSpigot;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.logging.Level;

public class KnockbackConfig {

    public static final File CONFIG_FILE = new File(RevampSpigot.getInstance().getMainDirectory(), "knockback.yml");

    public static YamlConfiguration config;

    public static void init() {
        config = new YamlConfiguration();
        if (!CONFIG_FILE.exists())
            try {
                CONFIG_FILE.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE, "Error whilst creating file knockback.yml", e);
                throw Throwables.propagate(e);
            }
        try {
            config.load(CONFIG_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e2) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load knockback.yml, please fix your syntax errors!", e2);
            throw Throwables.propagate(e2);
        }
        config.options().header("Knockback profiles configuration of RevampSpigot.\n");
        config.options().copyDefaults(true);
        readConfig(KnockbackConfig.class);
    }

    private static void readConfig(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPrivate(method.getModifiers()) && (method.getParameterTypes()).length == 0 && method.getReturnType() == void.class)
                try {
                    method.setAccessible(true);
                    method.invoke(null);
                } catch (InvocationTargetException e) {
                    throw Throwables.propagate(e.getCause());
                } catch (Exception e2) {
                    Bukkit.getLogger().log(Level.SEVERE, "Error invoking " + method, e2);
                }
        }
        try {
            config.save(CONFIG_FILE);
        } catch (IOException e3) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + CONFIG_FILE, e3);
        }
    }

    public static void saveConfig() {
        try {
            config.save(CONFIG_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void set(String path, Object val) {
        config.set(path, val);
    }

    public static boolean getBoolean(String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, config.getBoolean(path));
    }

    public static int getInt(String path, int def) {
        config.addDefault(path, def);
        return config.getInt(path, config.getInt(path));
    }

    public static <T> List getList(String path, T def) {
        config.addDefault(path, def);
        return config.getList(path, config.getList(path));
    }

    public static String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, config.getString(path));
    }

    public static double getDouble(String path, double def) {
        config.addDefault(path, def);
        return config.getDouble(path, config.getDouble(path));
    }

    private static void defaultKnockback() {
        config.set("defaultKnockback", "default");
    }
}

