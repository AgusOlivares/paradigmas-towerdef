package org.example.Enemigos;

import org.example.Map.Map;

/**
 * Especialización de la clase Enemy para el enemigo de tipo humano
 * @author Luciana Puentes
 */

public class EnemyHuman extends Enemy {
    public EnemyHuman(int row, int col, Map map, int health, int gold, int magika, int damage, int speed, int range) {
        super(row, col, map, health, gold, magika, damage, speed, range);
    }
}
