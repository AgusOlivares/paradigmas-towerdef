package org.example;

import org.example.Enemigos.Enemy;
import org.example.Map.Map;
import org.example.Map.MapElements.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {

    private ScheduledExecutorService scheduler;
    public Map map;
    private List<Enemy> levelEnemies;
    private Timer timer;

    public Game() {
        Map gameMap = new Map();
        this.map = gameMap;
        this.levelEnemies = new ArrayList<>();
        this.timer = new Timer();

    }

    public void playGame() {
        scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(() -> {
            map.printMap();
        }, 0, 1100, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            for (Enemy enemy : levelEnemies) {
                enemy.controller(enemy);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void addEnemy(Enemy enemy) {
        this.levelEnemies.add(enemy);
        Path salida = (Path) map.getStartCell().getContent();
        salida.addEnemy(enemy);
        enemy.setCell(salida);
    }
}