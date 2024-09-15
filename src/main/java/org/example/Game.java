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

/**
 * Clase que se encarga de generar el juego
 * @autor Agustín Olivares
 */


public class Game {

    private ScheduledExecutorService scheduler;
    public Map map; //mapa donde se juega
    private List<Enemy> levelEnemies; //lista que contiene los enemigos del nivel
    private Timer timer; //timer para hacer avanzar los enemigos

    public Game() {
        Map gameMap = new Map();
        this.map = gameMap;
        this.levelEnemies = new ArrayList<>();
        this.timer = new Timer();

    }

    public void playGame() {
        /**
         * método que imprime el mapa y permite a los enemigos interactuar con el entorno
         */
        scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(() -> {
            map.printMap();
        }, 0, 1100, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            for (Enemy enemy : levelEnemies) {
                enemy.controller(enemy); //metodo que los hace caminar o atacar segun sea el caso
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void addEnemy(Enemy enemy) {
        /**
         * Método que se encarga de añadir los enemigos que aparecen en el nivel actual
         * @param enemy El enemigo a ser añadido
         */
        this.levelEnemies.add(enemy);
        Path salida = (Path) map.getStartCell().getContent();
        salida.addEnemy(enemy);
        enemy.setCell(salida);
    }
}