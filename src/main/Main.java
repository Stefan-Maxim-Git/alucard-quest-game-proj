package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame Window = new JFrame();
        ContextSettings gp = new ContextSettings();

        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      // Properly closes the game when X is pressed
        Window.setResizable(false);                                 // Non-resizable window (might be changed in the future if game is fullscreen)
        Window.setTitle("Alucard Quest");                           // Window Title: Alucard Quest
        Window.add(gp);
        Window.pack();
        Window.setLocationRelativeTo(null);                         // Centers current window
        Window.setVisible(true);                                    // Visible window

        gp.startMainThread();
    }
}