package org.example.Mapa;

import org.example.Mapa.MapElements.MapElement;

/**
 * La clase Cell va a componer el mapa del nivel y es la encargada de contener ya sean casillas de torre, casillas de camino enemigo o pasto.
 * @author Olivares Agustin
 * @version 1.0
 * @since 19/08/2024
 */
public class Cell {

    public int col;
    public int row;
    public MapElement content;

    public Cell(int col, int row, MapElement content) {
        this.col = col; // Corregido para asignar los valores recibidos
        this.row = row; // Corregido para asignar los valores recibidos
        this.content = content;
    }

    // Método de prueba para impresión
    public char getValor() {
        return this.content != null ? 'C' : ' '; // 'C' si hay contenido, 'X' si no
    }

    public MapElement getContent() {
        return content;
    }

    public Cell setContent(MapElement content) {
        this.content = content;
        return this; // Devuelve la celda para un encadenamiento conveniente
    }
}