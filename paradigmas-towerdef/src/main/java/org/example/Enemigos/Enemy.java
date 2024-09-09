package org.example.Enemigos;

import org.example.Mapa.MapElements.MapElement;


import org.example.Mapa.MapElements.CerroDeLaGloria;
import org.example.Mapa.MapElements.MapElement;
import org.example.Mapa.MapElements.pathCell;

public abstract class Enemy {

    protected int health;
    protected boolean debuffState;
    protected int gold;
    protected int magic;
    protected int damage;
    protected int walkrate;
    protected int range;
    protected pathCell cell;

    public Enemy(int health, int gold, int magic, int damage, int walkrate, int range) {
        this.health = health;
        this.gold = gold;
        this.magic = magic;
        this.damage = damage;
        this.walkRate = walkrate;
        this.range = range;
        this.debuffState = false;
    }

    public int getHealth() {
        return health;
    }

    public int getGold() {
        return gold;
    }

    public int getMagic() {
        return magic;
    }

    public int getDamage() {
        return damage;
    }


    public float getWalkrate() {
        return walkrate;
    }

    public int getRange() {
        return range;
    }


    public pathCell getCell() {
        return cell;
    }

    public void setDebuff(boolean state) {
        this.debuffState = state;
        if (state) {
            if (this.walkrate > 1) {
                this.walkrate = (int) this.walkrate / 2;
            }
        } else {
            this.walkrate = (int) this.walkrate * 2;
        }
    }

    public boolean isAlive() {

        return this.health > 0;
    }

    public void walk(Enemy enemy) {
        int i;
        for (i = 0; i == this.walkrate; i++) {
            if (enemy.cell.next != null) {
                enemy.cell.enemies.remove(enemy);
                enemy.cell = enemy.cell.next;
                enemy.cell.addEnemy(enemy);
        }
        enemy.setDebuff(false);
    }

    public void attackCerro(Enemy enemy, CerroDeLaGloria cerro) {
        if (enemy.cell.next instanceof MapElement.CerroDeLaGloria) {
            cerro.health = cerro.health - enemy.damage;
        }
    }

    public int getRow() {
        return this.cell != null ? this.cell.row : -1;
    }

    public int getCol() {
        return this.cell != null ? this.cell.col : -1;
    }

}

