package eu.revamp.spigot.files;

import eu.revamp.spigot.utils.chat.color.CC;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LanguageFile {

    private File file;
    private YamlConfiguration configuration;

    public LanguageFile() {
        this.load();
    }

    public void load() {
        this.file = new File("language.yml");
        //this.file = new File(RevampSpigot.getInstance().getDataFolder(), "language.yml");
        /*
        if (!this.file.exists()) {
            RevampSpigot.getInstance().saveResource("language.yml", false);
        }
        */
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        try {
            this.configuration.set(path, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String path) {
        if (this.configuration.contains(path)) {
            return CC.translate(this.configuration.getString(path));
        }
        return null;
    }

    public long getLong(String path) {
        if (this.configuration.contains(path)) {
            return this.configuration.getLong(path);
        }
        return 0L;
    }

    public List<String> getStringList(String path) {
        if (this.configuration.contains(path)) {
            ArrayList<String> strings = new ArrayList<>();
            for (String string : this.configuration.getStringList(path)) {
                strings.add(CC.translate(string));
            }
            return strings;
        }
        return null;
    }

    public File getFile() {
        return this.file;
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }
}
