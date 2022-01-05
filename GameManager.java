package pacman;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    //***************// ATTRIBUTES //***************//

    //===============// Board for the game //===============//

    private Board gameBoard;

    //===============// Entities //===============//

    private PacMan pacman;
    private List<Phantom> phantomList;

    //===============// Game status & properties //===============//

    public static final int WIN_THRESHOLD = 5000; //to adjust
    private static final int NB_MAX_TURNS = Integer.MAX_VALUE; //to adjust
    private boolean gameOver;
    private int score;

    //***************// METHODS //***************//

    //===============// Constructors //===============//

    public GameManager() {
        gameBoard = null;
        gameOver = false;
        score = 0;
        pacman = null;
        phantomList = new ArrayList<Phantom>();
    }

    public GameManager(Board b, PacMan pm, List<Phantom> phantoms){
        gameBoard = b;
        gameOver = false;
        score = 0;
        pacman = pm;

        phantomList = new ArrayList<Phantom>();
        for (Phantom p : phantoms) {
            phantomList.add(p);
        }
    }

    //===============// Game initiation //===============//

    /*
     * Initiate the board for the game and the position of each entity
     */
    public void initGame(Coordinates pacManPosition, List<Coordinates> phantomPositions, Coordinates[] wallPosition, Coordinates[] gumsPosition) {
        int[][] tab = new int[Board.BOARD_SIZE_Y][Board.BOARD_SIZE_X];
        for (int k = 0; k < Board.BOARD_SIZE_Y; k++) {
            for (int l = 0; l < Board.BOARD_SIZE_X; l++) {
                tab[k][l] = Board.EMPTY;
            }
        }
        for (int i = 0; i < wallPosition.length; i++) {
            tab[wallPosition[i].getX()][wallPosition[i].getY()] = Board.WALL;
        }
        for (int j = 0; j < gumsPosition.length; j++) {
            tab[gumsPosition[j].getX()][gumsPosition[j].getY()] = Board.GUM;
        }
        gameBoard = new Board(tab, this);
        gameOver = false;
        initEntities(pacManPosition, phantomPositions);
    }

    /*
     * Initiate the board for the game and the position of each entity
     */
    public void initGame(Coordinates pacManPosition, List<Coordinates> phantomPositions, Board b) {
        gameBoard = b;
        gameOver = false;
        initEntities(pacManPosition, phantomPositions);
    }

    /*
     * Initiate each entities
     */
    private void initEntities(Coordinates pacManPos, List<Coordinates> phantomsPos) {
        //Creates the phantoms at initial position given in phantomPos
        for(Coordinates c : phantomsPos) {
            phantomList.add(new PhantomAggressive(c, gameBoard, pacManPos));
        }
        pacman = new PacMan(pacManPos, gameBoard, phantomsPos, score);
    }

    //===============// Getters & setters for game status & properties //===============//

    /*
     * Sets the gameBoard
     */
    public void setGameBoard(Board b){
        this.gameBoard = b;
    }

    /*
     * Returns the current score of PacMan
     */
    public Board getBoard() { return gameBoard; }

    /*
     * Returns the current score of PacMan
     */
    public int getScore() { return score; }

    /*
     * Sets the current score of PacMan to a value s
     */
    public void setScore(int s) { score = s; }

    /*
     * Gets Pacman
     */
    public PacMan getPacman() {
        return pacman;
    }

    /*
     * Tells if PacMan has won the game or not
     */
    public boolean hasPacmanWon() {
        if (isGameOver()) {
            if (noMoreGums() || (score > WIN_THRESHOLD)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Checks if there is no gums left on the game board
     */
    private boolean noMoreGums() {
        if (gameBoard.getNbGumLeft() == 0) {
            return true;
        }
        return false;
    }

    /*
     * Tells if the game is over
     */
    public boolean isGameOver() { return gameOver; }

    /*
     * Resets every parameters of the game
     */
    public void reset() {
        gameBoard = null;
        gameOver = false;
        score = 0;
        pacman = null;
    }

    /*
     * Gets the list of entities
     */
    public List<Phantom> getPhantomList() {
        return phantomList;
    }

    /*
     * Gets the list of entities' coordinates
     */
    public List<Coordinates> getPhantomCoordinatesList() {
        List<Coordinates> res = new ArrayList<>();

        for(Entity e : phantomList){
            res.add(e.getCoordinates());
        }

        return res;
    }

    /*
     *  GameManager's toString() return its board toString()
     */
    public String toString(){
        return this.gameBoard.toString();
    }

    //===============// Play //===============//

    /*
     * Moves an entity and updates some game elements
     */
    public void playTurn(List<Phantom> phantoms, PacMan pac) throws Exception {
        boolean diedToAGhost = false;
        boolean ateAGum = false;
        int phantomNum = 0;

        // PacMan plays
        pac.play();
        Coordinates pacPosition = pac.getCoordinates();

        // Checks if PacMan has eaten a gum
        if (gameBoard.isGum(pacPosition)) {
            gameBoard.removeGum(pacPosition);
            ateAGum = true;
        }

        // Checks if PacMan is on the same tile as a ghost
        for (Coordinates c : getPhantomCoordinatesList()) {
            if (pacPosition.equals(c)) {
                diedToAGhost = true;
                gameOver = true;
            }
        }

        // Entities play if the game is not over
        for(Phantom p : phantoms) {
            if (!diedToAGhost) {
                p.play();
                if (pacPosition.equals(p)) {
                    diedToAGhost = true;
                    gameOver = true;
                }
            }
        }

        // Score update
        if (ateAGum) {
            score += 100;
        }
         if (diedToAGhost) {
            score -= 1000;
        }
        score++;
    }

    /**
     * TO FINISH
     **/
    public static void main (String[] args) throws Exception {

        // Initialisation du board de jeu
        Board board = new Board(true, null); // board de test

        // Coordonnees tests a modifier au besoin
        Coordinates pacmanTestStartingCoord = new Coordinates(18,8);
        Coordinates phantom1TestStartingCoord = new Coordinates(0,0);
        Coordinates phantom2TestStartingCoord = new Coordinates(28,0);
        Coordinates phantom3TestStartingCoord = new Coordinates(28,25);
        Coordinates phantom4TestStartingCoord = new Coordinates(0,25);

        ArrayList<Coordinates> phantomsCoordList = new ArrayList<>();
        phantomsCoordList.add(phantom1TestStartingCoord);
        phantomsCoordList.add(phantom2TestStartingCoord);
        phantomsCoordList.add(phantom3TestStartingCoord);
        phantomsCoordList.add(phantom4TestStartingCoord);

        // Initialisation des entites
        PacMan pacMan = new PacMan(pacmanTestStartingCoord, board, phantomsCoordList, 0);
        Phantom phantom1 = new PhantomRandom(phantom1TestStartingCoord, board, pacmanTestStartingCoord);
        Phantom phantom2 = new PhantomTimid(phantom2TestStartingCoord, board, pacmanTestStartingCoord);
        Phantom phantom3 = new PhantomAmbusher(phantom4TestStartingCoord, board, pacmanTestStartingCoord,phantomsCoordList);
        Phantom phantom4 = new PhantomAggressive(phantom3TestStartingCoord, board, pacmanTestStartingCoord);

        List<Phantom> phantomList = new ArrayList<>();
        phantomList.add(phantom1);
        phantomList.add(phantom2);
        phantomList.add(phantom3);
        phantomList.add(phantom4);

        // Creation du game manager
        GameManager gm = new GameManager(board, pacMan, phantomList);

        // Association du game manager au board
        board.setGameManager(gm);

        // Compteur de tours
        int nbTurns = 0;

        // Etat initial du board
        System.out.println("=== ETAT INITIAL DU BOARD ===");
        System.out.println(gm);
        System.out.println("=============================");

        // Permettra de laisser un affichage de l'état courant du jeu pendant au moins 0.5 secondes
        long formerTime = System.currentTimeMillis();

        // Lancement de la simulation
        while (!(gm.isGameOver() || nbTurns >= GameManager.NB_MAX_TURNS)) {
            try {
                gm.playTurn(phantomList,pacMan);

                // Recuperation des coordonnees des phantoms pour update
                List<Coordinates> phantCoordList = new ArrayList<>();
                phantCoordList.add(phantom1.getCoordinates());
                phantCoordList.add(phantom2.getCoordinates());
                phantCoordList.add(phantom3.getCoordinates());
                phantCoordList.add(phantom4.getCoordinates());

                // Mise a jour des coordonnees de pacman pour calcul de distance
                for (Phantom p: phantomList) {
                    p.setCoordinates(pacMan.getCoordinates());
                }

                // Update pour chaque Ambusher les coordonnées des autres phantoms
                for (Phantom p: phantomList) {
                    if(p instanceof PhantomAmbusher){
                        List<Coordinates> otherPhantCoords=new ArrayList<>(phantCoordList);
                        otherPhantCoords.remove(p.coordinates);
                        ((PhantomAmbusher)p).setOtherPhantomsPos(otherPhantCoords);
                    }
                }

                // Mise a jour des coordonnees des phantoms pour pacman
                pacMan.setPhantomsPos(phantCoordList);

                // Mise a jour du score dans la classe PacMan
                pacMan.setScore(gm.getScore());

                // Affichage du score courant de PacMan
                System.out.println("==================");
                System.out.println("SCORE DE PACMAN :");
                System.out.println(gm.getScore());
                System.out.println("==================");

                // Affichage du board
                System.out.println(gm);
            } catch (Exception e) {
                System.out.println("ERREUR lors du deplacement de PacMan !");
                e.printStackTrace();
            }
            nbTurns++;

            // Permet de laisser un affichage de l'état courant du jeu pendant au moins 0.5 secondes
            if(System.currentTimeMillis()-formerTime<500){
                Thread.sleep(500-(System.currentTimeMillis()-formerTime));
            }
            formerTime = System.currentTimeMillis();
        }
        System.out.println("Game stopped after "+nbTurns+ " turns");

        // Etat final du board (a commenter si affichage trop gourmand)
        System.out.println("=== ETAT FINAL DU BOARD ===");
        System.out.println(gm);
        System.out.println("SCORE DE PACMAN :");
        System.out.println(gm.getScore());
        System.out.println("=============================");

    }
}

