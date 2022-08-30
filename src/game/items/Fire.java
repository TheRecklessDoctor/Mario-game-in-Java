package game.items;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Fire that is used by player and deals damage to enemies
 */
public class Fire extends Item {

    /**
     * Stores the number of turns that have passed.
     */
    private int numTurns = 0;

    /**
     * Stores damage dealt by fire onto enemies if attacked by player
     */
    private int damage = 20;

    /**
     * constructor for Fire class
     */
    public Fire() {
        super("Fire", 'v', false);
    }

    /**
     * Called once per turn, so that Fire experiences the passage time.
     * Removed after 3 turns
     * @param currentLocation The location of the Ground the object is located at
     */
    @Override
    public void tick(Location currentLocation) {
        if(numTurns == 3){
            currentLocation.removeItem(this);
        }

        this.dealDamage(currentLocation);
        numTurns = numTurns + 1;
    }

    /**
     * Deals damage to enemies at the current location by dropping fire at the enemies' location
     * Removes unconscious actor and drops all items
     * @param currentLocation The location of the Ground the object is located at
     */
    public void dealDamage(Location currentLocation){
        if(currentLocation.containsAnActor()){
            currentLocation.getActor().hurt(damage);
            if (!currentLocation.getActor().isConscious()){
                ActionList dropActions = new ActionList();
                // drop all items
                for (Item item : currentLocation.getActor().getInventory())
                    dropActions.add(item.getDropAction(currentLocation.getActor()));
                for (Action drop : dropActions)
                    drop.execute(currentLocation.getActor(), currentLocation.map());
                // remove actor
                currentLocation.map().removeActor(currentLocation.getActor());
            }
        }
    }
}
