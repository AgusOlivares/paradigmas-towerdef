package org.example.Enemigos;

import org.example.Map.Map;

public class EnemyHobbit extends Enemy {

    boolean randomRewards;

    public EnemyHobbit(int row, int col, Map map, int health, int gold, int magika, int damage, int speed, int range) {
        super(row, col, map, health, gold, magika, damage, speed, range);
        this.randomRewards = randomRewards();
    }

    public boolean randomRewards() {
        return Math.random() < 0.5;
    }

}
