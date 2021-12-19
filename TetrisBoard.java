package eecs40.tetris;

import eecs40.util.Utility;
import java.util.ArrayList;
import java.util.Arrays;

public class TetrisBoard extends AbstractTetrisBoard {
    private ArrayList<TetrisObserver> observers = new ArrayList<TetrisObserver>();
    private int board_width;
    private int board_height;
    private static int border = 2;
    private int[][] initBoard;
    private int[][] actualShape = new int[4][4];
    private int addFlag;
    private int currentLine; // keep track of where blocks are
    private int[][] shapeLocation = new int[2][4];

    @Override
    public void init(int height, int width) {
        board_height = height;
        board_width = width;
        initBoard = new int[height+1][width+2];
        currentLine = height-1;

        for(int y = 0; y<height+1; y++){
            for(int x = 0; x<width+2; x++){
                if(y != height && (x == 0 || x == width+1)){
                    initBoard[y][x] = border;
                }
                else if(y == height){
                    initBoard[y][x] = border;
                }
            }
        }
    }

    @Override
    public void addShape(TetrisShape s) {
        //s is the random shape from enum class
        //StartingX = (width - 4)/2
        int StartingX = (board_width - 4) / 2;
        Utility.arrayCopy(s.getShapeArray(), actualShape);

        int rows = actualShape.length;

        //need to make the shape contain in the bottom 2 rows
        for (int i = 0; i < actualShape.length; i++){
            int j = actualShape[0][i];
            int k = actualShape[1][i];

            actualShape[0][i] = actualShape[rows-2][i];
            actualShape[rows-2][i] = j;

            actualShape[1][i] = actualShape[rows-1][i];
            actualShape[rows-1][i] = k;
        }
        //System.out.println(Arrays.deepToString(actualShape));

        //print first row of shape to board
        //start from StartingX
        int counter = 0;
        for (int n = StartingX+1; n<StartingX+4; n++){
            initBoard[0][n] = actualShape[3][counter];
            counter++;
        }
        addFlag = 1;
    }

    synchronized boolean isCollision() {
        /*Check if there is no collision if user tends to move left, right, down and shape rotation,
        when you want to move a shape to the left and right, you need ot make it impossible to move the boundary of the
        game board or any existing shapes that are already piled up. In the case of the moving down, the next figure should
        be ready when teh current shape meets the boundary or piled shapes. In the case of rotation, if the result of
        the rotation collides with an existing boundary or shape, it should not be rotated.
         */
        //need a way to keep track of moving shape
        //differentiate between 1's and 0's
       /* for(int y = 0; y<board_height+1; y++){
            for(int x = 0; x<board_width+2; x++) {
                if()
            }
        }*/
        return false;
    }

    public void check() {
        /*
        When user wants to movedown() or moveDownToEnd you need to check if there is line already completed. if one line
        is completed, you need to delete it
         */
        /*for(int i = 0; i<board_height; i++) {
            for (int j = 0; j < board_width; j++) {
                if (initBoard[i][j] == 1){

                }
            }
        }*/
    }

    @Override
    public synchronized void moveLeft() {

    }

    @Override
    public synchronized void moveRight() {

    }

    @Override
    public synchronized void moveDown() {
        int counter = 0;
        int StartingX = (board_width - 4) / 2;
        if(addFlag == 1){ // if op before was to add shape
            for (int n = StartingX+1; n<StartingX+4; n++){
                initBoard[0][n] = actualShape[2][counter];
                initBoard[1][n] = actualShape[3][counter];
                counter++;
            }
            addFlag = 0;
            return;
        }
        else {
            for(int y = 0; y<board_height-1; y++) { // operate within borders
                for (int x = 1; x < board_width+1; x++) {
                    if (currentLine == board_height-1){
                        for (int j = 0; j<currentLine; j++) {
                            initBoard[j+1][x] = 1;
                            //if(isCollision == true) check for if collision occurs
                            initBoard[j][x] = 0;
                        }
                    }
                    }
                }
            }
        //System.out.println(Arrays.deepToString(actualShape));
    }

    @Override
    public void moveDownToEnd() {
        for(int y = 0; y<board_height-1; y++) { // operate within borders
            for (int x = 1; x < board_width + 1; x++) {

            }
        }
    }

    @Override
    public void rotateLeft() {

    }

    @Override
    public int[][] getBoardArray() {
        return initBoard;
    }

    @Override
    public void addObserver(TetrisObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(TetrisObserver o) {
        observers.remove(o);
    }
}