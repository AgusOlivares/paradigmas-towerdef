package org.example.Player;

import org.example.Map.Cell;
import org.example.Map.Map;
import org.example.Map.MapElements.Path;
import org.example.Map.MapElements.SpecialTower;
import org.example.Map.MapElements.Tower;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase del jugador, su responsabilidad es la de colocar y destruir torres, cuando se cambia de nivel se devuelve el oro al igual que si se vendieran las torres
 * Tambien puede rendirse si asi lo desea
 *
 * @author Agustin Olivares
 */

public class Player {

    Scanner scanner = new Scanner(System.in);
    private String name;
    private int gold;
    private int level;
    private ArrayList<Tower> towers;

    // Constructor del jugador
    public Player(String playerName) {
        this.name = playerName;
        this.gold = 200;
        this.level = 1;
        this.towers = new ArrayList<>();
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public void setTowers(ArrayList<Tower> towers) {
        this.towers = towers;
    }

    // Método para colocar una torre en el mapa
    public void placeTower(Map gameMap) {

        boolean validPosition = false;

        while (!validPosition) {
            try {
                System.out.print("Ingrese la fila (0-9): ");
                int row = Integer.parseInt(scanner.nextLine());

                System.out.print("Ingrese la columna (A-Z): ");
                char columnChar = scanner.nextLine().toUpperCase().charAt(0);

                // Validar fila y columna
                if (row < 0 || row >= Map.getROWS() || columnChar < 'A' || columnChar > 'Z') {
                    System.out.println("Posición inválida. Las filas deben ser entre 0-9 y las columnas entre A-T.");
                    continue;
                }

                System.out.print("Ingrese el tipo de torre: 1: Torre Común (100 oro) | 2: Torre Especial (300 oro): ");
                int type = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada

                int col = columnChar - 'A'; // Convertir la letra de columna en un índice numérico
                Cell targetCell = gameMap.getCell(row, col); // Obtener la celda objetivo

                // Verificar si la celda está disponible para colocar una torre
                if (targetCell.getContent() == null && !(targetCell.getContent() instanceof Path)) {
                    Tower newTower;

                    // Crear e insertar la torre según el tipo y el oro disponible
                    switch (type) {
                        case 1:
                            if (this.gold < 100) {
                                System.out.println("Oro insuficiente para una torre común.");
                                return;
                            } else {
                                newTower = new Tower(row, col, gameMap);
                                this.gold -= 100; // Restar el oro correspondiente
                                break;
                            }
                        case 2:
                            if (this.gold < 300) {
                                System.out.println("Oro insuficiente para una torre especial.");
                                return;
                            } else {
                                newTower = new SpecialTower(row, col, gameMap);
                                this.gold -= 300; // Restar el oro correspondiente
                                break;
                            }
                        default:
                            System.out.println("El tipo de torre no es válido, intente nuevamente.");
                            continue;
                    }

                    // Colocar la torre en el mapa
                    System.out.println("Colocando torre en la posición: (" + row + ", " + columnChar + ")");
                    targetCell.setContent(newTower);
                    System.out.println("¡Torre colocada con éxito!");
                    towers.add(newTower);

                    validPosition = true;
                } else {
                    System.out.println("La celda seleccionada no está disponible para colocar una torre.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número para la fila.");
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida. Por favor, intente nuevamente.");
            }
        }
    }


    public void demolishTower(Map gameMap) {

        boolean validPosition = false;

        while (!validPosition) {
            try {
                System.out.print("Ingrese la fila (0-9): ");
                int row = Integer.parseInt(scanner.nextLine());

                System.out.print("Ingrese la columna (A-Z): ");
                char columnChar = scanner.nextLine().toUpperCase().charAt(0);

                // Validar fila y columna
                if (row < 0 || row >= Map.getROWS() || columnChar < 'A' || columnChar > 'Z') {
                    System.out.println("Posición inválida. Las filas deben ser entre 0-9 y las columnas entre A-Z.");
                    continue;
                }

                int col = columnChar - 'A'; // Convertir la letra de columna en un índice numérico

                // Verificar si la celda está disponible
                Cell targetCell = gameMap.getCell(row, col);
                if (targetCell.getContent() != null && targetCell.getContent() instanceof Tower) {

                    // Metodo para demoler la torre
                    this.gold += (((Tower) targetCell.getContent()).getCost() / 2);
                    this.towers.remove(targetCell.getContent());
                    targetCell.setContent(null);


                    System.out.println("¡Torre demolida con éxito!");

                    validPosition = true;
                } else {
                    System.out.println("La celda seleccionada no está disponible para demoler una torre.");
                }

            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, intente ingresando datos validos");
            }
        }
    }

    public void recicleTowers() {
        for (Tower presentTower : towers) {
            this.gold += presentTower.getCost();
        }
        towers.clear();
    }

}
