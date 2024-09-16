package org.example;

import org.example.Enemigos.*;
import org.example.Map.Map;
import org.example.Map.MapElements.Path;
import org.example.Map.MapElements.Tower;
import org.example.Player.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Clase que representa el núcleo del juego.
 * Gestiona el flujo principal del juego, incluyendo la creación de enemigos,
 * el control de las torres y la actualización del mapa al cambiar de nivel.
 *
 * @author Agustin Olivares
 */
public class Game implements gameInterface{

    private ScheduledExecutorService scheduler;
    private ArrayList<Enemy> levelEnemies;
    private Stack<Enemy> enemyPool;
    private Timer timer;
    public Player player;
    private Level level;


    /**
     * Constructor de la clase Game.
     * Inicializa las listas de enemigos, el temporizador y el pool de enemigos.
     */
    public Game() {
        this.levelEnemies = new ArrayList<>();
        this.timer = new Timer();
        this.enemyPool = new Stack<>();
    }

    /**
     * Inicia y ejecuta el bucle principal del juego.
     * Controla la frecuencia de aparición de los enemigos y la actualización de las torres.
     * También gestiona las condiciones de fin de oleada o derrota.
     *
     * @return true cuando el juego se está ejecutando correctamente.
     */
    public boolean playGame() {

        int frequency;
        switch (player.getLevel()) {
            case 1:
                frequency = 4000;
                break;
            case 2:
                frequency = 3000;
                break;
            case 3:
                frequency = 2500;
                break;
            case 4:
                frequency = 2000;
                break;
            default:
                frequency = 4000;
        } // Frecuencia con la que spawnean los enemigos segun el nivel del jugador

        scheduler = Executors.newScheduledThreadPool(3); // pool de hilos en los cuales correr las tareas

        scheduler.scheduleAtFixedRate(() -> {
            if (!enemyPool.isEmpty()) {
                levelEnemies.add(enemyPool.pop());
            }
        }, 0, frequency, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            printSpace();
            level.getMap().printMap();
        }, 0, 500, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            Iterator<Enemy> iterator = levelEnemies.iterator();
            while (iterator.hasNext()) {
                Enemy enemy = iterator.next();
                if (!enemy.isAlive()) {
                    player.setGold(player.getGold() + enemy.getGold());
                    iterator.remove();
                    enemy.path.enemies.remove(enemy);
                } else {
                    enemy.controller(enemy);
                }
            }
            for (Tower tower : player.getTowers()) {
                tower.updateEnemyOrder();
                tower.attack();
            }
            if (level.getMap().getCdlGloria().isDeath()) {
                scheduler.shutdownNow();
                System.out.println("NOOOOO, Ten cuidado " + player.getName() + "!!");
                level.getMap().getCdlGloria().setDead(true);
            }

            if (enemyPool.isEmpty() && levelEnemies.isEmpty()) { // creo que habria que cambiar lo del nivel a enemyPool
                scheduler.shutdownNow();
                System.out.println("¡Increible! ¡Has derrotado la oleada!");
                System.out.println("Se avecinan mas enemigos, tenemos que reforzar las defensas");
                System.out.println("Presiona cualquier tecla para seguir adelante");
            }


        }, 0, 1100, TimeUnit.MILLISECONDS);

        return true;
    }
    /**
     * Añade un enemigo al nivel actual y lo asocia con el camino de salida.
     *
     * @param enemy El enemigo a añadir.
     */
    public void addEnemy( @NotNull Enemy enemy) {
        this.levelEnemies.add(enemy);
        Path salida = (Path) level.getMap().getStartCell().getContent();
        salida.addEnemy(enemy);
        enemy.setPath(salida);
    }


    /**
     * Inicializa el juego, solicita el nombre del jugador y empieza el nivel correspondiente.
     *
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws InterruptedException Si el hilo es interrumpido durante las pausas del juego.
     */
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
        Thread.sleep(1000);
        System.out.println("Estas preparado? Vamos alla!");
        Thread.sleep(3000);
        level = new Level(player.getLevel());
        transicionAsteriscos();
        printSpace();

        boolean aviableLevels = false;

        boolean valid = false;

        while (!aviableLevels) {

            while (!valid) {

                if (level.getMap().getCdlGloria().isDeath()) {
                    valid = true;
                    aviableLevels = true;
                    continue;
                }


                System.out.println("A continaucion elige una de las siguientes opciones:");
                System.out.println("1 - Empezar el Juego");
                System.out.println("2 - Ver Mapa");
                System.out.println("3 - Ver instrucciones");
                System.out.println("4 - Ver mis datos");
                System.out.println("5 - Colocar torre");
                System.out.println("6 - Demoler torre");
                System.out.println("7 - Ver introduccion");
                System.out.println("8 - Salir");

                String option = scanner.nextLine();

                printSpace();

                switch (option) {
                    case "1":
                        if (level.getMap().getCdlGloria().isDeath()) {
                            valid = true;
                            aviableLevels = true;
                            continue;
                        }

                        if (player.getLevel() == 5){
                            System.out.println("Espera un momento...");
                            Thread.sleep(1000);
                            System.out.println("Lo hemos conseguido !!");
                            Thread.sleep(1000);
                            continue;

                        }else if (level.getEnemiesInWave().isEmpty()){
                            System.out.println("Ya has derrotado a todas las olas aqui!");
                            Thread.sleep(1000);
                            System.out.println("Levanta todas tus torretas, ya se que te costaron mucho oro pero debemos continuar");
                            Thread.sleep(1000);
                            System.out.println("Hay que cubrir los demas flancos! No hay tiempo que perder, vamos!!");
                            Thread.sleep(2000);
                            if (player.getLevel() < 5){
                                transicionAsteriscos();
                                player.recicleTowers();
                                player.setLevel(player.getLevel() + 1);
                                this.level = new Level(player.getLevel());
                            }
                        } else {
                            if (!level.getEnemiesInWave().isEmpty() && enemyPool.isEmpty()) {
                                enemyPool = level.getEnemiesInWave().pop();
                            }
                            playGame();
                            valid = true;
                            continue;
                        }

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
                        level.getMap().printMap();
                        player.placeTower(level.getMap());
                        break;
                    case "6":
                        level.getMap().printMap();
                        player.demolishTower(level.getMap());
                        break;
                    case "7":
                        ShowIntroduction();
                        break;
                    case "8":
                        System.out.println("Te vas a rendir? Eres un cobarde!");
                        Thread.sleep(1000);
                        System.out.println(player.getName() + ": Lo lamento, si, soy un cobarde...");
                        valid = true;
                        aviableLevels = true;
                        break;
                    default:
                        System.out.println("Por favor ingrese una opcion valida");
                }

                if (!valid) {}
            }

            boolean statusCDLG = level.getMap().getCdlGloria().isDeath();

            if (!statusCDLG) {
                System.in.read();
                printSpace();
                valid = false;
            } else {
                aviableLevels = true;
                System.out.println("El Cerro ha caido!" +
                        " Fuimos derrotados, se ha perdido todo...");
                System.out.println("No lo hemos podido conseguir, tantas muertes...");
                Thread.sleep(1500);
                System.out.println("Con que fin...");
                Thread.sleep(1000);
                System.out.println(player.getName() + "... Lo lamento tanto, no quise que terminaras asi...");
                Thread.sleep(2000);
                return;
            }

            if (player.getLevel() > 4 && !level.getMap().getCdlGloria().isDeath()) {
                System.out.println("Hemos logrado proteger el Cerro de la gloria, muchas gracias por tus esfuerzos " + player.getName());
                System.out.println("Hasta la proxima!");
                aviableLevels = true;
            }
        }
    }
    /**
     * Imprime el título del juego en la consola.
     */
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


    /**
     * Imprime lineas en blanco para generar espacio en la consola
     */
    public void printSpace(){
        for (int i = 0; i < 15; i++) {
            System.out.println();
        }
    }

    /**
     * Crea una transición visual con asteriscos para mejorar la experiencia del juego.
     *
     * @throws InterruptedException Si el hilo es interrumpido durante la pausa.
     */
    public void transicionAsteriscos() throws InterruptedException {

        String trans = "";

        for (int i = 0; i < 20; i++) {
            trans += " * ";
            System.out.println(trans);
            System.out.println();
            Thread.sleep(100);
        }
    }


    /**
     * Muestra una breve guia sobre los enemigos
     */
    @Override
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
        System.out.println("Nota: Ten cuidado cuando veas un 2 o un ! en los caminos, significa que hay mas de un enemigo en esa casilla!!");
    }
    /**
     * Muestra los datos del juegador y su oro por consola
     */
    @Override
    public void ShowPlayerDetails(){
        System.out.println("Nombre jugador: " + player.getName());
        System.out.println("Nivel: " + player.getLevel());
        System.out.println("Oro disponible: " + player.getGold());
    };

    /**
     * Muestra una breve introducción del juego
     */
    @Override
    public void ShowIntroduction() {
        System.out.println("El cerro de la gloria esta siendo atacado y necesitamos de tu ayuda para defenderlo " + player.getName());
        System.out.println("Deberas colocar estrategicamente las torres de defensa para protegernos lo mejor posible y conseguir las recompensas que deje el enemigo.");
        System.out.println("Contamos con una torre especial cuya habilidad es la de realentizar a tus enemigos e incluso puede congelarlos completamente!!");
        System.out.println("Este cerro es muy escarpado y dificil de recorrer por lo que puede que no te encuentres siempre los mismos caminos");
        System.out.println("en el mapa veras una 'S' por esa entrada es que vienen los enemigos, los '*' son las sendas que transitaran. los espacios en blanco son los lugares donde puedes colocar tus torretas");
        System.out.println("Y finalmente, la 'C' que veras en el mapa es nuestro querido Cerro de la Gloria, debes protegerlo a toda costa.");
    }
}