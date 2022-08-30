package game.grounds;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Indestructible;
import game.capabilities.Resettable;
import game.items.Coin;

import java.util.ArrayList;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground implements Resettable, Indestructible {

	/**
	 * Stores if this Dirt object has been called to reset itself
	 */
	private boolean resetCalled = false;

	/**
	 * Stores if this Dirt object has already reset
	 */
	private boolean hasResetted = false;

	/**
	 * A constructor for Dirt class.
	 * Sets display char and registers this Dirt object as resettable.
	 */
	public Dirt() {
		super('.');
		this.registerInstance();
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
