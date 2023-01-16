package com.zestarr.pluginportal.commands.ppmSubCommands;

import com.zestarr.pluginportal.PluginPortal;
import com.zestarr.pluginportal.commands.commandUtility.SubCommandEnum;
import com.zestarr.pluginportal.commands.commandUtility.SubCommandManager;
import com.zestarr.pluginportal.type.LocalPlugin;
import com.zestarr.pluginportal.type.PreviewingPlugin;
import com.zestarr.pluginportal.utils.ChatUtil;
import org.bukkit.command.CommandSender;

public class UpdateSubCommand extends SubCommandManager {
    @Override
    public void execute(CommandSender sender, String[] args, SubCommandEnum subCommandEnum) {
        if (args.length == 1) {
            sender.sendMessage(ChatUtil.format("&7&l[&b&lPPM&7&l] &8&l> &7Listing all plugins that can be updated: "));
            for (LocalPlugin plugin : PluginPortal.getMainInstance().getLocalPluginManager().getPlugins().values()) {
                if (plugin.updateNeeded()) {
                    sender.sendMessage(ChatUtil.format(" &a+ &7" + plugin.getPreviewingPlugin().getSpigotName()));
                }
            }
        }

        if (args.length != 2) return;
        String name = args[1];
        if (PluginPortal.getMainInstance().getLocalPluginManager().getPlugins().get(name) == null) {
            sender.sendMessage(ChatUtil.format("&7&l[&b&lPPM&7&l] &8&l> &c" + name + " is not installed. Did you mean to run /ppm install " + name + "?"));
            return;
        }
        LocalPlugin plugin = PluginPortal.getMainInstance().getLocalPluginManager().getPlugins().get(name);
        if (plugin.matchesVersion(new PreviewingPlugin(plugin.getPreviewingPlugin().getId()).getVersion())) {
            sender.sendMessage(ChatUtil.format("&7&l[&b&lPPM&7&l] &8&l> &c" + name + " is already up to date."));
            return;
        } else {
            PluginPortal.getMainInstance().getDownloadManager().update(plugin);
            // def will cause no issues/errors! we need a better detection system for deleting plugins btw
            sender.sendMessage(ChatUtil.format("&7&l[&b&lPPM&7&l] &8&l> &c" + name + " has been updated to version " + new PreviewingPlugin(plugin.getPreviewingPlugin().getId()).getVersion() + "."));
        }


    }
}
