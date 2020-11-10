package com.AxiusDesigns.AxiusPlugins.PlayerLimiter.YAMLHandlers;

import com.AxiusDesigns.AxiusPlugins.PlayerLimiter.PlayerLimiter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class Config {

    PlayerLimiter plugin;

    HashMap<String, String> messages;

    public HashMap<String, String> configData;

    public Config(PlayerLimiter instance) {
        plugin = instance;
        this.configData = new HashMap<String, String>();
    }

    public HashMap<String, String> getMessageData() {
        File f = new File(plugin.getDataFolder().getParentFile() + File.separator + "AxiusPlugins" + File.separator + "PlayerLimiter" + File.separator + "config.yml");
        if(!f.exists()) {
            saveMessages();
            try {
                f.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return loadMessages();
    }

    public HashMap<String, String> loadMessages() {
        File f = new File(plugin.getDataFolder().getParentFile() + File.separator + "AxiusPlugins" + File.separator + "PlayerLimiter" + File.separator + "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        for(String message : config.getConfigurationSection("").getKeys(true)) this.configData.put(message, config.getString(message));
        return this.configData;
    }

    private void saveMessages() {
        setMessage("Limit.Addition", "1", false);
        setMessage("Random.Enabled", "false", false);
        setMessage("Random.Min", "1", false);
        setMessage("Random.Max", "3", false);
    }

    private void setMessage(String key, String value, boolean override) {
        File f = new File(plugin.getDataFolder().getParentFile() + File.separator + "AxiusPlugins" + File.separator + "PlayerLimiter" + File.separator + "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        if(!config.isSet(key)||override) {
            config.set(key, value);
            try {
                config.save(f);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
