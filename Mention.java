package eu.shotwar.utils;

import java.util.Collection; 

import net.md_5.bungee.api.ChatColor;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

public class Mention implements Listener {
	
	public Mention () {}
	
	@EventHandler
	private final void onPlayerChatTabComplete(final PlayerChatTabCompleteEvent event) {
		final String token = event.getLastToken();
		
		if (token.startsWith("@")) {
			
			final Collection<String> autoCompletions = event.getTabCompletions();
			autoCompletions.clear();
			
			final String begin = token.replaceAll("@", "").toLowerCase();
			for (final Player player : Bukkit.getOnlinePlayers()) {
			
				final String playerName = player.getName();
				
				if (playerName.toLowerCase().startsWith(begin)) {
					autoCompletions.add("@" + playerName);
				}
			}
		}
	}
	
	@EventHandler
	private final void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
		for (final Player player : Bukkit.getOnlinePlayers()) {
			
			final String message = event.getMessage();
			
			final String playerName = "@" + player.getName();
			
			if (StringUtils.containsIgnoreCase(message,  playerName)) {
				event.setMessage(message.replaceAll(playerName, ChatColor.GOLD + playerName));
				player.playNote(player.getLocation(), Instrument.PIANO, Note.natural(1,  Note.Tone.A));
			}
		}
	}
}
