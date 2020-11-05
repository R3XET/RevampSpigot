package eu.revamp.spigot.utils.reflection;

import java.lang.reflect.Field;

public abstract class NMSClass {
    public static boolean initialized = false;

    public static Class<?> Entity;

    public static Class<?> EntityLiving;

    public static Class<?> EntityInsentient;

    public static Class<?> EntityAgeable;

    public static Class<?> EntityHorse;

    public static Class<?> EntityCreeper;

    public static Class<?> EntityArmorStand;

    public static Class<?> EntityWitherSkull;

    public static Class<?> EntitySlime;

    public static Class<?> World;

    public static Class<?> PacketPlayOutSpawnEntityLiving;

    public static Class<?> PacketPlayOutSpawnEntity;

    public static Class<?> PacketPlayOutEntityDestroy;

    public static Class<?> PacketPlayOutAttachEntity;

    public static Class<?> PacketPlayOutMount;

    public static Class<?> PacketPlayOutEntityTeleport;

    public static Class<?> PacketPlayOutEntityMetadata;

    public static Class<?> DataWatcher;

    public static Class<?> WatchableObject;

    public static Class<?> ItemStack;

    public static Class<?> ChunkCoordinates;

    public static Class<?> BlockPosition;

    public static Class<?> Vector3f;

    public static Class<?> EnumEntityUseAction;

    public static Class<?> NBTTagCompound;

    public static Class<?> BiomeBase;

    static {
        if (!initialized) {
            for (Field field : NMSClass.class.getDeclaredFields()) {
                if (field.getType().equals(Class.class))
                    try {
                        field.set(null, Reflection.getNMSClassWithException(field.getName()));
                    } catch (Exception exception) {
                        if (field.getName().equals("WatchableObject"))
                            try {
                                field.set(null, Reflection.getNMSClassWithException("DataWatcher$WatchableObject"));
                            } catch (Exception exception1) {
                                exception1.printStackTrace();
                            }
                    }
            }
            initialized = true;
        }
    }
}
