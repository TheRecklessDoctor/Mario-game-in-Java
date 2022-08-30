package game.actors;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Monologue;
import game.actions.AttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.capabilities.Resettable;
import game.capabilities.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A little fungus guy.
 */
public class Goomba extends Enemies implements Resettable {

	/**
	 * Stores possible behaviours that can be implemented by this Goomba object
	 */
	private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour
	/**
	 * Stores FollowBehaviour target
	 */
	private Actor target = null;

	/**
	 * Stores if this Goomba object has been called to reset itself
	 */
	private boolean reset = false;

	/**
	 * Stores the number of turns that have passed.
	 */
	private int numTurns;

	/**
	 * Constructor for Goomba Class
	 */
	public Goomba() {
		super("Goomba", 'g', 20);
		this.behaviours.put(1, new AttackBehaviour());
		this.behaviours.put(10, new WanderBehaviour());
		this.registerInstance();
	}

	/**
	 * Sets weapon of this Goomba object when unarmed
	 * @return weapon for this unarmed Goomba object
	 */
	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(10, "kicks");
	}


	/**
	 * Figures out what to do next.
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return action to be performed by this Goomba object
	 */
	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

		numTurns = numTurns + 1;
		int check = numTurns % 2;
		if (check == 0) {
			Monologue.getInstance().execute(display, this);
		}

		if (this.reset){
			map.removeActor(this);
			return new DoNothingAction();
		}

		Random random = new Random();
		// random.nextInt(100) generates value between 0 to 99.
		if (random.nextInt( 100) < 10) {
			map.removeActor(this);
			return new DoNothingAction();
		}

		if (this.hasCapability(Status.PROVOKED)){
			if(target == null){
				for (Exit exit : map.locationOf(this).getExits()) {
					Location destination = exit.getDestination();
					if (destination.containsAnActor()){
						Actor possibleTarget = destination.getActor();
						if(possibleTarget instanceof Player){
							target = possibleTarget;
						}
					}
				}
			}
			Action action = behaviours.get(1).getAction(this, map);
			if (action != null){
				return action;
			}
			if (target.isConscious()){
				Behaviour followBehaviour = new FollowBehaviour(target);
				action = followBehaviour.getAction(this, map);
				if (action != null){
					return action;
				}
			}
			return new DoNothingAction();
		}

		for(Behaviour Behaviour : behaviours.values()) {
			Action action = Behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();
	}

	/**
	 * Sets reset to true
	 */
	@Override
	public void resetInstance() {
		this.reset = true;
	}
}
