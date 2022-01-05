package pacman;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;

/**
 * In this class, we only describe the wall and gums positions.
 * Entities's positions are managed by the GameManager directly into the Entities.
 *
 * Thus : the only valid values in the matrix are :
 * 0 = empty
 * 1 = wall
 * 20 = gum
 */

public class Board {

    //===============// Static attributes //===============//

    public static int BOARD_SIZE_X = 29;
    public static int BOARD_SIZE_Y = 26;

        //Values to put into the int[][] currentBoard
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int GUM = 20;

        //Kind of enum to indicate direction relatively to a tile
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    //===============// Instance attributes //===============//

    private int nbGumLeft;
    
        //Boards
    private int[][] currentBoard;   //The one used everytime...
    private int[][] initialBoard;   //... is initially saved here, to restart easily a game with the save board
    private int[][] testBoard = {   { GUM,      GUM,    GUM,    GUM,        GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    WALL,   WALL,   GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      GUM,    GUM,    GUM,        GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      GUM,    GUM,    GUM,        GUM,    GUM,    WALL,   WALL,   GUM,    GUM,    GUM,    GUM,    WALL,   WALL,   GUM,    GUM,    GUM,    GUM,    WALL,   WALL,   GUM,    GUM,    GUM,    GUM,    GUM,    GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   EMPTY,  WALL,   WALL,   EMPTY,  WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   EMPTY,  WALL,   WALL,   EMPTY,  WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   EMPTY,  WALL,   WALL,   WALL,   EMPTY,  EMPTY,  WALL,   WALL,   WALL,   EMPTY,  WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   EMPTY,  WALL,   EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  WALL,   EMPTY,  WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      GUM,    GUM,    GUM,        GUM,    GUM,    GUM,    EMPTY,  EMPTY,  WALL,   EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  WALL,   EMPTY,  EMPTY,  GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   EMPTY,  WALL,   EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  WALL,   EMPTY,  WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   EMPTY,  WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   EMPTY,  WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  EMPTY,  WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   EMPTY,  WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   EMPTY,  WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   EMPTY,  WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   EMPTY,  WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      GUM,    GUM,    GUM,        GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    WALL,   WALL,   GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      GUM,    GUM,    WALL,       WALL,   GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    WALL,   WALL,   GUM,    GUM,    GUM},
                                    { WALL,     WALL,   GUM,    WALL,       WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL},
                                    { WALL,     WALL,   GUM,    WALL,       WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL},
                                    { GUM,      GUM,    GUM,    GUM,        GUM,    GUM,    WALL,   WALL,   GUM,    GUM,    GUM,    GUM,    WALL,   WALL,   GUM,    GUM,    GUM,    GUM,    WALL,   WALL,   GUM,    GUM,    GUM,    GUM,    GUM,    GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      WALL,   WALL,   WALL,       WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   GUM,    WALL,   WALL,   GUM,    WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   WALL,   GUM},
                                    { GUM,      GUM,    GUM,    GUM,        GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM,    GUM},
    };  //An hard-coded board, to test our solver without to focus on coding a board generator

    // GameManager is used to get Entities' coordinates, to make the toString method
    private GameManager gm;

    //===============// Getters and setters //===============//

    /*
    @warning : If the solver gets too slow, it can be interesting for the getCurrentBoard() method not to return
    a copy of the board but directly the board
    */
    public int[][] getCurrentBoard(){
        return copyMatrix(currentBoard);
    }

    public int getNbGumLeft(){
        return this.nbGumLeft;
    }

    public void setGameManager(GameManager gameManager){
        this.gm = gameManager;
    }

    //===============// Constructors //===============//

    public Board(boolean testBoard, GameManager gameManager){

        if(testBoard){
            //load the test board into the current one
            BOARD_SIZE_Y = this.testBoard[0].length;
            BOARD_SIZE_X = this.testBoard.length;
            this.currentBoard = copyMatrix(this.testBoard);

        }else{
            try {
                this.currentBoard = generateRandomBoard();
                BOARD_SIZE_Y = this.currentBoard[0].length;
                BOARD_SIZE_X = this.currentBoard.length;
            } catch (ExecutionControl.NotImplementedException e) {
                e.printStackTrace();
            }
        }
        //save this board into initialBoard (to restart game easier)
        this.initialBoard = copyMatrix(this.currentBoard);

        this.nbGumLeft = this.countGum();

        this.gm = gameManager;
    }

    public Board(int[][] tab, GameManager gameManager){

        //load the given board into the current one
        this.currentBoard = copyMatrix(tab);

        //save this board into initialBoard (to restart game easier)
        this.initialBoard = copyMatrix(this.currentBoard);

        BOARD_SIZE_Y = this.currentBoard[0].length;
        BOARD_SIZE_X = this.currentBoard.length;

        this.nbGumLeft = this.countGum();

        this.gm = gameManager;
    }

    public Board(int[][] tab){

        //load the given board into the current one
        this.currentBoard = copyMatrix(tab);

        //save this board into initialBoard (to restart game easier)
        this.initialBoard = copyMatrix(this.currentBoard);

        BOARD_SIZE_Y = this.currentBoard[0].length;
        BOARD_SIZE_X = this.currentBoard.length;

        this.nbGumLeft = this.countGum();

        this.gm = null;
    }

    //===============// Public methods //===============//

    public void resetBoard(){

        //Copies the initial board into the current one
        currentBoard = copyMatrix(initialBoard);
    }

    public boolean isWall(Coordinates c){
        int i = c.getX();
        int j = c.getY();

        return (this.currentBoard[i][j] == 1);
    }

    public boolean isOutOfBounds(Coordinates c){
        int i = c.getX();
        int j = c.getY();

        return(i >= BOARD_SIZE_X || j >= BOARD_SIZE_Y || i<0 || j<0);
    }

    public boolean isGum(Coordinates c){
        int i = c.getX();
        int j = c.getY();

        return (this.currentBoard[i][j] == 20);
    }

    public boolean isWalkable(Coordinates c){
        return ((!isOutOfBounds(c)) && (!isWall(c)));
    }

    //which tiles are walkable, given a coordinate
    public List<Integer> getWalkableNeighbours(Coordinates c) throws Exception{
        List<Integer> res = new ArrayList<>();
        int x = c.getX();
        int y = c.getY();

        if(isWalkable(new Coordinates(x-1,y))){
            res.add(UP);
        }
        if(isWalkable(new Coordinates(x+1,y))){
            res.add(DOWN);
        }
        if(isWalkable(new Coordinates(x,y-1))){
            res.add(LEFT);
        }
        if(isWalkable(new Coordinates(x,y+1))){
            res.add(RIGHT);
        }

        if(res.isEmpty()){
            throw new Exception("Board.getWalkableNeighbours() : No neighbour walkable");
        }

        return res;
    }

    public int removeGum(Coordinates c) throws Exception{
        int x = c.getX();
        int y = c.getY();

        if(!(this.isWalkable(c)) || this.currentBoard[x][y] != GUM){
            throw new Exception("EXCEPTION : Specified coordinates aren't pointing to a GUM !");
        }

        this.currentBoard[x][y] = EMPTY;
        this.nbGumLeft--;

        return nbGumLeft;
    }

    //===============// Useful tools (private methods) //===============//

    private int[][] generateRandomBoard() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("EXCEPTION : generateRandomBoard not yet implemented");
    }

    private static int[][] copyMatrix(int[][] tab){
        int[][] res = new int[BOARD_SIZE_X][BOARD_SIZE_Y];

        for (int i =0 ; i<BOARD_SIZE_X ; i++){
            for (int j = 0 ; j < BOARD_SIZE_Y ; j++){
                res[i][j] = tab[i][j];
            }
        }
        return res;
    }

    private int countGum(){
        int res = 0;

        for(int i = 0 ; i < BOARD_SIZE_X ; i++){
            for (int j = 0 ; j < BOARD_SIZE_Y ; j++){
                if(this.currentBoard[i][j] == GUM)
                    res++;
            }
        }
        return res;
    }

    private String convertIntToString(int val){
        switch (val){
            case 0:
                return " ";     // Empty tile


            case 1:
                return "+";     // Wall


            case 20:
                return ".";     // Tile with a gum

            default:
                return "UNKNOWN";
        }
    }

    //===============// Inherited from Object class //===============//

    // 2 boards are equal if and only if their tiles are identical.
    public boolean equals(Board b){
        for(int i = 0 ; i < BOARD_SIZE_X ; i++){
            for (int j = 0 ; j < BOARD_SIZE_Y ; j++){
                if(b.currentBoard[i][j] != this.currentBoard[i][j])
                    return false;
            }
        }
        return true;
    }

    // Gives a textual representation of the board, taking GameManager's Entities into account.
    public String toString(){
        if(gm!=null){
            String res = "-------------------------------------------------------------------------------------------------------------\n";     // The top boundary of the board

            Coordinates pacmansCoord = gm.getPacman().getCoordinates(); // Pacman's position on the board

            for(int i = 0 ; i < BOARD_SIZE_X ; i++){

                res += "|\t";   //The beginning of a row

                for (int j = 0 ; j <= BOARD_SIZE_Y-1 ; j++){

                    Coordinates currentCoord = new Coordinates(i,j);

                    //if the current tile parsed on the board is Pacman
                    if(currentCoord.equals(pacmansCoord)){
                        res += "P";
                    }
                    //if the current tile holds a Phantom
                    else if(gm.getPhantomCoordinatesList().contains(currentCoord)){
                        Phantom CurrentPhantom = null;
                        for (Entity phantom:(gm.getPhantomList())) {
                            if(phantom.getCoordinates().equals(currentCoord))CurrentPhantom = (Phantom) phantom;
                        }
                        res += CurrentPhantom.toString();
                    }
                    //elsewhere, it is a wall, a gum or just an empty tile
                    else{
                        res += convertIntToString(this.currentBoard[i][j]);
                    }

                    res += "\t";
                }

                res += "|\n";           // the ending of a row
            }
            res += "-------------------------------------------------------------------------------------------------------------";         // The bottom boundary of the board
            return res;
        }
        return "ERROR : THE GAME MANAGER IS NULL => USE THE SURCHARGE";
    }

    // Gives a textual representation of the board, taking the given Entities into account.
    public String toString(Coordinates pacmanPos, List<Coordinates> phantomsPos ){

        String res = "-------------------------------------\n";     // The top boundary of the board

        Coordinates pacmansCoord = pacmanPos; // Pacman's position on the board

        for(int i = 0 ; i < BOARD_SIZE_X ; i++){

            res += "|\t";   //The beginning of a row

            for (int j = 0 ; j <= BOARD_SIZE_Y-1 ; j++){

                Coordinates currentCoord = new Coordinates(i,j);

                //if the current tile parsed on the board is Pacman
                if(currentCoord.equals(pacmansCoord)){
                    res += "P";
                }
                //if the current tile holds a Phantom
                else if(phantomsPos.contains(currentCoord)){
                    res += "f";
                }
                //elsewhere, it is a wall, a gum or just an empty tile
                else{
                    res += convertIntToString(this.currentBoard[i][j]);
                }

                res += "\t";
            }

            res += "|\n";           // the ending of a row
        }
        res += "-------------------------------------";         // The bottom boundary of the board
        return res;
    }

    //===============// MAIN ( TEST METHOD ) //===============//
    public static void main(String[] args) {
        /*GameManager gm = new GameManager();
        gm.initGame(); // instantiation de pacman, des fantômes et du board

        Board b1 = new Board(true, gm);*/

        /*int[][] matrix =  {   { 0,    20,     1,      0,      1,      20,     20,     0 },
                { 1,    20,     20,     0,      20,     1,      1,      0 },
                { 1,    1,      20,     1,      20,     20,     20,     20},
                { 20,   20,     20,     1,      0,      20,     1,      20},
                { 1,    20,     1,      1,      1,      20,     0,      1 },
                { 20,   20,     20,     20,     0,      0,      0,      0 },
                { 20,   1,      1,      0,      1,      1,      1,      20 },
                { 20,   20,     20,     20,     20,     20,     1,      0 }
        };*/

        //Board b2 = new Board(matrix);

        //System.out.println("Is b1 equal to b2 ? " + b1.equals(b2));

        //System.out.println(b2.toString());

        //Coordinates coordinates = new Coordinates(0,2);
        //System.out.println(b1.isWalkable(coordinates));

        //Board b3 = new Board(b1.getCurrentBoard());
        //System.out.println(b1.equals(b3));

        //b1.currentBoard[0][1] = 0;
        //System.out.println(b1);

        //b1.resetBoard();
        //System.out.println(b1);

        /*System.out.println(b1);
        System.out.println(b1.getNbGumLeft());
        try {
            System.out.println(b1.removeGum(new Coordinates(0,1))); //cas walkable
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(b1.removeGum(new Coordinates(0,8))); //cas pas walkable
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        /*System.out.println(b1);
        try {
            System.out.println(b1.getWalkableNeighbours(new Coordinates(4,5)));

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
