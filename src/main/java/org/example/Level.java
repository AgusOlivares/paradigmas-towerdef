package org.example;

import org.example.Enemigos.*;
import org.example.Map.Map;

import java.util.Random;
import java.util.Stack;

public class Level {

    private int level;
    private Map map;
    private Stack<Stack<Enemy>> enemiesInWave;
    private String[] enemiesInLevel;


    public Level(int level) {
        Map gameMap = new Map(100);
        this.map = gameMap;
        this.level = level;

        switch (level) {
            case 1:
                this.enemiesInLevel = new String[]{"Human"};
                this.enemiesInWave = generarOleadas(4, 1);
                break;
            case 2:
                enemiesInLevel = new String[]{"Human", "Dwarf"};
                this.enemiesInWave = generarOleadas(3, 3);
                break;
            case 3:
                enemiesInLevel = new String[]{"Human", "Dwarf", "Hobbit"};
                this.enemiesInWave = generarOleadas(4, 3);
                break;
            case 4:
                enemiesInLevel = new String[]{"Human", "Dwarf", "Hobbit", "Elf"};
                this.enemiesInWave = generarOleadas(4, 4);
                break;
            default:
                break;
        }

    }

    // Método que genera las oleadas de enemigos
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

                if (enemy == "Human") {
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

    public int WaveReward() {
        int reward = 0; //Gold
        for (Stack<Enemy> group : enemiesInWave) {
            for (Enemy unit : group) {
                int enemyGold = unit.getGold();
                reward += enemyGold;
            }
        }
        return reward;
    }

//    public ArrayList<Enemy> getAllEnemies(){
//        ArrayList<Enemy> allEnemies = new ArrayList<>();
//        for (Stack<Enemy> group : enemiesInWave) {
//            for (Enemy unit : group) {
//                allEnemies.add(unit);
//            }
//        }
//
//        return allEnemies;
//    }

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
