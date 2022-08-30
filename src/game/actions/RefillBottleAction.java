package game.actions;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Water;
import game.items.Bottle;

/**
 * action to refill bottle with water obtained from fountain
 */
public class RefillBottleAction extends Action {

    /**
     * water obtained from fountain
     */
    public Water water;

    /**
     * action to refill bottle with water obtained from fountain
     * @param water water obtained from fountain
     */
    public RefillBottleAction(Water water){
        this.water = water;
    }

    /**
     * actions execute method
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return appropriate string
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        for (Item item: actor.getInventory() ){
            if (item instanceof Bottle) {
                ((Bottle) item).push(water);
            }
        }
        return "Mario refilled bottle with " + water.toString();
    }

    /**
     * returns a success message for refilling bottle with water from fountain
     * @param actor The actor performing the action.
     * @return string showing success
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Mario refill " + water.toString();
    }
}
