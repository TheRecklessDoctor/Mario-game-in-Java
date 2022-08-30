package game.actions;

import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.items.Fire;
import game.capabilities.HideCapable;
import game.actors.Player;
import game.capabilities.Status;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;

	/**
	 * The direction of incoming attack.
	 */
	protected String direction;

	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 *
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	/**
	 * Deal damage to target, if certain criteria are met.
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of the Action performed
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		if(target.hasCapability(Status.INVINCIBLE)){
			return target + " is INVINCIBLE. No damage dealt by " + actor + ".";
		}

		if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result;

		if ( (!(target instanceof Player)) & (!target.hasCapability(Status.PROVOKED))){
			target.addCapability(Status.PROVOKED);
		}

		if(target.hasCapability(Status.TALL)){
			target.removeCapability(Status.TALL);
		}

		if(actor.hasCapability(Status.INVINCIBLE)){
			int dealtDamage = 0;
			while(target.isConscious()){
				target.hurt(damage);
				dealtDamage += damage;
			}
			result = actor + " " + weapon.verb() + " " + target + " for " + dealtDamage + " damage.";
		} else if(actor.hasCapability(Status.FIREATTACK)){
			target.hurt(damage);
			map.locationOf(target).addItem(new Fire());
			result = actor + " " + weapon.verb() + " & burns " + target + " for " + damage + " damage.";
		} else {
			target.hurt(damage);
			result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		}

		if (!target.isConscious()) {

			if ( (target instanceof HideCapable) & (!target.hasCapability(Status.HIDE)) ){
				target.resetMaxHp(50);
				target.addCapability(Status.HIDE);
				result += "Koopa is now hiding in its shell.";
				return result;
			}

			ActionList dropActions = new ActionList();
			// drop all items
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction(actor));
			for (Action drop : dropActions)
				drop.execute(target, map);
			// remove actor
			map.removeActor(target);

			if(target instanceof HideCapable){
				result +=  System.lineSeparator() + target + "'s shell has been destroyed.";
			} else {
				result += System.lineSeparator() + target + " is killed.";
			}
		}

		return result;
	}

	/**
	 * Returns a description of the attack action suitable to display in the menu.
	 * @param actor The actor performing the action.
	 * @return a String, e.g. "Player attacks Goomba at east"
	 */
	@Override
	public String menuDescription(Actor actor) {
		if(actor.hasCapability(Status.FIREATTACK)){
			return actor + " attacks " + target + " at " + direction + " with fire!";
		}
		return actor + " attacks " + target + " at " + direction;
	}
}
