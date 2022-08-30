package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.items.Bottle;

/**
 * action to drink water obtained from fountain
 */
public class DrinkWaterAction extends Action {

    /**
     * bottle to store water obtained from fountain
     */
    private Bottle bottle;

    /**
     * action to drink water from bottle
     * @param bottle bottle to store water obtained from fountain
     */
    public DrinkWaterAction(Bottle bottle) {
        this.bottle = bottle;
    }

    /**
     * actions execute method
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return appropriate string
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.heal(bottle.peek().getHpToHeal());
        Player character = (Player) actor;
        character.setIntrinsicDamage(character.getIntrinsicDamage()+bottle.peek().getBaseDamageIncrease());
        String water = bottle.peek().toString();
        bottle.pop();
        return "Mario drank "+ water;
    }

    /**
     * returns a success message for consumption
     * @param actor The actor performing the action.
     * @return string showing success
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Mario consumes Bottle " + bottle.display();
    }
}
