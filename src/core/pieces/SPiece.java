package core.pieces;

import core.game.IRotablePiece;
import core.game.MatrixUtils;

/**
 *
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public class SPiece implements IRotablePiece {

    private boolean[][] matrix = {
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, true, true, false},
        {false, true, true, false, false},
        {false, false, false, false, false},
    };

    public boolean[][] getMatrix() {
        return matrix;
    }

    public int getColor() {
        return IPiece.MAGENTA;
    }

    public void rotate(boolean clockwise) {
        matrix = MatrixUtils.rotate(matrix, clockwise);
    }


}
