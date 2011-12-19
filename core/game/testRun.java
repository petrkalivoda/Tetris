package core.game;
import java.util.Random;
/**
 *
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public class testRun {
    public static void main(String[] args) {
        Random rand = new Random();
        int[][] matrix;
        GameState gs = new GameState();
        Game.resetState(gs);
        gs.tick();
        while(!gs.isOver()) {
            int code, times;
            code = rand.nextInt(3);
            switch(code) {
                case 0:
                    times = rand.nextInt(5);
                    for (int i = 0; i < times; i++) {
                        gs.doEvent(Event.MOVE_LEFT);
                    }
                    break;

                case 1:
                    times = rand.nextInt(5);
                    for (int i = 0; i < times; i++) {
                        gs.doEvent(Event.MOVE_RIGHT);
                    }
                    break;

                case 2:
                    gs.doEvent(Event.ROTATE);
                    break;

            }
            gs.tick();
        }


        matrix = gs.getMatrix();
        MatrixUtils.output(matrix);

    }
}
