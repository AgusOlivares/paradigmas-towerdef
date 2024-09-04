package org.example.Mapa.MapElements;

import java.util.ArrayList;
import java.util.List;
import org.example.Enemigos.Enemy;

public class pathCell extends MapElement {

    public boolean isStart;
    public List<Enemy> enemies; // Lista de enemigos en esta celda
    public pathCell next; // Puntero al siguiente nodo del camino

    public pathCell() {
        this.enemies = new ArrayList<>();
        this.next = null;
        this.isStart = false;
    }

    public pathCell(boolean start) {
        this.enemies = new ArrayList<>();
        this.next = null;
        this.isStart = start;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public pathCell getNext() {
        return next;
    }

    public void setNext(pathCell next) {
        this.next = next;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
}


// estas proximas lineas de codigo son para agregarle a la casilla final que es el cerro de la gloria
// VIDA_MAX = 1000 por ej
// public int Vida;
// public void RecargarVida(){setVida=VIDA_MAX} para facilitarte el metodo de pasar de nivel