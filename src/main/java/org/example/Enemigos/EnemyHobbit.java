package org.example.Enemigos;

import org.example.Map.Map;

public class EnemyHobbit extends Enemy {

    boolean randomRewards;

    public EnemyHobbit(Map map) {
        super(map, 60, 20, 10, 4);
        this.randomRewards = randomRewards();
    }

    public boolean randomRewards() {
        return Math.random() < 0.5;
    }

}
