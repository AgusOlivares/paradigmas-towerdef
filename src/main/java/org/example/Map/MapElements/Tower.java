package org.example.Torres;

import org.example.Enemigos.Enemy;
import org.example.Map.Cell;
import org.example.Map.Map;
import org.example.Map.MapElements.MapElement;
import org.example.Map.MapElements.Path;

import java.util.ArrayList;

public class Tower extends MapElement {
    private static final double maxDamage = 100;
    private static final double maxCost = 600;
    private static final double maxRange = 7;
    public Cell[][] map;

    public double damage;
    public double cost;
    public int range;
    public ArrayList<Path> atkCells;
    public ArrayList<Enemy> enemyQueue;

    public Tower(double damageScale, double costScale, double rangeScale, Map map) {
        this.damage = damageScale * maxDamage;
        this.cost = costScale * maxCost;
        this.range = (int) (rangeScale * maxRange);
        this.atkCells = findAtkCells();
        this.enemyQueue = null;
        this.map = map.getMap();
    }

    // Actualiza la cola de orden en el que la torre dispara
    public void updateEnemyOrder(ArrayList<Enemy> enemies) {
        this.enemyQueue = new ArrayList<>();
        for (Path path : this.atkCells) {
            for (Enemy enemy : path.getEnemies()) {
                if (enemy != null && !this.enemyQueue.contains(enemy)) {
                    this.enemyQueue.add(enemy);
                }
            }
        }
    }

    // Encuentra las celdas (del camino enemigo) a las que la torre puede atacar
    public ArrayList<Path> findAtkCells() {
        ArrayList<Path> atkCells = new ArrayList<>();

        int minRow = this.getRow() - this.range;
        int maxRow = this.getRow() + this.range;
        int minCol = this.getCol() - this.range;
        int maxCol = this.getCol() + this.range;


        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                if (i >= 0 && i < Map.ROWS && j >= 0 && j < Map.COLS) {
                    if (this.map[i][j].getContent() instanceof Path) {
                        atkCells.add((Path) this.map[i][j].getContent());
                    }
                }
            }
        }
        return atkCells;
    }

    // Checkea rápidamente si un enemigo está en el rango de la torre
    public boolean insideRange(Enemy enemy) {
        int minRow = this.getRow() - this.range;
        int maxRow = this.getRow() + this.range;
        int minCol = this.getCol() - this.range;
        int maxCol = this.getCol() + this.range;
        int enemyRow = enemy.getRow();
        int enemyCol = enemy.getCol();
        return (enemyRow >= minRow && enemyRow <= maxRow) && (enemyCol >= minCol && enemyCol <= maxCol);
    }

    public int getRow() {
        return this.cell != null ? this.cell.row : -1;
    }

    public int getCol() {
        return this.cell != null ? this.cell.col : -1;
    }
}