package org.example.Enemigos;


import org.example.Map.Map;

public class EnemyDwarf extends Enemy {
    public int baseHealth;
    public int baseDamage;
    public int baseSpeed;

    public EnemyDwarf(int row, int col, Map map, int health, int gold, int magika, int damage, int speed, int range) {
        super(row, col, map, health, gold, magika, damage, speed, range);
    }

    public void gigaDwarf() {
        if (health < health / 2) {
            damage = damage * 2;
            speed = speed * 2;
        }
    }
}