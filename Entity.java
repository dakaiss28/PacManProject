package pacman;

public abstract class Entity {

    protected Coordinates coordinates ;
    protected Board board;

    //===============// Constructors //===============//

    public Entity(Coordinates coordinates, Board board){
        this.coordinates = coordinates ;
        this.board = board;
    }

    //===============// Play Methods //===============//

    public abstract void play();

    //===============// Move Methods //===============//

    public boolean move(int direction) throws Exception{
        switch (direction){
            case Board.UP:
                return this.moveUp();

            case Board.DOWN:
                return this.moveDown();

            case Board.RIGHT:
                return this.moveRight();

            case Board.LEFT:
                return this.moveLeft();

            default:
                throw new Exception("Entity.move(int direction) : EXCEPTION\nNo valid direction.");

        }
    }

    public boolean moveLeft(){
        Coordinates neighbor = new Coordinates(coordinates.getX(),coordinates.getY()-1);
        boolean condition = board.isWalkable(neighbor);
        if(condition) coordinates = neighbor;
        return condition;
    }

    public boolean moveRight(){
        Coordinates neighbor = new Coordinates(coordinates.getX(),coordinates.getY()+1);
        boolean condition = board.isWalkable(neighbor);
        if(condition) coordinates = neighbor;
        return condition;
    }

    public boolean moveUp(){
        Coordinates neighbor = new Coordinates(coordinates.getX()-1,coordinates.getY());
        boolean condition = board.isWalkable(neighbor);
        if(condition) coordinates = neighbor;
        return condition;
    }

    public boolean moveDown(){
        Coordinates neighbor = new Coordinates(coordinates.getX()+1,coordinates.getY());
        boolean condition = board.isWalkable(neighbor);
        if(condition) coordinates = neighbor;
        return condition;
    }

    //===============// Getters & Setters //===============//

    public Coordinates getCoordinates() {
        return coordinates;
    }

    //===============// Object Inherited Methods //===============//

    public String toString(){
        return "[Type : " + this.getClass().toString() + ", Coordinates : " + this.coordinates + "]";
    }
}
