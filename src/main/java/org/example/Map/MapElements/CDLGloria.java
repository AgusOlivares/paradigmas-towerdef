package org.example.Map.MapElements;

/**
 * Especialización de la clase map element para modelar el cerro de la gloria
 *
 * @author Agustín Olivares
 */

public class CDLGloria extends MapElement {

    public int health;
    private boolean dead;

    /**
     * Crea una instancia de CDLGloria a partir de una posición de fila, otra de columna y una cantidad de puntos de vida
     *
     * @param row    posición de fila
     * @param col    posición de columna
     * @param health cantidad de puntos de vida
     */
    public CDLGloria(int row, int col, int health) {
        super(row, col);
        /**
         * @see MapELement
         */
        this.health = health;
        this.dead = false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Método que verifica si el Cerro de la Gloria ha caído
     *
     * @return true si el Cerro ha caído, false en caso contrario
     */

    public boolean isDeath() {
        /**
         * Si la cantidad de puntos de vida es menor o igual a cero retorna true
         */
        return health <= 0;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
