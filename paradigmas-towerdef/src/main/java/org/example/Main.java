package org.example;

import org.example.Map.Cell;
import org.example.Map.Map;
import org.example.Torres.Tower;

public class Main {
    public static void main(String[] args) {
        Map map = new Map();

        Cell cell = new Cell(5, 5, null);
        Tower tower = new Tower(1.0, 1.0, 1.0, map);
        cell.setContent(tower);

        System.out.println("Tower is at row: " + tower.getRow() + ", col: " + tower.getCol());
    }
}