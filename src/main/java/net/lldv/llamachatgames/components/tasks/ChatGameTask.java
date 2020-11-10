package net.lldv.llamachatgames.components.tasks;

import net.lldv.llamachatgames.LlamaChatGames;
import net.lldv.llamachatgames.components.api.ChatGameAPI;
import net.lldv.llamachatgames.components.language.Language;

public class ChatGameTask implements Runnable {

    private int id;
    private final LlamaChatGames plugin;

    public ChatGameTask(LlamaChatGames plugin) {
        this.plugin = plugin;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void run() {
        if (this.plugin.getChatGameAPI().isRunning()) {
            this.plugin.getServer().getOnlinePlayers().values().forEach(player -> player.sendMessage(Language.get("nobody-solved")));
            this.plugin.getChatGameAPI().setCurrentGame(null);
        }
        if (this.plugin.getServer().getOnlinePlayers().values().size() >= ChatGameAPI.getNeededPlayers()) {
            this.plugin.getChatGameAPI().startRandomChatGame();
        }
    }
}
