package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.WeaponItem;

/**
 * A class that represents a Wrench weapon
 */
public class Wrench extends WeaponItem{

    /**
     * Constructor for Wrench Class
     * Sets object's name, displayChar, damage, verb and hitRate
     */
    public Wrench() {
        super("Wrench", 'W', 50, "destroy", 80);
    }

}
