package core.pieces;

/**
 *
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public class OPiece implements core.game.IPiece {

    private boolean[][] matrix = {
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, true, true, false},
        {false, false, true, true, false},
        {false, false, false, false, false},
    };

    public boolean[][] getMatrix() {
        return matrix;
    }

    public int getColor() {
        return IPiece.YELLOW;
    }

}