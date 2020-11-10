package net.lldv.llamachatgames.components.api;

import cn.nukkit.Player;
import cn.nukkit.level.Sound;
import cn.nukkit.network.protocol.PlaySoundPacket;
import lombok.Getter;
import lombok.Setter;
import net.lldv.llamachatgames.LlamaChatGames;
import net.lldv.llamachatgames.components.data.ChatGame;
import net.lldv.llamachatgames.components.language.Language;
import net.lldv.llamaeconomy.LlamaEconomy;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ChatGameAPI {

    private final LlamaChatGames instance;

    public ChatGameAPI(LlamaChatGames instance) {
        this.instance = instance;
    }

    public List<ChatGame> chatGames = new ArrayList<>();

    @Getter
    @Setter
    private ChatGame currentGame;

    @Getter
    @Setter
    private static int neededPlayers;

    public void startRandomChatGame() {
        Random rand = new Random();
        ChatGame chatGame = this.chatGames.get(rand.nextInt(this.chatGames.size()));
        this.setCurrentGame(chatGame);
        this.instance.getServer().getOnlinePlayers().values().forEach(player -> this.playSound(player, Sound.NOTE_PLING));
        if (this.getCurrentGame().getGameType() == ChatGame.GameType.SCRAMBLE) {
            this.instance.getServer().broadcastMessage(Language.get("chatgame-start-broadcast", chatGame.getGame(), this.scramble(chatGame.getNeededText())));
            return;
        }
        if (this.getCurrentGame().getGameType() == ChatGame.GameType.OPPOSITE) {
            this.instance.getServer().broadcastMessage(Language.get("chatgame-start-broadcast", chatGame.getGame(), chatGame.getNeededText().split(":")[0]));
            return;
        }
        this.instance.getServer().broadcastMessage(Language.get("chatgame-start-broadcast", chatGame.getGame(), chatGame.getNeededText()));
    }

    public boolean isRunning() {
        return this.getCurrentGame() != null;
    }

    public void winChatGame(Player player, ChatGame chatGame) {
        this.setCurrentGame(null);
        this.instance.getServer().broadcastMessage(Language.get("chatgame-won", player.getName()));
        CompletableFuture.runAsync(() -> {
            LlamaEconomy.getAPI().addMoney(player, chatGame.getMoney());
            player.sendMessage(Language.get("chatgame-reward", chatGame.getMoney()));
            this.playSound(player, Sound.RANDOM_LEVELUP);
        });
    }

    public String scramble(String input) {
        ArrayList<Character> characters = new ArrayList<>();
        char[] arrayOfChar;
        int j = (arrayOfChar = input.toCharArray()).length;
        for (int i = 0; i < j; i++) {
            char c = arrayOfChar[i];
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while (characters.size() != 0) {
            int random = (int) (Math.random() * characters.size());
            output.append(characters.remove(random));
        }
        return output.toString();
    }

    private void playSound(Player player, Sound sound) {
        PlaySoundPacket packet = new PlaySoundPacket();
        packet.name = sound.getSound();
        packet.x = new Double(player.getLocation().getX()).intValue();
        packet.y = (new Double(player.getLocation().getY())).intValue();
        packet.z = (new Double(player.getLocation().getZ())).intValue();
        packet.volume = 1.0F;
        packet.pitch = 1.0F;
        player.dataPacket(packet);
    }

}
