package rievrs.proScoreboard;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public class ScoreboardMain {
	
	private static int taskID;
	public static Objective obj;
	
	private static int MAX_CHARS = 24;
	
	private static String serializeToScoreboard(String string, boolean right) {
		int spacesToAdd = (MAX_CHARS - string.length())/2 + 2;
		String spaces = String.join("", Collections.nCopies(spacesToAdd, " "));
		
		if(right) {
			return string + " " + " ";
		}
		
		return spaces+string;
	}
	
	public static void start(Player p) {
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			
			int count = 0;
			LobbyBoard board = new LobbyBoard(p.getUniqueId());
			
			@Override
			public void run() {
				
				 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				if(!board.hasID()) {
					board.setID(taskID);
				}
				
				if(count == 13) {
					count = 0;
				}
				ScoreboardMain.addTeam("datetime", "§3", formatter.format(LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))), "", p.getScoreboard());
				
				
				createBoard(p);
				
				count++;
				
			}
			
		}, 0, 20);
	}
	
	public static void updateTeam(Team team, String prefix, String suffix) {
		team.setSuffix(serializeToScoreboard(suffix, true));
		team.setPrefix(serializeToScoreboard(prefix, false));
	}
	
	public static Team addTeam(String teamName, String entry, String prefix, String suffix, Scoreboard scoreboard) {
		Team team = scoreboard.getTeam(teamName);
		if (team == null) {
			team = scoreboard.registerNewTeam(teamName);
		}
			
		if(prefix.length() > 0) {
			team.setPrefix(serializeToScoreboard(prefix, false));
		}
		if(suffix.length() > 0) {
			team.setSuffix(serializeToScoreboard(suffix, true));
		}
		
		team.addEntry(entry);
		return team;
	}
	
	public static void createBoard(Player p) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
		obj = scoreboard.registerNewObjective("ProScoreboard", "dummy", ChatColor.GOLD + "" + ChatColor.BOLD + "TEKCRAFT");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		obj.getScore("§3").setScore(6);
		obj.getScore("§8").setScore(5);
		obj.getScore("§6").setScore(4);
		obj.getScore("§5").setScore(3);
		obj.getScore("§0").setScore(2);
		
		obj.getScore("§c").setScore(1);
		obj.getScore("§d").setScore(0);
		
		ScoreboardMain.addTeam("datetime", "§3", formatter.format(LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))), "", scoreboard);
		
		ScoreboardMain.addTeam("blank_space_1", "§8", "", "", scoreboard);
		
		ScoreboardMain.addTeam("players", "§6", createKeyStyle("Jogadores: "), createValueStyle(String.valueOf(Bukkit.getOnlinePlayers().size())), scoreboard);
		ScoreboardMain.addTeam("mobkills", "§5", createKeyStyle("Mobs matados: "), createValueStyle(String.valueOf(p.getStatistic(Statistic.MOB_KILLS))), scoreboard);
		ScoreboardMain.addTeam("deaths", "§0", createKeyStyle("Mortes: "), createValueStyle(String.valueOf(p.getStatistic(Statistic.DEATHS))), scoreboard);
		
		ScoreboardMain.addTeam("blank_space_2", "§c", "", "", scoreboard);
		
		ScoreboardMain.addTeam("ping", "§d", createKeyStyle("Ping: "), createValueStyle(String.valueOf(p.getPing()) + "ms"), scoreboard);
		
		p.setScoreboard(scoreboard);
		
		scoreboard.getTeam("mobkills").addEntry(p.getName());
		scoreboard.getTeam("players").addEntry(p.getName());
		
	}
	
	public static String createKeyStyle(String key) {
		return ChatColor.WHITE + "" + ChatColor.BOLD + key;
	}
	
	public static String createValueStyle(String value) {
		return ChatColor.GRAY + value;
	}
	
}
