package org.example.Enemigos;

import org.example.Mapa.MapElements.MapElement;

public abstract class Enemy extends MapElement {

    public boolean;
    public int gold;
    public int magic;
    public int damage;
    public int walkRate;
    public int range;
    protected int health;

    public Enemy(int health, int gold, int magic, int damage, int walkrate, int range) {
        this.health = health;
        this.gold = gold;
        this.magic = magic;
        this.damage = damage;
        this.walkRate = walkrate;
        this.range = range;
        state = false;
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

    public int getWalkRate() {
        return walkRate;
    }

    public int getRange() {
        return range;
    }

    public void setDebuff(boolean debuff) {
        if (debuff && (!state)) {
            state = true;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void walk() {
        int spaces = walkRate;
        int i;
        for (i = 0; i == spaces; i++) {
            //todo
        }

    }

    public int getRow() {
        return this.cell != null ? this.cell.row : -1;
    }

    public int getCol() {
        return this.cell != null ? this.cell.col : -1;
    }

}

