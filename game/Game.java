/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Булдова А.В.
 */
public class Game extends JFrame {

    private static Game game_game;
    private static long last_frame_time;
    private static Image tree;
    private static Image snow;
    private static Image end;
    private static float drop_left = 200;
    private static float drop_top = -100;
    private static float drop_v = 200;
    private static int score = 0;

    public static void main(String[] args) throws IOException {
        tree = ImageIO.read(Game.class.getResourceAsStream("elka.png"));
        snow = ImageIO.read(Game.class.getResourceAsStream("cnezh.png"));
        end = ImageIO.read(Game.class.getResourceAsStream("gg.png"));
        game_game = new Game();
        game_game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_game.setLocation(200, 50);
        game_game.setSize(1200, 800);
        game_game.setResizable(false);
        last_frame_time = System.nanoTime();
        GameField game_field = new GameField();
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float drop_right = drop_left + end.getWidth(null);
                float drop_bottom = drop_top + end.getHeight(null);
                boolean is_drop = x >= drop_left && x <= drop_right && y >= drop_top && y <= drop_bottom;

                if (is_drop) {
                    drop_top = -100;
                    drop_left = (int) (Math.random() * (game_field.getWidth() - end.getWidth(null)));
                    drop_v = drop_v + 10;
                    score++;
                    game_game.setTitle("Score: " + score);
                }
            }
        });
        game_game.add(game_field);
        game_game.setVisible(true);
    }

    private static void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float delt_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
        drop_top = drop_top + drop_v * delt_time;

        // Calculate scaling factors to fit images to the screen
        double scaleX = (double) game_game.getWidth() / tree.getWidth(null);
        double scaleY = (double) game_game.getHeight() / tree.getHeight(null);

        // Draw the tree scaled to fit the screen
        g.drawImage(tree, 0, 0, (int) (tree.getWidth(null) * scaleX), (int) (tree.getHeight(null) * scaleY), null);

        // Draw the snow scaled to fit the screen
        g.drawImage(snow, (int) (drop_left * scaleX), (int) (drop_top * scaleY), (int) (end.getWidth(null) * scaleX), (int) (end.getHeight(null) * scaleY), null);

        // Draw the end screen scaled to fit the screen if necessary
        if (drop_top > game_game.getHeight()) {
            g.drawImage(end, 0, 0, game_game.getWidth(), game_game.getHeight(), null);
        }
    }

    private static class GameField extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
