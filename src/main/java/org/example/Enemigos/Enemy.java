package org.example.Enemigos;

import org.example.Map.Map;
import org.example.Map.MapElements.CDLGloria;
import org.example.Map.MapElements.Path;
/**
 * especialización de la clase map element para el modelado de los enemigos
 * @author Luciana Puentes
 */
public abstract class Enemy {

    public static final int enemyTypes = 4;

    public Path path;
    public Map map;
    public int health;
    public int gold;
    public int damage;
    public int speed;
    public boolean debuffed = false;
    /**
     * Crea un enemigo y lo posiciona en el principio del path
     * @param mapa donde posicionar el enemigo
     */

    public Enemy(Map map, int health, int gold, int damage, int speed) {
        this.map = map;
        /**
         *@see Map
         */
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
     * @return  true si el enemigo está vivo, false en caso contrario
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
            enemy.path.enemies.remove(enemy); // remuevo al enemigo de la lista de enemigos de la celda
            Path nuevaCelda = enemy.path.next; // Puntero a la proxima celda del camino
            enemy.path = nuevaCelda;
            enemy.setPath(nuevaCelda);
            /**
             * @see Path
             */
            enemy.path.addEnemy(enemy);
            if (nextIsCerro()) {
                break;
            }
        }
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
        Path nextPath = this.path.next;
        /**
        *@see Path
        */
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
        setHealth(getHealth() - damage);
        //this.health = this.health - damage;
    }
    /**
     * método para atacar al cerro de la gloria
     */
    public void attackCerro() {
        /**
         * resta los puntos de daño del enemigo a la vida del cerro de la gloria
         */
        CDLGloria cerro = (CDLGloria) map.getEndCell().getContent();
        /**
         * @see Map
         * @see CDLGloria
         */
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
