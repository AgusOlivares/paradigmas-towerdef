package org.example.Map.MapElements;

import org.example.Enemigos.Enemy;
import org.example.Map.Map;

public class SpecialTower extends Tower {

    public static int damage = 60;
    public static int cost = 300;
    public static int range = 3;

    public SpecialTower(int row, int col, Map map) {
        super(row, col, map);
    }

    public void attack() {
        if (!this.enemyQueue.isEmpty()) {
            Enemy enemy = this.enemyQueue.get(0);
            enemy.receiveDamage(this.damage);
            enemy.setDebuff(true);
            if (!enemy.isAlive()) {
                this.enemyQueue.remove(enemy);
            }
        }
    }
}
