package pacman;

/**
 * This class is used to make motionless Phantoms, for debuging purposes
 * As those Phantoms don't move, it is easier to analyse PacMan's behaviour
 */

public class PhantomFixed extends Phantom{


    //===============// Constructors //===============//

    public PhantomFixed(Coordinates coordinates, Board board, Coordinates pacman) {
        super(coordinates, board, pacman);
    }

    //===============// Getters & Setters //===============//
    //For debuging purpose, it is allowed to set manually the Phantom's coordinates
    public void setCoordinates(Coordinates c){
        this.coordinates = c;
    }

    //===============// Play Methods //===============//

    @Override
    public void play() {
        //Does nothing : the phantom is fixed into the board
    }
}
