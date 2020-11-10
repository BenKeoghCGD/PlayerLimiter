package com.AxiusDesigns.AxiusPlugins.PlayerLimiter;

import com.AxiusDesigns.AxiusPlugins.PlayerLimiter.Events.PingListenerBungee;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class PlayerLimiterBungee extends Plugin {

    public String prefix = "[PlayerLimiter] ";

    public Configuration configData;

    @Override
    public void onEnable() {
        System.out.print(prefix + "Enabling plugin");

        //FILES
        System.out.print("- Checking files (1/2)");
        File data = new File(this.getDataFolder().getParentFile() + File.separator + "AxiusPlugins");
        if(!data.exists()) {
            System.out.print("- File not found, creating (1/2)");
            data.mkdir();
        }
        else System.out.print("- File found (1/2)");

        System.out.print("- Checking files (2/2)");
        File a = new File(this.getDataFolder().getParentFile() + File.separator + "AxiusPlugins" + File.separator + "PlayerLimiter");
        if(!a.exists()) {
            System.out.print("- File not found, creating (2/2)");
            a.mkdir();
        }
        else System.out.print("- File found (2/2)");

        //CONF/MESSAGES
        System.out.print("- Loading config.yml");

        File file = new File(a, "config.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            configData = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(a, "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //COMMANDS

        //EVENTS
        getProxy().getPluginManager().registerListener(this, new PingListenerBungee(this));

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
