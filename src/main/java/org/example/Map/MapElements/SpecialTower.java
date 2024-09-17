package org.example.Map.MapElements;

import org.example.Enemigos.Enemy;
import org.example.Map.Map;

/**
 * Especializacion de la clase Tower, posee un efecto de hielo que realentiza al enemigo
 * @author Adriano Fabris
 */

public class SpecialTower extends Tower {

    public static int damage = 60;
    public static int cost = 300;
    public static int range = 3;

    public SpecialTower(int row, int col, Map map) {
        super(row, col, map);
    }

    /**
     * Metodo redefinido de Tower, al golpear al enemigo le aplica un debuff que reduce su avance a la mitad
     * @see Tower
     */

    @Override
    public void attack() {
        if (!this.getEnemyQueue().isEmpty()) {
            Enemy enemy = this.getEnemyQueue().get(0);
            enemy.receiveDamage(this.damage);
            enemy.setDebuff(true);
            if (!enemy.isAlive()) {
                this.getEnemyQueue().remove(enemy);
            }
        }
    }
}
