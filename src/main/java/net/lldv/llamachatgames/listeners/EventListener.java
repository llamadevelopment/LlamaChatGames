package net.lldv.llamachatgames.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import net.lldv.llamachatgames.LlamaChatGames;
import net.lldv.llamachatgames.components.data.ChatGame;

public class EventListener implements Listener {

    private final LlamaChatGames instance;

    public EventListener(LlamaChatGames instance) {
        this.instance = instance;
    }

    @EventHandler
    public void on(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (this.instance.getChatGameAPI().getCurrentGame() != null) {
            ChatGame chatGame = this.instance.getChatGameAPI().getCurrentGame();
            if (chatGame.getGameType() == ChatGame.GameType.OPPOSITE) {
                if (message.equalsIgnoreCase(chatGame.getNeededText().split(":")[1])) {
                    this.instance.getChatGameAPI().winChatGame(player, chatGame);
                }
                return;
            }
            if (message.equalsIgnoreCase(chatGame.getNeededText())) {
                this.instance.getChatGameAPI().winChatGame(player, chatGame);
            }
        }
    }

}
