package org.example.Enemigos;

import org.example.Map.Map;
import org.example.Map.MapElements.CDLGloria;
import org.example.Map.MapElements.MapElement;
import org.example.Map.MapElements.Path;

/**
 * especialización de la clase map element para el modelado de los enemigos
 * @author Luciana Puentes
 */
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

    /**
     * método para reducir la velocidad del enemigo en caso de estar ralentizado
     * @param state true si está ralentizado, false caso contrario
     */
    public void setDebuff(boolean state) {
        /**
         * reduce la velocidad del enemigo a la mitad en caso de estar ralentizado y que
         * su velocidad sea mayor a uno
         */
        this.debuffed = state;
        if (state) {
            if (this.speed > 1) {
                this.speed = this.speed / 2;
            } else {
                this.speed = this.speed * 2;
            }
        }
    }

    /**
     * método que verifica si el enemigo está vivo
     * @return  true si está vivo, false en caso contrario
     */
    public boolean isAlive() {
        /**
         * si la vida del emenigo es mayor a cero, retorna true sino, false
         */
        return this.health > 0;
    }

    /**
     * método que permite a los enemigos avanzar por el camino
     * @param enemy enemigo que se quiere mover
     */
    public void walk(Enemy enemy) {
        /**
         * hace avanzar al enemigo acorde a su velocidad usando la propiedad de linked list
         * de path, con la precaución de frenar al enemigo en la celda anterior al cerro
         * de la gloria
         */
        for (int i = 0; i < this.speed; i++) {
            enemy.cell.enemies.remove(enemy); // remuevo al enemigo de la lista de enemigos de la celda
            Path nuevaCelda = enemy.cell.next; // Puntero a la proxima celda del camino
            enemy.cell = nuevaCelda; //asigna la nueva posicion
            enemy.setCell(nuevaCelda);
            enemy.cell.addEnemy(enemy);//añade al enemigo a la lista de la celda actual
            if (nextIsCerro()) {
                break;
            }
        }
        //quita el debuff del enemigo
        enemy.setDebuff(false);
    }

    /**
     * método que determina la acción que el enemigo va a realizar
     * @param enemy enemigo que realizará las acciones
     */
    public void controller(Enemy enemy) {
        /**
         * si la proxima celda es el cerro, el enemigo inicia su ataque
         * sino el enemigo debe avanza por el camino
         */
        if (!nextIsCerro())
            walk(enemy);
        else
            attackCerro();
    }

    /**
     * método que verifica si la próxima celda es el cerro de la gloria
     * @return true si la próxima celda es el cerro de la gloria, false en caso contrario
     */
    public boolean nextIsCerro() {
        /**
         * si la próxima celda de la celda actual del enemigo es una instancia del
         * cerro de la gloria, retorna true
         */
        Path nextPath = this.cell.next;
        int nextPathRow = nextPath.getRow();
        int nextPathCol = nextPath.getCol();
        return map.getGrid()[nextPathRow][nextPathCol] == map.getEndCell();
    }

    /**
     * método para reducir la vida del enemigo
     * @param damage daño que recibe el enemigo
     */
    public void receiveDamage(int damage) {
        /**
         * resta los puntos de daño recibidos a la vida actual del enemigo
         */
        this.health = this.health - damage;
    }

    /**
     * método para atacar al cerro de la gloria
     */
    public void attackCerro() {
        /**
         * resta los puntos de daño del enemigo a la vida del cerro de la gloria
         */
        CDLGloria cerro = (CDLGloria) map.getEndCell().getContent();
        cerro.setHealth(cerro.getHealth() - this.damage);
    }
}