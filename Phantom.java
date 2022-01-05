package pacman;

public abstract class Phantom extends Entity {
    protected Coordinates pacman;

    //===============// Constructors //===============//

    public Phantom(Coordinates coordinates, Board board) {
        super(coordinates, board);
    }

    public Phantom(Coordinates coordinates, Board board, Coordinates pacman) {
        super(coordinates, board);
        this.pacman = pacman;
    }

    //===============// Getters & Setters //===============//

    public void setCoordinates(Coordinates c) { this.pacman = c; }

    //===============// Play Methods //===============//
    
    public abstract void play();

    //===============// Object inherited Methods //===============//

    @Override
    public String toString() {
        return "f";
    }
}
