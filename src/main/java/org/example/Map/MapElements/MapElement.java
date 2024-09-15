package org.example.Map.MapElements;

import org.example.Map.Cell;

/**
 * clase que modela los elementos que ocupan el mapa del juego
 * @author Agustín Olivares
 */

public abstract class MapElement {
    public int row;
    public int col;

    public MapElement(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

}