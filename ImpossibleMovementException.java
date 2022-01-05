package pacman;

public class ImpossibleMovementException extends Exception {
    String msg = "the movement you asked for was impossible to achieve";

    public ImpossibleMovementException(){

    }

    public ImpossibleMovementException(String msg){
        this.msg = msg;
    }
}
