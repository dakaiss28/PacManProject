package pacman;

import java.util.*;

public class HeuristicMaxMax implements Heuristic{


    public HeuristicMaxMax(){

    }

    @Override
    public float heuristic(GameState gs) {

        List<Integer> dist_phantom = computeDistancesToPhantoms(gs);

        Collections.sort(dist_phantom);

        int distanceNearestPhantom = dist_phantom.get(0);
        int distanceSecondNearestPhantom = dist_phantom.get(1);
        int distanceThirdNearestPhantom = dist_phantom.get(2);
        int distanceFourthNearestPhantom = dist_phantom.get(3);
        //System.out.println("The closest is at : "+ distanceNearestPhantom);
        //float nbGumLeft = gs.getGameBoard().getNbGumLeft();


        int diagonal = distance(gs.gameBoard,new Coordinates(0,0), new Coordinates(gs.gameBoard.BOARD_SIZE_X, gs.gameBoard.BOARD_SIZE_Y));

        float heur = (float) (gs.getScore() - (diagonal*2 - distanceNearestPhantom - distanceSecondNearestPhantom*0.5 -distanceThirdNearestPhantom*0.35 - distanceFourthNearestPhantom*0.15)*50);

        float heurNormalized = (heur - (10000 + diagonal/2)) / (20000);
        return heurNormalized;

    }

    public List<Integer> computeDistancesToPhantoms(GameState gs){
        ArrayList<Integer> dist_phantom = new ArrayList<>();
        //System.out.println("The distance from pacman of the different phantoms are : ");
        for(int i=0; i< gs.getPhantomPositions().size(); i++){
            int distToCurrent = distance(gs.gameBoard,gs.getPacmanPosition(),gs.phantomPositions.get(i));
            dist_phantom.add(distToCurrent);
            //System.out.println(distToCurrent);
        }
        return dist_phantom;
    }

    public int distance(Board board,Coordinates a, Coordinates b) {
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
                //System.out.println("la distance minimale est  "+ node.dist);
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

    @Override
    public int computeBestDirection(Map<Float, Integer> mapScoreDirection) {
        // Here, the choice is to maximize the heuristic, that's why we reverse TreeMap's natural order in its constructor
        TreeMap<Float,Integer> treeMap = new TreeMap<>(Collections.reverseOrder());

        //We copy the given Map into the TreeMap, in order to sort by descending keys (score)
        for(Map.Entry<Float,Integer> entry : mapScoreDirection.entrySet()){
            treeMap.put(entry.getKey(),entry.getValue());
        }

        //We return the direction linked to the greatest key (which correspond to the first entry)
        return treeMap.get(treeMap.firstKey());
    }

    private float euclidienneDistance(Coordinates a, Coordinates b){
        return (float)Math.sqrt(Math.pow((a.getY() - b.getY()),2) + Math.pow((a.getX() - b.getX()),2));
    }
}
