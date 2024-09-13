package org.example;

import org.example.Enemigos.Enemy;
import org.example.Map.Map;
import org.example.Map.MapElements.MapElement;
import org.example.Map.MapElements.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {

    private ScheduledExecutorService scheduler;
    public Map map; //lo cambie a publico a proposito
    private List<Enemy> LevelEnemies;
    private Timer timer;

    // Constructor de la clase Game
    public Game() {
        Map mapaGame = new Map();
        this.map = mapaGame;
        this.LevelEnemies = new ArrayList<>();
        this.timer = new Timer();

    }

    // cambiaste este metodo
    public void playGame() {
        scheduler = Executors.newScheduledThreadPool(2);

        // Se imprime el mapa cada 0.5 segundos
        scheduler.scheduleAtFixedRate(() -> {
            map.printMap();
        }, 0, 700, TimeUnit.MILLISECONDS);

        // Mueve a los enemigos cada 1 segundo
        scheduler.scheduleAtFixedRate(() -> {
            for (Enemy enemy : LevelEnemies) {
                enemy.walk(enemy);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    /*
    public void playGame() {
        // Se imprime el map cada 0.5 segundos
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                map.printMap();
            }
        }, 0, 500);

        Time();

    }


    // Función para hacer avanzar a los enemigos cada 1.0 segundo
    private void Time() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (map) {  // Bloque sincronizado
                    for (Enemy enemy : enemies) {
                        enemy.walk(enemy);
                    }
                }
            }
        }, 1000, 1000);
    }

*/


    /*
    // Función para agregar enemigos al juego
    public void addEnemy(Enemy enemy) {
        this.LevelEnemies.add(enemy);
        Path salida = (Path) map.getStartCell().getContent();
        //salida.addEnemy(enemy); la lista de abajo no agrega el enemigo a la lista de la celda, por eso no lo renderiza
        //startCell.addEnemy(enemy);
        salida.addEnemy(enemy);
    }*/

    public void addEnemy(Enemy enemy) {
        // Agregar al enemigo a la lista general de enemigos del nivel
        this.LevelEnemies.add(enemy);

        // Obtener la celda de salida (start cell) del mapa
        Path salida = (Path) map.getStartCell().getContent();

        // Agregar al enemigo a la lista de enemigos de la celda de salida
        salida.addEnemy(enemy);

        // Asegurarse de que el enemigo también apunte a la celda correcta
        enemy.setCell(salida);
    }
}