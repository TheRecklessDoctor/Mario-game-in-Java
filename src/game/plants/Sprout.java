package game.plants;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.JumpActorAction;
import game.actors.Goomba;
import game.capabilities.Resettable;
import game.capabilities.Status;
import game.grounds.Dirt;
import game.items.Coin;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for a Sprout object
 */
public class Sprout extends Tree implements Resettable {

    /**
     * Stores the number of turns that have passed.
     */
    private int numTurns;

    /**
     * Stores the possibility of success an Actor jumping to this Sprout object
     */
    private int successRate = 90;

    /**
     * Stores damage to be dealt if Actor's jump fails
     */
    private int fallDamage = 10;

    /**
     * Stores name of this Sprout object.
     */
    private String groundName = "Sprout";

    /**
     * Stores if this Sprout object has been called to reset itself
     */
    private boolean resetCalled = false;

    /**
     * Stores if this Sprout object has already reset
     */
    private boolean hasResetted = false;

    /**
     * A constructor for Sprout class.
     * Sets display char, numTurns to 0 and registers this Sprout object as resettable.
     */
    public Sprout() {
        super('+');
        numTurns = 0;
        this.registerInstance();
    }

    /**
     * Called once per turn, so that Sprout experiences the passage time.
     * Decides whether to spawnGoomba, becomeSapling or reset itself.
     * @param location The location of the Ground the object is located at
     */
    @Override
    public void tick(Location location) {

        numTurns = numTurns + 1;
        Random random = new Random();

        // random.nextInt(100) generates value between 0 to 99.
        if (random.nextInt( 100) < 10 & resetCalled == false) {
            spawnGoomba(location);
        }
        if (numTurns == 10) {
            becomeSapling(location);
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
            resetCalled = false; // Enables Goomba to be spawned in the next turn
        }
    }

    /**
     * Spawns a Goomba object at current location.
     * If no actor located at its current location
     * @param currentLocation The location of the Ground the object is located at
     */
    public void spawnGoomba(Location currentLocation) {
        if (!currentLocation.containsAnActor()){
            currentLocation.map().at(currentLocation.x(), currentLocation.y()).addActor(new Goomba());
        }
    }

    /**
     * Turn Sprout into a Sapling
     * @param currentLocation The location of the Ground the object is located at
     */
    public void becomeSapling(Location currentLocation){
        currentLocation.setGround(new Sapling());
        Random random = new Random();
        if(random.nextInt( 100) < 50){
            currentLocation.addItem(new FireFlower());
        }
    }

    /**
     * Possible actions that can be executed by other actors with this Sprout object
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return an ActionList of actions that can be acted by the acting Actor with this Sprout object
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
