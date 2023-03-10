package com.zestarr.pluginportal.commands.subcommands;

import com.zestarr.pluginportal.PluginPortal;
import com.zestarr.pluginportal.commands.commandutil.SubCommandEnum;
import com.zestarr.pluginportal.commands.commandutil.SubCommandManager;
import com.zestarr.pluginportal.type.LocalPlugin;
import com.zestarr.pluginportal.utils.ChatUtil;
import org.bukkit.command.CommandSender;

public class ListSubCommand extends SubCommandManager {
    @Override
    public void execute(CommandSender sender, String[] args, SubCommandEnum subCommandEnum) {
        sender.sendMessage(ChatUtil.format("&7&l[&b&lPPM&7&l] &8&l> &7Listing all installed plugins..."));
        for (LocalPlugin plugin : PluginPortal.getMainInstance().getLocalPluginManager().getPlugins().values()) {
            if (plugin.getPreviewingPlugin().getSpigotName() != null && !plugin.getPreviewingPlugin().getSpigotName().isEmpty()) {
                sender.sendMessage(ChatUtil.format(" &a+ &7" + plugin.getPreviewingPlugin().getSpigotName()));
            }
        }
    }
}
