package net.lldv.llamachatgames.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.lldv.llamachatgames.LlamaChatGames;
import net.lldv.llamachatgames.components.language.Language;

public class ChatgameCommand extends PluginCommand<LlamaChatGames> {

    public ChatgameCommand(LlamaChatGames owner) {
        super(owner.getConfig().getString("Commands.Chatgame.Name"), owner);
        this.setDescription(owner.getConfig().getString("Commands.Chatgame.Description"));
        this.setPermission(owner.getConfig().getString("Commands.Chatgame.Permission"));
        this.setAliases(owner.getConfig().getStringList("Commands.Chatgame.Aliases").toArray(new String[]{}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(this.getPermission())) {
            if (!this.getPlugin().getChatGameAPI().isRunning()) {
                this.getPlugin().getChatGameAPI().startRandomChatGame();
            } else sender.sendMessage(Language.get("game-already-running"));
        } else sender.sendMessage(Language.get("no-permission"));
        return true;
    }

}
