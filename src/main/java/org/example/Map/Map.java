package org.example.Map;

import org.example.Map.MapElements.CDLGloria;
import org.example.Map.MapElements.Path;

import java.util.Random;

/**
 * clase que modela el mapa del juego, compuesta por la clase cell
 * @author Agustín Olivares
 */

public class Map {

    public static final int ROWS = 10; //máximo de filas
    public static final int COLS = 20; //máximo de columnas

    private Cell[][] grid;
    private Cell startCell;
    private Cell endCell;

    public Map() {
        this.grid = new Cell[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = new Cell(null);
            }
        }

        createPath();
    }

    /**
     * Método que imprime el mapa por pantalla
     */
    public void printMap() {
        /**
         * imprime las filas y columnas de celdas con sus encabezados correspondientes para guiar
         * al usuario en la colocación de las torres, imprime S para indicar la celda donde
         * inicia el camino enemigo, C para indicar la celda donde se encuentra el cerro de
         * la gloria y la inicial del tipo enemigo si la celda está siendo ocupada por uno
         */
        System.out.println();
        // Imprimo encabezado de columnas
        System.out.print("  ");
        for (int i = 0; i < grid[0].length; i++) {
            System.out.print(" " + (char) ('A' + i) + "  ");
        }
        System.out.println();

        // Imprimo filas con encabezados
        for (int i = 0; i < grid.length; i++) {
            System.out.print(i + " "); // Muestra el índice de la fila
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getContent() instanceof Path) {
                    Path currentPath = (Path) grid[i][j].getContent();

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
                } else if (grid[i][j].getContent() instanceof CDLGloria) {
                    System.out.print(" C |"); // Imprimir 'C' si es CDLGloria
                } else {
                    System.out.print(" * |"); // Imprimir '*' si no es un camino ni CDLGloria
                }
            }
            System.out.println();
        }
    }

    /*public void createPath() {
        Random random = new Random();
        int startRow = random.nextInt(ROWS); // Random starting row on the left side
        Cell currentCell = this.startCell = grid[startRow][0]; // Start from the random start cell

        */

    /**
     * método que genera aleatoriamente el camino por donde transitan los enemigos
     */
    public void createPath() {
        /**
         * genera un camino aleatorio desde una columna inicial hasta la última columna del grid,
         * moviéndose hacia la derecha, arriba o abajo.
         * Al final, coloca el cerro de la gloria como posición final
         */
        Random random = new Random(); //crea una instancia random para generar un número aleatorio
        int startRow = random.nextInt(ROWS); //usa la instancia para obtener una fila aleatoria
        this.startCell = grid[startRow][0]; //define el inicio del camino
        int currentRow = startRow; //guarda la posicion fila inicial
        int currentCol = 0; //guarda la posicion colunma inicial
        Cell currentCell = grid[startRow][currentCol]; //guarda la celda inicial
        Path startPath = new Path(currentRow, currentCol); //crea el objeto path de la celda inicial
        grid[currentRow][currentCol].setContent(startPath); //establece el contenido de la celda inicial

        while (currentCol < COLS - 1) {
            //empieza a recorrer el mapa en dirección aleatoria
            int direction = random.nextInt(3); // 0 = derecha, 1 =arriba, 2 = abajo

            if (direction == 0 && currentCol < COLS - 1) {
                // se mueve a la derecha
                currentCol++;
            } else if (direction == 1 && currentRow > 0) {
                // se mueve hacia arriba
                currentRow--;
            } else if (direction == 2 && currentRow < ROWS - 1) {
                // se mueve hacia abajo
                currentRow++;
            } else {
                //si la dirección no es válida, continua a la prócima iteración
                continue;
            }

            Path newPath = new Path(currentRow, currentCol); //crea un path con la posición actual
            grid[currentRow][currentCol].setContent(newPath); //establece el path como contenido de la celda actual

            //Conecta la celda anterior con la nueva celda
            if (currentCell.getContent() instanceof Path) {
                ((Path) currentCell.getContent()).setNext(newPath);
                //establece la nueva celda como siguiente en el camino
            }
            currentCell = grid[currentRow][currentCol]; // se mueve a la siguiente celda
        }

        // la última celda es el cerro de la gloria
        CDLGloria cerro = new CDLGloria(currentRow, COLS - 1, 1000);
        grid[currentRow][COLS - 1].setContent(cerro); //define el contenido de la celda final
        this.endCell = grid[currentRow][COLS - 1]; // define la celda final como la actual
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }
}
