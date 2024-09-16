package org.example;

import org.example.Enemigos.*;
import org.example.Map.Map;
import org.example.Player.Player;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Stack;

/**
 * La clase Level representa un nivel dentro del juego.
 * Cada nivel tiene un mapa, enemigos específicos y oleadas de enemigos que se generan segun el nivel del jugador.
 */
public class Level {

    private int level;
    private Map map;
    private Stack<Stack<Enemy>> enemiesInWave;
    private String[] enemiesInLevel;


    /**
     * Constructor de la clase Level.
     * Inicializa el nivel, genera el mapa y las oleadas de enemigos de acuerdo al nivel actual.
     *
     * @param level El número del nivel a jugar.
     */
    public Level(int level) {
        Map gameMap = new Map(100);
        this.map = gameMap;
        this.level = level;

        switch (level){
            case 1:
                this.enemiesInLevel = new String[] {"Human"};
                this.enemiesInWave = generarOleadas(3, 4);
                break;
            case 2:
                enemiesInLevel = new String[] {"Human", "Dwarf"};
                this.enemiesInWave = generarOleadas(1, 10);
                break;
            case 3:
                enemiesInLevel = new String[] {"Human", "Dwarf", "Hobbit"};
                this.enemiesInWave = generarOleadas(4, 5);
                break;
            case 4:
                enemiesInLevel = new String[] {"Human", "Dwarf", "Hobbit", "Elf"};
                this.enemiesInWave = generarOleadas(2, 10);
                break;
            default:
                break;
        }

    }

    /**
     * Genera oleadas de enemigos para el nivel actual.
     * Cada oleada contiene un número aleatorio de enemigos según el tamaño de la ola.
     *
     * @param numOleadas El número de oleadas que se generarán.
     * @param tamanoOla El número de enemigos por cada oleada.
     * @return Una pila de pilas de enemigos que representan las oleadas.
     */
    public Stack<Stack<Enemy>> generarOleadas(int numOleadas, int tamanoOla) {

        Stack<Stack<Enemy>> oleadas = new Stack<Stack<Enemy>>();
        Random random = new Random();

        for (int i = 0; i < numOleadas; i++) {
            Stack<Enemy> ola = new Stack<Enemy>();
            for (int j = 0; j < tamanoOla; j++) {
                // Selecciona un enemigo aleatorio de 'enemiesInLevel'
                int randomIndex = random.nextInt(enemiesInLevel.length);
                String enemy = this.enemiesInLevel[randomIndex];

                Enemy unit;

                if (enemy == "Human"){
                    unit = new EnemyHuman(map);
                } else if (enemy == "Dwarf") {
                    unit = new EnemyDwarf(map);
                } else if (enemy == "Hobbit") {
                    unit = new EnemyHobbit(map);
                } else if (enemy == "Elf") {
                    unit = new EnemyElf(map);
                } else {
                    break;
                }

                ola.add(unit);
            }
            oleadas.add(ola);
        }

        return oleadas;
    }


    // Getters & Setters
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Stack<Stack<Enemy>> getEnemiesInWave() {
        return enemiesInWave;
    }
}
