package net.lldv.llamachatgames.components.tasks;

import net.lldv.llamachatgames.LlamaChatGames;
import net.lldv.llamachatgames.components.api.API;
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
        if (LlamaChatGames.getApi().isRunning()) {
            this.plugin.getServer().getOnlinePlayers().values().forEach(player -> player.sendMessage(Language.get("nobody-solved")));
            LlamaChatGames.getApi().setCurrentGame(null);
        }
        if (this.plugin.getServer().getOnlinePlayers().values().size() >= API.getNeededPlayers()) {
            LlamaChatGames.getApi().startRandomChatGame();
        }
    }
}
