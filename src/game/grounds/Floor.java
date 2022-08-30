package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Indestructible;
import game.actors.Player;
import game.capabilities.Resettable;
import game.items.Coin;

import java.util.ArrayList;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground implements Resettable, Indestructible {

	/**
	 * Stores if this Floor object has been called to reset itself
	 */
	private boolean resetCalled = false;

	/**
	 * Stores if this Floor object has already reset
	 */
	private boolean hasResetted = false;

	/**
	 * A constructor for Floor class.
	 * Sets display char and registers this Floor object as resettable.
	 */
	public Floor() {
		super('_');
		this.registerInstance();
	}

	/**
	 * Whether actor can actor this Floor object's location
	 * @param actor the Actor to check
	 * @return true if actor can enter (actor instanceof Player); false otherwise.
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		if(actor instanceof Player){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Called once per turn, so that Floor experiences the passage time.
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
