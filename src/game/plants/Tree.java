package game.plants;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import game.capabilities.FlyCapable;
import game.capabilities.Status;

/**
 * Class for Tree object
 */
public abstract class Tree extends Ground {
    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     */
    public Tree(char displayChar) {
        super(displayChar);
    }

    /**
     * Whether actor can actor this Tree object's location
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
}
