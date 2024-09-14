package org.example;

import org.example.Enemigos.*;
import org.example.Map.Map;
import org.example.Player.Player;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Level {

    private int level;
    private Map map;
    private ArrayList<ArrayList<Enemy>> enemiesInWave;
    private String[] enemiesInLevel;


    public Level(int level) {
        Map gameMap = new Map(100);
        this.map = gameMap;
        this.level = level;

        switch (level){
            case 1:
                this.enemiesInLevel = new String[] {"Human"};
                this.enemiesInWave = generarOleadas(4, 1);
                break;
            case 2:
                enemiesInLevel = new String[] {"Human", "Dwarf"};
                this.enemiesInWave = generarOleadas(3, 2);
                break;
            case 3:
                enemiesInLevel = new String[] {"Human", "Dwarf", "Hobbit"};
                this.enemiesInWave = generarOleadas(4, 2);
                break;
            case 4:
                enemiesInLevel = new String[] {"Human", "Dwarf", "Hobbit", "Elf"};
                this.enemiesInWave = generarOleadas(4, 3);
                break;
            default:
                break;
        }

    }

    // Método que genera las oleadas de enemigos
    public ArrayList<ArrayList<Enemy>> generarOleadas(int numOleadas, int tamanoOla) {

        ArrayList<ArrayList<Enemy>> oleadas = new ArrayList<ArrayList<Enemy>>();
        Random random = new Random();

        for (int i = 0; i < numOleadas; i++) {
            ArrayList<Enemy> ola = new ArrayList<Enemy>();
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

    public int WaveReward(){

        int reward = 0; //Gold

        for (ArrayList<Enemy> group : enemiesInWave) {
            for (Enemy unit : group) {
                int enemyGold = unit.getGold();
                reward += enemyGold;
            }
        }

        return reward;
    }


}
