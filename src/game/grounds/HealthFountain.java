package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.RefillBottleAction;
import game.items.HealthWater;

/**
 * Fountain with water that increases hit points
 */
public class HealthFountain extends Fountain {

    /**
     * A constructor for HealthFountain class.
     * Sets display char
     */
    public HealthFountain() {
        super('H');
    }

    /**
     * Returns actions that can be performed by actor on this HealthFountain object
     * @param actor the Actor that might perform an action.
     * @param location String representing the location of the Actor
     * @param direction  String representing the direction of the fountain
     * @return list of actions
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction){
        if(location == location.map().locationOf(actor)){
            ActionList actions = new ActionList();
            actions.add(new RefillBottleAction(new HealthWater()));
            return actions;
        }

        return new ActionList();
    }
}
