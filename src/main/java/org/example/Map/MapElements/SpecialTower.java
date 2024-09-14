package org.example.Map.MapElements;

import org.example.Map.Map;

public class SpecialTower extends Tower {

    public int damage = 1000;
    public int cost = 200;
    public int range = 3;

    public SpecialTower(int row, int col, Map map) {
        super(row, col, map);
    }
}
