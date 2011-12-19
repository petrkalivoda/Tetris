package core.pieces;

import core.game.IRotablePiece;
import core.game.MatrixUtils;

/**
 *
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public class JPiece implements IRotablePiece {

    /**
     * 00000
     * 00000
     * 01X10
     * 00010
     * 00000
     */
    private boolean[][] matrix = {
        {false, false, false, false, false},
        {false, true, true, true, false},
        {false, false, false, true, false},
        {false, false, false, false, false},
        {false, false, false, false, false},
    };


    public void rotate(boolean clockwise) {
        matrix = MatrixUtils.rotate(matrix, clockwise);
    }

    public int getColor() {
        return IPiece.BLUE;
    }

    public boolean[][] getMatrix() {
        return matrix;
    }

}
