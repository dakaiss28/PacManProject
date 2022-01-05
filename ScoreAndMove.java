package pacman;

public class ScoreAndMove {

    float score;
    int move;

    //===============// Constructors //===============//

    public ScoreAndMove(){
        score = (float)Integer.MIN_VALUE;
        move = -1;
    }

    public ScoreAndMove(float score, int move){
        this.score = score;
        this.move = move;
    }

    //===============// Getters & Setters //===============//

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }
}
