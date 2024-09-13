package org.example;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.map.printMap();
        game.addTower();
        game.playGame(1);

    }
}