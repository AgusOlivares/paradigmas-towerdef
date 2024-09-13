package org.example.Map.MapElements;

import org.example.Map.Cell;

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