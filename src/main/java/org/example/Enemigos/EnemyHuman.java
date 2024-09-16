package org.example.Enemigos;

import org.example.Map.Map;
/**
 * Especialización de la clase Enemy para el enemigo de tipo humano
 * @author Luciana Puentes
 */
public class EnemyHuman extends Enemy {
    /**
     * Crea un enemigo de tipo humano
     * @param map mapa donde se crea el enemigo de tipo humano
     */
    public EnemyHuman(Map map) {
        super(map, 100, 66, 10, 2);
    }
}
