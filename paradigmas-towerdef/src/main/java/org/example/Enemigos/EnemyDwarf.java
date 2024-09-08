package org.example.Enemigos;

public class EnemyDwarf  extends Enemy {
    private int baseHealth;
    private int baseDamage;
    private int baseSpeed;

    public EnemyDwarf() {

        super(70, 20, 10, 7, 1, 1);
        baseHealth = 70;
        baseDamage = 20;
        baseSpeed = 1;

    }

    public void dwarfBuff(EnemyDwarf dwarf) {
        float buff= (float) dwarf.baseHealth /2;

        if (dwarf.health>=buff){
            dwarf.damage=dwarf.baseDamage*2;
            dwarf.walkrate=dwarf.baseSpeed*2;
        }

    }


}
