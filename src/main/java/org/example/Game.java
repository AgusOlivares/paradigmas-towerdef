package org.example;

import org.example.Enemigos.*;
import org.example.Map.Map;
import org.example.Map.MapElements.Path;

import java.io.IOException;
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
        Map gameMap = new Map(50);
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
            if (map.getCdlGloria().isDeath()) {
                System.out.println("Game Over");
                scheduler.shutdown();
            }

        }, 0, 1000, TimeUnit.MILLISECONDS);
    }


    public void generateEnemies(int difficulty) {
        for (int i = 0; i < Enemy.enemyTypes; i++) {
            for (int j = 0; j < difficulty; j++) {

                switch (i) {
                    case 0:
                        addEnemy(new EnemyDwarf(map));
                        break;
                    case 1:
                        addEnemy(new EnemyElf(map));
                        break;
                    case 2:
                        addEnemy(new EnemyHuman(map));
                        break;
                    case 3:
                        addEnemy(new EnemyHobbit(map));
                        break;
                }
            }

        }
    }


    public void addEnemy(Enemy enemy) {
        this.levelEnemies.add(enemy);
        Path salida = (Path) map.getStartCell().getContent();
        salida.addEnemy(enemy);
        enemy.setPath(salida);
    }

    public void clearConsole() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}