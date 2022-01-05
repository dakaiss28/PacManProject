package pacman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class HeurMinmax implements Heuristic{
    @Override
      public float heuristic(GameState gs) {
        ArrayList<Integer> dist_phantom = new ArrayList<Integer>();
        for(int i=0; i< gs.getPhantomPositions().size(); i++){
            dist_phantom.add(euclidienneDistance(gs.getPacmanPosition(),gs.phantomPositions.get(i)));
        }
        Collections.sort(dist_phantom);

        int distanceNearestPhantom = dist_phantom.get(0);
        float nbGumLeft = gs.getGameBoard().getNbGumLeft();

        float heur = distanceNearestPhantom/(nbGumLeft +1);
        int diagonal = euclidienneDistance(new Coordinates(0,0), new Coordinates(gs.gameBoard.BOARD_SIZE_X, gs.gameBoard.BOARD_SIZE_Y));

        float heurNormalized = (heur - diagonal/2) / (diagonal/2);
        return heurNormalized;
    }

    private int euclidienneDistance(Coordinates a, Coordinates b){
        return (int)Math.sqrt(Math.pow((a.getY() - b.getY()),2) + Math.pow((a.getX() - b.getX()),2));
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
}
