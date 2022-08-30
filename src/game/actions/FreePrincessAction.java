package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * action to free Princess Peach
 */
public class FreePrincessAction extends Action {

    /**
     * actor to be freed
     */
    private Actor otherActor;

    /**
     * action to free Princess Peach
     * @param otherActor actor to be freed
     */
    public FreePrincessAction(Actor otherActor) {
        this.otherActor = otherActor;
    }

    /**
     * actions execute method
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return appropriate string
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        return(otherActor + " has been freed by " + actor);
    }

    /**
     * returns a success message for freeing Princess Peach
     * @param actor The actor performing the action.
     * @return string showing success
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Free " + otherActor + " with key";
    }
}
