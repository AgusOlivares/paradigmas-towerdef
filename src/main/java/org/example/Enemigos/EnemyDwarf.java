package org.example.Enemigos;


import org.example.Map.Map;

/**
 * especialización de Enemy para el modelado de enemigo de tipo enano
 * @author Luciana Puentes
 */

public class EnemyDwarf extends Enemy {
    public int baseHealth;
    public int baseDamage;
    public int baseSpeed;

    public EnemyDwarf(int row, int col, Map map, int health, int gold, int magika, int damage, int speed, int range) {
        super(row, col, map, health, gold, magika, damage, speed, range);
    }

    /**
     * método que aumenta el daño que inflinge el enano
     * si se encuentra a más de la mitad de la vida
     */
    public void gigaDwarf() {
        /**
         * si la vida actual del enano es maypr a la mitad de su vida base,
         * establece su daño como el doble del daño base
         */
        if (health > (int)baseHealth / 2) {
            damage = baseDamage * 2;}
        else{
            damage = baseDamage;
        }


        }
    }
