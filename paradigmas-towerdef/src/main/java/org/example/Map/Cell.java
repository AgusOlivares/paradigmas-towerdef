package org.example.Map;

import org.example.Map.MapElements.MapElement;

/**
 * La clase Cell va a componer el mapa del nivel y es la encargada de contener ya sean casillas de torre, casillas de camino enemigo o pasto.
 *
 * @author Olivares Agustin
 * @version 1.0
 * @since 19/08/2024
 */

public class Cell {
    public int col;
    public int row;
    public MapElement content;

    public Cell(int col, int row, MapElement content) {
        this.col = col;
        this.row = row;
        this.setContent(content);
    }

    public char getValor() {
        return this.content != null ? 'C' : ' ';
    }

    public MapElement getContent() {
        return content;
    }

    public Cell setContent(MapElement content) {
        this.content = content;
        if (content != null) {
            content.setCell(this);
        }
        return this;
    }
}