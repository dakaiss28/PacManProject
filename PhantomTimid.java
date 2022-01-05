package pacman;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PhantomTimid extends Phantom {

    //===============// Constructors //===============//

    public PhantomTimid(Coordinates coordinates, Board board) {
        super(coordinates, board);
    }

    public PhantomTimid(Coordinates coordinates, Board board, Coordinates pacman) {
        super(coordinates, board, pacman);
    }

    //===============// Play Methods //===============//

    @Override
    public void play() {
        this.chooseNeighbour(this.pacman);
    }

    public void chooseNeighbour(Coordinates b){
        int i = coordinates.getX();
        int j = coordinates.getY();
        List<Coordinates> c = new ArrayList<Coordinates>();
        Coordinates up = new Coordinates(i-1,j); c.add(up);
        Coordinates down = new Coordinates(i+1,j);c.add(down);
        Coordinates left = new Coordinates(i,j-1);c.add(left);
        Coordinates right = new Coordinates(i,j+1); c.add(right);

        Coordinates chosen = null;
        int max = Integer.MIN_VALUE;
        for (Coordinates cor :c) {
            if(board.isWalkable(cor)){
                int aux = distance(cor, b);
                if(aux>max){
                    max = aux;
                    chosen = cor;
                }
            }
        }
        coordinates = chosen;
    }

    public int distance(Coordinates a, Coordinates b) {
        class Node {
            Coordinates c;
            int dist;

            Node(Coordinates c, int dis){
                this.c=c;
                dist=dis;
            }
        }

        int rowNum[] = {-1, 0, 0, 1};
        int colNum[] = {0, -1, 1, 0};

        boolean[][] visited = new boolean[Board.BOARD_SIZE_X][Board.BOARD_SIZE_Y];
        visited[a.getX()][a.getY()] = true;
        Queue<Node> q = new LinkedList<>();

        q.add(new Node(a,0));

        while(!q.isEmpty()) {
            Node node = q.peek();
            Coordinates pt = new Coordinates(node.c.getX(),node.c.getY());

            if ( pt.getX()==b.getX() && pt.getY() == b.getY()) {
                return node.dist;
            }
            q.remove();
            for (int i = 0; i < 4; i++) {
                int row = pt.getX() + rowNum[i];
                int col = pt.getY() + colNum[i];
                Coordinates coor = new Coordinates(row,col);
                if(board.isWalkable(coor) && !visited[row][col]){
                    visited[row][col]= true;
                    q.add(new Node(coor,node.dist+1));
                }
            }
        }
        return -1;
    }

    //===============// Object inherited Methods //===============//

    @Override
    public String toString() {
        return "T";
    }
}
