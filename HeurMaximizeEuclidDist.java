package pacman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 *  Functor implementing Heuristic interface :
 *  Its purpose is to maximize the euclidian distance between Pacman and Phantoms
 *  The greater the returned int is, the better the heuristic is.
 */

public class HeurMaximizeEuclidDist implements Heuristic{

    //Computes a score (int) corresponding to a given GameState
    @Override
    public float heuristic(GameState gs) {
        ArrayList<Integer> dist_phantom = new ArrayList<Integer>();
        for(int i=0; i< gs.getPhantomPositions().size(); i++){
            dist_phantom.add(euclidienneDistance(gs.getPacmanPosition(),gs.phantomPositions.get(i)));
        }
        Collections.sort(dist_phantom);
        int diagonal = euclidienneDistance(new Coordinates(0,0), new Coordinates(gs.gameBoard.BOARD_SIZE_X, gs.gameBoard.BOARD_SIZE_Y));
        return (diagonal - dist_phantom.get(0));
    }

    private int euclidienneDistance(Coordinates a, Coordinates b){
        return (int)Math.sqrt(Math.pow((a.getY() - b.getY()),2) + Math.pow((a.getX() - b.getX()),2));
    }

    //For a given map of <Score, Dircetion>, returns the direction (int) associated to the best score,
    // according to the heuristic : the maximal score.
    @Override
    public int computeBestDirection(Map<Float, Integer> mapScoreDirection){
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
