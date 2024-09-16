package org.example.Map.MapElements;

public class CDLGloria extends MapElement {

    public int health;
    private boolean dead;

    public CDLGloria(int row, int col, int health) {
        super(row, col);
        this.health = health;
        this.dead = false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDeath(){
        return health <= 0;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}