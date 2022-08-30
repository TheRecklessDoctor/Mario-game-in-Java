package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.SpecialItems;

/**
 * action to consume an item
 */
public class ConsumeItemAction extends Action {
    /**
     * item to be consumed
     */
    private SpecialItems toConsume;

    /**
     * capabilities player will obtain
     */
    private Enum<?> capability;

    /**
     * capabilities player will obtain after consuming item
     * @param toConsume item to be consumed
     * @param capability capabilities player will obtain
     */
    public ConsumeItemAction(SpecialItems toConsume, Enum<?> capability){
        this.toConsume = toConsume;
        this.capability = capability;
    }


    /**
     * getter for toConsume
     * @return instance of item
     */
    public Item getToConsume(){
        return this.toConsume;
    }


    //methods

    /**
     * actions execute method
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return appropriate string
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        actor.increaseMaxHp(toConsume.getMaxHpIncrease());
        actor.heal(toConsume.getHpToHeal());
        actor.addCapability(capability);

        //remove item from inventory and location
        actor.removeItemFromInventory(toConsume);
        map.locationOf(actor).removeItem(toConsume);

        return menuDescription(actor);

    }

    /**
     * returns a success message for consumption
     * @param actor The actor performing the action.
     * @return string showing success
     */
    public String menuDescription(Actor actor){
        return actor +" consumes " +this.getToConsume();
    }
}
