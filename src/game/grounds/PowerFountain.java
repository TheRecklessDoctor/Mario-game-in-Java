package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.items.PowerWater;
import game.actions.RefillBottleAction;

/**
 * Fountain with water that increases base damage
 */
public class PowerFountain extends Fountain {

    /**
     * A constructor for PowerFountain class.
     * Sets display char
     */
    public PowerFountain() {
        super('A');
    }

    /**
     * Returns actions that can be performed by actor on this PowerFountain object
     * @param actor the Actor that might perform an action.
     * @param location String representing the location of the Actor
     * @param direction  String representing the direction of the fountain
     * @return list of actions
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction){
        if(location == location.map().locationOf(actor)){
            ActionList actions = new ActionList();
            actions.add(new RefillBottleAction(new PowerWater()));
            return actions;
        }

        return new ActionList();
    }
}
