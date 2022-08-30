package game.plants;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.JumpActorAction;
import game.actors.FlyingKoopa;
import game.actors.Koopa;
import game.capabilities.Resettable;
import game.capabilities.Status;
import game.grounds.Dirt;
import game.items.Coin;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for a Mature object
 */
public class Mature extends Tree implements Resettable {

    /**
     * Stores the number of turns that have passed.
     */
    private int numTurns;

    /**
     * Stores the possibility of success an Actor jumping to this Mature object
     */
    private int successRate = 70;

    /**
     * Stores damage to be dealt if Actor's jump fails
     */
    private int fallDamage = 30;

    /**
     * Stores name of this Mature object.
     */
    private String groundName = "Mature";

    /**
     * Stores if this Mature object has been called to reset itself
     */
    private boolean resetCalled = false;

    /**
     * Stores if this Mature object has already reset
     */
    private boolean hasResetted = false;

    /**
     * A constructor for Mature class.
     * Sets display char, numTurns to 0 and registers this Mature object as resettable.
     */
    public Mature(){
        super('T');
        numTurns = 0;
        this.registerInstance();
    }

    /**
     * Called once per turn, so that Mature experiences the passage time.
     * Decides whether to spawnKoopa, becomeDirt,growSprout or reset itself.
     * @param location The location of the Ground the object is located at
     */
    @Override
    public void tick(Location location) {

        numTurns = numTurns + 1;
        Random random = new Random();

        // random.nextInt(100) generates value between 0 to 99.
        if (random.nextInt( 100) < 15 & resetCalled == false) {
            if ( random.nextInt( 100) < 50 ){
                spawnKoopa(location);
            } else {
                spawnFLyingKoopa(location);
            }
        }
        if (numTurns == 5) {
            growSprout(location);
        }
        if (random.nextInt( 100) < 20 ) {
            becomeDirt(location);
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
            resetCalled = false; // Enables Koopa to be spawned in the next turn
        }
    }

    /**
     * Spawns a Koopa object at current location.
     * If no actor located at its current location
     * @param currentLocation The location of the Ground the object is located at
     */
    public void spawnKoopa( Location currentLocation ){
        if (!currentLocation.containsAnActor()){
            currentLocation.map().at(currentLocation.x(), currentLocation.y()).addActor(new Koopa());
        }
    }

    /**
     * Spawns a FlyingKoopa object at current location.
     * If no actor located at its current location
     * @param currentLocation The location of the Ground the object is located at
     */
    public void spawnFLyingKoopa(Location currentLocation ){
        if (!currentLocation.containsAnActor()){
            currentLocation.map().at(currentLocation.x(), currentLocation.y()).addActor(new FlyingKoopa());
        }
    }

    /**
     * Grow sprout at adjacent empty locations of this Mature object
     * @param currentLocation The location of the Ground the object is located at
     */
    public void growSprout( Location currentLocation ){

        Random random = new Random();
        ArrayList<Location> possibleGrowLocations = new ArrayList<Location>();

        for (Exit exit:  currentLocation.getExits()){
            Location destination = exit.getDestination();
            if(destination.getGround() instanceof Dirt & !destination.containsAnActor()){
                possibleGrowLocations.add(destination);
            }
        }
        if (!possibleGrowLocations.isEmpty()) {
            int growLocationIndex = random.nextInt(possibleGrowLocations.size());
            Location growLocation = possibleGrowLocations.get(growLocationIndex);
            growLocation.setGround(new Sprout());
        }
        numTurns = 0;
    }

    /**
     * Turns this Mature object to a Dirt object
     * @param currentLocation The location of the Ground the object is located at
     */
    public void becomeDirt( Location currentLocation ) {
        currentLocation.setGround(new Dirt());
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
