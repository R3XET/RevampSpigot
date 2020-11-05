package eu.revamp.spigot.config;

import eu.revamp.spigot.RevampSpigot;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Config {

    public Config() {
        save(new ArrayList<>(), getClass(), this, 0);
    }

    private void set(String key, Object value) {
        String[] split = key.split("\\.");
        Object instance = getInstance(split, getClass());
        if (instance != null) {
            Field field = getField(split, instance);
            if (field != null)
                try {
                    if (field.getAnnotation(Final.class) != null)
                        return;
                    if (field.getType() == String.class && !(value instanceof String))
                        value = (new StringBuilder(String.valueOf(value))).toString();
                    field.set(instance, value);
                    return;
                } catch (IllegalAccessException|IllegalArgumentException e) {
                    RevampSpigot.getInstance().getLogger().warning("Error:", e);
                }
        }
        RevampSpigot.getInstance().getLogger().warning("Failed to set config option: {0}: {1} | {2} ",
                key, value, instance);
    }


    public boolean load(File file) {
        YamlConfiguration yml;
        if (!file.exists())
            return false;
        try {
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
                yml = YamlConfiguration.loadConfiguration(reader);
            }
        } catch (IOException ex) {
            RevampSpigot.getInstance().getLogger().warning("Unable to load config.", ex);
            return false;
        }
        this.set(yml, "");
        return true;
    }


    public void set(ConfigurationSection yml, String oldPath) {
        for (String key : yml.getKeys(false)) {
            Object value = yml.get(key);
            String newPath = oldPath + (oldPath.isEmpty() ? "" : ".") + key;
            if (value instanceof ConfigurationSection) {
                set((ConfigurationSection)value, newPath);
                continue;
            }
            set(newPath, value);
        }
    }

    public void save(File file) {
        try {
            File parent = file.getParentFile();
            if (parent != null)
                file.getParentFile().mkdirs();
            Path configFile = file.toPath();
            Path tempCfg = (new File(file.getParentFile(), "__tmpcfg")).toPath();
            List<String> lines = new ArrayList<>();
            save(lines, getClass(), this, 0);
            Files.write(tempCfg, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
            try {
                Files.move(tempCfg, configFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException atomicMoveNotSupportedException) {
                Files.move(tempCfg, configFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException iOException) {}
    }

    private String toYamlString(Object value, String spacing) {
        if (value instanceof List) {
            Collection<?> listValue = (Collection)value;
            if (listValue.isEmpty())
                return "[]";
            StringBuilder m = new StringBuilder();
            for (Object obj : listValue)
                m.append(System.lineSeparator()).append(spacing).append("- ").append(toYamlString(obj, spacing));
            return m.toString();
        }
        if (value instanceof String) {
            String stringValue = (String)value;
            if (stringValue.isEmpty())
                return "''";
            return "\"" + stringValue + "\"";
        }
        return (value != null) ? value.toString() : "null";
    }

    private void save(List<String> lines, Class clazz, Object instance, int indent) {
        try {
            String spacing = repeat(" ", indent);
            Field[] arrayOfField;
            int i;
            byte b;
            for (i = (arrayOfField = clazz.getFields()).length, b = 0; b < i; ) {
                Field field = arrayOfField[b];
                if (field.getAnnotation(Ignore.class) == null) {
                    Class<?> current = field.getType();
                    if (field.getAnnotation(Ignore.class) == null) {
                        Comment comment = field.getAnnotation(Comment.class);
                        if (comment != null) {
                            String[] arrayOfString;
                            int j;
                            byte b1;
                            for (j = (arrayOfString = comment.value()).length, b1 = 0; b1 < j; ) {
                                String commentLine = arrayOfString[b1];
                                lines.add(spacing + "# " + commentLine);
                                b1++;
                            }
                        }
                        Create create = field.getAnnotation(Create.class);
                        if (create != null) {
                            Object value = field.get(instance);
                            setAccessible(field);
                            if (indent == 0)
                                lines.add("");
                            comment = current.getAnnotation(Comment.class);
                            if (comment != null) {
                                byte b1;
                                String[] arrayOfString;
                                int j;
                                for (j = (arrayOfString = comment.value()).length, b1 = 0; b1 < j; ) {
                                    String commentLine = arrayOfString[b1];
                                    lines.add(spacing + "# " + commentLine);
                                    b1++;
                                }
                            }
                            lines.add(spacing + toNodeName(current.getSimpleName()) + ":");
                            if (value == null)
                                field.set(instance, value = current.newInstance());
                            save(lines, current, value, indent + 2);
                        } else {
                            lines.add(spacing + toNodeName(field.getName() + ": ") + toYamlString(field.get(instance), spacing));
                        }
                    }
                }
                b++;
            }
        } catch (IllegalAccessException|IllegalArgumentException|InstantiationException|NoSuchFieldException|SecurityException e) {
            RevampSpigot.getInstance().getLogger().warning("Error:", e);
        }
    }

    private Field getField(String[] split, Object instance) {
        try {
            Field field = instance.getClass().getField(toFieldName(split[split.length - 1]));
            setAccessible(field);
            return field;
        } catch (IllegalAccessException|NoSuchFieldException|SecurityException illegalAccessException) {
            RevampSpigot.getInstance().getLogger().warning("Invalid config field: {0} for {1}",
                    String.join(".", split), toNodeName(instance.getClass().getSimpleName()));
            return null;
        }
    }

    private Object getInstance(String[] split, Class<?> root) {
        try {
            Class<?> clazz = (root == null) ? MethodHandles.lookup().lookupClass() : root;
            Object instance = this;
            while (split.length > 0) {
                switch (split.length) {
                    case 1:
                        return instance;
                }
                Class<?> found = null;
                Class[] classes = clazz.getDeclaredClasses();
                Class[] arrayOfClass1;
                int i;
                byte b;
                for (i = (arrayOfClass1 = classes).length, b = 0; b < i; ) {
                    Class<?> current = arrayOfClass1[b];
                    if (current.getSimpleName().equalsIgnoreCase(toFieldName(split[0]))) {
                        found = current;
                        break;
                    }
                    b++;
                }
                try {
                    Field instanceField = clazz.getDeclaredField(toFieldName(split[0]));
                    setAccessible(instanceField);
                    Object value = instanceField.get(instance);
                    if (value == null) {
                        value = found.newInstance();
                        instanceField.set(instance, value);
                    }
                    clazz = found;
                    instance = value;
                    split = Arrays.copyOfRange(split, 1, split.length);
                } catch (NoSuchFieldException noSuchFieldException) {
                    return null;
                }
            }
        } catch (IllegalAccessException|IllegalArgumentException|InstantiationException|SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String toFieldName(String node) {
        return node.toUpperCase().replaceAll("-", "_");
    }

    private String toNodeName(String field) {
        return field.toLowerCase().replace("_", "-");
    }

    private void setAccessible(Field field) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
    }

    private String repeat(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++)
            sb.append(s);
        return sb.toString();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    public @interface Comment {
        String[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Create {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Final {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    public @interface Ignore {}
}

