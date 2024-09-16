package org.example.Map;

import org.example.Enemigos.EnemyDwarf;
import org.example.Enemigos.EnemyElf;
import org.example.Enemigos.EnemyHobbit;
import org.example.Enemigos.EnemyHuman;
import org.example.Map.MapElements.*;

import java.util.Random;
/**
 * clase que modela el mapa del juego, compuesta por la clase cell
 * @author Agustín Olivares
 */

public class Map {

    public static final int ROWS = 10;
    public static final int COLS = 26;

    private Cell[][] grid;
    private Cell startCell;
    private Cell endCell;
    /**
     * crea el mapa donde se desarrollará el juego
     * @param cantidad de vida del cerro de la gloria
     */
    public Map(int cdlgHealth) {
        this.grid = new Cell[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = new Cell(null);
            }
        }
        createPath(cdlgHealth);
    }
     /**
     * Método que imprime el mapa por pantalla
     */

    public void printMap() {
         /**
         * Imprime las filas y columnas de celdas con sus encabezados correspondientes para guiar
         * al usuario en la colocación de las torres, imprime S para indicar la celda donde
         * inicia el camino enemigo, C para indicar la celda donde se encuentra el cerro de
         * la gloria, * para las celdas que forman parte del camino enemigo
         * y la inicial del tipo enemigo si la celda está siendo ocupada por uno
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
                MapElement element = grid[i][j].getContent();
                if (element instanceof Path) {
                    Path currentPath = (Path) element;

                    // Si es el inicio del camino
                    if (currentPath.isStart()) {
                        System.out.print(" S |");
                    } else {
                        // Verificar si hay enemigos en esta celda
                        if (!currentPath.getEnemies().isEmpty()) {
                            switch (currentPath.getEnemies().size()) {
                                case 1:
                                    if (currentPath.getEnemies().get(0) instanceof EnemyElf) {
                                        System.out.print(" E |");
                                    } else if (currentPath.getEnemies().get(0) instanceof EnemyHuman) {
                                        System.out.print(" H |");
                                    } else if (currentPath.getEnemies().get(0) instanceof EnemyHobbit) {
                                        System.out.print(" B |");
                                    } else if (currentPath.getEnemies().get(0) instanceof EnemyDwarf) {
                                        System.out.print(" D |");
                                    }
                                    break;
                                case 2:
                                    System.out.print(" 2 |"); // Dos enemigos
                                    break;
                                default:
                                    System.out.print(" ! |");
                                    break;
                            }
                        } else {
                            System.out.print(" * |"); // Celda vacía
                        }
                    }
                } else if (element instanceof CDLGloria) {
                    System.out.print(" C |"); // Imprimir 'C' si es CDLGloria
                } else if (element instanceof Tower) {
                    if (element instanceof SpecialTower){
                        System.out.print(" F |");
                    } else {
                        System.out.print(" T |"); // Imprimir 'T' si es una torre
                    }
                } else {
                    System.out.print("   |"); // Celda vacía
                }
            }
            System.out.println();
        }
    }
    /**
     * método que genera aleatoriamente el camino por donde transitan los enemigos
     */
    public void createPath(int cdlgHealth) {
        /**
         * Genera un camino aleatorio desde una columna inicial hasta la última columna del grid,
         * moviéndose hacia la derecha, arriba o abajo.
         * Al final, coloca el cerro de la gloria como posición final
         */
        Random random = new Random();
        int startRow = random.nextInt(ROWS);

        this.startCell = grid[startRow][0];
        int currentRow = startRow;
        int currentCol = 0;
        Cell currentCell = grid[startRow][currentCol];
        Path startPath = new Path(currentRow, currentCol, true);
        grid[currentRow][currentCol].setContent(startPath);

        int previousDirection = 0; // Para rastrear la última dirección
        int repeatMove = 0; // Contador para movimientos repetidos después de un giro

        while (currentCol < COLS - 1) {
            int direction;

            // Si estamos repitiendo el movimiento, continuamos en la misma dirección
            if (repeatMove > 0) {
                direction = previousDirection;
                repeatMove--;
            } else {
                direction = random.nextInt(3); // 0 = right, 1 = up, 2 = down

                // Si cambiamos de dirección, debemos forzar dos movimientos consecutivos en la nueva dirección
                if (direction != previousDirection) {
                    repeatMove = 1; // Debemos repetir el movimiento una vez más después de girar
                }
            }

            // Aplicar movimiento en la dirección seleccionada
            if (direction == 0 && currentCol < COLS - 1) { // Derecha
                currentCol++;
            } else if (direction == 1 && currentRow > 0 && grid[currentRow - 1][currentCol].getContent() == null) { // Arriba
                currentRow--;
            } else if (direction == 2 && currentRow < ROWS - 1 && grid[currentRow + 1][currentCol].getContent() == null) { // Abajo
                currentRow++;
            } else {
                continue; // Si el movimiento no es válido, probamos otra vez
            }

            // Crear nuevo Path y actualizar el grid
            Path newPath = new Path(currentRow, currentCol);
            grid[currentRow][currentCol].setContent(newPath);

            // Conectar el camino anterior con el nuevo
            if (currentCell.getContent() instanceof Path) {
                ((Path) currentCell.getContent()).setNext(newPath);
            }
            currentCell = grid[currentRow][currentCol]; // Moverse a la siguiente celda

            // Actualizar la última dirección
            previousDirection = direction;
        }

        // El último camino es el Cerro de la Gloria
        CDLGloria cerro = new CDLGloria(currentRow, COLS - 1, cdlgHealth);
        grid[currentRow][COLS - 1].setContent(cerro);
        this.endCell = grid[currentRow][COLS - 1]; // Actualizar el final del camino
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

    public CDLGloria getCdlGloria() {
        return (CDLGloria) endCell.getContent();
    }
}
