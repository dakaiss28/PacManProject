package pacman;

import java.util.Map;

public interface Heuristic {

    //Computes a score (int) corresponding to a given GameState
    float heuristic(GameState gs);

    //For a given map of <Score, Dircetion>, returns the direction (int) associated to the best score,
    // according to the heuristic.
    int computeBestDirection(Map<Float, Integer> mapScoreDirection);
}
