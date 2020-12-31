package net.lldv.llamachatgames;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.TaskHandler;
import lombok.Getter;
import net.lldv.llamachatgames.commands.ChatgameCommand;
import net.lldv.llamachatgames.components.api.API;
import net.lldv.llamachatgames.components.data.ChatGame;
import net.lldv.llamachatgames.components.language.Language;
import net.lldv.llamachatgames.components.tasks.ChatGameTask;
import net.lldv.llamachatgames.listeners.EventListener;

public class LlamaChatGames extends PluginBase {

    @Getter
    private static API api;

    @Getter
    private TaskHandler chatGameTask;

    @Override
    public void onEnable() {
        try {
            this.saveDefaultConfig();
            Language.init(this);
            api = new API(this);
            API.setNeededPlayers(this.getConfig().getInt("Settings.NeededPlayers"));
            this.loadPlugin();
            this.getLogger().info("§aLlamaChatGames successfully started.");
        } catch (Exception e) {
            e.printStackTrace();
            this.getLogger().error("§4Failed to load LlamaChatGames.");
        }
    }

    private void loadPlugin() {
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        this.getServer().getCommandMap().register("llamachatgames", new ChatgameCommand(this));

        this.getConfig().getSection("ChatGames").getAll().getKeys(false).forEach(s -> this.getConfig().getStringList("ChatGames." + s).forEach(e -> {
            String name = this.getConfig().getString("Settings." + s + ".Name");
            ChatGame.GameType type = ChatGame.GameType.valueOf(s.toUpperCase());
            int time = this.getConfig().getInt("Settings." + s + ".Time");
            double money = this.getConfig().getDouble("Settings." + s + ".Money");
            api.chatGames.add(new ChatGame(name, type, e, time, money));
        }));

        this.chatGameTask = this.getServer().getScheduler().scheduleDelayedRepeatingTask(this, () -> {
            ChatGameTask runnable = new ChatGameTask(this);
            runnable.setId(this.getServer().getScheduler().scheduleRepeatingTask(this, runnable, this.getConfig().getInt("Settings.RepeatTime") * 20).getTaskId());
        }, this.getConfig().getInt("Settings.RepeatTime") * 20, this.getConfig().getInt("RepeatTime") * 20);
    }

}