package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Player;

/**
 * A class that represents Lava which will deal damage if player steps on it.
 */
public class Lava extends Ground{

    /**
     * Stores damage dealt by lava onto player if player steps on it
     */
    private int damage = 15;

    /**
     * A constructor for Lava class.
     */
    public Lava() {
        super('L');
    }

    /**
     * Whether actor can enter this Lava object's location
     * @param actor the Actor to check
     * @return true if actor is player; false otherwise.
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
     * Called once per turn, so that Lava experiences the passage time.
     * @param location The location of the Ground the object is located at
     */
    @Override
    public void tick(Location location) {
        this.dealDamage(location);
    }

    /**
     * Deals damage to those that can enter this Lava object's location
     * Removes unconscious player
     * @param location The location of the Ground the object is located at
     */
    public void dealDamage(Location location){
        if(location.containsAnActor()){
            location.getActor().hurt(damage);
            if (!location.getActor().isConscious()){
                location.map().removeActor(location.getActor());
            }
        }
    }

}
