package org.example;

import org.example.Enemigos.Enemy;
import org.example.Enemigos.EnemyDwarf;

/**
 * clase main que ejecuta el juego
 * @author Agustín Olivares
 */

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Enemy enemy = new EnemyDwarf(4, 0, game.map, 1, 1, 1, 10, 1, 5);
        game.addEnemy(enemy);
        game.playGame();
    }
}