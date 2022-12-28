package com.zestarr.pluginportal.managers;

import com.zestarr.pluginportal.PluginPortal;
import com.zestarr.pluginportal.types.LocalPlugin;
import com.zestarr.pluginportal.types.OnlinePlugin;
import com.zestarr.pluginportal.utils.ConfigUtils;
import com.zestarr.pluginportal.utils.HttpUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.zestarr.pluginportal.utils.ConfigUtils.getPluginListFileConfig;

public class PluginManager {
    private final HashMap<String, OnlinePlugin> plugins = new HashMap<>();

    public void setup() {
        loadPluginList();
        try {
            loadPlugins();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPlugins() throws IOException {
        FileConfiguration config = getPluginListFileConfig();
        for (String str : getPluginListFileConfig().getConfigurationSection("Plugins.").getKeys(false)) {
            OnlinePlugin plugin = new OnlinePlugin();
            plugin.setDisplayName(str);
            plugin.setDefaultFileName(config.getString("Plugins." + str + ".fileName"));
            plugin.setDescription(config.getString("Plugins." + str + ".description"));
            plugin.setDownloadLink(config.getString("Plugins." + str + ".downloadLink"));
            plugin.setVersion(config.getString("Plugins." + str + ".version"));
            plugin.setSha256(config.getString("Plugins." + str + ".sha256"));
            plugin.setDownloadCount(config.getLong("Plugins." + str + ".downloadCount"));

            if (plugin.getDefaultFileName() == null) {
                plugin.setDefaultFileName(plugin.getDisplayName() + (plugin.getVersion() == null ? "" : "-" + plugin.getVersion()));
            }

            plugins.put(str, plugin);
        }

    }

    public void loadPluginList() {
        try {
            ConfigUtils.getPluginListFile().createNewFile();
            HttpUtils.downloadData("https://raw.githubusercontent.com/Zestarr/PluginPortal/master/PluginsList.yml", new File("PluginPortalPlugins.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean isPluginUpToDate(LocalPlugin plugin) {
        if (plugin.isInstalled()) {
            if (plugin.getOnlinePlugin().getVersion().equals(PluginPortal.getPluginManager().getPlugins().get(plugin.getOnlinePlugin().getDisplayName()).getVersion())) {
                return true;
            }
        }

        return false;
    }

    public void updatePlugin(LocalPlugin plugin) throws IOException {
        if (!isPluginUpToDate(plugin)) {
            new File(plugin.getFilePath()).delete();
            plugin.setInstalled(false);
            HttpUtils.downloadUniversalPlugin(plugin.getOnlinePlugin());
        }
    }


    public Map<String, OnlinePlugin> getPlugins() { return plugins; }

}