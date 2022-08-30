package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.MapManager;

import java.util.ArrayList;

/**
 * action to teleport to another map
 */
public class TeleportActorAction extends Action {

    /**
     * an array of all game maps in world
     */
    private ArrayList<GameMap> gameMaps = MapManager.getInstance().getMaps();

    /**
     * an array of previous locations the player went to
     */
    private static ArrayList<Location> previousLocations = new ArrayList<Location>();

    /**
     * map the player is currently on
     */
    private GameMap map;

    /**
     * action to teleport to another map
     * @param location location the actor is at
     */
    public TeleportActorAction(Location location){
        this.map = location.map();
    }

    /**
     * actions execute method
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return appropriate string
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if(map == gameMaps.get(0)){
            previousLocations.add(map.locationOf(actor));
            if (gameMaps.get(1).at(0,0).containsAnActor()){
                gameMaps.get(1).removeActor(gameMaps.get(1).at(0,0).getActor());
            }
            map.moveActor(actor, gameMaps.get(1).at(0,0));
            return actor + " teleported to Lava Zone";
        } else {
            if (previousLocations.get(previousLocations.size() - 1).containsAnActor()){
                previousLocations.get(previousLocations.size() - 1).map().removeActor(previousLocations.get(previousLocations.size() - 1).getActor());
            }
            map.moveActor(actor,previousLocations.get(previousLocations.size() - 1));
            return actor + " teleported back from Lava Zone to Main Map";
        }
    }

    /**
     * returns a success message for consumption
     * @param actor The actor performing the action.
     * @return string showing success
     */
    @Override
    public String menuDescription(Actor actor) {
        if(map == gameMaps.get(0)){
            return "Teleport to Lava Zone";
        } else {
            return "Teleport back to Main Map";
        }
    }

}
