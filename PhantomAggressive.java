package pacman;

import java.util.*;

public class PhantomAggressive extends Phantom {

    //===============// Constructors //===============//

    public PhantomAggressive(Coordinates coordinates, Board board, Coordinates pacman) {
        super(coordinates, board);
        this.pacman = pacman;
    }

    //===============// Play Methods //===============//

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
        int min = Integer.MAX_VALUE;
        for (Coordinates cor :c) {
            if(board.isWalkable(cor)){
                int aux = distance(cor, b);
                if(aux<min){
                    min = aux;
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

    public static void main(String[] args) {

       /* Board board = new Board(true,null);
        Coordinates a = new Coordinates(0,0);
        Coordinates b = new Coordinates(2,2);
        PhantomAggressive p = new PhantomAggressive(a,board,b);
        p.play(); */
    }

    @Override
    public String toString() {
        return "A";
    }

}
