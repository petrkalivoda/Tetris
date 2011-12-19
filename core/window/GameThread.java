package core.window;
import core.game.Game;
import core.game.GameState;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public class GameThread extends Thread {
    
    JPanel parent;

    public GameThread(JPanel parent) {
        this.parent = parent;
        parent.addKeyListener(new KeyListener());
    }
    
    @Override
    public void run() {
        while(true) {
            if(Game.isForced()) {
                Game.forceCaught();
                GameState state = new GameState(Main.MATRIX_WIDTH, Main.MATRIX_HEIGHT);
                Game.resetState(state);
                long startTime, endTime = 0;

                while(!Game.isOver() && !Game.isForced()) {
                    startTime = Calendar.getInstance().getTimeInMillis();

                    while((endTime - startTime) < Game.getTickInterval() && !Game.isForced()) {
                        try {
                            if(!Game.isPaused()) {
                                Game.doEvent();
                                sleep(15);
                                endTime = Calendar.getInstance().getTimeInMillis();
                                parent.repaint();
                            }else {
                                sleep(100);
                            }
                        }catch(java.lang.InterruptedException e) {}

                    }

                    if(!Game.isPaused()) {
                        Game.tick();
                    }

                }

                if(!Game.isForced()) {
                    JOptionPane.showMessageDialog(parent, "KONEC HRY!");
                }
            }

            try {
                sleep(1000);
            }catch(java.lang.InterruptedException e) {}
        }

    }

}
