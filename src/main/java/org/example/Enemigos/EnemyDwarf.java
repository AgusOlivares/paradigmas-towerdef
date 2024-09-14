package org.example.Enemigos;


import org.example.Map.Map;

public class EnemyDwarf extends Enemy {
    public int baseHealth;
    public int baseDamage;
    public int baseSpeed;

    public EnemyDwarf(Map map) {
        super(map, 100, 66, 10, 1);
    }
}