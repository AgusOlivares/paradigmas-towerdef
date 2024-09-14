package org.example.Enemigos;

import org.example.Map.Map;
import org.example.Map.MapElements.CDLGloria;
import org.example.Map.MapElements.Path;

public class EnemyElf extends Enemy {

    public EnemyElf(Map map) {
        super(map, 100, 66, 10, 5);
    }
    @Override
    public boolean nextIsCerro() {
        Path nextnextPath= this.path.next.next;
        Path nextPath = this.path.next;
        int nextPathRow = nextPath.getRow();
        int nextPathCol = nextPath.getCol();
        int nextnextPathRow = nextnextPath.getRow();
        int nextnextPathCol = nextnextPath.getCol();
        return map.getGrid()[nextPathRow][nextPathCol] == map.getEndCell() || map.getGrid()[nextnextPathRow][nextnextPathCol] == map.getEndCell();
    }
    public void controller(Enemy elf) {
        if (!nextIsCerro())
            walk(elf);
        else
            attackCerro();
    }
}

