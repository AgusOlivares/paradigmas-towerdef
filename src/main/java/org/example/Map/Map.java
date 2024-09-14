package org.example.Map;

import org.example.Enemigos.EnemyDwarf;
import org.example.Enemigos.EnemyElf;
import org.example.Enemigos.EnemyHobbit;
import org.example.Enemigos.EnemyHuman;
import org.example.Map.MapElements.CDLGloria;
import org.example.Map.MapElements.MapElement;
import org.example.Map.MapElements.Path;
import org.example.Map.MapElements.Tower;

import java.util.Random;

public class Map {

    public static final int ROWS = 10;
    public static final int COLS = 26;

    private Cell[][] grid;
    private Cell startCell;
    private Cell endCell;

    public Map(int cdlgHealth) {
        this.grid = new Cell[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = new Cell(null);
            }
        }
        createPath(cdlgHealth);
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
                    System.out.print(" T |"); // Imprimir 'T' si es una torre
                } else {
                    System.out.print("   |"); // Celda vacía
                }
            }
            System.out.println();
        }
    }

    public void createPath(int cdlgHealth) {
        Random random = new Random();
        int startRow = random.nextInt(ROWS);

        this.startCell = grid[startRow][0];
        int currentRow = startRow;
        int currentCol = 0;
        Cell currentCell = grid[startRow][currentCol];
        Path startPath = new Path(currentRow, currentCol, true);
        grid[currentRow][currentCol].setContent(startPath);

        while (currentCol < COLS - 1) {
            int direction = random.nextInt(3); // 0 = right, 1 = up, 2 = down

            if (direction == 0 && currentCol < COLS - 1) {
                currentCol++;
            } else if (direction == 1 && currentRow > 0 && grid[currentRow - 1][currentCol].getContent() == null) {
                currentRow--;
            } else if (direction == 2 && currentRow < ROWS - 1 && grid[currentRow + 1][currentCol].getContent() == null) {
                currentRow++;
            } else {
                continue;
            }

            Path newPath = new Path(currentRow, currentCol);
            grid[currentRow][currentCol].setContent(newPath);

            // Connect the previous path with the new path
            if (currentCell.getContent() instanceof Path) {
                ((Path) currentCell.getContent()).setNext(newPath);
            }
            currentCell = grid[currentRow][currentCol]; // Move to the next path
        }

        // The last path is the Cerro de la Gloria
        CDLGloria cerro = new CDLGloria(currentRow, COLS - 1, cdlgHealth);
        grid[currentRow][COLS - 1].setContent(cerro);
        this.endCell = grid[currentRow][COLS - 1]; // Update the end path
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