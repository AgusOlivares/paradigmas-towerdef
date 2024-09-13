package org.example.Enemigos;


public class EnemyDwarf extends Enemy {
    int baseHealth;
    int baseDamage;
    int baseSpeed;

    public EnemyDwarf() {
        super(70, 20, 10, 7, 1, 1);
        this.baseHealth = 70;
        this.baseDamage = 20;
        this.baseSpeed = 1;
    }

    public void dwarfBuff(EnemyDwarf dwarf) {
        float buff = (float) dwarf.baseHealth / 2;
        if (dwarf.health >= buff) {
            dwarf.damage = dwarf.baseDamage * 2;
            dwarf.walkRate = dwarf.baseSpeed * 2;
        }
    }
}
