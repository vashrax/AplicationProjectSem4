package application;

import java.awt.*;

/**
 * Program okienkowy storzony w ramach Projektu - Projektowanie Aplikacji.
 * Informatyka Niestacjonarne sem. 4
 *
 * @author Grzegorz Ciosek (VashRaX)
 * @version 1.0.0
 *
 */

public class Application {

    /**
     * Metoda main wywołująca konstruktor bezwarunkowy - uruchomienie GUI.
     *
     * @param args ---
     */

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ApplicationGui window = new ApplicationGui();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
