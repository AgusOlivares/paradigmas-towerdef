package org.example;

import org.example.Enemigos.Enemy;
import org.example.Map.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    private Map map;
    private List<Enemy> enemies;
    private Timer timer;

    // Constructor de la clase Game
    public Game() {
        Map mapaGame = new Map();
        mapaGame.Map();
        this.map = mapaGame;
        this.enemies = new ArrayList<>();
        this.timer = new Timer();
    }

    public void playGame() {
        // Se imprime el map cada 0.5 segundos
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                map.printMap();
            }
        }, 0, 500);

        // Inicia el avance de los enemigos
        Time();
    }

    // Función para hacer avanzar a los enemigos cada 1.0 segundo
    private void Time() {

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                // AGREGAR ACÁ PROCEDIMIENTOS DINÁMICOS

                for (Enemy enemy : enemies) {
                    enemy.walk(enemy);
                }


            }
        }, 1000, 1000);
    }


    // Función para agregar enemigos al juego
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
//        startCell.addEnemy(enemy);
    }
}