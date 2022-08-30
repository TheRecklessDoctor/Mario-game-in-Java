package game.items;

/**
 * Water that increases base damage
 */
public class PowerWater extends Water {

    /**
     * constructor for PowerWater class
     */
    public PowerWater() {
        super("Power Water", 'a', false);
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
     * getter for increase in base damage
     * @return base damage to increase
     */
    @Override
    public int getBaseDamageIncrease() {
        return 15;
    }
}
