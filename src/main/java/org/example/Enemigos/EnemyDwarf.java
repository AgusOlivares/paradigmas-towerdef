package org.example.Enemigos;


import org.example.Map.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Especialización de la clase Enemy para el modelado el enemigo de tipo enano
 *
 * @author Luciana Puentes
 */
public class EnemyDwarf extends Enemy {
    private int baseHealth;
    private int baseDamage;

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

    /**
     * Sobreescritura del metodo Controller, si este enemigo tiene menos de la mitad de la vida, se duplica su daño
     * @param enemy enemigo que realizará las acciones
     */
    @Override
    public void Controller(@NotNull Enemy enemy) {

        if (!nextIsCerro()) {
            walk(enemy);
        } else {
            if (getHealth() < (int) baseHealth / 2) {
                this.setDamage((int) baseDamage * 2);
            }
            attackCerro();
        }

    }
}

