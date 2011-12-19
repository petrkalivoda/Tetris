package core.window;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;
import javax.swing.JPanel;
import core.game.Game;
import java.awt.Font;
import core.game.IPiece;

/**
 *
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public class GameBoard extends JPanel{

   // private Timer timer; //casovac
    private GameThread thread;

    public GameBoard(JFrame parent) {

        Dimension size = new Dimension((Main.MATRIX_WIDTH+2) * (Main.SQUARE_SIZE+1),
                (Main.MATRIX_HEIGHT+1) * (Main.SQUARE_SIZE+1));
        setSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        //setBorder(BorderFactory.createLineBorder(Color.YELLOW, Main.BORDER_SIZE));
        setLayout(null);
        setFocusable(true);
        setBackground(Main.BACKGROUND_COLOR);
    }

    public void start() {
        thread = new GameThread(this);
        thread.run();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(Game.hasState()) {
            paintGameMatrix(g);
            paintNextPiece(g);
            paintScoreBoard(g);
            paintLevel(g);
            paintHighScore(g);
            paintBorderSquares(g);
        }
    }
    
    /**
     * Vypise dalsi piece
     * @param g
     */
    private void paintNextPiece(Graphics g) {
        IPiece nextPiece = Game.getNextPiece();
        if(nextPiece instanceof IPiece) {
            
            int left_ofset = (Main.MATRIX_WIDTH + 2) * (Main.SQUARE_SIZE + 1) + 40, top_ofset = 30;
            boolean[][] matrix = nextPiece.getMatrix();
            int color = nextPiece.getColor();

            //vypsani kosticek
            for (int y = 0; y < matrix.length; y++) {
                for (int x = 0; x < matrix[0].length; x++) {
                    g.setColor(matrix[y][x] ? new Color(color) : Main.BACKGROUND_COLOR);
                    g.fillRect(left_ofset + ((x - 1) * (Main.SQUARE_SIZE) + 1), top_ofset + (y * (Main.SQUARE_SIZE) + 1), Main.SQUARE_SIZE - 1, Main.SQUARE_SIZE - 1);

                }

            }

            //vypsani nadpisu
            Font font = new Font("Dialog", Font.BOLD, 24);
            g.setFont(font);
            g.setColor(Color.RED);
            g.drawString("NEXT:", left_ofset, top_ofset + 20);

        }
    }
    
    /**
     * Vypise skore
     * @param g
     */
    private void paintScoreBoard(Graphics g) {

        int left_ofset = (Main.MATRIX_WIDTH + 2) * (Main.SQUARE_SIZE + 1) + 40, top_ofset = 250;
        Font font = new Font("Dialog", Font.BOLD, 24);
        g.setFont(font);
        g.setColor(Color.RED);
        g.drawString("SCORE:", left_ofset, top_ofset);
        g.setColor(Color.YELLOW);
        g.drawString(String.format("%010d", Game.getScore()), left_ofset - 30, top_ofset + 40);
    }

    /**
     * Vykreslí highscore
     * @todo
     * @param g
     */
    private void paintHighScore(Graphics g) {
        int left_ofset = (Main.MATRIX_WIDTH + 2) * (Main.SQUARE_SIZE + 1) + 40, top_ofset = 504;
        Font font = new Font("Dialog", Font.BOLD, 24);
        g.setFont(font);
        g.setColor(Color.RED);
        g.drawString("HIGH SCORE:", left_ofset - 40, top_ofset);
        g.setColor(Color.YELLOW);
        g.drawString(String.format("%010d", Game.getHighScore()), left_ofset - 30, top_ofset + 40);
    }

    /**
     * Vypíše level
     * @param g
     */
    private void paintLevel(Graphics g) {
        int left_ofset = (Main.MATRIX_WIDTH + 2) * (Main.SQUARE_SIZE + 1) + 40, top_ofset = 380;
        Font font = new Font("Dialog", Font.BOLD, 24);
        g.setFont(font);
        g.setColor(Color.RED);
        g.drawString("LEVEL:", left_ofset, top_ofset);
        g.setColor(Color.YELLOW);
        g.drawString(String.format("%d", Game.getLevel()), left_ofset + 30, top_ofset + 40);
    }

    /**
     * Vykresli herni matici
     * @param g
     */
    private void paintGameMatrix(Graphics g) {
        int[][] gameMatrix = Game.getGameMatrix();
     //   MatrixUtils.output(gameMatrix);
        for (int y = 0; y < gameMatrix.length; y++) {
            for (int x = 0; x < gameMatrix[0].length; x++) {
                g.setColor(gameMatrix[y][x] != 0 ? new Color(gameMatrix[y][x]) : Main.BACKGROUND_COLOR);
                g.fillRect(x * Main.SQUARE_SIZE +Main.SQUARE_SIZE + 1,
                        y * Main.SQUARE_SIZE + Main.SQUARE_SIZE + 1,
                        Main.SQUARE_SIZE - 1, Main.SQUARE_SIZE - 1);
            }
        }

    }

    /**
     * Vykresluje okraje
     * @param g
     */
    private void paintBorderSquares(Graphics g) {
        //horizontalni okolo padajicich
        g.setColor(Main.BORDER_COLOR);
        for (int x = 0; x < Main.MATRIX_WIDTH + 2; x++) {
            g.fillRect(x * Main.SQUARE_SIZE + 1, 1, Main.SQUARE_SIZE - 1, Main.SQUARE_SIZE - 1);
            g.fillRect(x * Main.SQUARE_SIZE + 1, (Main.MATRIX_HEIGHT + 1) * Main.SQUARE_SIZE + 1, Main.SQUARE_SIZE - 1, Main.SQUARE_SIZE - 1);

        }

        //vertikalni okolo padajicich
        for (int y = 1; y <= Main.MATRIX_HEIGHT; y++) {
            g.fillRect(1, y * Main.SQUARE_SIZE + 1, Main.SQUARE_SIZE -1, Main.SQUARE_SIZE -1);
            g.fillRect((Main.MATRIX_WIDTH + 1) * Main.SQUARE_SIZE + 1, y * Main.SQUARE_SIZE + 1, Main.SQUARE_SIZE -1, Main.SQUARE_SIZE -1);

        }

        //vertikalni okolo infa
        for (int x = 0; x < 8; x++) {
            g.fillRect((Main.MATRIX_WIDTH + 2) * (Main.SQUARE_SIZE) + (x * Main.SQUARE_SIZE + 1),
                    1, Main.SQUARE_SIZE - 1, Main.SQUARE_SIZE - 1);

            g.fillRect((Main.MATRIX_WIDTH + 2) * (Main.SQUARE_SIZE) + (x * Main.SQUARE_SIZE + 1),
                    8 * Main.SQUARE_SIZE + 1, Main.SQUARE_SIZE - 1, Main.SQUARE_SIZE - 1);

            g.fillRect((Main.MATRIX_WIDTH + 2) * (Main.SQUARE_SIZE) + (x * Main.SQUARE_SIZE + 1),
                    13 * Main.SQUARE_SIZE + 1, Main.SQUARE_SIZE - 1, Main.SQUARE_SIZE - 1);

            g.fillRect((Main.MATRIX_WIDTH + 2) * (Main.SQUARE_SIZE) + (x * Main.SQUARE_SIZE + 1),
                    18 * Main.SQUARE_SIZE + 1, Main.SQUARE_SIZE - 1, Main.SQUARE_SIZE - 1);

            g.fillRect((Main.MATRIX_WIDTH + 2) * (Main.SQUARE_SIZE) + (x * Main.SQUARE_SIZE + 1),
                    23 * Main.SQUARE_SIZE + 1, Main.SQUARE_SIZE - 1, Main.SQUARE_SIZE - 1);


        }

        //horizontalni okolo infa
        for (int y = 1; y <= 23; y++) {
            g.fillRect((Main.MATRIX_WIDTH + 2) * (Main.SQUARE_SIZE) + (7 * Main.SQUARE_SIZE + 1),
                    y * Main.SQUARE_SIZE + 1, Main.SQUARE_SIZE - 1, Main.SQUARE_SIZE - 1);

        }

    }

}
