package eu.revamp.spigot.knockback;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KnockbackManager {
    private Knockback defaultKnockback;

    private final Map<String, Knockback> knockbackProfiles = new HashMap<>();

    public KnockbackManager() {
        KnockbackConfig.init();
        if (KnockbackConfig.config != null && KnockbackConfig.config.contains("knockbacks"))
            getKnockbacks().forEach(name -> addProfile(Knockback.getByName(name), name));
        if (this.knockbackProfiles.isEmpty())
            initProfile("default");
        this.defaultKnockback = this.knockbackProfiles.get(KnockbackConfig.getString("defaultKnockback", "default"));
    }

    public YamlConfiguration getKnockbackConfig() {
        return KnockbackConfig.config;
    }

    public Knockback getKnockbackProfile(String name) {
        return this.knockbackProfiles.get(name);
    }

    public Set<String> getKnockbacks() {
        return getKnockbackConfig().getConfigurationSection("knockbacks").getKeys(false);
    }

    public void initProfile(String name) {
        if (!this.knockbackProfiles.containsKey(name)) {
            Knockback.getByName(name).save();
            reloadProfile(name);
        }
    }

    public void reloadProfile(String name) {
        if (this.knockbackProfiles.containsKey(name)) {
            this.knockbackProfiles.get(name).load();
        } else {
            this.knockbackProfiles.put(name, Knockback.getByName(name).load());
        }
    }

    public Knockback createProfile(String name) {
        Knockback knockback = new Knockback(name);
        initProfile(name);
        return knockback;
    }

    public void deleteKnockback(String name) {
        this.knockbackProfiles.remove(name).delete();
    }

    private void addProfile(Knockback knockback, String name) {
        this.knockbackProfiles.putIfAbsent(name, knockback);
    }

    public boolean isDefaultKnockback(Knockback knockback) {
        return this.defaultKnockback.equals(knockback);
    }

    public Knockback getDefaultKnockback() {
        return this.defaultKnockback;
    }

    public void setDefaultKnockback(Knockback defaultKnockback) {
        this.defaultKnockback = defaultKnockback;
    }

    public Map<String, Knockback> getKnockbackProfiles() {
        return this.knockbackProfiles;
    }
}

