package rievrs.proScoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import rievrs.proScoreboard.events.PlayerEvents;

public class Main extends JavaPlugin implements Listener {
	
	public static Main instance;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		Bukkit.getConsoleSender().sendMessage("§2Plugin Iniciado - §6Pro Scoreboard");		
		
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
		
		if(!Bukkit.getOnlinePlayers().isEmpty()) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				ScoreboardMain.createBoard(player);
				ScoreboardMain.start(player);
			}
		}
	}	

}