package org.example.Map.MapElements;

import org.example.Enemigos.Enemy;
import org.example.Map.Map;

/**
 * Especialización de la clase tower para torres con habilidades especiales
 * @author Adriano Fabris
 */
public class SpecialTower extends Tower {

    public int damage = 1000;
    public int cost = 200;
    public int range = 3;

    /**
     * Crea una torre de tipo especial
     * @param row fila donde se posiciona la torre
     * @param col columna donde se posiciona la torre
     * @param map mapa donde se posiciona la torre
     */
    public SpecialTower(int row, int col, Map map) {
        /**
         * @see Tower
         */
        super(row, col, map);
    }
    @Override
    /**
     * Ataca al enemigo, reduciendo su vida y ralentizándolo
     */
    public void attack() {
        if (!this.enemyQueue.isEmpty()) {
            //si la torre tiene enemigos en su cola de ataque
            Enemy enemy = this.enemyQueue.get(0); //ataca al primer enemigo
            /**
             * @see Enemy
             */
            enemy.receiveDamage(this.damage); //reduce la vida del enemigo
            enemy.setDebuff(true); //ralentizar al enemigo
            if (!enemy.isAlive()) { //si el enemigo murió
                this.enemyQueue.remove(enemy); //eliminar al enemigo de la cola de ataque
            }
        }
    }
}
