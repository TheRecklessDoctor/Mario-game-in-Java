package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Monologue;
import game.actions.FreePrincessAction;
import game.items.PrincessKey;

import java.util.List;

/**
 * A princess waiting to be saved by Mario.
 */
public class PrincessPeach extends Actor {

    /**
     * Stores the number of turns that have passed.
     */
    private int numTurns;

    /**
     * Constructor for PrincessPeach Class
     */
    public PrincessPeach() {
        super("Princess Peach", 'P', 0);
    }

    /**
     * Returns actions that can be performed by otherActor on this PrincessPeach object
     * @param otherActor the Actor that might perform an action.
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if(checkForKey(otherActor)){
            actions.add(new FreePrincessAction(this));
        }
        return actions;
    }

    /**
     * Figures out what to do next.
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action to be performed by this PrincessPeach object
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        numTurns = numTurns + 1;
        int check = numTurns % 2;
        if (check == 0) {
            Monologue.getInstance().execute(display, this);
        }
        return new DoNothingAction();
    }

    /**
     * Checks if the player has a key in its inventory
     * @param otherActor the actor(player) which will be able to free Princess Peach
     * @return true if the player has a key and false if the player doesn't have a key
     */
    public boolean checkForKey(Actor otherActor){
        List<Item> inventory = otherActor.getInventory();
        for (Item item: inventory){
            if (item instanceof PrincessKey){
                return true;
            }
        }
        return false;
    }
}
