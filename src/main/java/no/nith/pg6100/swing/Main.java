package no.nith.pg6100.swing;

import javax.swing.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Klasse som kjører Swing applikasjonen.
 */
public class Main {

    private static void visGUI() {
        JFrame frame = new JFrame("PG6100");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        MainWindow contentPane=new MainWindow();
        contentPane.setOpaque(true);
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                visGUI();
            }
        });
    }
}
