package game.items;

import edu.monash.fit2099.engine.items.Item;

/**
 * Class for magical water
 */
public abstract class Water extends Item {

    /**
     * Constructor.
     * @param name name of water
     * @param displayChar character to display for this type of water
     * @param portable boolean that decides if water portable
     */
    public Water(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
    }

    /**
     * getter for increase in hit points
     */
    abstract public int getHpToHeal();

    /**
     * getter for increase in base damage
     */
    abstract public int getBaseDamageIncrease();

}
