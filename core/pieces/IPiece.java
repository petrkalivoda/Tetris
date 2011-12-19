package core.pieces;

import core.game.MatrixUtils;

/**
 *
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public class IPiece implements core.game.IRotablePiece{

    /**
     * 00000
     * 00000
     * 01X11
     * 00000
     * 00000
     */
    private boolean[][] matrix = {
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, true, true, true, true},
        {false, false, false, false, false},
        {false, false, false, false, false},

    };

    private boolean rotated = false;


    public int getColor() {
        return IPiece.RED;
    }

    public boolean[][] getMatrix() {
        return matrix;
    }

    public void rotate(boolean clockwise) {
        matrix = MatrixUtils.rotate(matrix, !rotated);
        rotated = !rotated;
    }
        
}