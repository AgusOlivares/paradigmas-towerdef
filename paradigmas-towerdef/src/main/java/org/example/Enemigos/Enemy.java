package org.example.Enemigos;


import org.example.Mapa.MapElements.pathCell;
import org.example.Mapa.Cell;
import org.example.Mapa.MapElements.CerroDeLaGloria;

public abstract class Enemy {

    protected int health;
    public boolean state;
    public int gold;
    public int magic;
    public int damage;
    public float walkrate;
    public int range;
    public pathCell cell;
    //constructores
    public Enemy(int health, int gold,int magic, int damage, float walkrate, int range) {
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
    public float getWalkrate(){
        return walkrate;
    }
    public int getRange(){
        return range;
    }

    public pathCell getCell() {
        return cell;
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

    public void walk(Enemy enemy){
        float spaces= enemy.walkrate;
        /*
        if (state){
            spaces=spaces/2;
            if (spaces<1){
                enemy.removeDebuff();
                return;
            }
        }
        */
        int i;
        for (i=0;i==spaces;i++){
            enemy.cell.enemies.remove(enemy);
            if (enemy.cell.next!=null ){
                enemy.cell=enemy.cell.next;
                enemy.cell.addEnemy(enemy);
            }
        }
        /*
        if (enemy.state){
            enemy.removeDebuff();
        }
        */

    }
    public void removeDebuff(){
        if (state){
            state=false;
        }
    }
    public void attackCerro(Enemy enemy,CerroDeLaGloria cerro){
        if (enemy.cell.next==null){
            cerro.Healt= cerro.Healt-enemy.damage;
        }
    }
}

