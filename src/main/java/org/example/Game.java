package org.example;

import org.example.Enemigos.*;
import org.example.Map.Map;
import org.example.Map.MapElements.Path;
import org.example.Map.MapElements.Tower;
import org.example.Player.Player;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {

    private ScheduledExecutorService scheduler;
    public Map map;
    private List<Enemy> levelEnemies;
    private Timer timer;
    public Player player;

    public Game() {
        Map gameMap = new Map(50);
        this.map = gameMap;
        this.levelEnemies = new ArrayList<>();
        this.timer = new Timer();
        this.player = new Player("Javier");
    }

    public void playGame(int difficulty) {
        final int[] totalEnemies = {difficulty * 3};

        scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(() -> {
            map.printMap();
        }, 0, 1100, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            if (totalEnemies[0] > 0) {
                spawnEnemy();
                totalEnemies[0]--;
            }

            for (Enemy enemy : levelEnemies) {
                enemy.controller(enemy);
            }
            if (map.getCdlGloria().isDeath()) {
                System.out.println("Game Over");
                scheduler.shutdown();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void spawnEnemy() {
        int enemyType = new Random().nextInt(4);
        switch (enemyType) {
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


//
//
    public void generateEnemies(int difficulty) {
        final int[] totalEnemies = {difficulty * 10}; // Example: 10 enemies per difficulty level
        scheduler = Executors.newScheduledThreadPool(3);

        scheduler.scheduleAtFixedRate(() -> {
            if (totalEnemies[0] > 0) {
                int enemyType = totalEnemies[0] % Enemy.enemyTypes; // Cycle through enemy types
                switch (enemyType) {
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
                totalEnemies[0]--;
            } else {
                scheduler.shutdown();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS); // Spawn one enemy every second
    }

    public void addTower() {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;

        do {
            System.out.println();
            System.out.print("Ingrese la fila de la torre: ");
            int row = scanner.nextInt();
            System.out.print("Ingrese la columna de la torre: ");
            int col = scanner.nextInt();
            System.out.print("Ingrese el tipo de torre (1: común, 2: especial): ");
            int type = scanner.nextInt();

            if (map.getGrid()[row][col].getContent() instanceof Path) {
                System.out.println("No se puede colocar una torre en un camino");
            } else if (map.getGrid()[row][col].getContent() instanceof Tower) {
                System.out.println("Ya hay una torre en esa celda");
            } else if (row < 0 || row >= Map.ROWS || col < 0 || col >= Map.COLS) {
                System.out.println("Posición inválida");
//            } else if (type == 1) {
//                // todo: agregar comprobación de dinero
//            } else if (type == 2) {
//                // todo: agregar comprobación de dinero
//            }
            } else {
                Tower newTower = new Tower(row, col, map);
                map.getCell(row, col).setContent(newTower);
                valid = true;
            }
        } while (!valid);

    }
///

    public void addEnemy(Enemy enemy) {
        this.levelEnemies.add(enemy);
        Path salida = (Path) map.getStartCell().getContent();
        salida.addEnemy(enemy);
        enemy.setPath(salida);
    }






}