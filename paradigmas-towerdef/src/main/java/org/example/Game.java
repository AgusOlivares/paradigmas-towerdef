package org.example;

import org.example.Mapa.Map;
import org.example.Mapa.MapElements.pathCell;
import org.example.Enemigos.Enemy;

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
        mapaGame.Map(10,10);
        this.mapa = mapaGame;
        this.enemies = new ArrayList<>();
        this.timer = new Timer();
    }

    // Función para iniciar el juego
    public void playGame() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mapa.imprimirMatriz();
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
                    enemy.walk(enemy); // Mover cada enemigo al siguiente pathCell
                }
            }
        }, 1000, 1000); // Ejecuta cada 1 segundo
    }

    // Método para agregar enemigos al juego
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
        pathCell startCell = (pathCell) mapa.getSalida().getContent();
        startCell.addEnemy(enemy);
    }
}