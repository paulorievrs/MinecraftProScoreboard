package rievrs.proScoreboard.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import rievrs.proScoreboard.LobbyBoard;
import rievrs.proScoreboard.ScoreboardMain;

public class PlayerEvents implements Listener {

	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		ScoreboardMain.createBoard(event.getPlayer());
		ScoreboardMain.start(event.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		LobbyBoard board = new LobbyBoard(event.getPlayer().getUniqueId());
		if(board.hasID()) {
			board.stop();
		}
	}
}
