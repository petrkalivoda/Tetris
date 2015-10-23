package core.game;

/**
 * Obaluje konstanty pro jednotlive udalosti
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public interface Event {
    public static final int ROTATE = 0; //rotace kostky
    public static final int DROP = 1; //zrychlene padani
    public static final int MOVE_LEFT = 2; //pohyb doleva
    public static final int MOVE_RIGHT = 3; //pohyb doprava
    public static final int PAUSE = 4; //pauza
    public static final int STOP_DROP = 5; //konec rychleho padani
}
