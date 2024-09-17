package org.example.Enemigos;

import org.example.Map.Map;
import org.example.Map.MapElements.CDLGloria;
import org.example.Map.MapElements.MapElement;
import org.example.Map.MapElements.Path;

/**
 * Especialización de la clase MapElement para el modelado de los enemigos
 *
 * @author: Luciana Puentes
 */
public abstract class Enemy extends MapElement {

    public Path path;
    public Map map;
    public int health;
    public int gold;
    public int damage;
    public int speed;
    public boolean debuffed = false;
    private int baseSpeed;

    /**
     * Crear un enemigo y posicionarlo en el mapa
     *
     * @param: Map donde posicionar el enemigo y datos enteros de salud, oro, daño y velocidad
     */
    public Enemy(Map map, int health, int gold, int damage, int speed) {
        super(map.getStartCell().getContent().getRow(), map.getStartCell().getContent().getCol());
        this.map = map;
        /**
         *@see: Map
         */
        this.path = (Path) map.getStartCell().getContent();
        this.health = health;
        this.gold = gold;
        this.damage = damage;
        this.speed = speed;
        this.baseSpeed = speed;
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
     * Método para reducir la velocidad del enemigo en el caso de estar ralentizado
     *
     * @param state estado de ralentización del enemigo
     */
    public void setDebuff(boolean state) {
        /**
         * Reduce la velocidad del enemigo a la mitad cuando está ralentizado y su velocidad sea mayor a uno
         */
        this.debuffed = state;
        if (state) {
            if (this.speed > 1) {
                this.speed = this.speed / 2;
            } else {
                this.speed = 0;
            }
        }
        if (!state) {
            this.speed = this.baseSpeed;
        }
    }

    /**
     * Método que verifica si el enemigo está vivo
     *
     * @return: true si el enemigo está vivo, false en caso contrario
     */
    public boolean isAlive() {
        /**
         * Si la vida del emenigo es mayor a cero, retorna true, falso en caso contrario
         */
        return this.health > 0;
    }

    /**
     * Método que permite a los enemigos avanzar por el camino
     *
     * @param enemy enemigo que avanza por el camino
     */
    public void walk(Enemy enemy) {
        /**
         * Hace avanzar al enemigo acorde a su velocidad. Usa una Linked List como camino con la precaución de frenarlo
         * en la celda anterior al Cerro de la Gloria
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
     * Método que determina la acción que el enemigo va a realizar
     *
     * @param enemy enemigo que realizará las acciones
     */
    public void Controller(Enemy enemy) {
        /**
         * Si la próxima celda es el cerro, el enemigo inicia su ataque
         */
        if (!nextIsCerro()) {
            walk(enemy);
        } else {
            attackCerro();
        }
    }

    /**
     * Método que verifica si la próxima celda es el Cerro de la Gloria
     *
     * @return true si la próxima celda es el cerro de la gloria, false en caso contrario
     */
    public boolean nextIsCerro() {
        /**
         * Retorna true si la próxima celda es el Cerro de la Gloria, false en caso contrario
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
     * Método que permite reducir la vida del enemigo
     *
     * @param damage daño que recibe el enemigo
     */
    public void receiveDamage(int damage) {
        /**
         * Resta los puntos de daño recibidos a la vida actual del enemigo
         */
        setHealth(getHealth() - damage);
        //this.health = this.health - damage;
    }

    /**
     * Método para atacar al Cerro de la Gloria
     */
    public void attackCerro() {
        /**
         * Resta los puntos de daño del enemigo a la vida del Cerro de la Gloria
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
