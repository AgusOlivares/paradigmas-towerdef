package org.example.Enemigos;

import org.example.Mapa.MapElements.CerroDeLaGloria;

public class EnemyElf extends Enemy{
    public EnemyElf() {
        super(50, 10, 5, 3, 1, 3);
    }
    @Override
    public void attackCerro(Enemy elf, CerroDeLaGloria cerro) {
        if (elf.cell.next.next==null || elf.cell.next==null ){
            cerro.health =cerro.health -elf.damage;
        }
    }


}
