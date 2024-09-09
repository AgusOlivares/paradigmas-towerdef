package org.example.Mapa.MapElements;

import org.example.Mapa.Cell;

public abstract class MapElement {
    protected Cell cell;

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}