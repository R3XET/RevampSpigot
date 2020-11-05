package net.minecraft.server;

// CraftBukkit start

import eu.revamp.spigot.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.entity.EnderPearl;
import org.bukkit.event.entity.EnderpearlLandEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.material.Gate;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class EntityEnderPearl extends EntityProjectile {

	public EntityLiving c;

	private final List<Block> fenceGates = Arrays.asList(Blocks.ACACIA_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE);

	public EntityEnderPearl(World world) {
		super(world);
		this.loadChunks = world.paperSpigotConfig.loadUnloadedEnderPearls; // PaperSpigot
	}

	public EntityEnderPearl(World world, EntityLiving entityliving) {
		super(world, entityliving);
		this.c = entityliving;
		this.loadChunks = world.paperSpigotConfig.loadUnloadedEnderPearls; // PaperSpigot
	}

	protected void a(MovingObjectPosition movingobjectposition) {
		EntityLiving entityliving = this.getShooter();
		if (Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_STRING && movingobjectposition.a() != null) {
			Block block = this.world.getType(movingobjectposition.a()).getBlock();
			if (block == Blocks.TRIPWIRE) {
				return;
			}
		}
		if (Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_FENCE_GATE && movingobjectposition.a() != null) {
			Block block = this.world.getType(movingobjectposition.a()).getBlock();
			if (fenceGates.contains(block)) {
				BlockIterator bi = null;
				try {
					Vector l = new Vector(this.locX, this.locY, this.locZ);
					Vector l2 = new Vector(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
					Vector dir = new Vector(l2.getX() - l.getX(), l2.getY() - l.getY(), l2.getZ() - l.getZ()).normalize();
					bi = new BlockIterator(this.world.getWorld(), l, dir, 0.0, 1);
				}
				catch (IllegalStateException ignored) {}
				if (bi != null) {
					boolean open = true;
					while (bi.hasNext()) {
						org.bukkit.block.Block b = bi.next();
						if (b.getState().getData() instanceof Gate && !((Gate)b.getState().getData()).isOpen()) {
							open = false;
							break;
						}
					}
					if (open) {
						return;
					}
				}
			}
		}
		if (Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_SLAB && movingobjectposition.a() != null) {
			Block block = this.world.getType(movingobjectposition.a()).getBlock();
			if (block == Blocks.STONE_SLAB || block == Blocks.WOODEN_SLAB) {
				BlockIterator bi = null;
				try {
					Vector l = new Vector(this.locX, this.locY, this.locZ);
					Vector l2 = new Vector(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
					Vector dir = new Vector(l2.getX() - l.getX(), l2.getY() - l.getY(), l2.getZ() - l.getZ()).normalize();
					bi = new BlockIterator(this.world.getWorld(), l, dir, 0.0, 1);
				}
				catch (IllegalStateException ignored) {}
				if (bi != null) {
					boolean open = true;
					while (bi.hasNext()) {
						org.bukkit.block.Block b = bi.next();
						if (b.getState().getData() instanceof Gate && !((Gate)b.getState().getData()).isOpen()) {
							open = false;
							break;
						}
					}
					if (open) {
						return;
					}
				}
			}
		}
		if (Settings.IMP.SETTINGS.ENDER_PEARL.ALLOW_PEARL_THROUGH_COBWEB && movingobjectposition.a() != null) {
			Block block = this.world.getType(movingobjectposition.a()).getBlock();
			if (block == Blocks.WEB) {
				return;
			}
		}
		if (movingobjectposition.entity != null) {
			movingobjectposition.entity.damageEntity(DamageSource.projectile(this, entityliving), 0.0f);
		}
		if (this.inUnloadedChunk && this.world.paperSpigotConfig.removeUnloadedEnderPearls) {
			this.die();
		}
		for (int i = 0; i < 32; ++i) {
			this.world.addParticle(EnumParticle.PORTAL, this.locX, this.locY + this.random.nextDouble() * 2.0, this.locZ, this.random.nextGaussian(), 0.0, this.random.nextGaussian());
		}
		if (!this.world.isClientSide) {
			if (entityliving instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer)entityliving;
				if (entityplayer.playerConnection.a().g() && entityplayer.world == this.world && !entityplayer.isSleeping()) {
					CraftPlayer player = entityplayer.getBukkitEntity();
					EnderpearlLandEvent.Reason reason = (movingobjectposition.entity != null) ? EnderpearlLandEvent.Reason.ENTITY : EnderpearlLandEvent.Reason.BLOCK;
					CraftEntity bukkitHitEntity = (movingobjectposition.entity != null) ? movingobjectposition.entity.getBukkitEntity() : null;
					EnderpearlLandEvent landEvent = new EnderpearlLandEvent((EnderPearl)this.getBukkitEntity(), reason, bukkitHitEntity);
					this.world.getServer().getPluginManager().callEvent(landEvent);
					if (landEvent.isCancelled()) {
						this.die();
						return;
					}
					Location location = this.getBukkitEntity().getLocation();
					location.setPitch(player.getLocation().getPitch());
					location.setYaw(player.getLocation().getYaw());
					PlayerTeleportEvent teleEvent = new PlayerTeleportEvent(player, player.getLocation(), location, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
					Bukkit.getPluginManager().callEvent(teleEvent);
					if (!teleEvent.isCancelled() && !entityplayer.playerConnection.isDisconnected()) {
						if (this.random.nextFloat() < 0.05f && this.world.getGameRules().getBoolean("doMobSpawning") && Settings.IMP.SETTINGS.ENDER_PEARL.ENDERMITE_SPAWNING) {
							EntityEndermite entityendermite = new EntityEndermite(this.world);
							entityendermite.a(true);
							entityendermite.setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ, entityliving.yaw, entityliving.pitch);
							this.world.addEntity(entityendermite);
						}
						if (entityliving.au()) {
							entityliving.mount(null);
						}
						entityplayer.playerConnection.teleport(teleEvent.getTo());
						entityliving.fallDistance = 0.0f;
						CraftEventFactory.entityDamage = this;
						entityliving.damageEntity(DamageSource.FALL, 5.0f);
						CraftEventFactory.entityDamage = null;
					}
				}
			}
			else if (entityliving != null) {
				entityliving.enderTeleportTo(this.locX, this.locY, this.locZ);
				entityliving.fallDistance = 0.0f;
			}
			this.die();
		}
	}


	//TODO OLD CODE
/*
	protected void a(MovingObjectPosition movingobjectposition) {
		EntityLiving entityliving = this.getShooter();
		if (!RevampSpigot.getInstance().pearlfacegate) {
			Block block = this.world.getType(movingobjectposition.a()).getBlock();

			if (block == Blocks.TRIPWIRE) {
				return;
			} else if (block == Blocks.FENCE_GATE) {
				BlockIterator bi = null;

				try {
					Vector l = new Vector(this.locX, this.locY, this.locZ);
					Vector l2 = new Vector(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
					Vector dir = new Vector(l2.getX() - l.getX(), l2.getY() - l.getY(), l2.getZ() - l.getZ())
							.normalize();
					bi = new BlockIterator(this.world.getWorld(), l, dir, 0, 1);
				} catch (IllegalStateException e) {
					// Do nothing
				}

				if (bi != null) {
					boolean open = true;
					while (bi.hasNext()) {
						org.bukkit.block.Block b = bi.next();
						if (b.getState().getData() instanceof Gate && !((Gate) b.getState().getData()).isOpen()) {
							open = false;
							break;
						}
					}
					if (open) {
						return;
					}
				}
			}
		}

		if (movingobjectposition.entity != null) {
			movingobjectposition.entity.damageEntity(DamageSource.projectile(this, entityliving), 0.0F);
		}

		// PaperSpigot start - Remove entities in unloaded chunks
		if (this.inUnloadedChunk && world.paperSpigotConfig.removeUnloadedEnderPearls) {
			this.die();
		}
		// PaperSpigot end

		for (int i = 0; i < 32; ++i) {
			this.world.addParticle(EnumParticle.PORTAL, this.locX, this.locY + this.random.nextDouble() * 2.0D,
					this.locZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
		}

		if (!this.world.isClientSide) {
			if (entityliving instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) entityliving;

				if (entityplayer.playerConnection.a().g() && entityplayer.world == this.world
						&& !entityplayer.isSleeping()) {
					// CraftBukkit start - Fire PlayerTeleportEvent
					org.bukkit.craftbukkit.entity.CraftPlayer player = entityplayer.getBukkitEntity();
					org.bukkit.Location location = getBukkitEntity().getLocation();
					location.setPitch(player.getLocation().getPitch());
					location.setYaw(player.getLocation().getYaw());

					PlayerTeleportEvent teleEvent = new PlayerTeleportEvent(player, player.getLocation(), location,
							PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
					Bukkit.getPluginManager().callEvent(teleEvent);

					if (!teleEvent.isCancelled() && !entityplayer.playerConnection.isDisconnected()) {
						if (this.random.nextFloat() < 0.05F && this.world.getGameRules().getBoolean("doMobSpawning")) {
							EntityEndermite entityendermite = new EntityEndermite(this.world);

							entityendermite.a(true);
							entityendermite.setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ,
									entityliving.yaw, entityliving.pitch);
							this.world.addEntity(entityendermite);
						}

						if (entityliving.au()) {
							entityliving.mount(null);
						}

						entityplayer.playerConnection.teleport(teleEvent.getTo());
						entityliving.fallDistance = 0.0F;
						CraftEventFactory.entityDamage = this;
						entityliving.damageEntity(DamageSource.FALL, 5.0F);
						CraftEventFactory.entityDamage = null;
					}
					// CraftBukkit end
				}
			} else if (entityliving != null) {
				entityliving.enderTeleportTo(this.locX, this.locY, this.locZ);
				entityliving.fallDistance = 0.0F;
			}

			this.die();
		}

	}
 */
	//TODO OLD CODE

	public void t_() {
		EntityLiving entityliving = this.getShooter();

		if (entityliving != null && entityliving instanceof EntityHuman && !entityliving.isAlive()) {
			this.die();
		} else {
			super.t_();
		}

	}
}
