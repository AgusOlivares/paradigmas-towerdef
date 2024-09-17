package org.example.Enemigos;

import org.example.Map.Map;

/**
 * Especialización de la clase Enemy para el modelado el enemigo de tipo hobbit
 *
 * @author Luciana Puentes
 */
public class EnemyHobbit extends Enemy {

    /**
     * Crea un enemigo de tipo hobbit
     *
     * @param map el mapa donde se crea el emenigo de tipo hobbit
     */
    public EnemyHobbit(Map map) {
        super(map, 100, 10 + (int) (Math.random() * 10), 10, 3);

    }


}
