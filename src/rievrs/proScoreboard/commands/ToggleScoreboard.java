package rievrs.proScoreboard.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import rievrs.proScoreboard.ScoreboardMain;

public class ToggleScoreboard implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		
		if(!(sender instanceof Player)) {
		    return false;
		}
		
		Player p = (Player) sender;
		Scoreboard board = p.getScoreboard();
		
		if(board == null) {
			ScoreboardMain.start(p);
		} else {
			Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			p.setScoreboard(scoreboard);
		}
		
		return true;
		
	}
	
}
