package org.example.Enemigos;


import org.example.Map.Map;

/**
 * Especialización de la clase Enemy
 * @author Luciana Puentes
 * @version 1.3
 *
 */

public class EnemyDwarf extends Enemy {
    public int baseHealth;
    public int baseDamage;

    public EnemyDwarf(Map map) {
        super(map, 100, 66, 10, 1);
        this.baseHealth = 100;
        this.baseDamage = 10;
    }

    /**
     * Método que duplica el daño base del enemigo tipo enano
     * si éste tiene más de la mitad de la vida
     * @
     */
    public void buffDwarf(){
       int buff=(int) (this.baseHealth/2);
        if (this.health>=buff){
            this.damage=this.baseDamage*2;

        }
    }
    public void debuffDwarf(){
        int debuff=(int) (this.baseHealth/2);
        if (this.health<=debuff){
            this.damage=this.baseDamage;
        }
    }

}
