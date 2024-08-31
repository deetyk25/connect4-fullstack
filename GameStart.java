package com.company;

public class GameStart {
    private final Connect4Game game;
    private final GUISetup ourGUI;
    public GameStart() {
        game = new Connect4Game("R", "Y", 6, 7);
        ourGUI = new GUISetup(game.isOneReallyPlaying(), game, 6, 7);
    }
}
