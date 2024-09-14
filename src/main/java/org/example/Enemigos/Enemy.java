package org.example.Enemigos;

import org.example.Map.Map;
import org.example.Map.MapElements.CDLGloria;
import org.example.Map.MapElements.Path;

public abstract class Enemy {

    public static final int enemyTypes = 4;

    public Path path;
    public Map map;
    public int health;
    public int gold;
    public int damage;
    public int speed;
    public boolean debuffed = false;

    public Enemy(Map map, int health, int gold, int damage, int speed) {
        this.map = map;
        this.path = (Path) map.getStartCell().getContent();
        this.health = health;
        this.gold = gold;
        this.damage = damage;
        this.speed = speed;
    }

    public int getRow() {
        return this.path.getRow();
    }

    public int getCol() {
        return this.path.getCol();
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
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
            enemy.path.enemies.remove(enemy); // remuevo al enemigo de la lista de enemigos de la celda
            Path nuevaCelda = enemy.path.next; // Puntero a la proxima celda del camino
            enemy.path = nuevaCelda;
            enemy.setPath(nuevaCelda);
            enemy.path.addEnemy(enemy);
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
        Path nextPath = this.path.next;
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

    // Getters & Setters
    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isDebuffed() {
        return debuffed;
    }

    public void setDebuffed(boolean debuffed) {
        this.debuffed = debuffed;
    }
        //
}