package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.PiranhaPlant;
import game.actions.JumpActorAction;
import game.actions.TeleportActorAction;
import game.capabilities.Indestructible;
import game.capabilities.Resettable;
import game.capabilities.Status;

/**
 * A class that represents WarpPipe which allows player to teleport to another map
 */
public class WarpPipe extends Ground implements Resettable, Indestructible {

    /**
     * Stores the possibility of success an Actor jumping to this Wall object
     */
    private int successRate = 100;

    /**
     * Stores damage to be dealt if Actor's jump fails
     */
    private int fallDamage = 0;

    /**
     * Stores name of this WarpPipe object.
     */
    private String groundName = "Warp Pipe";

    /**
     * Stores the number of turns that have passed.
     */
    private int numTurns = 1;

    /**
     * A constructor for WarpPipe class.
     * Sets display char and registers this WarpPipe object as resettable.
     */
    public WarpPipe() {
        super('C');
        this.registerInstance();
    }

    /**
     * Whether actor can enter this WarpPipe object's location
     * @param actor the Actor to check
     * @return true if actor can enter (actor.hasCapability(Status.INVINCIBLE)); false otherwise.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        if (actor.hasCapability(Status.INVINCIBLE)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Called once per turn, so that WarpPipe experiences the passage time.
     * Decides whether to spawn piranha plant
     * @param location The location of the Ground the object is located at
     */
    @Override
    public void tick(Location location) {
        if(numTurns == 1){
            spawnPiranhaPlant(location);
        }
        numTurns = numTurns + 1;
    }

    /**
     * Spawns piranha plant if there's no actor on it
     * @param location The location of the Ground the object is located at
     */
    public void spawnPiranhaPlant(Location location){
        if(!location.containsAnActor()){
            location.addActor(new PiranhaPlant());
        }
    }

    /**
     * Returns actions that can be performed by actor on this WarpPipe object
     * @param actor the Actor that might perform an action.
     * @param location String representing the location of the Actor
     * @param direction  String representing the direction of the warp pipe
     * @return list of actions
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        if (location == location.map().locationOf(actor)){
            actions.add(new TeleportActorAction(location));
        }
        if (actor.hasCapability(Status.INVINCIBLE)){
            return new ActionList();
        } else {
            if (location != location.map().locationOf(actor)){
                actions.add(new JumpActorAction(location, direction, successRate, fallDamage, groundName));
            }
            return actions;
        }
    }

    /**
     * Set numTurns to 0
     */
    @Override
    public void resetInstance() {
        numTurns = 0;
    }
}
