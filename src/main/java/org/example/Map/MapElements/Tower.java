package org.example.Map.MapElements;

import org.example.Enemigos.Enemy;
import org.example.Map.Cell;
import org.example.Map.Map;

import java.util.ArrayList;
/**
 * Especialización de la clase map element para modelar las torres
 * @author Adriano Fabris
 */
public class Tower extends MapElement {
    public Map map;
    public ArrayList<Path> atkCells;
    public ArrayList<Enemy> enemyQueue;

    public int damage = 50;
    public int cost = 100;
    public int range = 2;

    public Tower(int row, int col, Map map) {
        super(row, col);
        /**
         * @see MapElement
         */
        this.map = map;
        this.atkCells = this.findAtkCells();
        this.enemyQueue = new ArrayList<>();
    }
    /**
     * Método para infligir daño a los enemigos
     */
    // Ataca al enemigo en la cola ordenada
    public void attack() {
        /**
         * Recorre la cola de enemigos de la torre y ataca al enemigo en la cabeza de la cola.
         * Si el enemigo muere, lo quita de la cola de enemigos
         */
        if (!this.enemyQueue.isEmpty()) {
            Enemy enemy = this.enemyQueue.get(0);
            /**
             * @see Enemy
             */
            enemy.receiveDamage(this.damage);
            if (!enemy.isAlive()) {
                this.enemyQueue.remove(enemy);
            }
        }
    }

    // Actualiza la cola ordenada de enemigos
    /**
     * Método para actualizar la cola de ataque de la torre
     */
    public void updateEnemyOrder() {
        /**
         * verifica una por una las posiciones atacables de la torre em caso de que
         * haya llegado un nuevo enemmigo, si es así lo añade a la cola de ataque
         */
        enemyQueue = new ArrayList<>();
        for (Path path : this.atkCells) {
            for (Enemy enemy : path.getEnemies()) {
                /**
                 * @see Path
                 */
                if (enemy != null && !this.enemyQueue.contains(enemy)) {
                    this.enemyQueue.add(enemy);
                }
            }
        }
    }

    // Encuentra las celdas (que pertenenecen al camino enemigo) a las que la torre puede atacar
    /**
     * Método que encuentra las celdas del camino enemigo a las que la torre puede atacar
     * @return atkCells una lista que contiene las celdas que la torre puede atacar
     */
    public ArrayList<Path> findAtkCells() {
        /**
         * recorre el rango de celdas que la torre puede atacar (calculado mediante range)
         * encontrando aquellas que son instancias de Path
         * si lo son, las celdas son añadidas a la lista atkCells
         */
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
    /**
     * Método que verifica si un enemigo se encuentra dentro del rango de la torre
     * @param enemy el emenigo a verificar
     * @return true si el enemigo está dentro del rango de la torre, false en caso contrario
     */
    public boolean insideRange(Enemy enemy) {
        /**
         * compara los valores row,col del enemigo al rango de la torre
         * si los valores del enemigo pertenecen al intervalo que define dicho rango,
         * el enemigo está dentro del rango de la torre
         */
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
