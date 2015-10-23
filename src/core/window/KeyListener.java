package core.window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import core.game.Game;
import core.game.Event;

/**
 *
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public class KeyListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch(code) {
            case KeyEvent.VK_LEFT:
                Game.sendEvent(Event.MOVE_LEFT);
                break;

            case KeyEvent.VK_RIGHT:
                Game.sendEvent(Event.MOVE_RIGHT);
                break;

            case KeyEvent.VK_DOWN:
                Game.sendEvent(Event.DROP);
                break;

            case KeyEvent.VK_UP:
                Game.sendEvent(Event.ROTATE);
                break;

            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_PAUSE:
                Game.sendEvent(Event.PAUSE);
                break;

            case KeyEvent.VK_F2:
                Main.newGameDialog();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            Game.sendEvent(Event.STOP_DROP);
        }

    }
}
