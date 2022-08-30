package game.items;

import edu.monash.fit2099.engine.items.Item;

/**
 * Class for special items
 */
public abstract class SpecialItems extends Item {

    /**
     * Constructor.
     * @param name name of the special item
     * @param displayChar character to display for this type of special item
     * @param portable boolean that decides if the special item portable
     */
    public SpecialItems(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
    }

    /**
     * getter for increase in hit points
     */
    abstract public int getHpToHeal();

    /**
     * getter for increase in max hit points
     */
    abstract public int getMaxHpIncrease();

}
