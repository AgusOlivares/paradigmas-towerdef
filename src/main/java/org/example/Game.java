package org.example;

import org.example.Enemigos.*;
import org.example.Map.Map;
import org.example.Map.MapElements.Path;
import org.example.Map.MapElements.Tower;
import org.example.Player.Player;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game implements gameInterface{

    private ScheduledExecutorService scheduler;
    private ArrayList<Enemy> levelEnemies;
    private Stack<Enemy> enemyPool;
    private Timer timer;
    public Player player;
    private Level level;

    public Game() {
        this.levelEnemies = new ArrayList<>();
        this.timer = new Timer();
        this.enemyPool = new Stack<>();
    }

    public boolean playGame() {

        int frequency;
        switch (player.getLevel()){
            case 1:
                frequency = 5000;
                break;
            case 2:
                frequency = 4500;
                break;
            case 3:
                frequency = 3500;
                break;
            case 4:
                frequency = 3000;
                break;
            default:
                frequency = 4000;
        }

        scheduler = Executors.newScheduledThreadPool(3);

        scheduler.scheduleAtFixedRate(() -> {
            if (!level.getEnemiesInWave().isEmpty() && enemyPool.isEmpty()){
                enemyPool = level.getEnemiesInWave().pop();
            }

            if (!enemyPool.isEmpty()){
                levelEnemies.add(enemyPool.pop());

            }
        }, 0, frequency, TimeUnit.MILLISECONDS );

        scheduler.scheduleAtFixedRate(() -> {
            level.getMap().printMap();
            System.out.println(player.getGold());
        }, 0, 700, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            for (Enemy enemy : levelEnemies) {
                if (!enemy.isAlive()) {
                    player.setGold(player.getGold() + enemy.getGold());
                    levelEnemies.remove(enemy);
                    enemy.path.enemies.remove(enemy);
                } else {
                    enemy.controller(enemy);
                }
                for(Tower tower : player.getTowers()){
                    tower.updateEnemyOrder();
                    tower.attack();
                }
            }
            if (level.getMap().getCdlGloria().isDeath()) {
                System.out.println("El Cerro ha caido! Fuimos derrotados");
                scheduler.shutdown();
            }
        }, 0, 1100, TimeUnit.MILLISECONDS);


        return true;
    }

    public void addEnemy(Enemy enemy) {
        this.levelEnemies.add(enemy);
        Path salida = (Path) level.getMap().getStartCell().getContent();
        salida.addEnemy(enemy);
        enemy.setPath(salida);
    }


    public void initGame() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        try {
            printTitle();
            System.in.read();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread.sleep(1000);
        printSpace();
        System.out.println("Cual es tu nombre?: ");
        String playerName = scanner.nextLine();

        this.player = new Player(playerName);
        System.out.println("Bienvenido al campo de batalla " + playerName + "!");
        System.out.println("Necesitamos tu ayuda para defender el Cerro de la Gloria");
        //Thread.sleep(1000);
        System.out.println("Estas preparado? Vamos alla!");
        //Thread.sleep(3000);
        level = new Level(player.getLevel());
        transicionAsteriscos();
        printSpace();

        boolean aviableLevels = false;

        boolean valid = false;

        while (!aviableLevels) {

            while (!valid) {

                System.out.println("A continaucion elije una de las siguientes opciones:");
                System.out.println("1 - Empezar el Juego");
                System.out.println("2 - Ver Mapa");
                System.out.println("3 - Ver instrucciones");
                System.out.println("4 - Ver mis datos");
                System.out.println("5 - Colocar torre");
                System.out.println("6 - Demoler torre");
                System.out.println("7 - Salir");

                String option = scanner.nextLine();

                switch (option) {
                    case "1":
                        playGame();
                        break;
                    case "2":
                        level.getMap().printMap();
                        break;
                    case "3":
                        ShowInstructions();
                        break;
                    case "4":
                        ShowPlayerDetails();
                        break;
                    case "5":
                        player.placeTower(level.getMap());

                        break;
                    case "6":
                        player.demolishTower(level.getMap());
                        break;
                    case "7":
                        System.out.println("Te vas a rendir? como un cobarde?");
                        player.surrender();
                        valid = true;
                        break;
                    default:
                        System.out.println("Por favor ingrese una opcion valida");
                }
                printSpace();
            }


        }




    }

    public void printTitle(){

        System.out.println("===========    ||=======||     \\\\                //      ||======     ||====\\\\              ");
        System.out.println("===========    ||       ||      \\\\              //       ||           ||     ||         ");
        System.out.println("    ||         ||       ||       \\\\    //\\\\    //        ||===        ||=====//        ");
        System.out.println("    ||         ||       ||        \\\\  //  \\\\  //         ||           ||     \\\\       ");
        System.out.println("    ||         ||=======||         \\\\//    \\\\//          ||======     ||      \\\\           ");
        System.out.println("||=======\\\\     ||======   ||=======   ||======   ||\\\\    ||   ||======   ||=====          ");
        System.out.println("||       ||     ||         ||          ||         || \\\\   ||   ||         ||        ");
        System.out.println("||       ||     ||===      ||====      ||===      ||  \\\\  ||   ||=====||  ||===          ");
        System.out.println("||       ||     ||         ||          ||         ||   \\\\ ||          ||  ||         ");
        System.out.println("||=======//     ||======   ||          ||======   ||    \\\\||    ======||  ||=====        ");
        System.out.println("----------------------Presione cualquier tecla para contiuar----------------------");

    }




    public void printSpace(){
        for (int i = 0; i < 15; i++) {
            System.out.println();
        }
    }

    public void transicionAsteriscos() throws InterruptedException {

        String trans = "";

        for (int i = 0; i < 20; i++) {
            trans += " * ";
            System.out.println(trans);
            System.out.println();
            Thread.sleep(100);
        }
    }

    public void ShowInstructions(){
        System.out.println("En el juego existen varios tipos de enemigos:");
        System.out.println();
        System.out.println("Humano: Es el enemigo comun lo veras aparecer en el mapa con una 'H'");
        System.out.println();
        System.out.println("Enano: Este enemigo es muy resistente, tiene un avance muy lento, pero cuidado! cuando baje del 25% de su daño aumentara, lo veras aparecer en el mapa con una 'D'");
        System.out.println();
        System.out.println("Hobbit: Que es eso que va corriendo por alli? Ese es el Hobbit, el enemigo mas rapido del juego, si te descuidas escapara de todas tus torres, pero cuando lo mates te puede dar una recompesa extra lo veras aparecer en el mapa con una 'B'");
        System.out.println();
        System.out.println("Elfo: El poderoso elfo es el enemigo mas orgulloso, puede atacar a distancia asi que tienes que estar alerta, lo veras en el mapa con una 'E'!");
        System.out.println();
        System.out.println();
    }

    public void ShowPlayerDetails(){
        System.out.println("Nombre jugador: " + player.getName());
        System.out.println("Nivel: " + player.getLevel());
        System.out.println("Oro disponible: " + player.getGold());
    };


}