package core.game;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Trida reprezentujici instanci hry
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public class Game {
    
    private static GameState state;
    private static LinkedList<Integer> eventQueue = new LinkedList<Integer>();
    private static boolean paused = false;
    private static int speedMultiplier = 1;
    public static final double SPEED_PRESCALER = 0.75;
    public static final long TICK_INTERVAL = 666; //500ns pro level 1.
    private static boolean forceNew = true;
    private static int highScore = 0; //nejvyssi skore
    private static final String SCORE_FILENAME = "score.dat";

    /**
     * Statickej konstruktor načte highscore ze souboru
     */
    static {
        File file = new File(SCORE_FILENAME);
        if(file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                BASE64Decoder decoder = new BASE64Decoder();
                highScore = Integer.parseInt(new String(decoder.decodeBuffer(br.readLine())));

            }catch(java.io.IOException e) {/* nic */}
        }
    }

    /**
     * Vycisti high score
     */
    public static void clearHighScore() {
        highScore = 0;
        File file = new File(SCORE_FILENAME);
        if(file.exists()) {
            file.delete();
        }
        
    }


    /**
     * Resetuje hru do predaneho stavu
     * @param state Stav hry
     */
    public static void resetState(GameState state) {
        eventQueue.clear();
        Game.state = state;
        speedMultiplier = 1;
        state.tick();
    }

    /**
     * Vraci herni matici
     * @return
     * @throws IllegalStateException
     */
    public static int[][] getGameMatrix() throws IllegalStateException {
        if(state instanceof GameState) {
            return state.getMatrix();
        }

        throw new IllegalStateException("Not in a game.");
    }

    /**
     * Po padnuti kostky se musi resetnout multiplier.
     */
    public static void resetMultiplier() {
        speedMultiplier = 1;
    }

    /**
     * Prejme herni udalost a provede s ni neco
     * @param code
     */
    public static void sendEvent(int code) {
        switch(code) {
            case Event.PAUSE:
                paused = !paused;
                break;

            case Event.DROP:
                speedMultiplier = 10;
                break;

            case Event.STOP_DROP:
                speedMultiplier = 1;
                break;

            default:
                eventQueue.addLast(code);
                break;
        }
        
    }

    /**
     * Udela jeden event z fronty (pokud tam nejaky je)
     */
    public static void doEvent() {
        if(state instanceof GameState && !eventQueue.isEmpty()) {
            state.doEvent(eventQueue.removeFirst());
        }
    }


    /**
     * Vrací zda je hra pauzlá.
     * @return
     */
    public static boolean isPaused() {
        return paused;
    }

    /**
     * Vraci tick interval
     * @return
     */
    public static float getTickInterval() {
        if(state instanceof GameState) {
            return (TICK_INTERVAL * ((float)SPEED_PRESCALER/state.getLevel())) / speedMultiplier;

        }else return TICK_INTERVAL / speedMultiplier;
    }

    /**
     * Vrací level
     * @return
     */
    public static int getLevel() {
        if(state instanceof GameState) {
            return state.getLevel();
        }

        return 0;
    }

    /**
     * Vraci nasledujici kosticku
     * @return
     */
    public static IPiece getNextPiece() {
        return state.getNextPiece();
    }

    /**
     * Vrací aktuální skóre
     * @return
     */
    public static int getScore() {
        if(state instanceof GameState) {
            if(state.getScore() > highScore) {
                highScore = state.getScore();
            }
            return state.getScore();
        }
        
        return 0;
    }

    /**
     * Vrací highskóre
     * @return
     */
    public static int getHighScore() {
        return highScore;
    }

    /**
     * Vraci jestli existuje state
     * @return
     */
    public static boolean hasState() {
        return state instanceof GameState;

    }

    /**
     * Kontroluje zda hra skoncila
     * @return
     */
    public static boolean isOver() {
        return state.isOver();
    }

    /**
     * Provede tik hry
     */
    public static void tick() {
        if(state instanceof GameState) {
            state.tick();
        }
    }

    public static void forceNew() {
        forceNew = true;

    }

    /**
     * Vynucovani nove hry
     * @return
     */
    public static boolean isForced() {
        return forceNew;
    }

    public static void forceCaught() {
        forceNew = false;
    }

    /**
     * Zapíše high score do souboru
     */
    public static void writeHighScore() {
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode(String.valueOf(highScore).getBytes());
        try {
            FileWriter fw = new FileWriter("score.dat");
            fw.write(encoded);
            fw.flush();
            fw.close();
        }catch(java.io.IOException e) { /* nepovedlo */ }

    }
}
