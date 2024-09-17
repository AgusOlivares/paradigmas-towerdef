package org.example;

import java.io.IOException;

/**
 * CLase desde donde se crea y ejecuta el juego
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Game game = new Game();
        game.initGame();
        return;
    }
}