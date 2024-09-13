package org.example.Enemigos;

import org.example.Map.Map;
import org.example.Map.MapElements.CDLGloria;
import org.example.Map.MapElements.MapElement;
import org.example.Map.MapElements.Path;

public abstract class Enemy extends MapElement {

    public Path cell;
    public Map map;
    public int health;
    public int gold;
    public int magika;
    public int damage;
    public int speed;
    public int range;
    public boolean debuffed;

    public Enemy(int row, int col, Map map, int health, int gold, int magika, int damage, int speed, int range) {
        super(row, col);
        this.map = map;
        this.cell = (Path) map.getCell(row, col).getContent();
        this.health = health;
        this.gold = gold;
        this.magika = magika;
        this.damage = damage;
        this.speed = speed;
        this.range = range;
        this.debuffed = false;
    }

    public Path getCell() {
        return cell;
    }

    public void setCell(Path cell) {
        this.cell = cell;
    }

    public void setDebuff(boolean state) {
        this.debuffed = state;
        if (state) {
            if (this.speed > 1) {
                this.speed = this.speed / 2;
            } else {
                this.speed = this.speed * 2;
            }
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void walk(Enemy enemy) {
        for (int i = 0; i < this.speed; i++) {
            enemy.cell.enemies.remove(enemy); // remuevo al enemigo de la lista de enemigos de la celda
            Path nuevaCelda = enemy.cell.next; // Puntero a la proxima celda del camino
            enemy.cell = nuevaCelda;
            enemy.setCell(nuevaCelda);
            enemy.cell.addEnemy(enemy);
            if (nextIsCerro()) {
                break;
            }
        }
        enemy.setDebuff(false);
    }


    public void controller(Enemy enemy) {
        if (!nextIsCerro())
            walk(enemy);
        else
            attackCerro();
    }

    public boolean nextIsCerro() {
        Path nextPath = this.cell.next;
        int nextPathRow = nextPath.getRow();
        int nextPathCol = nextPath.getCol();
        return map.getGrid()[nextPathRow][nextPathCol] == map.getEndCell();
    }

    public void receiveDamage(int damage) {
        this.health = this.health - damage;
    }

    public void attackCerro() {
        CDLGloria cerro = (CDLGloria) map.getEndCell().getContent();
        cerro.setHealth(cerro.getHealth() - this.damage);
    }
}