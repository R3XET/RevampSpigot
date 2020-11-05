package eu.revamp.spigot.knockback;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.stream.Stream;

public class Knockback {

    private final String name;

    private double horizontal = 1.0D;

    private double vertical = 1.0D;

    private double airHorizontal = 1.1D;

    private double airVertical = 1.1D;

    private double verticalLimit = 0.4000000059604645D;

    private double frictions = 2.0D;

    private boolean friction = false;

    private boolean stopSprint = false;

    private boolean comboMode = false;

    private double potionSpeed = 0.05D;

    private double potionOffSet = -20.0D;

    private double potionDistance = 0.5D;

    private double bowHorizontal = 1.0D;

    private double bowVertical = 1.0D;

    private double rodHorizontal = 1.0D;

    private double rodVertical = 1.0D;

    private double sprintHorizontal = 1.0D;

    private double sprintVertical = 1.0D;

    private double slowDown = 0.6D;

    public Knockback(String name) {
        this.name = name;
    }

    public Knockback(String name, double horizontal, double vertical, double airHorizontal, double airVertical, double verticalLimit, double frictions, boolean friction, boolean stopSprint, boolean comboMode, double potionSpeed, double potionOffSet, double potionDistance, double bowHorizontal, double bowVertical, double rodHorizontal, double rodVertical, double sprintHorizontal, double sprintVertical, double slowDown) {
        this.name = name;
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.airHorizontal = airHorizontal;
        this.airVertical = airVertical;
        this.verticalLimit = verticalLimit;
        this.frictions = frictions;
        this.friction = friction;
        this.stopSprint = stopSprint;
        this.comboMode = comboMode;
        this.potionSpeed = potionSpeed;
        this.potionOffSet = potionOffSet;
        this.potionDistance = potionDistance;
        this.bowHorizontal = bowHorizontal;
        this.bowVertical = bowVertical;
        this.rodHorizontal = rodHorizontal;
        this.rodVertical = rodVertical;
        this.sprintHorizontal = sprintHorizontal;
        this.sprintVertical = sprintVertical;
        this.slowDown = slowDown;
    }

    public static Knockback getByName(String name) {
        String pre = "knockbacks." + name;
        return new Knockback(name, KnockbackConfig.getDouble(pre + ".horizontal", 1.0D),
                KnockbackConfig.getDouble(pre + ".vertical", 1.0D),
                KnockbackConfig.getDouble(pre + ".airHorizontal", 1.1D),
                KnockbackConfig.getDouble(pre + ".airVertical", 1.1D),
                KnockbackConfig.getDouble(pre + ".verticalLimit", 0.4000000059604645D),
                KnockbackConfig.getDouble(pre + ".frictions", 2.0D),
                KnockbackConfig.getBoolean(pre + ".friction", false),
                KnockbackConfig.getBoolean(pre + ".stopSprint", false),
                KnockbackConfig.getBoolean(pre + ".comboMode", false),
                KnockbackConfig.getDouble(pre + ".potionSpeed", 0.5D),
                KnockbackConfig.getDouble(pre + ".potionOffSet", -20.0D),
                KnockbackConfig.getDouble(pre + ".potionDistance", 0.5D),
                KnockbackConfig.getDouble(pre + ".bowHorizontal", 1.0D),
                KnockbackConfig.getDouble(pre + ".bowVertical", 1.0D),
                KnockbackConfig.getDouble(pre + ".rodHorizontal", 1.0D),
                KnockbackConfig.getDouble(pre + ".rodVertical", 1.0D),
                KnockbackConfig.getDouble(pre + ".sprintHorizontal", 1.0D),
                KnockbackConfig.getDouble(pre + ".sprintVertical", 1.0D),
                KnockbackConfig.getDouble(pre + ".slowDown", 0.6D));
    }

    public Knockback load() {
        try {
            YamlConfiguration knockbackConfig = KnockbackConfig.config;
            Stream.of(getClass().getDeclaredFields()).filter(field -> !field.getType().equals(String.class))

                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            String fieldName = "knockbacks." + this.name + "." + field.getName();
                            Object value = knockbackConfig.get(fieldName);
                            if (value == null) {
                                Object fieldValue = field.get(this);
                                knockbackConfig.set(fieldName, fieldValue);
                            } else {
                                field.set(this, value);
                            }
                            field.setAccessible(false);
                        } catch (Exception var6) {
                            var6.printStackTrace();
                        }
                    });
            knockbackConfig.save(KnockbackConfig.CONFIG_FILE);
            return this;
        } catch (Throwable var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public Knockback delete() {
        KnockbackConfig.set("knockbacks." + this.name, null);
        KnockbackConfig.saveConfig();
        return this;
    }

    public Knockback save() {
        try {
            Stream.of(getClass().getDeclaredFields()).forEach(field -> {
                try {
                    YamlConfiguration knockbackConfig = KnockbackConfig.config;
                    String fieldName = "knockbacks." + this.name + "." + field.getName();
                    Object fieldValue = field.get(this);
                    if (!(fieldValue instanceof String))
                        knockbackConfig.set(fieldName, fieldValue);
                } catch (Exception var5) {
                    var5.printStackTrace();
                }
            });
            KnockbackConfig.config.save(KnockbackConfig.CONFIG_FILE);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
        return this;
    }

    public void test(Player player) {
        double horizontal = (isFriction() ? getFrictions() : 1.0D) * (player.isOnGround() ? this.horizontal : this.airHorizontal);
        double vertical = (isFriction() ? getFrictions() : 1.0D) * (player.isOnGround() ? this.vertical : this.airVertical);
        Vector velocity = new Vector(player.getVelocity().getX() * horizontal, player.getVelocity().getY() * vertical, player.getVelocity().getZ() * horizontal);
        player.setVelocity(velocity);
    }

    /*public void CustomKnocback(Player player, boolean friction, int frictions, int horizontal, int airHorizontal, int vertical, int airVertical) {
        double hor = (friction ? frictions : 1.0D) * (player.isOnGround() ? horizontal : airHorizontal);
        double ver = (friction ? frictions : 1.0D) * (player.isOnGround() ? vertical : airVertical);
        Vector velocity = new Vector(player.getVelocity().getX() * hor, player.getVelocity().getY() * ver, player.getVelocity().getZ() * hor);
        player.setKnockback(null);
        player.setVelocity(velocity);
    }*/

    public String getName() {
        return this.name;
    }

    public double getHorizontal() {
        return this.horizontal;
    }

    public double getVertical() {
        return this.vertical;
    }

    public double getAirHorizontal() {
        return this.airHorizontal;
    }

    public double getAirVertical() {
        return this.airVertical;
    }

    public double getVerticalLimit() {
        return this.verticalLimit;
    }

    public double getFrictions() {
        return this.frictions;
    }

    public boolean isFriction() {
        return this.friction;
    }

    public boolean isStopSprint() {
        return this.stopSprint;
    }

    public boolean isComboMode() {
        return this.comboMode;
    }

    public double getPotionSpeed() {
        return this.potionSpeed;
    }

    public double getPotionOffSet() {
        return this.potionOffSet;
    }

    public double getPotionDistance() {
        return this.potionDistance;
    }

    public double getBowHorizontal() {
        return this.bowHorizontal;
    }

    public double getBowVertical() {
        return this.bowVertical;
    }

    public double getRodHorizontal() {
        return this.rodHorizontal;
    }

    public double getRodVertical() {
        return this.rodVertical;
    }

    public double getSprintHorizontal() {
        return this.sprintHorizontal;
    }

    public double getSprintVertical() {
        return this.sprintVertical;
    }

    public double getSlowDown() {
        return this.slowDown;
    }

    public void setHorizontal(double horizontal) {
        this.horizontal = horizontal;
    }

    public void setVertical(double vertical) {
        this.vertical = vertical;
    }

    public void setAirHorizontal(double airHorizontal) {
        this.airHorizontal = airHorizontal;
    }

    public void setAirVertical(double airVertical) {
        this.airVertical = airVertical;
    }

    public void setVerticalLimit(double verticalLimit) {
        this.verticalLimit = verticalLimit;
    }

    public void setFrictions(double frictions) {
        this.frictions = frictions;
    }

    public void setFriction(boolean friction) {
        this.friction = friction;
    }

    public void setStopSprint(boolean stopSprint) {
        this.stopSprint = stopSprint;
    }

    public void setComboMode(boolean comboMode) {
        this.comboMode = comboMode;
    }

    public void setPotionSpeed(double potionSpeed) {
        this.potionSpeed = potionSpeed;
    }

    public void setPotionOffSet(double potionOffSet) {
        this.potionOffSet = potionOffSet;
    }

    public void setPotionDistance(double potionDistance) {
        this.potionDistance = potionDistance;
    }

    public void setBowHorizontal(double bowHorizontal) {
        this.bowHorizontal = bowHorizontal;
    }

    public void setBowVertical(double bowVertical) {
        this.bowVertical = bowVertical;
    }

    public void setRodHorizontal(double rodHorizontal) {
        this.rodHorizontal = rodHorizontal;
    }

    public void setRodVertical(double rodVertical) {
        this.rodVertical = rodVertical;
    }

    public void setSprintHorizontal(double sprintHorizontal) {
        this.sprintHorizontal = sprintHorizontal;
    }

    public void setSprintVertical(double sprintVertical) {
        this.sprintVertical = sprintVertical;
    }

    public void setSlowDown(double slowDown) {
        this.slowDown = slowDown;
    }
}

