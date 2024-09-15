package org.example.Map.MapElements;

import org.example.Enemigos.Enemy;

import java.util.ArrayList;
import java.util.List;

/**
 * Especialización de la clase map element para modelar el camino donde transitan los enemigos
 * @author Agustín Olivares
 */
public class Path extends MapElement {

    public boolean isStart; //true si el inicio del camino
    public List<Enemy> enemies; // Lista de enemigos en esta celda
    public Path next; // Puntero al siguiente nodo del camino

    public Path(int row, int col) {
        super(row, col);
        this.enemies = new ArrayList<>();
        this.next = null;
        this.isStart = false;
    }

    public Path(int row, int col, boolean start) {
        super(row, col);
        this.enemies = new ArrayList<>();
        this.next = null;
        this.isStart = start;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public Path getNext() {
        return next;
    }

    public void setNext(Path next) {
        this.next = next;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
}

