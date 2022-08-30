package game.items;

import edu.monash.fit2099.engine.items.Item;

/**
 * A coin with a value
 */
public class Coin extends Item {

    /**
     * used to hold value of coin
     */
    int value;

    /**
     * constructor for Coin class
     * @param value value of the coin
     */
    public Coin(int value){
        super("Coin",'$',true);
        setValue(value);
    }

    //setters

    /**
     * setter for value
     * @param value holds coin value
     */
    public void setValue(int value){
        this.value = value;
    }
    //getters

    /**
     * getter for value
     * @return value of coin
     */
    public int getValue(){
        return this.value;
    }
}
