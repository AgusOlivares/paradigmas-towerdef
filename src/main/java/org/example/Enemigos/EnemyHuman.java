package org.example.Enemigos;

import org.example.Map.Map;

public class EnemyHuman extends Enemy {
    public EnemyHuman(int row, int col, Map map, int health, int gold, int magika, int damage, int speed, int range) {
        super(row, col, map, health, gold, magika, damage, speed, range);
    }
}
