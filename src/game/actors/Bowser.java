package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Monologue;
import game.actions.AttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;
import game.behaviours.FollowBehaviour;
import game.capabilities.Resettable;
import game.capabilities.Status;
import game.items.PrincessKey;

import java.util.HashMap;
import java.util.Map;

/**
 * Dinosaur.
 */
public class Bowser extends Enemies implements Resettable {

    /**
     * Stores FollowBehaviour target
     */
    private Actor target = null;

    /**
     * Stores if this Bowser object has been called to reset itself
     */
    private boolean resetCalled = false;

    /**
     * Stores if this Bowser object has been already reset
     */
    private boolean hasResseted = false;

    /**
     * Stores possible behaviours that can be implemented by this Bowser object
     */
    private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour

    /**
     * Stores the number of turns that have passed.
     */
    private int numTurns;

    /**
     * Constructor for Bowser Class
     */
    public Bowser() {
        super("Bowser", 'B', 500);
        this.behaviours.put(1, new AttackBehaviour());
        this.addCapability(Status.FIREATTACK);
        this.addItemToInventory(new PrincessKey());
        this.registerInstance();
    }

    /**
     * Sets weapon of this Bowser object when unarmed
     * @return weapon for this unarmed Bowser object
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(80, "punch");
    }

    /**
     * Figures out what to do next.
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action to be performed by this Bowser object
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        numTurns = numTurns + 1;
        int check = numTurns % 2;
        if (check == 0) {
            Monologue.getInstance().execute(display, this);
        }

        if (this.resetCalled & !this.hasResseted){
            if (map.at(0,6).containsAnActor()) {
                if (map.at(0, 6).getActor() != this){
                    map.removeActor(map.at(0, 6).getActor());
                    map.moveActor(this, map.at(0, 6));
                }
            } else{
                map.moveActor(this, map.at(0, 6));
            }
            this.heal(this.getMaxHp());
            this.target = null;
            this.removeCapability(Status.PROVOKED);
            this.hasResseted = true;
            return new DoNothingAction();
        }

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

        if (this.hasCapability(Status.PROVOKED)){
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
        resetCalled = true;
    }
}
