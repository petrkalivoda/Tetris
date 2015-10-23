package core.game;

/**
 * A piece that can be rotated
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public interface IRotablePiece extends IPiece {

    /**
     * Rotate the piece one step possible
     */
    public void rotate(boolean clockwise);

}
