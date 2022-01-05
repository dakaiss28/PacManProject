package pacman;

import java.util.HashMap;
import java.util.List;

public class PacMan extends Entity {
    private static final int MAX_DEPTH = 6; // depth limit for the minimax research algorithm - how much advance PacMan has
    private List<Coordinates> phantoms;
    private int score;
    private int pacMove;


    //===============// PACMAN CONSTRUCTOR //===============//

    public PacMan(Coordinates coordinates, Board board, List<Coordinates> phantomsList, int score) {
        super(coordinates, board);
        this.phantoms = phantomsList;
        this.score = score;
        this.pacMove = -1;
    }

    //===============// GETTERS & SETTERS METHODS //===============//

    public int getScore() { return score; }

    public void setScore(int s) { score = s; }

    public int getPacMove() { return pacMove; }

    public void setPacMove(int m) { pacMove = m; }

    public void setPhantomsPos(List<Coordinates> coordinates) {
        this.phantoms.clear();
        for (Coordinates c : coordinates) {
            this.phantoms.add(c);
        }
    }

    //===============// PLAY METHODS //===============//

    /**
     * Makes PacMan play and move in a direction computed by an algorithm (glouton, minmax - with alpha-beta pruning or not)
     * Uses a simulation of the current game to make its decision
     */
    public void play() {
        GameState gs = new GameState(board, coordinates, phantoms, score);
        try {
            //move(glouton(new HeurMinmax()));
            move(maximax(gs));
        } catch (Exception e) {
            //System.out.println("Erreur lors du deplacement de pacman");
            //.printStackTrace();
        }
    }

    //===============// GLOUTON ALGORITHM //===============//

    /**
     * Makes PacMan move towards the best direction given the heuristic.
     */
    public int glouton(Heuristic heur) throws Exception{

        //We'll keep track of the associations between scores and directions, to sort them by score in a Map

        // HashMap<Score,Direction>
        HashMap<Float,Integer> mapScoresDir = new HashMap<>();

        //Compute heuristics only for walkable neighbours
        List<Integer> walkableNeighbours = board.getWalkableNeighbours(this.coordinates);

        //Creates a GameState with current info about the game in order to simulate games where Pacman moves towards
        //each of the walkable neighbours
        GameState state = new GameState(board, coordinates, phantoms, score);

        for(Integer direction : walkableNeighbours){
            float score = heur.heuristic(state.simulateGameState(direction));
            mapScoresDir.put(score,direction);
        }

        return heur.computeBestDirection(mapScoresDir);
    }


    //===============// MAXMAX ALGORITHM //===============//

    /**
     * Launches the MinMax algorithm on the game state simulation
     * @return an integer value corresponding to the best move computed by the algorithm
     * Directions code : UP = 1 ; DOWN = 2 ; LEFT = 3 ; RIGHT = 4
     */
    public int maximax(GameState gs) throws Exception {

        return max(gs, 0).getMove();
    }

    /**
     * TO FINISH : methode isGameOver() a faire dans le GameState
     * @return the max value of the children nodes, the score (if terminal state) or an heuristic value
     */
    public ScoreAndMove max(GameState gs, int depth) throws Exception {
        if (gs.isGameOver()) {
            return new ScoreAndMove((score(gs)-10000)/20000,-1);
        } else if (depth >= MAX_DEPTH) {
            return new ScoreAndMove(eval(new HeuristicMaxMax(), gs),-1);
        }

        ScoreAndMove bestScoreAndMove = new ScoreAndMove();

        List<Integer> moves = gs.getGameBoard().getWalkableNeighbours(gs.getPacmanPosition()); // returns all direction possible
        for (int move : moves) {

            GameState newGameState = gs.simulateGameState(move);

            float max = max(newGameState,depth+1).getScore();

            if (max > bestScoreAndMove.getScore()) {

                bestScoreAndMove.setScore(max);
                bestScoreAndMove.setMove(move);
            }
        }
        return bestScoreAndMove;
    }

    //===============// EVALUATION AND SCORE COMPUTING METHODS //===============//

    /**
     * Evaluate the heuristic value on the game state
     */
    public float eval(Heuristic h, GameState gs) {
        return h.heuristic(gs);
    }

    /**
     * Computes a "binary" style score
     */
    public float score(GameState gs) {
        /*if (gs.getScore() > GameManager.WIN_THRESHOLD) {
            return 1f; // PacMan wins
        } else if (gs.getScore() < GameManager.WIN_THRESHOLD) {
            return -1f; // PacMan loses
        } else {
            return 0f; // Null match
        }*/
        return gs.getScore();
    }
}