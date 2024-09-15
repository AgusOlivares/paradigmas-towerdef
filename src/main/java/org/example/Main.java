package org.example;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.map.printMap();
        game.player.placeTower(game.map);
        game.playGame(1);

    }
}