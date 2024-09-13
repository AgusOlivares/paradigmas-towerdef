package org.example.Enemigos;

import org.example.Map.MapElements.Path;

public abstract class Enemy {
    Path cell;
    int health;
    int gold;
    int magic;
    int damage;
    int walkRate;
    int range;
    boolean debuffState;

    public Enemy(int health, int gold, int magic, int damage, int walkRate, int range) {
        this.health = health;
        this.gold = gold;
        this.magic = magic;
        this.damage = damage;
        this.walkRate = walkRate;
        this.range = range;
        this.debuffState = false;
    }

    public Path getCell() {
        return cell;
    }

    public void setCell(Path cell) {
        this.cell = cell;
    }

    public int getRow() {
        return this.cell != null && this.cell.getCell() != null ? this.cell.getCell().row : -1;
    }

    public int getCol() {
        return this.cell != null && this.cell.getCell() != null ? this.cell.getCell().col : -1;
    }

    public void setDebuff(boolean state) {
        this.debuffState = state;
        if (state) {
            if (this.walkRate > 1) {
                this.walkRate = this.walkRate / 2;
            } else {
                this.walkRate = this.walkRate * 2;
            }
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void walk(Enemy enemy) {
        for (int i = 0; i < this.walkRate; i++) {

            if (enemy.cell.next != null) {
                enemy.cell.enemies.remove(enemy); // remuevo al enemigo de la lista de enemigos de la celda
                Path nuevaCelda = enemy.cell.next; // Puntero a la proxima celda del camino
                enemy.cell = nuevaCelda;
                enemy.setCell(nuevaCelda);
                enemy.cell.addEnemy(enemy);
            } else {
                break;
            }
            enemy.setDebuff(false);
        }
    }

    public void receiveDamage(int damage) {
        this.health = this.health - damage;
    }
/*
        public void attackCerro (Enemy enemy, CerroDeLaGloria cerro){
            if (enemy.position.next instanceof MapElement.CerroDeLaGloria) {
                cerro.health = cerro.health - enemy.damage;
            }*/
}