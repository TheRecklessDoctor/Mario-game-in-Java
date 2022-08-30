package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;

import java.util.Random;

/**
 * Class for Jumping to a location.
 */
public class JumpActorAction extends Action {

    /**
     * Stores location to jump to.
     */
    protected Location jumpToLocation;

    /**
     * Stores direction in which acting Actor will jump.
     */
    protected String direction;

    /**
     * Stores possibility of jump being successful.
     */
    protected int successRate;

    /**
     * Stores damage to be dealt if jump by Actor fails.
     */
    protected int fallDamage;

    /**
     * Name of ground Actor will be jumping to.
     */
    protected String groundName;

    /**
     * Constructor to create an Action that will move the Actor to a Location in a given Direction, if jump successful.
     * @param jumpToLocation Stores location to jump to.
     * @param direction Stores direction in which acting Actor will jump.
     * @param successRate  Stores possibility of jump being successful.
     * @param fallDamage  Stores damage to be dealt if jump by Actor fails.
     * @param groundName Name of ground Actor will be jumping to.
     */
    public JumpActorAction(Location jumpToLocation, String direction, int successRate, int fallDamage, String groundName) {
        this.jumpToLocation = jumpToLocation;
        this.direction = direction;
        this.successRate = successRate;
        this.fallDamage = fallDamage;
        this.groundName = groundName;
    }

    /**
     * Move Actor to JumpToLocation if jump is successful. Else will deal damage to Actor.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return Description of the result of the jump performed by the Actor.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Random random = new Random();
        if (actor.hasCapability(Status.TALL)){
            map.moveActor(actor, jumpToLocation);
            String returnString = "Successful jump to " + groundName +".";
            returnString += "\nCurrent Location: " + groundName + "( " + jumpToLocation.x() + "," + jumpToLocation.y() + " ).";
            return returnString;
        } else if (random.nextInt( 100) < successRate) {
            map.moveActor(actor, jumpToLocation);
            String returnString = "Yay, successful jump to " + groundName +". ";
            returnString += "Current Location: " + groundName + "( " + jumpToLocation.x() + "," + jumpToLocation.y() + " ).";
            return returnString;
        } else {
            actor.hurt(fallDamage);
            String returnString = "Unlucky, unsuccessful jump to "+ groundName +". Damage Received: " + fallDamage + ". ";
            returnString += "Maybe try looking for some Magical Items.";
            return returnString;
        }
    }

    /**
     * Returns a description of this movement suitable to display in the menu.
     * @param actor The actor performing the action.
     * @return a description of the Action suitable for the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " jumps " + direction + " to " + groundName;
    }
}
