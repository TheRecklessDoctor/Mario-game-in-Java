package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Status;
import game.items.Wrench;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for Toad speaking to actor.
 */
public class SpeakAction extends Action {
    /**
     * Target which Toad talks to.
     */
    protected Actor target;
    /**
     * Direction of target which Toad talks to.
     */
    protected String direction;
    /**
     * Picks random value in a range.
     */
    protected Random rand = new Random();

    /**
     * Constructor to create an Action that allows Toad to speak with actor when actor is nearby.
     * @param target Target which Toad talks to.
     * @param direction Direction of target which Toad talks to.
     */
    public SpeakAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    /**
     * Creates an array with sentences which Toad will say to target, sentences are to be said randomly where certain sentences will not be said if conditions are fulfilled.
     * @param actor Actor which speaks to target, aka Toad.
     * @param map Map where Toad is on.
     * @return A sentence randomly, depending on if conditions are fulfilled.
     */
    public String execute(Actor actor, GameMap map) {
        int num;
        ArrayList<String> speak = new ArrayList<>();
        speak.add("You might need a wrench to smash Koopa's hard shells.");
        speak.add("The Princess is depending on you! You are our only hope.");
        speak.add("Being imprisoned in these walls can drive a fungus crazy :(");
        speak.add("You better get back to finding the Power Stars.");
        if (actor.getWeapon() instanceof Wrench) {
            if (actor.hasCapability(Status.INVINCIBLE)) {
                int min = 1;
                int max = 2;
                num = rand.nextInt(max-min)+min;
            } else {
                int min = 1;
                int max = 3;
                num = rand.nextInt(max-min)+min;
            }
            return speak.get(num);
        }
        else if (actor.hasCapability(Status.INVINCIBLE)) {
            num = rand.nextInt(speak.size()-1);
            return speak.get(num);
        }
        else {
            num = rand.nextInt(speak.size());
            return speak.get(num);
        }
    }

    /**
     * Returns a description of this action suitable to display in the menu.
     * @param actor The actor which speaks to the target, aka Toad.
     * @return A description of the action suitable for the menu.
     */
    public String menuDescription (Actor actor){
        return actor + " speaks with " + target + " at " + direction;
        }

    }

