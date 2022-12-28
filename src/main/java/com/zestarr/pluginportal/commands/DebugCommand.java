package com.zestarr.pluginportal.commands;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.zestarr.pluginportal.PluginPortal;
import com.zestarr.pluginportal.utils.HttpUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.zestarr.pluginportal.utils.ChatUtils.format;

public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(format("&7&l[&b&lPPM&7&l] &8&l> &cPlease specify a debug command!"));
            return true;
        }

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("download")) {
                if (args[1] == null || PluginPortal.getPluginManager().getPlugins().get(args[1]) == null) {
                    sender.sendMessage(format("&7&l[&b&lPPM&7&l] &8&l> &cPlease specify a plugin to download!"));
                    return true;
                } else {
                    HttpUtils.downloadUniversalPlugin(PluginPortal.getPluginManager().getPlugins().get(args[1]));
                    sender.sendMessage(format("&7&l[&b&lPPM&7&l] &8&l> &7Plugin has been downloaded!"));
                    for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
                        System.out.println("\n---------------------------------------\n");
                        System.out.println("plugin#getName(): " + plugin.getName());
                        System.out.println("getDescription#getName(): " + plugin.getDescription().getName());
                        System.out.println("Version: " + plugin.getDescription().getVersion());
                        System.out.println("Authors: " + plugin.getDescription().getAuthors());
                        System.out.println("Description: " + plugin.getDescription().getDescription());
                    }


                }
            } else {
                sender.sendMessage(format("&7&l[&b&lPPM&7&l] &8&l> &cPlease specify a debug command!"));
                return true;
            }
        }



        return false;
    }
}