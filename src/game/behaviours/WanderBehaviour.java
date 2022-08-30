package game.behaviours;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.behaviours.Behaviour;

/**
 *  * A class that figures out a MoveAction that will move the actor one step in a random direction.
 */
public class WanderBehaviour extends Action implements Behaviour {

	private final Random random = new Random();

	/**
	 * Returns a MoveAction to wander to a random location, if possible.  
	 * If no movement is possible, returns null.
	 *
	 * @param actor the Actor enacting the behaviour
	 * @param map the map that actor is currently on
	 * @return an Action, or null if no MoveAction is possible
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		ArrayList<Action> actions = new ArrayList<Action>();

		for (Exit exit : map.locationOf(actor).getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
			}
		}

		if (!actions.isEmpty()) {
			return actions.get(random.nextInt(actions.size()));
		}
		else {
			return null;
		}

	}

	/**
	 * Performs the action.
	 * @param actor The actor performing the action.
	 * @param map Map where the actor performing the action is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		return menuDescription(actor);
	}

	/**
	 * Returns a sentence said by the actor performing the action.
	 * @param actor The actor performing the action.
	 * @return a sentence said by the actor performing the action.
	 */
	@Override
	public String menuDescription(Actor actor) {
		return "Raagrh...";
	}
}
