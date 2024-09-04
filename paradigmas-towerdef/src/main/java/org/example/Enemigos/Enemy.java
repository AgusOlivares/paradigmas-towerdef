package org.example.Enemigos;

package org.example.Enemigos;

public abstract class Enemy {
    protected int health;
    public boolean state;
    public int gold;
    public int magic;
    public int damage;
    public int walkrate;
    public int range;
    //constructores
    public Enemy(int health, int gold,int magic, int damage, int walkrate, int range) {
        this.health = health;
        this.gold = gold;
        this.magic = magic;
        this.damage = damage;
        this.walkrate = walkrate;
        this.range = range;
        state=false;
    }
    //metodos
    public int getHealth(){
        return health;
    }
    public int getGold(){
        return gold;
    }
    public int getMagic(){
        return magic;
    }
    public int getDamage(){
        return damage;
    }
    public int getWalkrate(){
        return walkrate;
    }
    public int getRange(){
        return range;
    }
    public void setDebuff(boolean debuff){
        //realentizar enemigo
        if (debuff && (!state)){
            state=true;
        }
    }
    public boolean isAlive(){
        //verifica si el enemigo está vivo
        if(health>0){return true;}
        else{return false;}
    }

    public void walk(){
        int spaces=walkrate;
        int i;
        for (i=0;i==spaces;i++){
            //seguir cuando este el mapa
        }

    }
}
public class EnemyElf extends Enemy{
    public EnemyElf() {
        super(50,10,5,3,1,3);
    }
}
public class EnemyHuman extends Enemy{
    public EnemyHuman() {
        super(30,5,0,3,1,1);
    }
}
public class EnemyDwarf  extends Enemy{
    public EnemyDwarf() {
        super(70,20,10,7,1,1);
    }
    public class EnemyHobbit extends Enemy{
        public EnemyHobbit() {
            super(60,15,20,3,2,2);
        }
    }

