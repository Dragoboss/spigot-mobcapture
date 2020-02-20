package net.johnbrooks.mh.commands;

import net.johnbrooks.mh.Language;
import net.johnbrooks.mh.Main;
import net.johnbrooks.mh.Settings;
import net.johnbrooks.mh.items.UniqueProjectileData;
import net.johnbrooks.mh.managers.UpdateManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class CommandMobCapture implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("MobCapture.Admin")) {
            sender.sendMessage(Language.getKey("errorCommandPermissions"));
            return true;
        }

        if (args.length == 0) {
            if (sender instanceof Player) {
                sender.sendMessage(Language.getKeyWithoutPrefix("prefix") + "This plugin was created by WiseHollow! (and modified by mjl1010)");
                Player player = (Player) sender;
                TextComponent message = new TextComponent(Language.getKeyWithoutPrefix("prefix") + ChatColor.UNDERLINE + "Click here " +
                        ChatColor.RESET + "" + ChatColor.BLUE + "to see my profile and my other plugins! " + Main.plugin.getName() + "!");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/members/wisehollow.14804/"));
                player.spigot().sendMessage(message);
            } else
                sender.sendMessage(ChatColor.BLUE + Main.plugin.getName() + " was created by WiseHollow. Check out my other plugins on my SpigotMC profile!");
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("version")) {
                sender.sendMessage(ChatColor.BLUE + Main.plugin.getName() + "'s current version: " + Main.plugin.getDescription().getVersion());
                if (UpdateManager.isUpdateAvailable())
                    sender.sendMessage(Language.getKeyWithoutPrefix("prefix") + "Update is available.");
                else
                    sender.sendMessage(Language.getKeyWithoutPrefix("prefix") + "Everything is up-to-date.");
                return true;
            } else if (args[0].equalsIgnoreCase("update")) {
                if (UpdateManager.isUpdateAvailable()) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        TextComponent message = new TextComponent(Language.getKeyWithoutPrefix("prefix") + ChatColor.UNDERLINE + "Click here " +
                                ChatColor.RESET + "" + ChatColor.BLUE + "to get the latest version of " + Main.plugin.getName() + "!");
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://goo.gl/yiWQnT"));
                        player.spigot().sendMessage(message);
                    }
                    else
                        sender.sendMessage("Go to https://goo.gl/yiWQnT to get the latest version of " + Main.plugin.getName() + "!");
                }
                else
                    sender.sendMessage(Language.getKeyWithoutPrefix("prefix") + "Everything is up-to-date.");
                return true;
            } else if (args[0].equalsIgnoreCase("reload")) {
                Main.plugin.reloadConfig();
                Settings.load();
                sender.sendMessage(Language.getKeyWithoutPrefix("prefix") + "Configuration has been reloaded!");
                return true;
            } else if (args[0].equalsIgnoreCase("regenConfig")) {
                File file = new File("plugins" + File.separator + Main.plugin.getName() + File.separator + "config.yml");
                file.delete();
                Main.plugin.saveDefaultConfig();
                Main.plugin.reloadConfig();
                Settings.load();
                sender.sendMessage(Language.getKeyWithoutPrefix("prefix") + "Configuration has been regenerated and reloaded!");
                return true;
            } else if (args[0].equalsIgnoreCase("spawn")) {
                if (!UniqueProjectileData.isEnabled()) {
                    sender.sendMessage("Unique Projectiles are not enabled in the config.");
                } else if (sender instanceof Player) {
                    ((Player) sender).getInventory().addItem(UniqueProjectileData.spawn());
                    sender.sendMessage("Spawned Unique Projectile.");
                }
                return true;
            }
        }

        return false;
    }
}
