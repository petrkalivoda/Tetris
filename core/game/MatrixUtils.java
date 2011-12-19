package core.game;

/**
 * Tool for rotating matrices
 * @author Petr Kalivoda <petr.kalivoda@gmail.com>
 */
public class MatrixUtils {

    /**
     * Rotates a matrix
     * @param matrix
     * @param clockwise
     * @return
     */
    public static boolean[][] rotate(boolean[][] matrix, boolean clockwise) {
        if(clockwise) {
            return rotateLeft(matrix);
        }else{
            return rotateRight(matrix);
        }
    }

    /**
     * Rotates a matrix 90 degrees clockwise
     * @param matrix
     * @return
     */
    public static boolean[][] rotateLeft(boolean[][] matrix) {
        boolean[][] new_matrix = new boolean[matrix[0].length][matrix.length];
        for(int y = new_matrix.length-1; y >=0; y--){
            for(int x = 0; x < new_matrix[0].length; x++) {
                new_matrix[y][new_matrix[0].length-1-x] = matrix[x][y];
            }
        }
        return new_matrix;
    }


    /**
     * Rotates a matrix 90 degrees counter-clockwise
     * @param matrix
     * @return
     */
    public static boolean[][] rotateRight(boolean[][] matrix) {
        boolean[][] new_matrix = new boolean[matrix[0].length][matrix.length];
        for(int y = new_matrix.length-1; y >=0; y--){
            for(int x = 0; x < new_matrix[0].length; x++) {
                new_matrix[y][x] = matrix[x][new_matrix.length-1-y];
            }
        }
        return new_matrix;
    }

    /**
     * Vypise matici do stdout.
     * @param matrix
     */
    public static void output(int[][] matrix) {
        String[][] retmatrix = new String[matrix.length][matrix[0].length];
        int maxlen, remainder;
        String retstring = "";

        for (int n = 0; n < retmatrix[0].length; n++) {
            maxlen = 0;
            for (int m = 0; m < retmatrix.length; m++) { //zjisteni nejvetsiho, zapsani prvku
                retmatrix[m][n] = ""+matrix[m][n];
                if(maxlen < retmatrix[m][n].length()) {
                    maxlen = retmatrix[m][n].length();
                }
            }

            for (int m = 0; m < retmatrix.length; m++) { //dorovnani mezer
                remainder = maxlen - retmatrix[m][n].length();
                for (int c = 0; c < remainder; c++) {
                    retmatrix[m][n] = " "+retmatrix[m][n];
                }
            }
        }

        for (int m = 0; m < retmatrix.length; m++) { //vytvoreni vysledneho stringu
            for (int n = 0; n < retmatrix[0].length; n++) {
                retstring += retmatrix[m][n] + " ";
            }
            retstring += "\n"; //odradkovani
        }
        System.out.println(retstring);
    }

    /**
     * Vypisuje boolean matici
     * @param matrix
     */
    public static void output(boolean[][] matrix) {
        int[][] newmatrix = boolToI(matrix);
        output(newmatrix);
    }

    /**
     * Konvertuje pole boolean[][] na integer
     * @param matrix
     * @return
     */
    public static int[][] boolToI(boolean[][] matrix) {
        int[][] newmatrix = new int[matrix.length][matrix[0].length];
        for (int x = 0; x < newmatrix.length; x++) {
            for (int y = 0; y < newmatrix[0].length; y++) {
                newmatrix[x][y] = matrix[x][y] ? 1 : 0;
            }
        }

        return newmatrix;
    }
}