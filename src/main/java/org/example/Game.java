package org.example;

import org.example.Enemigos.Enemy;
import org.example.Map.MapElements.Path;
import org.example.Map.MapElements.Tower;
import org.example.Player.Player;

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

public class Game implements gameInterface {

    public Player player;
    private ScheduledExecutorService scheduler;
    private ArrayList<Enemy> levelEnemies;
    private Stack<Enemy> enemyPool;
    private Timer timer;
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

        scheduler = Executors.newScheduledThreadPool(3); // pool de hilos en los cuales corren las tareas

        scheduler.scheduleAtFixedRate(() -> {
            if (!enemyPool.isEmpty()) {
                levelEnemies.add(enemyPool.pop());
            }
        }, 0, frequency, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            printSpace();
            level.getMap().printMap();
            System.out.println("Vida actual del Cerro: " + level.getMap().getCdlGloria().getHealth());
        }, 0, 500, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            Iterator<Enemy> iterator = levelEnemies.iterator();
            while (iterator.hasNext()) {
                Enemy enemy = iterator.next();
                if (!enemy.isAlive()) {
                    player.setGold(player.getGold() + enemy.getGold());
                    iterator.remove();
                    enemy.getPath().getEnemies().remove(enemy);
                } else {
                    enemy.Controller(enemy);
                }
            }
            for (Tower tower : player.getTowers()) {
                tower.updateEnemyOrder();
                tower.attack();
            }
            if (level.getMap().getCdlGloria().isDeath()) {
                scheduler.shutdownNow();
                System.out.println("¡¡NO, ten cuidado " + player.getName() + "!!");
                System.out.println("Presione la tecla ENTER para continuar.");
                level.getMap().getCdlGloria().setDead(true);
            }

            if (enemyPool.isEmpty() && levelEnemies.isEmpty()) {
                scheduler.shutdownNow();
                System.out.println("¡Increíble! ¡Has derrotado toda la oleada!");
                System.out.println("Pero... Se avecinan más enemigos, tenemos que reforzar las defensas");
                System.out.println("Presione cualquier tecla para continuar: ");
            }


        }, 0, 1100, TimeUnit.MILLISECONDS);

        return true;
    }

    /**
     * Añade un enemigo al nivel actual y lo asocia con el camino de salida.
     * @param enemy El enemigo a añadir.
     */
    public void addEnemy(Enemy enemy) {
        this.levelEnemies.add(enemy);
        Path salida = (Path) level.getMap().getStartCell().getContent();
        salida.addEnemy(enemy);
        enemy.setPath(salida);
    }


    /**
     * Inicializa el juego, solicita el nombre del jugador y empieza el nivel correspondiente.
     *
     * @throws IOException          Si ocurre un error de entrada/salida.
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
        System.out.print("¿Cuál es tu nombre? Escríbelo por favor: ");
        String playerName = scanner.nextLine();

        this.player = new Player(playerName);
        System.out.println("¡Bienvenido al campo de batalla " + playerName + "!");
        System.out.println("Necesitamos tu ayuda para defender el legendario Cerro de la Gloria");
        Thread.sleep(1000);
        System.out.println("¿Estás preparado? ¡Vamos allá!");
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


                System.out.println("A continuación elige una de las siguientes opciones: ");
                System.out.println("1 - Empezar el Juego");
                System.out.println("2 - Ver Mapa");
                System.out.println("3 - Ver Instrucciones");
                System.out.println("4 - Ver mis Datos");
                System.out.println("5 - Colocar Torre");
                System.out.println("6 - Demoler Torre");
                System.out.println("7 - Ver Introducción");
                System.out.println("8 - Salir");
                System.out.print("Opción seleccionada: ");

                String option = scanner.nextLine();

                printSpace();

                switch (option) {
                    case "1":
                        if (level.getMap().getCdlGloria().isDeath()) {
                            valid = true;
                            aviableLevels = true;
                            continue;
                        }

                        if (player.getLevel() == 5) {
                            System.out.println("Espera un momento...");
                            Thread.sleep(1000);
                            System.out.println("ENHORABUENA. Hemos logrado proteger el Cerro de la Gloria, muchas gracias por tus esfuerzos " + player.getName());
                            Thread.sleep(1000);
                            System.out.println("¡Hasta la próxima!");
                            Thread.sleep(1000);
                            System.exit(0);

                        } else if (level.getEnemiesInWave().isEmpty()) {
                            System.out.println("¡Ya has derrotado a todas las olas aquí!");
                            Thread.sleep(1000);
                            System.out.println("Levantemos todas tus torretas, sé que te costaron mucho oro, pero debemos continuar...");
                            Thread.sleep(1000);
                            System.out.println("¡Hay que cubrir los demás flancos! ¡No hay tiempo que perder, vamos!");
                            Thread.sleep(2000);
                            if (player.getLevel() < 5) {
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
                        System.out.println("¿Te vas a rendir? ¡Eres un cobarde!");
                        Thread.sleep(1000);
                        boolean flag = false;
                        while (!flag) {
                            System.out.print("¿Estás seguro? Escriba 'soy un cobarde' (en minúsculas) para continuar: ");
                            String answer = scanner.nextLine();
                            if (answer.equalsIgnoreCase("soy un cobarde")) {
                                System.out.println("¡Hasta la próxima!");
                                Thread.sleep(1000);
                                System.exit(0);
                            } else {
                                System.out.println("Además de cobarde no sabe leer, ¿eh? Reintente.");
                            }
                        }
                        break;
                    default:
                        System.out.print("Por favor, ingrese una opcion válida.");
                }

                if (!valid) {
                }
            }

            boolean statusCDLG = level.getMap().getCdlGloria().isDeath();

            if (!statusCDLG) {
                System.in.read();
                printSpace();
                valid = false;
            } else {
                aviableLevels = true;
                System.out.println("El Cerro de la Gloria ha caido!" +
                        " Fuimos derrotados, hemos perdido todo, tantas muertes...");
                Thread.sleep(1500);
                System.out.println("Con qué fin...");
                Thread.sleep(1000);
                System.out.println(player.getName() + ". Lo lamento tanto, no quise que terminaras así...");
                Thread.sleep(2000);
                System.exit(0);
            }
        }
    }


    /**
     * Imprime el título del juego en la consola.
     */
    public void printTitle() {
        System.out.println();
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
        System.out.println();
        System.out.println("--------------------- Presione la tecla ENTER para continuar ----------------------");

    }


    /**
     * Imprime lineas en blanco para generar espacio en la consola
     */
    public void printSpace() {
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
    public void ShowInstructions() {
        System.out.println("En el juego existen varios tipos de enemigos: ");
        System.out.println();
        System.out.println("    HUMANO (H): es el enemigo más común, lo verás en el mapa con una 'H'");
        System.out.println();
        System.out.println("    ENANO (D): este enemigo muy resistente pero tiene un avance muy lento. ¡Pero cuidado! Cuando baje del 25% de su daño aumentará, lo verás en el mapa con una 'D'");
        System.out.println();
        System.out.println("    HOBBIT (B): ¿Qué es eso que va corriendo por allí? Es el Hobbit, el enemigo más rapido del juego, si te descuidas escapara de todas tus torres, pero cuando lo mates puede darte una recompesa, extra lo veras aparecer en el mapa con una 'B'");
        System.out.println();
        System.out.println("    ELFO (E): El poderoso elfo es el enemigo mas orgulloso, puede atacar a distancia asi que tienes que estar alerta, lo veras en el mapa con una 'E'!");
        System.out.println();
        System.out.println("Nota: ten cuidado cuando veas un 2 o un ! en los caminos, significa que hay mas de un enemigo en esa casilla.");
    }

    /**
     * Muestra los datos del juegador y su oro por consola
     */
    @Override
    public void ShowPlayerDetails() {
        System.out.println("Nombre del Jugador: " + player.getName());
        System.out.println("Nivel actual: " + player.getLevel());
        System.out.println("Oro disponible: " + player.getGold());
    }

    ;

    /**
     * Muestra una breve introducción del juego
     */
    @Override
    public void ShowIntroduction() {
        System.out.println("El Cerro de la Gloria está siendo atacado. Necesitamos de tu ayuda para defenderlo, " + player.getName() + '.');
        System.out.println("Deberás colocar estratégicamente las torres (T) de defensa para protegernos lo mejor posible y conseguir las recompensas que deje el enemigo.");
        System.out.println("Contamos con una torre especial (F) cuya habilidad es la de realentizar a tus enemigos e incluso puede congelarlos completamente.");
        // System.out.println("Este cerro es muy escarpado y dificil de recorrer por lo que puede que no te encuentres siempre los mismos caminos");
        System.out.println("En el mapa verás una (S) en la entrada, los (*) son las sendas que transitarán, y los espacios en blanco son los lugares donde puedes colocar tus torretas.");
        System.out.println("Y finalmente, la (C) que verás en el mapa es nuestro querido Cerro de la Gloria, debes protegerlo a toda costa.");
    }
}