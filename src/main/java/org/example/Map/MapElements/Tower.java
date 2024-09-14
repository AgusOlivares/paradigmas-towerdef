package org.example.Map.MapElements;

import org.example.Enemigos.Enemy;
import org.example.Map.Cell;
import org.example.Map.Map;

import java.util.ArrayList;

public class Tower extends MapElement {
    public Map map;
    public ArrayList<Path> atkCells;
    public ArrayList<Enemy> enemyQueue;

    public int damage = 500;
    public int cost = 100;
    public int range = 2;

    public Tower(int row, int col, Map map) {
        super(row, col);
        this.map = map;
        this.atkCells = this.findAtkCells();
        this.enemyQueue = new ArrayList<>();
    }

    // Ataca al enemigo en la cola ordenada
    public void attack() {
        if (!this.enemyQueue.isEmpty()) {
            Enemy enemy = this.enemyQueue.get(0);
            enemy.receiveDamage(this.damage);
            if (!enemy.isAlive()) {
                this.enemyQueue.remove(enemy);
            }
        }
    }

    // Actualiza la cola ordenada de enemigos
    public void updateEnemyOrder() {
        enemyQueue = new ArrayList<>();
        for (Path path : this.atkCells) {
            for (Enemy enemy : path.getEnemies()) {
                if (enemy != null && !this.enemyQueue.contains(enemy)) {
                    this.enemyQueue.add(enemy);
                }
            }
        }
    }

    // Encuentra las celdas (que pertenenecen al camino enemigo) a las que la torre puede atacar
    public ArrayList<Path> findAtkCells() {
        ArrayList<Path> atkCells = new ArrayList<>();

        int minRow = this.getRow() - this.range;
        int maxRow = this.getRow() + this.range;
        int minCol = this.getCol() - this.range;
        int maxCol = this.getCol() + this.range;
        Cell[][] grid = this.map.getGrid();


        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                if (i >= 0 && i < Map.ROWS && j >= 0 && j < Map.COLS) {
                    Cell cell = grid[i][j];
                    if (cell.getContent() instanceof Path) {
                        atkCells.add((Path) cell.getContent());
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

    public int getDamage() {
        return damage;
    }

    public int getCost() {
        return cost;
    }

    public int getRange() {
        return range;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Path> getAtkCells() {
        return atkCells;
    }

    public ArrayList<Enemy> getEnemyQueue() {
        return enemyQueue;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setAtkCells(ArrayList<Path> atkCells) {
        this.atkCells = atkCells;
    }

    public void setEnemyQueue(ArrayList<Enemy> enemyQueue) {
        this.enemyQueue = enemyQueue;
    }

}