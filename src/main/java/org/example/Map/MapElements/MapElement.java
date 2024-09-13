package org.example.Map.MapElements;

import org.example.Map.Cell;

public abstract class MapElement {
    protected Cell cell;

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}