package org.example.Mapa;

import org.example.Mapa.MapElements.CerroDeLaGloria;
import org.example.Mapa.MapElements.pathCell;

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

    private Cell[][] map;
    private Cell endCell;
    private Cell startCell;

    public void Map() {
        this.map = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = new Cell(i, j, null);
            }
        }
        pathCell InicioCamino = new pathCell(true);
        // Este tiene que ser el cerro de la gloria
        //pathCell FinalCamino = new pathCell();


        //this.Llegada = map[rows - 1][cols - 1].setContent(FInalCamino);

        crearCamino();
    }

    public void imprimirMatriz() {
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
                // Imprimir 'C' si el contenido es una instancia de pathCell, de lo contrario '*'
                if (map[i][j].getContent() instanceof pathCell) {
                    if (((pathCell) map[i][j].getContent()).isStart()) {
                        System.out.print(" S |");
                    } else {
                        System.out.print("   |");
                    }
                } else if (map[i][j].getContent() instanceof CerroDeLaGloria) {
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
                pathCell newPathCell = new pathCell(true);
                currentCell = map[startRow][j].setContent(newPathCell);
            } else if (j == map[startRow].length - 1) {
                CerroDeLaGloria Cerro = new CerroDeLaGloria(2000);
                currentCell = map[startRow][j].setContent(Cerro);
            } else {
                pathCell newPathCell = new pathCell();
                currentCell = map[startRow][j].setContent(newPathCell);
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