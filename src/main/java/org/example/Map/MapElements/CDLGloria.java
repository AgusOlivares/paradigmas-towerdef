package org.example.Map.MapElements;

/**
 * Especialización de la clase map element para modelar el cerro de la gloria
 * @author Agustín Olivares
 */
public class CDLGloria extends MapElement {

    public int health;

    public CDLGloria(int row, int col, int health) {
        super(row, col);
        this.health = health;
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
}