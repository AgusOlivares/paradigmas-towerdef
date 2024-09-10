package org.example;

import org.example.Enemigos.Enemy;
import org.example.Map.Map;
import org.example.Map.MapElements.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    private Map mapa;
    private List<Enemy> enemies; // Lista de enemigos en el juego
    private Timer timer;

    public Game() {
        Map mapaGame = new Map();
        mapaGame.Map();
        this.mapa = mapaGame;
        this.enemies = new ArrayList<>();
        this.timer = new Timer();
    }

    // Función para iniciar el juego
    public void playGame() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mapa.printMap();
            }
        }, 0, 500); // Imprime el mapa cada 0.5 segundos

        // Iniciar el avance de los enemigos
        Time();
    }

    // Función para avanzar a los enemigos cada segundo
    private void Time() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Enemy enemy : enemies) {
                    enemy.walk(enemy); // Mover cada enemigo al siguiente path
                }
            }
        }, 1000, 1000); // Ejecuta cada 1 segundo
    }

    // Método para agregar enemigos al juego
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
        Path startCell = (Path) mapa.getEndCell().getContent();
        startCell.addEnemy(enemy);
    }
}