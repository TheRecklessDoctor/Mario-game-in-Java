package game.plants;

import game.actions.ConsumeItemAction;
import game.items.SpecialItems;
import game.capabilities.Status;

/**
 * A flower which can be consumed by player
 */
public class FireFlower extends SpecialItems {


    /**
     * Constructor for fire flower
     *
     */
    public FireFlower() {
        super("Fire flower",'f',true);
        this.addAction(new ConsumeItemAction(this, Status.FIREATTACK));
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
        return 0;
    }
}
