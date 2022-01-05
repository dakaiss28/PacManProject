package pacman;

import java.util.*;

public class PhantomSim extends Phantom{

    //===============// Constructors //===============//

    public PhantomSim(Coordinates coordinates, Board board, Coordinates pacman) {
        super(coordinates, board);
        this.pacman = pacman;
    }

    //===============// Play Methods //===============//

    public void play() {
        int choice = -1;
        double rand = Math.random(); // returns a number between 0 and 1

        // Choose randomly the type of move to do (best, intermediate, worse...) by using a weighting strategy
        if (rand > 0.5) {
            choice = 0; // 50% chance to take the best move
        } else if (rand > 0.3) {
            choice = 1; // 20% chance to take the 2nd best move
        } else if (rand > 0.10) {
            choice = 2; // 20% chance to take the 3rd best move
        } else {
            choice = 3; // 10% chance to take the worst move
        }
        this.chooseNeighbour(this.pacman,choice);
    }

    public void setCoordinates(Coordinates c) { this.pacman = c; }

    // Choose a neighbour considering the distance and the choice to take the best or worst option (or between)
    public void chooseNeighbour(Coordinates b, int choice) {
        int i = coordinates.getX();
        int j = coordinates.getY();
        List<Coordinates> c = new ArrayList<Coordinates>();
        Coordinates up = new Coordinates(i-1,j); c.add(up);
        Coordinates down = new Coordinates(i+1,j); c.add(down);
        Coordinates left = new Coordinates(i,j-1); c.add(left);
        Coordinates right = new Coordinates(i,j+1); c.add(right);

        // TreeMap to have all coordinates sorted by the distance to PacMan (e.g. best to worse move)
        TreeMap<Integer,Coordinates> sortedMoves = new TreeMap<Integer,Coordinates>();

        // Fills the map (which is automatically sorted in ascending order
        for (Coordinates cor : c) {
            if(board.isWalkable(cor)) {
                int dist = distance(cor,b);
                // /!\ si deux distances identiques => au lieu d'enlever une possibilité ;
                // => on l'ajoute en faisant +1 pour quand même avoir le choix...
                if (sortedMoves.containsKey(dist)) {
                    sortedMoves.put(dist+1,cor);
                } else {
                    sortedMoves.put(dist,cor);
                }
            }
        }

        // Gets all the moves in right order
        Object[] val = sortedMoves.values().toArray();
        List<Coordinates> moves = new ArrayList<Coordinates>();
        for(Object o : val) {
            if (o instanceof Coordinates) {
                moves.add((Coordinates) o);
            }
        }

        // If the choice selected is higher than the number of choice possible
        // it takes the worst choice possible
        if(moves.size() < (choice+1)) {
            choice = moves.size() - 1;
        }

        // Moves the entity
        coordinates = moves.get(choice);
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

}
