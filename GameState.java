package pacman;

import java.util.List;
import java.util.ArrayList;

public class GameState {

    //================= Stored Variables =================//

    Board gameBoard;

    Coordinates pacmanPosition;

    List<Coordinates> phantomPositions;

    int lastMove;

    int score;

    /*
    Code du move en entier :
    Up 1
    Down 2
    Left 3
    Right 4
     */

    //===============// Constructors //===============//

    public GameState(){
        lastMove = -1;
        gameBoard = null;
        pacmanPosition = null;
        phantomPositions = null;
        score = 0;
    }

    public GameState(Board b, Coordinates pacmanPosition, List<Coordinates> phantomPositions, int score){
        lastMove = -1;
        gameBoard = b;
        this.pacmanPosition = pacmanPosition;
        this.phantomPositions = phantomPositions;
        this.score = score;
    }

    public GameState(int lastMove, Board b, Coordinates pacmanPosition, List<Coordinates> phantomPositions, int score){
        this.lastMove = lastMove;
        gameBoard = b;
        this.pacmanPosition = pacmanPosition;
        this.phantomPositions = phantomPositions;
        this.score = score;
    }

    //===============// Simulation Methods //===============//

    public GameState simulateGameState(int pacmanMove) throws ImpossibleMovementException{

       //Détermination de la nouvelle position de pacman à partir du mouvement

        Coordinates pacmanNewPos = null;

        switch (pacmanMove){
            case Board.UP :
                pacmanNewPos = new Coordinates(this.pacmanPosition.getX()-1,this.pacmanPosition.getY());
                break;

            case Board.DOWN :
                pacmanNewPos = new Coordinates(this.pacmanPosition.getX()+1,this.pacmanPosition.getY());
                break;

            case Board.LEFT :
                pacmanNewPos = new Coordinates(this.pacmanPosition.getX(),this.pacmanPosition.getY()-1);
                break;

            case Board.RIGHT :
                pacmanNewPos = new Coordinates(this.pacmanPosition.getX(),this.pacmanPosition.getY()+1);
                break;
        }
        if(!gameBoard.isWalkable(pacmanNewPos)){
            throw new ImpossibleMovementException();
        }

        //Data needed to simulate the new score
        boolean ateAGum = false;
        boolean diedToAGhost = false;
        int simulationScore = this.score;

        //Génération et simulation d'un tour de jeu des phantomes

        List<Phantom> phantoms = new ArrayList<>();
        List<Coordinates> simulationPhantomPositions = new ArrayList<>();

        for (Coordinates c : phantomPositions ) {
            Phantom simulationPhantom = new PhantomSim(c, gameBoard, pacmanNewPos);
            phantoms.add(simulationPhantom);
            simulationPhantomPositions.add(simulationPhantom.getCoordinates());
        }

        // Checks if PacMan is on the same tile as a ghost
        for (Coordinates c : simulationPhantomPositions) {
            if (pacmanNewPos.equals(c)) {
                diedToAGhost = true;
            }
        }

        // Entities play if the game is not over
        for(Phantom p : phantoms) {
            if (!diedToAGhost) {
                p.play();
                simulationPhantomPositions.add(p.getCoordinates());
                if (pacmanNewPos.equals(p)) {
                    diedToAGhost = true;
                }
            }
        }


        //Build a copy of the current board to avoid influencing the current gameState through simulation

        Board simulationBoard = new Board(gameBoard.getCurrentBoard(),null);



        //Remove the gum at pacman's new position if there is one
        try{
            simulationBoard.removeGum(pacmanNewPos);
            ateAGum = true;
        }catch (Exception e){
            //There was no Gum
        }

        for(Coordinates pc : phantomPositions){
            if(pc.equals(pacmanPosition)) diedToAGhost = true;
        }

        // Score update
        if (ateAGum) {
            simulationScore += 100;
        } else if (diedToAGhost) {
            simulationScore -= 1000;
        }
        simulationScore += 1;

        return new GameState(pacmanMove,simulationBoard, pacmanNewPos, simulationPhantomPositions,simulationScore);

    }

    public boolean isGameOver(){
        boolean res = false;

        if(gameBoard.getNbGumLeft() == 0){
            return true;
        }else{
            for(Coordinates pc : phantomPositions){
                if(pc.equals(pacmanPosition)) return true;
            }
            return false;
        }
    }



    //================= Getters and Setters =================//

    public Board getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Coordinates getPacmanPosition() {
        return pacmanPosition;
    }

    public void setPacmanPosition(Coordinates pacmanPosition) {
        this.pacmanPosition = pacmanPosition;
    }

    public List<Coordinates> getPhantomPositions() {
        return phantomPositions;
    }

    public void setPhantomPositions(List<Coordinates> phantomPositions) {
        this.phantomPositions = phantomPositions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
