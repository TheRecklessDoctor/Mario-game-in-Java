package game.items;

/**
 * Water that increases hit points
 */
public class HealthWater extends Water {

    /**
     * constructor for HealthWater class
     */
    public HealthWater() {
        super("Health Water", 'h', false);
    }

    /**
     * getter for increase in hit points
     * @return hit points to increase
     */
    @Override
    public int getHpToHeal() {
        return 50;
    }

    /**
     * getter for increase in base damage
     * @return base damage to increase
     */
    @Override
    public int getBaseDamageIncrease() {
        return 0;
    }
}
