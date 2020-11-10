package com.AxiusDesigns.AxiusPlugins.PlayerLimiter.Events;

import com.AxiusDesigns.AxiusPlugins.PlayerLimiter.PlayerLimiter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.HashMap;
import java.util.Random;

public class PingListener implements Listener {

    public HashMap<String, String> configData =  new HashMap<String, String>();
    PlayerLimiter plugin;

    public PingListener(PlayerLimiter playerLimiter) {
        this.plugin = playerLimiter;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ServerListPingEvent e) {
        configData = plugin.configData;
        if(!isBoolean(configData.get("Random.Enabled"))) {
            System.out.print(plugin.prefix + "Config line \"Enabled\" under \"Random\" Is not a valid boolean.");
            if(isInteger(configData.get("Limit.Addition"))) e.setMaxPlayers(Bukkit.getOnlinePlayers().size() + Integer.parseInt(configData.get("Limit.Addition")));
            else System.out.print(plugin.prefix + "Config line \"Addition\" under \"Limit\" Is not a valid integer.");
        }
        else {
            if(Boolean.parseBoolean(configData.get("Random.Enabled"))) {
                if(!isInteger(configData.get("Random.Min"))||!isInteger(configData.get("Random.Max"))) System.out.print(plugin.prefix + "Config line \"Min\" and/or \"Max\" under \"Random\" Is/Are not a valid integer.");
                else e.setMaxPlayers(Bukkit.getOnlinePlayers().size() + new Random().nextInt((Integer.parseInt(configData.get("Random.Max")) - Integer.parseInt(configData.get("Random.Min"))) + 1) + Integer.parseInt(configData.get("Random.Min")));
            }
            else {
                if(isInteger(configData.get("Limit.Addition"))) e.setMaxPlayers(Bukkit.getOnlinePlayers().size() + Integer.parseInt(configData.get("Limit.Addition")));
                else System.out.print(plugin.prefix + "Config line \"Addition\" under \"Limit\" Is not a valid integer.");
            }
        }

    }


    //API

    private boolean isBoolean(String s) {
        if(s.isEmpty()) return false;
        if(s.toLowerCase() == "true") return true;
        if(s.toLowerCase() == "false") return true;
        return false;
    }

    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}
