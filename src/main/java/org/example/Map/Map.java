package org.example.Map;

import org.example.Enemigos.Enemy;
import org.example.Enemigos.EnemyDwarf;
import org.example.Map.MapElements.CDLGloria;
import org.example.Map.MapElements.Path;

public class Map {

    public static final int ROWS = 10;
    public static final int COLS = 20;

    /**
     * Inicializo el mapa como una matriz conformada por instancias de la clase Cell
     */
    private Cell[][] map;
    private Cell startCell;
    private Cell endCell;

    // Constructor de la clase Map
    public Map() {
        this.map = new Cell[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                map[i][j] = new Cell(i, j, null); // Inicializa las celdas vacías
            }
        }

        this.startCell = map[4][0]; // Celda de inicio
        this.endCell = map[4][COLS - 1]; // Celda de fin
        crearCamino(); // Crea el camino en el mapa
    }

    // Método para imprimir el mapa
    public void printMap() {
        // Imprimo separación entre tableros
        for (int i = 0; i <= 10; i++) {
            System.out.println();
        }

        // Imprimo encabezado de columnas
        System.out.print("  ");
        for (int i = 0; i < map[0].length; i++) {
            System.out.print(" " + (char) ('A' + i) + "  ");
        }
        System.out.println();

        // Imprimo filas con encabezados
        for (int i = 0; i < map.length; i++) {
            System.out.print(i + " "); // Muestra el índice de la fila
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].getContent() instanceof Path) {
                    Path currentPath = (Path) map[i][j].getContent();

                    // Si es el inicio del camino
                    if (currentPath.isStart()) {
                        System.out.print(" S |");
                    } else {
                        // Verificar si hay enemigos en esta celda
                        if (!currentPath.getEnemies().isEmpty()) {
                            System.out.print(" D |"); // 'D' para indicar enemigos presentes
                        } else {
                            System.out.print("   |"); // Celda vacía
                        }
                    }
                } else if (map[i][j].getContent() instanceof CDLGloria) {
                    System.out.print(" C |"); // Imprimir 'C' si es CDLGloria
                } else {
                    System.out.print(" * |"); // Imprimir '*' si no es un camino ni CDLGloria
                }
            }
            System.out.println();
        }
    }

    // Método que crea el camino en el mapa, conectando las celdas de startCell a endCell
    public void crearCamino() {
        int startRow = 4; // Fila fija para el camino
        Cell currentCell = this.startCell; // Comenzamos desde la celda de inicio

        for (int j = 0; j < COLS; j++) {
            if (j == COLS - 1) {
                // La última celda es el Cerro de la Gloria
                CDLGloria cerro = new CDLGloria(2000);
                map[startRow][j].setContent(cerro);
                this.endCell = map[startRow][j]; // Actualizamos la celda final
            } else {

                if (j == 0){
                    Path newPath = new Path(true);
                    map[startRow][j].setContent(newPath);
                }
                // Crear un nuevo Path en las celdas intermedias
                Path newPath = new Path();
                map[startRow][j].setContent(newPath);

                // Conectar la celda anterior con la nueva celda
                if (currentCell.getContent() instanceof Path) {
                    ((Path) currentCell.getContent()).setNext(newPath);
                }
                currentCell = map[startRow][j]; // Avanzar a la siguiente celda
            }
        }
    }

    // Método para obtener una celda del mapa
    public Cell getCell(int row, int col) {
        return map[row][col];
    }

    public Cell[][] getMap() {
        return map;
    }

    public void setMap(Cell[][] map) {
        this.map = map;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }
}
