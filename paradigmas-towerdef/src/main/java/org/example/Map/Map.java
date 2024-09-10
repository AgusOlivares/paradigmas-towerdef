package org.example.Map;

import org.example.Map.MapElements.CDLGloria;
import org.example.Map.MapElements.Path;

public class Map {

    public static final int rows = 10;
    public static final int cols = 10;
    /**
     * Inicializo el mapa como una matriz conformada por instancias de la cLase Cell
     *
     * @author Olivares Agustin
     * @version 1.0
     * @since 19/08/2024
     */

    public Cell[][] map;
    public Cell startCell;
    public Cell endCell;

    public void Map() {
        this.map = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = new Cell(i, j, null);
            }
        }
        Path startPath = new Path(true);
        // Este tiene que ser el Cerro de la Gloria
        //path endCell = new path();
        //this.endCell = map[rows - 1][cols - 1].setContent(endCell);
        crearCamino();
    }

    public void printMap() {
        // Imprimo separacion entre tableros
        for (int i = 0; i <= 10; i++) {
            System.out.println();
        }

        // Imprimo encabezado de columnas
        System.out.print("  ");
        for (int i = 1; i <= map[0].length; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.println();

        // Imprimo filas con encabezados
        for (int i = 0; i < map.length; i++) {
            System.out.print((char) ('A' + i) + " "); // Convierte índice a letra
            for (int j = 0; j < map[i].length; j++) {
                // Imprimir 'C' si el contenido es una instancia de path, de lo contrario '*'
                if (map[i][j].getContent() instanceof Path) {
                    if (((Path) map[i][j].getContent()).isStart()) {
                        System.out.print(" S |");
                    } else {
                        System.out.print("   |");
                    }
                } else if (map[i][j].getContent() instanceof CDLGloria) {
                    System.out.print(" C |");
                } else {
                    System.out.print(" * |");
                }
            }
            System.out.println();
        }
    }


    // este metodo va en la clase Level que aun no se implementara crea un camino horizontal a partir de la endCell
    public void crearCamino() {
        int startRow = 4; // Suponiendo que la salida está en la primera fila
        int startCol = -1; // Suponiendo que la salida está en la primera columna

        Cell currentCell = this.endCell;

        for (int j = startCol + 1; j < map[startRow].length; j++) {

            if (j == (startCol + 1)) {
                Path newPath = new Path(true);
                currentCell = map[startRow][j].setContent(newPath);
            } else if (j == map[startRow].length - 1) {
                CDLGloria Cerro = new CDLGloria(2000);
                currentCell = map[startRow][j].setContent(Cerro);
            } else {
                Path newPath = new Path();
                currentCell = map[startRow][j].setContent(newPath);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return map[row][col];
    }

    public Cell[][] getMap() {
        return map;
    }

    public void setMap(Cell[][] map) {
        this.map = map;
    }

    public Cell getEndCell() {
        return endCell;
    }
}