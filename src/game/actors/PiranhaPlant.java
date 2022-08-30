package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Monologue;
import game.actions.AttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;
import game.capabilities.Resettable;
import game.capabilities.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * A plant that chomps.
 */
public class PiranhaPlant extends Enemies implements Resettable {

    /**
     * Stores possible behaviours that can be implemented by this PiranhaPlant object
     */
    private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour

    /**
     * Stores the number of turns that have passed.
     */
    private int numTurns;

    /**
     * Constructor for PiranhaPlant Class
     */
    public PiranhaPlant() {
        super("Piranha Plant", 'Y', 150);
        this.behaviours.put(1, new AttackBehaviour());
        this.registerInstance();
    }

    /**
     * Sets weapon of this PiranhaPlant object when unarmed
     * @return weapon for this unarmed PiranhaPlant object
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(90, "chomps");
    }


    /**
     * Figures out what to do next.
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action to be performed by this PiranhaPlant object
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        numTurns = numTurns + 1;
        int check = numTurns % 2;
        if (check == 0) {
            Monologue.getInstance().execute(display, this);
        }

        for(Behaviour Behaviour : behaviours.values()) {
            Action action = Behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * Heals and increases MaxHp points of all PiranhaPlants on the map by 50 once reset is called.
     */
    @Override
    public void resetInstance() {
        this.increaseMaxHp(50);
    }
}
