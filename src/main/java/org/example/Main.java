package org.example;

import org.example.Enemigos.EnemyDwarf;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        EnemyDwarf enemy = new EnemyDwarf();
        game.addEnemy(enemy);
        game.playGame();
        game.map.printMap();
    }
}