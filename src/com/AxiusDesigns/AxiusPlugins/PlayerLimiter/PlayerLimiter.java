package com.AxiusDesigns.AxiusPlugins.PlayerLimiter;

import com.AxiusDesigns.AxiusPlugins.PlayerLimiter.Events.PingListener;
import com.AxiusDesigns.AxiusPlugins.PlayerLimiter.YAMLHandlers.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class PlayerLimiter extends JavaPlugin {

    public String prefix = "[PlayerLimiter] ";

    public Config config;
    public HashMap<String, String> configData =  new HashMap<String, String>();

    @Override
    public void onEnable() {
        System.out.print(prefix + "Enabling plugin");

        //FILES
        System.out.print("- Checking files (1/2)");
        File data = new File(this.getDataFolder().getParentFile() + File.separator + "AxiusPlugins");
        if(!data.exists()) {
            System.out.print("- File not found, creating (1/2)");
            data.mkdir();
            System.out.print("- Checking files (2/2)");
            File rph = new File(data + File.separator + "PlayerLimiter");
            if(!rph.exists()) {
                System.out.print("- File not found, creating (2/2)");
                rph.mkdir();
            }
            else System.out.print("- File found (1/2)");

        }
        else System.out.print("- File found (2/2)");

        //CONF/MESSAGES

        System.out.print("- Loading config.yml");
        this.config = new Config(this);
        this.configData = this.config.getMessageData();

        //COMMANDS

        //EVENTS
        getServer().getPluginManager().registerEvents(new PingListener(this), this);

        //UPDATES
        System.out.print("- Checking for updates.");
        System.out.print(getUpdate());
        if(getUpdate() != null) {
            float f = Float.parseFloat(getUpdate());
            if(f > Float.parseFloat(getDescription().getVersion())) {
                System.out.print("- Update found (" + f + ").");
            }
        }
        System.out.print(prefix + "Plugin enabled.");

    }

    public String getUpdate() {
        String v = "";
        try {
            HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=72999").getBytes("UTF-8"));
            String vv = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            v = vv;
        }
        catch (Exception ex) {
            System.out.print("- Failed to check for an update on spigot.");
        }
        return v;
    }
}
