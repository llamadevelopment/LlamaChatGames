package net.lldv.llamachatgames.components.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChatGame {

    private final String game;
    private final GameType gameType;
    private final String neededText;
    private final int time;
    private final double money;

    public enum GameType {
        SCRAMBLE,
        TYPE,
        OPPOSITE
    }

}
