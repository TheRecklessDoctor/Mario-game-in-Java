package game.plants;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.JumpActorAction;
import game.capabilities.Resettable;
import game.capabilities.Status;
import game.grounds.Dirt;
import game.items.Coin;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for a Sapling object
 */
public class Sapling extends Tree implements Resettable {

    /**
     * Stores the number of turns that have passed.
     */
    private int numTurns;

    /**
     * Stores the possibility of success an Actor jumping to this Sapling object
     */
    private int successRate = 80;

    /**
     * Stores damage to be dealt if Actor's jump fails
     */
    private int fallDamage = 20;

    /**
     * Stores name of this Sapling object.
     */
    private String groundName = "Sapling";

    /**
     * Stores if this Sapling object has been called to reset itself
     */
    private boolean resetCalled = false;

    /**
     * Stores if this Sapling object has already reset
     */
    private boolean hasResetted = false;

    /**
     * Number of fire flowers grown
     */
    private int numFlowers = 0;

    /**
     * A constructor for Sapling class.
     * Sets display char, numTurns to 0 and registers this Sapling object as resettable.
     */
    public Sapling() {
        super('t');
        numTurns = 0;
        this.registerInstance();
    }

    /**
     * Called once per turn, so that Sapling experiences the passage time.
     * Decides whether to dropCoin, becomeMature or reset itself.
     * @param location The location of the Ground the object is located at
     */
    @Override
    public void tick(Location location) {

        numTurns = numTurns + 1;
        Random random = new Random();

        // random.nextInt(100) generates value between 0 to 99.
        if (random.nextInt( 100) < 10) {
            dropCoin(location);
        }
        if (numTurns == 10) {
            becomeMature(location);
        }

        if(resetCalled & !hasResetted){
            if (random.nextInt( 100) < 50) {
                location.setGround(new Dirt());
            }
            ArrayList<Item> toRemove = new ArrayList<Item>();
            for(Item item: location.getItems()){
                if(item instanceof Coin){
                    toRemove.add(item);
                }
            }
            if(!toRemove.isEmpty()){
                for ( Item item: toRemove){
                    location.removeItem(item);
                }
            }
            hasResetted = true;
        }
    }

    /**
     * Add a coin item to Sapling's current location.
     * @param currentLocation The location of the Ground the object is located at
     */
    public void dropCoin (Location currentLocation ) {
        currentLocation.addItem(new Coin(20));
    }

    /**
     * Turn Sapling into a Mature
     * @param currentLocation The location of the Ground the object is located at
     */
    public void becomeMature(Location currentLocation ) {
        currentLocation.setGround(new Mature());
        Random random = new Random();
        if(random.nextInt( 100) < 50){
            currentLocation.addItem(new FireFlower());
        }
    }

    /**
     * Possible actions that can be executed by other actors with this object
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return an ActionList of actions that can be acted by the acting Actor with this object
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        if (actor.hasCapability(Status.INVINCIBLE)){
            return new ActionList();
        } else {
            ActionList actions = new ActionList();
            if (location != location.map().locationOf(actor)){
                actions.add(new JumpActorAction(location, direction, successRate, fallDamage, groundName));
            }
            return actions;
        }
    }

    /**
     * Set resetCalled to true
     */
    @Override
    public void resetInstance() {
        resetCalled = true;
    }
}
