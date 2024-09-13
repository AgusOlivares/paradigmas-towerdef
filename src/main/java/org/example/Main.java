package org.example;

import org.example.Enemigos.Enemy;
import org.example.Enemigos.EnemyDwarf;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Enemy enemy = new EnemyDwarf(4, 0, game.map, 1, 1, 1, 5, 5, 5);
        game.addEnemy(enemy);
        game.playGame();
        game.map.printMap();
    }
}