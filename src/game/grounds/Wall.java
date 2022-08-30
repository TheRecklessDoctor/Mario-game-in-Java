package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.JumpActorAction;
import game.capabilities.FlyCapable;
import game.capabilities.Resettable;
import game.capabilities.Status;
import game.items.Coin;

import java.util.ArrayList;

/**
 * A class that represents a wall.
 */
public class Wall extends Ground implements Resettable {

	/**
	 * Stores the possibility of success an Actor jumping to this Wall object
	 */
	private int successRate = 80;

	/**
	 * Stores damage to be dealt if Actor's jump fails
	 */
	private int fallDamage = 20;

	/**
	 * Stores name of this Wall object.
	 */
	private String groundName = "Wall";

	/**
	 * Stores if this Wall object has been called to reset itself
	 */
	private boolean resetCalled = false;

	/**
	 * Stores if this Wall object has already reset
	 */
	private boolean hasResetted = false;

	/**
	 * A constructor for Wall class.
	 * Sets display char and registers this Wall object as resettable.
	 */
	public Wall() {
		super('#');
		this.registerInstance();
	}

	/**
	 * Whether actor can enter this Wall object's location
	 * @param actor the Actor to check
	 * @return true if actor can enter (actor.hasCapability(Status.INVINCIBLE)); false otherwise.
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		if (actor.hasCapability(Status.INVINCIBLE)  | actor instanceof FlyCapable) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * NA (Wall blocks objects thrown at player by enemies?)
	 * @return (true)
	 */
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}

	/**
	 * Possible actions that can be executed by actors with this object
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
	 * Called once per turn, so that Wall experiences the passage time.
	 * Decides whether to reset itself.
	 * @param location The location of the Ground the object is located at
	 */
	@Override
	public void tick(Location location) {
		if(resetCalled & !hasResetted){
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
	 * Set resetCalled to true
	 */
	@Override
	public void resetInstance() {
		resetCalled = true;
	}
}
