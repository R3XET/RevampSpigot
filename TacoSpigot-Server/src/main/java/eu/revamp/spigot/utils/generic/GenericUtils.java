package eu.revamp.spigot.utils.generic;

import eu.revamp.spigot.utils.chat.color.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class GenericUtils {
    public static int percentPick(int max, int min) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static void setSlots(int slots, JavaPlugin plugin) {
        slots = Math.abs(slots);

        try {
            Object invoke = Class.forName(Bukkit.getServer().getClass().getPackage().getName() + ".CraftServer").getDeclaredMethod("getHandle", new Class[0]).invoke(Bukkit.getServer());
            Field declaredField = invoke.getClass().getSuperclass().getDeclaredField("maxPlayers");

            declaredField.setAccessible(true);
            declaredField.set(invoke, slots);

            changeProperties(slots, plugin);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    public static void changeProperties(int slots, JavaPlugin plugin) {
        Path resolve = Paths.get(plugin.getDataFolder().getParentFile().getAbsolutePath()).getParent().resolve("server.properties");

        try {
            List<String> allLines = Files.readAllLines(resolve);

            for (int i = 0; i < allLines.size(); ++i) {
                if (allLines.get(i).startsWith("max-players")) {
                    allLines.remove(i);
                }
            }

            allLines.add("max-players=" + slots);

            Files.write(resolve, allLines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.translate(message));
    }

    public static Material getMaterial(String source) {
        try {
            return Material.valueOf(source);
        }
        catch (Exception e) {
            return Material.REDSTONE_BLOCK;
        }
    }

    public static <E> List<E> createList(Object object, Class<E> type) {
        List<E> output = new ArrayList<E>();
        if (object != null && object instanceof List) {
            List<?> input = (List<?>) object;
            for (Object value : input) {
                if (value != null) {
                    if (value.getClass() == null) {
                        continue;
                    }
                    if (!type.isAssignableFrom(value.getClass())) {
                        String simpleName = type.getSimpleName();
                        throw new AssertionError(("Cannot cast to list! Key " + value + " is not a " + simpleName));
                    }
                    E e = type.cast(value);
                    output.add(e);
                }
            }
        }
        return output;
    }

    public static <E> Set<E> castSet(Object object, Class<E> type) {
        Set<E> output = new HashSet<E>();
        if (object != null && object instanceof List) {
            List<?> input = (List<?>) object;
            for (Object value : input) {
                if (value != null) {
                    if (value.getClass() == null) {
                        continue;
                    }
                    if (!type.isAssignableFrom(value.getClass())) {
                        String simpleName = type.getSimpleName();
                        throw new AssertionError(("Cannot cast to list! Key " + value + " is not a " + simpleName));
                    }
                    E e = type.cast(value);
                    output.add(e);
                }
            }
        }
        return output;
    }

    public static <K, V> Map<K, V> castMap(Object object, Class<K> keyClass, Class<V> valueClass) {
        Map<K, V> output = new HashMap<K, V>();
        if (object != null && object instanceof Map) {
            Map<?, ?> input = (Map<?, ?>) object;
            String keyClassName = keyClass.getSimpleName();
            String valueClassName = valueClass.getSimpleName();
            Object[] array;
            for (int length = (array = input.keySet().toArray()).length, i = 0; i < length; ++i) {
                Object key = array[i];
                if (key != null && !keyClass.isAssignableFrom(key.getClass())) {
                    throw new AssertionError(("Cannot cast to HashMap: " + keyClassName + ", " + keyClassName + ". Value " + valueClassName + " is not a " + keyClassName));
                }
                Object value = input.get(key);
                if (value != null && !valueClass.isAssignableFrom(value.getClass())) {
                    throw new AssertionError(("Cannot cast to HashMap: " + valueClassName + ", " + valueClassName + ". Key " + key + " is not a " + valueClassName));
                }
                output.put(keyClass.cast(key), valueClass.cast(value));
            }
        }
        return output;
    }
}
