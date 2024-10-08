package org.example.Enemigos;

import org.example.Map.Map;
import org.example.Map.MapElements.Path;

/**
 * Especialización de la clase Enemy para el modelado el enemigo de tipo elfo
 *
 * @author Luciana Puentes
 */
public class EnemyElf extends Enemy {
    /**
     * Crea un enemigo de tipo elfo
     *
     * @param map mapa donde se crea el enemigo de tipo elfo
     */
    public EnemyElf(Map map) {
        /**
         * @see Enemy
         */
        super(map, 200, 66, 25, 3);
    }

    /**
     * Método que verifica si la próxima celda es el cerro de la gloria
     * * @return true si la próxima celda o la próxima de la próxima es el cerro de la gloria, false en caso contrario
     */
    public boolean nextIsCerro() {
        /**
         * si la próxima celda de la celda actual o la próxima de ella es una instancia del
         * cerro de la gloria, retorna true
         */
        Path nextPath = this.getPath().getNext();
        Path nextnextPath = this.getPath().getNext().getNext();
        int nextPathRow = nextPath.getRow();
        int nextPathCol = nextPath.getCol();
        int nextnextPathRow = nextnextPath.getRow();
        int nextnextPathCol = nextnextPath.getCol();
        return getMap().getGrid()[nextPathRow][nextPathCol] == getMap().getEndCell() || getMap().getGrid()[nextnextPathRow][nextnextPathCol] == getMap().getEndCell();
    }

    /**
     * Sobreescritura del metodo Controller
     * @param enemy enemigo que realizará las acciones
     */
    public void Controller(Enemy enemy) {
        /**
         * si la proxima celda es el cerro o la próxima a ella,
         * el enemigo inicia su ataque
         * sino el enemigo debe avanza por el camino
         */
        if (!nextIsCerro())
            walk(enemy);
        else
            attackCerro();
    }
}

