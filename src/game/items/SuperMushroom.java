package game.items;

import game.actions.ConsumeItemAction;
import game.capabilities.Status;
import game.items.SpecialItems;

/**
 * Magic mushroom
 */
public class SuperMushroom extends SpecialItems {


    /**
     * constructor for magic mushroom
     */
    public SuperMushroom(){
        super("Super Mushroom",'^',true);
        this.addAction(new ConsumeItemAction(this, Status.TALL));
    }


    /**
     * getter for increase in hit points
     * @return hit points to increase
     */
    @Override
    public int getHpToHeal() {
        return 0;
    }

    /**
     * getter for increase in max hit points
     * @return max hit points to increase
     */
    @Override
    public int getMaxHpIncrease() {
        return 50;
    }
}
