package org.example.Enemigos;

import org.example.Map.Map;

/**
 * Especialización de la clase Enemy para el enemigo de tipo humano
 * @author Luciana Puentes
 */

public class EnemyHuman extends Enemy {

    public EnemyHuman(Map map) {
        super(map, 100, 66, 10, 2);
    }
}
