package org.example.Enemigos;

import org.example.Map.Map;

/**
 * Especialización de la clase Enemy para el modelado el enemigo de tipo hobbit
 * @author Luciana Puentes
 */
public class EnemyHobbit extends Enemy {

    boolean randomRewards;

    public EnemyHobbit(Map map) {
        super(map, 100, 66, 10, 3);
        this.randomRewards = randomRewards();
    }

    /**
     * Método que aleatoriamente da recompensas adicionales por matar al enemigo hobbit
     * @return true si se darám recompensas adicionales, false en caso contrario
     */
    public boolean randomRewards() {
        /**
         * si un número aleatorio es menor a 0.5 entonces devuelve true
         */
        return Math.random() < 0.5;
    }

}
