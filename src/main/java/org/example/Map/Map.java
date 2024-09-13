package org.example.Map;

import org.example.Map.MapElements.CDLGloria;
import org.example.Map.MapElements.Path;

import java.util.Random;

public class Map {

    public static final int ROWS = 10;
    public static final int COLS = 20;

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

    public void printMap() {
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

    public void createPath() {
        Random random = new Random();
        int startRow = random.nextInt(ROWS);


        this.startCell = grid[startRow][0];
        int currentRow = startRow;
        int currentCol = 0;
        Cell currentCell = grid[startRow][currentCol];
        Path startPath = new Path(currentRow, currentCol);
        grid[currentRow][currentCol].setContent(startPath);

        while (currentCol < COLS - 1) {
            int direction = random.nextInt(3); // 0 = right, 1 = up, 2 = down

            if (direction == 0 && currentCol < COLS - 1) {
                // Move right
                currentCol++;
            } else if (direction == 1 && currentRow > 0) {
                // Move up
                currentRow--;
            } else if (direction == 2 && currentRow < ROWS - 1) {
                // Move down
                currentRow++;
            } else {
                // If the direction is invalid, continue to the next iteration
                continue;
            }

            Path newPath = new Path(currentRow, currentCol);
            grid[currentRow][currentCol].setContent(newPath);

            // Connect the previous cell with the new cell
            if (currentCell.getContent() instanceof Path) {
                ((Path) currentCell.getContent()).setNext(newPath);
            }
            currentCell = grid[currentRow][currentCol]; // Move to the next cell
        }

        // The last cell is the Cerro de la Gloria
        CDLGloria cerro = new CDLGloria(currentRow, COLS - 1, 1000);
        grid[currentRow][COLS - 1].setContent(cerro);
        this.endCell = grid[currentRow][COLS - 1]; // Update the end cell
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
