package dp;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Graphics;  //для графики

/* @author Булдова Александра */
public class gmee extends JFrame {
    private static gmee game_game;

    public static void main(String[] args) {
        game_game = new gmee();
        game_game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_game.setLocation(200, 50);
        game_game.setSize(900, 600);
        game_game.setResizable(false);
        
        GameField game_field = new GameField();
        game_game.add(game_field);
        game_game.setVisible(true);
    }

    public static void onRepaint(Graphics g) {
        g.fillOval(10, 10, 200, 100);
    }

    public static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Исправлено на super.paintComponent(g);
            onRepaint(g);
        }
    }
}