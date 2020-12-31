package net.lldv.llamachatgames.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import net.lldv.llamachatgames.LlamaChatGames;
import net.lldv.llamachatgames.components.data.ChatGame;

public class EventListener implements Listener {

    @EventHandler
    public void on(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (LlamaChatGames.getApi().getCurrentGame() != null) {
            ChatGame chatGame = LlamaChatGames.getApi().getCurrentGame();
            if (chatGame.getGameType() == ChatGame.GameType.OPPOSITE) {
                if (message.equalsIgnoreCase(chatGame.getNeededText().split(":")[1])) {
                    LlamaChatGames.getApi().winChatGame(player, chatGame);
                }
                return;
            }
            if (message.equalsIgnoreCase(chatGame.getNeededText())) {
                LlamaChatGames.getApi().winChatGame(player, chatGame);
            }
        }
    }

}
