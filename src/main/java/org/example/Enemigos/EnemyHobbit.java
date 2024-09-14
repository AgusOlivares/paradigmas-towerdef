package org.example.Enemigos;

import org.example.Map.Map;

public class EnemyHobbit extends Enemy {

    boolean randomRewards;

    public EnemyHobbit(Map map) {
        super(map, 100, 66, 10, 3);
        this.randomRewards = randomRewards();
    }

    public boolean randomRewards() {
        return Math.random() < 0.5;
    }

}
