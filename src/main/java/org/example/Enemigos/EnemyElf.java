package org.example.Enemigos;

import org.example.Map.Map;
/**
 * Especialización de la clase Enemy para el modelado el enemigo de tipo elfo
 * @author Luciana Puentes
 */
public class EnemyElf extends Enemy {
    /**
     * Crea un enemigo de tipo elfo
     * @param map mapa donde se crea el enemigo de tipo elfo
     */
    public EnemyElf(Map map) {
        /**
         * @see Enemy
         */
        super(map, 100, 66, 10, 5);
    }
     @Override
    /**
     * método que verifica si la próxima celda es el cerro de la gloria
     * * @return true si la próxima celda o la próxima de la próxima es el cerro de la gloria, false en caso contrario
     */
    public boolean nextIsCerro() {
        /**
         * si la próxima celda de la celda actual o la próxima de ella es una instancia del
         * cerro de la gloria, retorna true
         */
        Path nextPath = this.cell.next;
        Path nextnextPath = this.cell.next.next;
        int nextPathRow = nextPath.getRow();
        int nextPathCol = nextPath.getCol();
        int nextnextPathRow = nextnextPath.getRow();
        int nextnextPathCol = nextnextPath.getCol();
        return map.getGrid()[nextPathRow][nextPathCol] == map.getEndCell() || map.getGrid()[nextnextPathRow][nextnextPathCol] == map.getEndCell();
    }
    /**
     * método que determina la acción que el enemigo va a realizar
     * @param enemy enemigo que realizará las acciones
     */
    public void controller(Enemy enemy) {
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

