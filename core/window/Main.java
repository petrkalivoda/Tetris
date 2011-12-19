package core.window;
import core.game.Game;
import core.game.Event;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

/**
 *
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public class Main extends JFrame{

    public static final int MATRIX_WIDTH = 10; //sirka pole v kostickach
    public static final int MATRIX_HEIGHT = 22; //vyska pole v kostickach

    public static final int SQUARE_SIZE = 25; //velikost jednoho ctverecku v pixelech
    private GameBoard board;

    public static final Color BACKGROUND_COLOR = Color.BLACK; //barva pozadi
    public static final Color BORDER_COLOR = Color.GRAY; //barva okraje


    public Main() {
        super("Tetris");
        setSize((MATRIX_WIDTH+2) * (SQUARE_SIZE) + 207, (MATRIX_HEIGHT+2) * (SQUARE_SIZE+1) + 26);
        setResizable(false);
        board = new GameBoard(this);
        JOptionPane.setDefaultLocale(new Locale("cs", "CZ"));
        add(board);
        initMenu();

        //odchyti ukonceni hry
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitDialog();
            }
        });
    }

    public void start() {
        board.start();
    }

    /**
     * Vytvori mainmenu
     */
    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu game = new JMenu("Hra");
        menuBar.add(game);
        JMenuItem newGame = new JMenuItem("Nová hra");
        JMenuItem exitGame = new JMenuItem("Ukončit");
        JMenuItem clearScore = new JMenuItem("Vymazat high score");
        game.add(newGame);
        game.add(clearScore);
        game.addSeparator();
        game.add(exitGame);

        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent action) {
                newGameDialog();
            }
        });

        clearScore.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent action) {
                boolean paused = Game.isPaused();
                if(!paused) {
                    Game.sendEvent(Event.PAUSE); // pauza hry
                }

                int response = JOptionPane.showConfirmDialog(null, "Opravdu chcete vymazat high score?",
                        "Varování", JOptionPane.YES_NO_OPTION);

                if(response == 0) {
                    Game.clearHighScore();
                    board.repaint();
                }

                if(!paused) {
                    Game.sendEvent(Event.PAUSE); //odpauza
                }
            }
        });
        
        exitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent action) {
                exitDialog();
            }
        });
    }

    /**
     * Dialog pro ukončení hry
     */
    public static void exitDialog() {
        boolean paused = Game.isPaused();
        if(!paused) {
            Game.sendEvent(Event.PAUSE); // pauza hry
        }

        int response = JOptionPane.showConfirmDialog(null, "Opravdu chcete ukončit hru?",
                "Varování", JOptionPane.YES_NO_OPTION);

        if(response == 0) { //konec hry
            Game.writeHighScore();
            System.exit(0);
        }

        if(!paused) {
            Game.sendEvent(Event.PAUSE); // odpauza
        }
    }

    /**
     * Hodí dialog pro novou hru
     */
    public static void newGameDialog() {
        boolean paused = Game.isPaused();
        if(!paused) {
            Game.sendEvent(Event.PAUSE);
        }
        int response = JOptionPane.showConfirmDialog(null, "Opravdu chcete začít novou hru?",
                "Varování", JOptionPane.YES_NO_OPTION);

        if(response == 0) {
            Game.forceNew();
        }

        if(!paused) {
            Game.sendEvent(Event.PAUSE);
        }
    }


    public static void main(String[] args) {
        Main frame = new Main();
        frame.setVisible(true);
        frame.start();

    }
}
