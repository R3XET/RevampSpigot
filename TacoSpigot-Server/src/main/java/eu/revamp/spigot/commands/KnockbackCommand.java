package eu.revamp.spigot.commands;

import eu.revamp.spigot.RevampSpigot;
import eu.revamp.spigot.knockback.Knockback;
import eu.revamp.spigot.utils.chat.color.CC;
import eu.revamp.spigot.utils.player.PlayerUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnockbackCommand extends Command {

	private String incorrect;
	private RevampSpigot config = RevampSpigot.getInstance();

	public KnockbackCommand(String name) {
		super(name);
		this.description = "Modify the knockback in RevampSpigot.";
		this.usageMessage = "/knockback help";
		this.incorrect = CC.AQUA + "Incorrect Usage. \n/knockback create (name) \n/knockback remove (name) \n/knockback sethorizontal (name) (value) \n/knockback setairhorizontal (name) (value) \n/knockback setsprinthorizontal (name) (value) \n/knockback setvertical (name) (value) \n/knockback setairvertical (name) (value) \n/knockback setsprintvertical (name) (value) \n/knockback setbowhorizontal (name) (value)\n/knockback setbowvertical (name) (value)\n/knockback setrodhorizontal (name) (value)\n/knockback setrodvertical (name) (value)\n/knockback setpotionfallspeed (name) (value)\n/knockback setpotiondistance (name) (value)\n/knockback setpotionoffset (name) (value) \n/knockback setstopsprint (value) \n/knockback setslowdown (value) \n/knockback setallowfriction (name) (value)\n/knockback setfrictionvalue (name) (value)\n/knockback setdefault (name)\n/knockback test (name)\n/knockback info (name)\n/knockback bindprofile (name) (player)";
		setPermission("revampspigot.command.knockback");
		setAliases(Arrays.asList("kb", "kbs", "knockbacks"));
	}

	@Override
	public boolean execute(CommandSender sender, String currentAlias, String[] args) {
		if (!testPermission(sender))
			return true;
		if (args.length == 0) {
			sender.sendMessage(this.incorrect);
			return true;
		}
		if (args[0].equalsIgnoreCase("create")) {
			if (args.length < 2) {
				sender.sendMessage(this.incorrect);
				return true;
			}
			if (config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
				sender.sendMessage(CC.RED + "Knockback Profile " + args[1] + " was already exists!");
				return true;
			}
			config.getKnockbackManager().createProfile(args[1]);
			sender.sendMessage(CC.AQUA + "Created Knockback Profile " + CC.GRAY + "(" + args[1] + ")");
			return true;
		}
		if (args[0].equalsIgnoreCase("remove")) {
			if (args.length < 2) {
				sender.sendMessage(this.incorrect);
				return true;
			}
			if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
				sender.sendMessage(CC.RED + "Knockback Profile " + args[1] + " doesn't exist!");
				return true;
			}
			config.getKnockbackManager().deleteKnockback(args[1]);
			sender.sendMessage(CC.AQUA + "Wiped the knockback profile " + CC.GRAY + "(" + args[1] + ")");
			return true;
		}
		if (args[0].equalsIgnoreCase("sethorizontal") || args[0].equalsIgnoreCase("sethor")) {
			if (args.length != 3) {
				sender.sendMessage(this.incorrect);
				return true;
			}
			if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
				sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
				return true;
			}
			try {
				Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
				try {
					knockback.setHorizontal(Double.parseDouble(args[2]));
				} catch (NumberFormatException notnumber) {
					sender.sendMessage(CC.RED + "The number is invalid.");
					return true;
				}
				knockback.save();
				config.getKnockbackManager().reloadProfile(args[1]);
				sender.sendMessage(getKnockbackSettings(knockback));
				reloadKnockbacks();
			} catch (NullPointerException e) {
				sender.sendMessage(this.incorrect);
			}
		}
		if (args[0].equalsIgnoreCase("setvertical") || args[0].equalsIgnoreCase("setver")) {
			if (args.length != 3) {
				sender.sendMessage(this.incorrect);
				return true;
			}
			if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
				sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
				return true;
			}
			try {
				Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
				try {
					knockback.setVertical(Double.parseDouble(args[2]));
				} catch (NumberFormatException notnumber) {
					sender.sendMessage(CC.RED + "The number is invalid.");
					return true;
				}
				knockback.save();
				config.getKnockbackManager().reloadProfile(args[1]);
				sender.sendMessage(getKnockbackSettings(knockback));
				reloadKnockbacks();
			} catch (NullPointerException e) {
				sender.sendMessage(this.incorrect);
			}
		}
		if (args[0].equalsIgnoreCase("getloaded") || args[0].equalsIgnoreCase("list")) {
			sender.sendMessage(CC.GRAY + "Loaded Knockback Profiles:");
			config.getKnockbackManager().getKnockbackProfiles().values().forEach(knockback -> sender.sendMessage(CC.translate(getKnockbackSettings(knockback))));
		}
		if (args[0].equalsIgnoreCase("test")) {
			if (args.length < 2) {
				sender.sendMessage(this.incorrect);
				return true;
			}
			if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
				sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
				return true;
			}
			Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
			sender.sendMessage(CC.AQUA + "Tested Knockback Profile " + CC.GRAY + "(" + knockback.getName() + ")");
			knockback.test((Player)sender);
			return true;
		}
		if (!args[0].equalsIgnoreCase("info")) {
			if (args[0].equalsIgnoreCase("setverticallimit") || args[0].equalsIgnoreCase("verticallimit")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setVerticalLimit(Double.parseDouble(args[2]));
					} catch (Exception notnumber2) {
						sender.sendMessage(CC.RED + "The boolean is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setfrictionvalue") || args[0].equalsIgnoreCase("setfriction")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					if (!knockback.isFriction())
						sender.sendMessage(CC.AQUA + "That profile didn't enabled friction settings. \nUse /knockback setallowfriction " + args[1] + " true\n to enable friction.");
					try {
						knockback.setFrictions(Double.parseDouble(args[2]));
					} catch (Exception notnumber2) {
						sender.sendMessage(CC.RED + "The boolean is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("bindprofile") || args[0].equalsIgnoreCase("bind")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				Player player = Bukkit.getPlayer(args[2]);
				if (!player.isOnline()) {
					sender.sendMessage(CC.AQUA + "That player is not online.");
					return true;
				}
				try {
					Knockback knockback3 = config.getKnockbackManager().getKnockbackProfile(args[1]);
					CraftPlayer player1 = (CraftPlayer) player;
					player1.setKnockback(knockback3);
					sender.sendMessage(CC.AQUA + "Bound Knoackback Profile " + CC.GRAY + "(" + knockback3.getName() + ") " + CC.AQUA + " to Player " + CC.GRAY + player.getName() + "!");
					return true;
				} catch (Exception e2) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setallowfriction") || args[0].equalsIgnoreCase("allowfriction")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setFriction(Boolean.parseBoolean(args[2]));
					} catch (Exception notnumber2) {
						sender.sendMessage(CC.RED + "The boolean is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setdefault") || args[0].equalsIgnoreCase("default")) {
				if (args.length < 2) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						config.getKnockbackManager().setDefaultKnockback(knockback);
					} catch (Exception notnumber2) {
						sender.sendMessage(CC.RED + "The boolean is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setairvertical") || args[0].equalsIgnoreCase("airver")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setAirVertical(Double.parseDouble(args[2]));
					} catch (NumberFormatException notnumber) {
						sender.sendMessage(CC.RED + "The number is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setstopsprint") || args[0].equalsIgnoreCase("stopsprint")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setStopSprint(Boolean.parseBoolean(args[2]));
					} catch (Exception notnumber2) {
						sender.sendMessage(CC.RED + "The boolean is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setslowdown") || args[0].equalsIgnoreCase("slowdown")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setSlowDown(Double.parseDouble(args[2]));
					} catch (Exception notnumber2) {
						sender.sendMessage(CC.RED + "The number is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setbowvertical") || args[0].equalsIgnoreCase("bowvertical")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setBowVertical(Double.parseDouble(args[2]));
					} catch (NumberFormatException notnumber) {
						sender.sendMessage(CC.RED + "The number is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setrodhorizontal") || args[0].equalsIgnoreCase("rodhorizontal")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setRodHorizontal(Double.parseDouble(args[2]));
					} catch (NumberFormatException notnumber) {
						sender.sendMessage(CC.RED + "The number is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setrodvertical") || args[0].equalsIgnoreCase("rodvertical")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setRodVertical(Double.parseDouble(args[2]));
					} catch (NumberFormatException notnumber) {
						sender.sendMessage(CC.RED + "The number is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setpotionfallspeed") || args[0].equalsIgnoreCase("potionfallspeed")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setPotionSpeed(Double.parseDouble(args[2]));
					} catch (NumberFormatException notnumber) {
						sender.sendMessage(CC.RED + "The number is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setpotionoffset") || args[0].equalsIgnoreCase("potionoffset")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setPotionOffSet(Double.parseDouble(args[2]));
					} catch (NumberFormatException notnumber) {
						sender.sendMessage(CC.RED + "The number is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setpotiondistance") || args[0].equalsIgnoreCase("potiondistance")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setPotionDistance(Double.parseDouble(args[2]));
					} catch (NumberFormatException notnumber) {
						sender.sendMessage(CC.RED + "The number is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setcombo") || args[0].equalsIgnoreCase("combo")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setComboMode(Boolean.parseBoolean(args[1]));
					} catch (Exception notboolean) {
						sender.sendMessage(CC.RED + "The boolean is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setbowhorizontal") || args[0].equalsIgnoreCase("bowhorizontal")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setBowHorizontal(Double.parseDouble(args[2]));
					} catch (NumberFormatException notnumber) {
						sender.sendMessage(CC.RED + "The number is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setsprinthorizontal") || args[0].equalsIgnoreCase("sprinthor")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setSprintHorizontal(Double.parseDouble(args[2]));
					} catch (NumberFormatException notnumber) {
						sender.sendMessage(CC.RED + "The number is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setsprintvertical") || args[0].equalsIgnoreCase("sprintver")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setSprintVertical(Double.parseDouble(args[2]));
					} catch (NumberFormatException notnumber) {
						sender.sendMessage(CC.RED + "The number is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			if (args[0].equalsIgnoreCase("setairhorizontal") || args[0].equalsIgnoreCase("airhor")) {
				if (args.length < 3) {
					sender.sendMessage(this.incorrect);
					return true;
				}
				if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
					sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
					return true;
				}
				try {
					Knockback knockback = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
					try {
						knockback.setAirHorizontal(Double.parseDouble(args[2]));
					} catch (NumberFormatException notnumber) {
						sender.sendMessage(CC.RED + "The number is invalid.");
						return true;
					}
					knockback.save();
					config.getKnockbackManager().reloadProfile(args[1]);
					sender.sendMessage(getKnockbackSettings(knockback));
					reloadKnockbacks();
				} catch (NullPointerException e) {
					sender.sendMessage(this.incorrect);
				}
			}
			return true;
		}
		if (args.length < 2) {
			sender.sendMessage(this.incorrect);
			return true;
		}
		if (!config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1])) {
			sender.sendMessage(CC.AQUA + "That profile doesn't exist.");
			return true;
		}
		Knockback knockback2 = config.getKnockbackManager().getKnockbackProfiles().containsKey(args[1]) ? config.getKnockbackManager().getKnockbackProfiles().get(args[1]) : Knockback.getByName(args[1]);
		sender.sendMessage(CC.AQUA + "Viewing Knockback Profile " + CC.GRAY + knockback2.getName() + " (Default: " + config.getKnockbackManager().isDefaultKnockback(knockback2) + ")");
		sender.sendMessage(getKnockbackSettings(knockback2));
		return true;
	}

	public String getKnockbackSettings(Knockback knockback) {
		return CC.translate((config.getKnockbackManager().isDefaultKnockback(knockback) ? "&7 " : "") + "&bKnockback Settings &7(" + knockback.getName() + ")\n  &7&bHorizontal: &7" + knockback
				.getHorizontal() + ", &bVertical: &7" + knockback.getVertical() + "\n  &7&bSprint Horizontal: &7" + knockback
				.getSprintHorizontal() + ", &bSprint Vertical: &7" + knockback.getSprintVertical() + "\n  &7&bAir Horizontal: &7" + knockback
				.getAirHorizontal() + ", &bAir Vertical: &7" + knockback.getAirVertical() + "\n  &7&bBow Horizontal: &7" + knockback
				.getBowHorizontal() + ", &bBow Vertical: &7" + knockback.getBowVertical() + "\n  &7&bRod Horizontal: &7" + knockback
				.getRodHorizontal() + ", &bRod Vertical: &7" + knockback.getRodVertical() + "\n  &7&bPotion Speed: &7" + knockback
				.getPotionSpeed() + ", &bPotion Off Set: &7" + knockback.getPotionOffSet() + ", &bPotion Distance: &7" + knockback.getPotionDistance() + "\n  &7&bFriction: &7" + (
				knockback.isFriction() ? Double.valueOf(knockback.getFrictions()) : "Disabled") + "\n  &7&bCancel Sprint: &7" + knockback
				.isStopSprint() + ", &bCombo Mode: &7" + knockback.isComboMode() + "\n  &7&bVertical Limit: &7" + knockback
				.getVerticalLimit() + "\n  &7&bSlow Down (WTap): &7 " + knockback
				.getSlowDown());
	}

	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		Validate.notNull(sender, "Sender cannot be null");
		Validate.notNull(args, "Arguments cannot be null");
		Validate.notNull(alias, "Alias cannot be null");
		return new ArrayList<>(config.getKnockbackManager().getKnockbackProfiles().keySet());
	}


	private void reloadKnockbacks() {
		PlayerUtils.getOnlinePlayers().forEach(player -> {
			for (Knockback knockback : config.getKnockbackManager().getKnockbackProfiles().values()) {
				CraftPlayer player1 = (CraftPlayer) player;
				if (player1.getKnockback() != null && player1.getKnockback().getName().equalsIgnoreCase(knockback.getName()))
					player1.setKnockback(knockback);
			}
		});
	}
}
