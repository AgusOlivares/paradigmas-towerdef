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
    private MapElement content;

    public Cell(MapElement content) {
        this.content = content;
    }

    public MapElement getContent() {
        return this.content;
    }

    public void setContent(MapElement content) {
        this.content = content;
    }
}