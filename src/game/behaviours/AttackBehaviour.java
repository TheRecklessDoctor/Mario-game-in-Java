package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.actors.Player;
import game.capabilities.Status;
import game.grounds.Dirt;

/**
 * A class that figures out an AttackAction that will attack a target actor
 */
public class AttackBehaviour implements Behaviour {

    /**
     * Returns an attack action if an Actor instanceof Player is in any of its adjacent square.
     * Will perform check if an Actor instanceof Player is in any of this actor's adjacent square.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return An attack action if an Actor instanceof Player is in any of its adjacent square. Else null.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()){
                Actor possibleTarget = destination.getActor();
                if( (possibleTarget instanceof Player) & (destination.getGround() instanceof Dirt)){
                    Action action = new AttackAction(possibleTarget,exit.getName());
                    if(!actor.hasCapability(Status.PROVOKED)){
                        actor.addCapability(Status.PROVOKED);
                    }
                    return action;
                }
            }
        }
        return null;
    }
}
