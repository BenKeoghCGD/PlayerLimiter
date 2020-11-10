package com.AxiusDesigns.AxiusPlugins.PlayerLimiter.Events;

import com.AxiusDesigns.AxiusPlugins.PlayerLimiter.PlayerLimiterBungee;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Random;

public class PingListenerBungee implements Listener {

    Configuration configData;
    PlayerLimiterBungee plugin;

    public PingListenerBungee(PlayerLimiterBungee playerLimiterBungee) {
        this.plugin = playerLimiterBungee;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ProxyPingEvent e) {
        ServerPing.Players players = e.getResponse().getPlayers();
        configData = plugin.configData;
        if(!isBoolean(configData.get("Random.Enabled").toString())) {
            System.out.print(plugin.prefix + "Config line \"Enabled\" under \"Random\" Is not a valid boolean.");
            if(isInteger(configData.get("Limit.Addition").toString())) players.setMax(players.getOnline() + configData.getInt("Limit.Addition"));
            else System.out.print(plugin.prefix + "Config line \"Addition\" under \"Limit\" Is not a valid integer.");
        }
        else {
            if(Boolean.parseBoolean(configData.get("Random.Enabled").toString())) {
                if(!isInteger(configData.get("Random.Min").toString())||!isInteger(configData.get("Random.Max").toString())) System.out.print(plugin.prefix + "Config line \"Min\" and/or \"Max\" under \"Random\" Is/Are not a valid integer.");
                else players.setMax(players.getOnline() + new Random().nextInt((Integer.parseInt(configData.get("Random.Max").toString()) - Integer.parseInt(configData.get("Random.Min").toString())) + 1) + Integer.parseInt(configData.get("Random.Min").toString()));
            }
            else {
                if(isInteger(configData.get("Limit.Addition").toString())) players.setMax(players.getOnline() + configData.getInt("Limit.Addition"));
                else System.out.print(plugin.prefix + "Config line \"Addition\" under \"Limit\" Is not a valid integer.");
            }
        }
        e.setResponse(new ServerPing(e.getResponse().getVersion(), players, e.getResponse().getDescriptionComponent(), e.getResponse().getFaviconObject()));
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
