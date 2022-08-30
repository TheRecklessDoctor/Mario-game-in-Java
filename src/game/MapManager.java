package game;

import edu.monash.fit2099.engine.positions.GameMap;

import java.util.ArrayList;

/**
 * A global singleton map manager that returns game maps which are in the game world
 */
public class MapManager {

    /**
     * A list of maps in world
     */
    private ArrayList<GameMap> gameMaps;

    /**
     * A singleton map manager instance
     */
    private static MapManager instance;

    /**
     * Get the singleton instance of map manager
     * @return MapManager singleton instance
     */
    public static MapManager getInstance(){
        if(instance == null){
            instance = new MapManager();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private MapManager(){
        gameMaps = new ArrayList<>();
    }

    /**
     * Add the Map instance to the list
     */
    public void appendMap(GameMap map){
        gameMaps.add(map);
    }

    /**
     * Get map from the list
     * @return map
     */
    public ArrayList<GameMap> getMaps(){
        return gameMaps;
    }
}
