package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Monologue;
import game.actions.BuyItemAction;
import game.actions.SpeakAction;
import game.items.PowerStar;
import game.items.SuperMushroom;
import game.items.Wrench;

/**
 * helpful character to Player
 */
public class Toad extends Actor {

    /**
     * Stores the number of turns that have passed.
     */
    private int numTurns;

    /**
     * constructor for Toad
     */
    public Toad(){
        super("Toad",'O',0);
    }

    /**
     * sets actions allowed to be done by Toad
     * @param otherActor the Actor that is buying an item from Toad
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        Item item1 = new SuperMushroom();
        actions.add(new BuyItemAction(this,item1,400));

        Item item2 = new Wrench();
        actions.add(new BuyItemAction(this,item2,200));

        Item item3 = new PowerStar();
        actions.add(new BuyItemAction(this ,item3,600));

        actions.add( new SpeakAction(this, direction));
        return actions;
    }


    /**
     * what Toad does each turn
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action that does nothing - DoNothingAction instance
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
}
