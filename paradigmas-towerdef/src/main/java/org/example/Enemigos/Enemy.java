package org.example.Enemigos;

import org.example.Mapa.MapElements.pathCell;

public abstract class Enemy {

    pathCell cell;
    int health;
    int gold;
    int magic;
    int damage;
    int walkRate;
    int range;
    boolean debuffState;

    public Enemy(int health, int gold, int magic, int damage, int walkRate, int range) {
        this.health = health;
        this.gold = gold;
        this.magic = magic;
        this.damage = damage;
        this.walkRate = walkRate;
        this.range = range;
        this.debuffState = false;
    }

    public pathCell getCell() {
        return cell;
    }

    public void setDebuff(boolean state) {
        this.debuffState = state;
        if (state)
            if (this.walkRate > 1)
                this.walkRate = (int) this.walkRate / 2;
            else
                this.walkRate = (int) this.walkRate * 2;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void walk(Enemy enemy) {
        for (int i = 0; i == this.walkRate; i++) {
            if (enemy.cell.next != null) {
                enemy.cell.enemies.remove(enemy);
                enemy.cell = enemy.cell.next;
                enemy.cell.addEnemy(enemy);
            }
            enemy.setDebuff(false);
        }
    }
        /*
        public void attackCerro (Enemy enemy, CerroDeLaGloria cerro){
            if (enemy.cell.next instanceof MapElement.CerroDeLaGloria) {
                cerro.health = cerro.health - enemy.damage;
            }*/
}


