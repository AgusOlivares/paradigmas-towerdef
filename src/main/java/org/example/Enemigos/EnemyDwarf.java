package org.example.Enemigos;


import org.example.Map.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Especialización de la clase Enemy para el modelado el enemigo de tipo enano
 *
 * @author Luciana Puentes
 */
public class EnemyDwarf extends Enemy {
    public int baseHealth;
    public int baseDamage;

    /**
     * Crea un enemigo de tipo enano
     *
     * @param map el mapa donde se crea el enemigo de tipo enano
     */
    public EnemyDwarf(Map map) {
        /**
         * @see Enemy
         */
        super(map, 300, 50, 30, 2);
    }

    @Override
    public void Controller(@NotNull Enemy enemy) {
        /**
         * Si la próxima celda es el Cerro de la Gloria, lo ataca.
         * Aumenta el daño que inflinge el enano si se encuentra a menos de la mitad de la vida
         */

        if (!nextIsCerro()) {
            walk(enemy);
        } else {
            /**
             * Si la vida actual del enano es mayor a la mitad de su vida base,establece su daño como el doble del daño base
             */
            if (health < (int) baseHealth / 2) {
                this.damage = baseDamage * 2;
            }
            attackCerro();
        }

    }
}

