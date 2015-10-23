package core.game;

/**
 * Generic piece interface
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public interface IPiece {
    public final int GREEN = 0x00FF00;
    public final int CYAN = 0x00FFFF;
    public final int MAGENTA = 0xFF00FF;
    public final int YELLOW = 0xFFFF00;
    public final int ORANGE = 0xFFA500;
    public final int BLUE = 0x0000FF;
    public final int RED = 0xFF0000;

    /**
     * 
     * @return the Color of a piece
     */
    public int getColor();

    /**
     * 
     * @return the piece's matrix
     */
    public boolean[][] getMatrix();


}
