package eu.revamp.spigot.events.armor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorEquipEvent extends PlayerEvent implements Cancellable {

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ArmorEquipEvent)) {
            return false;
        }
        ArmorEquipEvent other = (ArmorEquipEvent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        EquipMethod this$equipType = this.getEquipType();
        EquipMethod other$equipType = other.getEquipType();
        if (this$equipType == null ? other$equipType != null : !this$equipType.equals(other$equipType)) {
            return false;
        }
        ArmorType this$type = this.getType();
        ArmorType other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        if (this.isCancelled() != other.isCancelled()) {
            return false;
        }
        ItemStack this$oldArmorPiece = this.getOldArmorPiece();
        ItemStack other$oldArmorPiece = other.getOldArmorPiece();
        if (this$oldArmorPiece == null ? other$oldArmorPiece != null : !this$oldArmorPiece.equals(other$oldArmorPiece)) {
            return false;
        }
        ItemStack this$newArmorPiece = this.getNewArmorPiece();
        ItemStack other$newArmorPiece = other.getNewArmorPiece();
        return this$newArmorPiece == null ? other$newArmorPiece == null : this$newArmorPiece.equals(other$newArmorPiece);
    }

    protected boolean canEqual(Object other) {
        return other instanceof ArmorEquipEvent;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        EquipMethod $equipType = this.getEquipType();
        result = result * 59 + ($equipType == null ? 43 : $equipType.hashCode());
        ArmorType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        result = result * 59 + (this.isCancelled() ? 79 : 97);
        ItemStack $oldArmorPiece = this.getOldArmorPiece();
        result = result * 59 + ($oldArmorPiece == null ? 43 : $oldArmorPiece.hashCode());
        ItemStack $newArmorPiece = this.getNewArmorPiece();
        result = result * 59 + ($newArmorPiece == null ? 43 : $newArmorPiece.hashCode());
        return result;
    }

    private static final HandlerList handlers = new HandlerList();

    private EquipMethod equipType;

    private ArmorType type;

    private boolean cancelled = false;

    private ItemStack oldArmorPiece;

    private ItemStack newArmorPiece;

    public ArmorEquipEvent(Player player, ItemStack newArmorPiece) {
        super(player);
        this.newArmorPiece = newArmorPiece;
    }


    public ArmorEquipEvent(Player player, EquipMethod equipType, ArmorType type, ItemStack oldArmorPiece, ItemStack newArmorPiece) {
        super(player);
        this.equipType = equipType;
        this.type = type;
        this.oldArmorPiece = oldArmorPiece;
        this.newArmorPiece = newArmorPiece;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    public HandlerList getHandlers() {
        return handlers;
    }

    public EquipMethod getMethod() {
        return this.equipType;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setOldArmorPiece(ItemStack oldArmorPiece) {
        this.oldArmorPiece = oldArmorPiece;
    }

    public void setNewArmorPiece(ItemStack newArmorPiece) {
        this.newArmorPiece = newArmorPiece;
    }

    public EquipMethod getEquipType() {
        return this.equipType;
    }

    public ArmorType getType() {
        return this.type;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public ItemStack getOldArmorPiece() {
        return this.oldArmorPiece;
    }

    public ItemStack getNewArmorPiece() {
        return this.newArmorPiece;
    }

    public enum EquipMethod {
        SHIFT_CLICK, DRAG, PICK_DROP, HOTBAR, HOTBAR_SWAP, DISPENSER, BROKE, DEATH
    }

    public enum ArmorType {
        HELMET(5),
        CHESTPLATE(6),
        LEGGINGS(7),
        BOOTS(8);

        private final int slot;

        ArmorType(int slot) {
            this.slot = slot;
        }

        public static ArmorType matchType(ItemStack itemStack) {
            if (itemStack == null || itemStack.getType().equals(Material.AIR))
                return null;
            String type = itemStack.getType().name();
            if (type.endsWith("_HELMET") || type.endsWith("_SKULL"))
                return HELMET;
            if (type.endsWith("_CHESTPLATE"))
                return CHESTPLATE;
            if (type.endsWith("_LEGGINGS"))
                return LEGGINGS;
            if (type.endsWith("_BOOTS"))
                return BOOTS;
            return null;
        }

        public int getSlot() {
            return this.slot;
        }
    }
    public String toString() {
        return "ArmorEquipEvent(equipType=" + getEquipType() + ", type=" + getType() + ", cancel=" + isCancelled() + ", oldArmorPiece=" + getOldArmorPiece() + ", newArmorPiece=" + getNewArmorPiece() + ")";
    }
}