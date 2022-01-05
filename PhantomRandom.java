package pacman;

import java.util.Random;

public class PhantomRandom extends Phantom {


    //===============// Constructors //===============//

    public PhantomRandom(Coordinates coordinates, Board board) {
        super(coordinates, board);
    }

    public PhantomRandom(Coordinates coordinates, Board board, Coordinates pacman) {
        super(coordinates, board);
        this.pacman = pacman;
    }

    //===============// Play Methods //===============//

    public void play() {
        this.randomMovement();
    }

    public boolean randomMovement(){

        int index = (int) (Math.random() * 4);

        switch(index){
            case 0 :
                if(this.moveUp())
                    return true;
                else
                    return randomMovement();
            case 1 :
                if(this.moveDown())
                    return true;
                else
                    return randomMovement();
            case 2 :
                if(this.moveLeft())
                    return true;
                else
                    return randomMovement();
            case 3 :
                if(this.moveRight())
                    return true;
                else
                    return randomMovement();
            default :
                return false;
        }

    }

    //===============// Object inherited Methods //===============//

    @Override
    public String toString() {
        return "R";
    }

    public static void main(String[] args) {
    /*
        Board board = new Board(true);
        Coordinates a = new Coordinates(2,4);
        Coordinates b = new Coordinates(1,1);
        Phantom p = new PhantomRandom(a,board);
        //System.out.println("la distance est de " + p.distance(a,b));

        System.out.println(p.getCoordinates());

        for(int i=0 ; i< 50 ; i++){
            p.play();
            System.out.println(p.getCoordinates());

        }

      */
    }
}
