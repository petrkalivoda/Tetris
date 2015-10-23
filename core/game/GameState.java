package core.game;

import java.io.Serializable;
import java.util.Random;
import core.pieces.*;

/**
 * Trida reprezentujici aktualni stav hry
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public class GameState implements Serializable {
    
    public static int MATRIX_WIDTH = 0;
    public static int MATRIX_HEIGHT = 0;
    private int[][] gameMatrix;
    private IPiece currentPiece;
    private IPiece nextPiece;
    private boolean gameOver = false;
    private Random generator = new Random();
    private int piece_x = 0; //x-ova pozice kostky (leveho horniho rohu jeji matice)
    private int piece_y = 0; //y-ova pozice kostky
    private int level = 1;
    private int score = 0;


    public GameState() {
        MATRIX_WIDTH = 11;
        MATRIX_HEIGHT = 40;
        gameMatrix = new int[MATRIX_HEIGHT] [MATRIX_WIDTH];
    }

    public GameState(int width, int height) {
        MATRIX_WIDTH = width;
        MATRIX_HEIGHT = height;
        gameMatrix = new int[MATRIX_HEIGHT] [MATRIX_WIDTH];
    }

    /**
     * Nastavi uvodni pozici kosticky na ~stred hraci plochy
     */
    private void setInitialPiecePosition() {
        int y_ofset = Integer.MAX_VALUE;
        int x_ofset = Integer.MAX_VALUE;
        boolean[][] pieceMatrix = currentPiece.getMatrix();
        
        for (int y = 0; y < pieceMatrix.length; y++) {
            for (int x = 0; x < pieceMatrix[0].length; x++) {
                if(pieceMatrix[y][x]) {
                    x_ofset = x < x_ofset ? x : x_ofset;
                    y_ofset = y < y_ofset ? y : y_ofset;
                    break;
                }
            }
        }
        
        piece_x = ((gameMatrix[0].length/2)-1)-x_ofset;
        piece_y = -y_ofset;
    }

    /**
     * Provede udalost ve hre
     * @param code
     */
    public synchronized void doEvent(int code) {
       if(gameOver || !(currentPiece instanceof IPiece)) { //pojistka
           return;
       }
       switch(code)  {
           case Event.MOVE_LEFT: //pohyb kostickou doleva
               movePiece(-1, 0);
               break;

           case Event.MOVE_RIGHT: //pohyb kostickou doprava
               movePiece(1, 0);
               break;

           case Event.ROTATE: //rotace kosticky
               if(currentPiece instanceof IRotablePiece) {
                   IRotablePiece piece = (IRotablePiece)currentPiece;
                   piece.rotate(true);
                   if(!movePiece(0, 0)) {
                       //pokud se otocene nevejde, rotujeme zpet
                       piece.rotate(false);
                   }
               }
               break;
       }
    }

    /**
     * Jeden "tik" hry, pri kterem se hra posouva
     */
    public synchronized void tick() {
        if(!gameOver) {
            if(!(nextPiece instanceof IPiece)) {
                nextPiece = randomPiece();
            }

            removeFullLines();
            
            if(currentPiece instanceof IPiece) {
                if(!movePiece(0, 1)){
                    //kdyz uz se kosticka nemuze pohnout, prilepi se
                    stickCurrentPiece();
                    currentPiece = null;
                }
            }else {

                //pokud momentalne neni ve hre zadny piece
                currentPiece = nextPiece;
                nextPiece = randomPiece();
                setInitialPiecePosition();
                if(!movePiece(0, 0)) { //pouze refresh zobrazeni, zaroven kontrola jestli se vubec vejde
                    currentPiece = null;
                    nextPiece = null;
                    gameOver = true;
                }
            }
        }

    }

    /**
     * Pohne kostickou relativne oproti XY
     * @extra {0,0} pro pouze prekresleni kosticky, napr. pri rotaci
     * @param x
     * @param y
     * @return success/error
     */
    private synchronized boolean movePiece(int x, int y) {
        if(tryMovePiece(x, y)) {
            piece_x += x;
            piece_y += y;
            return true;
        }
        return false;
    }

    /**
     * zkousi, zda muze pohnout kostickou relativne optoti XY
     * @param x
     * @param y
     * @return
     */
    private synchronized boolean tryMovePiece(int x, int y) {
        boolean[][] pieceMatrix = currentPiece.getMatrix();
       
        for (int inner_y = 0; inner_y < pieceMatrix.length; inner_y++) { //zkusime x
            for (int inner_x = 0; inner_x < pieceMatrix[0].length; inner_x++) { //zkusime y
                if(!pieceMatrix[inner_y][inner_x]) {
                    //kosticka tady neni definovana
                    continue;
                }

                if((piece_y + y + inner_y) >= gameMatrix.length //nevejde se Y zdola
                   || (piece_y + y + inner_y) < 0 //nevejde se Y zhora
                   || (piece_x + x + inner_x) < 0 //nevejde se X zleva
                   || (piece_x + x + inner_x) >= gameMatrix[y].length //nevejde se X zprava
                   || gameMatrix[piece_y + y + inner_y][piece_x + x + inner_x] != 0) { //ma pod sebou kosticku

                    return false;
                }
            }
        }
        return true;
        
    }

    /**
     * Urcuje, zda je hra prohrana
     * @return
     */
    public boolean isOver() {
        return gameOver;
    }

    /**
     * Vraci nahodnou kostku
     * @return
     */
    private IPiece randomPiece() {
        IPiece piece = null;

        switch(generator.nextInt(7)){
            case 0: piece = new core.pieces.IPiece(); break;
            case 1: piece = new JPiece(); break;
            case 2: piece = new LPiece(); break;
            case 3: piece = new OPiece(); break;
            case 4: piece = new SPiece(); break;
            case 5: piece = new TPiece(); break;
            case 6: piece = new ZPiece(); break;
        }
        
        return piece;
    }

    /**
     * Přilepí aktuální kostičku do herní matice
     */
    private void stickCurrentPiece() {
        try {
            if(currentPiece instanceof IPiece) {
                int color = currentPiece.getColor();
                boolean[][] pieceMatrix = currentPiece.getMatrix();
                for (int y = 0; y < pieceMatrix.length; y++) {
                    for (int x = 0; x < pieceMatrix[0].length; x++) {
                        if(!pieceMatrix[y][x]) {
                            continue; //pokud na tomto ofsetu v kosticce nic neni, preskakujeme
                        }

                        gameMatrix[y+piece_y][x+piece_x] = color;
                    }

                }
            }
            
        } catch(ArrayIndexOutOfBoundsException e) {/*nic*/}
    }

    /**
     * Vraci kopii aktualni herni matice
     * @return
     */
    public int[][] getMatrix() {
        int[][] tempMatrix = new int[gameMatrix.length][gameMatrix[0].length],
                tempMatrix2 = new int[gameMatrix.length][gameMatrix[0].length];
        for (int y = 0; y < tempMatrix.length; y++) {
            System.arraycopy(gameMatrix[y], 0, tempMatrix[y], 0, gameMatrix[y].length);
        }

        //ulozeni, prohozeni
        stickCurrentPiece();
        tempMatrix2 = gameMatrix;
        gameMatrix = tempMatrix;

        
        return tempMatrix2;
    }

    /**
     * Vraci aktualni level
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     * Odstrani plné řádky.
     */
    private void removeFullLines() {
        int count = 0;
        boolean full;
        int[] tmprow = new int[gameMatrix[0].length];

        for (int y = gameMatrix.length-1; y >= 0; y--) {
            full = true;
            for (int x = 0; x < gameMatrix[0].length; x++) {
                if(gameMatrix[y][x] == 0) {
                    full = false;
                    break;
                }
            }

            if(full) {
                count++;
                for (int x = 0; x < gameMatrix[0].length; x++) {
                    gameMatrix[y][x] = 0;
                }
                
                gravityTick(y);
                y++;


            }
        }

        if(count > 0) {
            addScore(count);
        }
    }

    /**
     * Udela jeden tik naivni gravitace
     * @param line
     */
    private void gravityTick(int line) {
        for (int y = line; y > 0; y--) {
            gameMatrix[y] = gameMatrix[y-1];
        }

    }

    /**
     * @see http://tetris.wikia.com/wiki/Scoring
     * @param numLines
     */
    private void addScore(int numLines) {
        int basic;
        switch(numLines) {
            case 4: basic = 1200; break;
            case 3: basic = 300; break;
            case 2: basic = 100; break;
            default: basic = 40; break;
        }

        score += basic * level;

        if(score > (level * 2) * 1000) {
            level++;
        }
    }

    /**
     * Vraci skore
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Vraci zda je ve hre kosticka
     * @return
     */
    public boolean hasPiece() {
        return currentPiece instanceof IPiece;
    }

    /**
     * Vrací příští kostičku
     * @return
     */
    public IPiece getNextPiece() {
        return nextPiece;
    }
}