package game.items;

import edu.monash.fit2099.engine.items.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Bottle to store water obtained from fountain
 */
public class Bottle extends Item {

    /**
     * An array list of water collected from fountains
     */
    private List<Water>  magicalWaters = new ArrayList<>();

    /**
     * constructor for Bottle class
     */
    public Bottle() {
        super("Bottle", 'B', false);
    }

    /**
     * retrieve last element in the array
     * @return last element in the array
     */
    public Water peek() {
        if (magicalWaters.isEmpty()) {
            return null;
        }
        return magicalWaters.get(magicalWaters.size() - 1);
    }

    /**
     * deletes last element in the array
     * @return last element in the array
     */
    public Water pop() {
        Water top = magicalWaters.get(magicalWaters.size() - 1);
        magicalWaters.remove(magicalWaters.size() - 1);
        return top;
    }

    /**
     * adds a new element in the array
     * @param water type of water
     */
    public void push(Water water) {
        magicalWaters.add(water);
    }

    /**
     * size of the array
     * @return size of the array
     */
    public int size() {
        return magicalWaters.size();
    }

    /**
     * checks if array is empty
     * @return true if array is empty, false otherwise
     */
    public boolean isEmpty() {
        return magicalWaters.isEmpty();
    }

    /**
     * displays the array of magicalWaters on the console menu
     * @return a string of the array of magicalWaters in the bottle
     */
    public String display(){
        if(! magicalWaters.isEmpty()){
            String returnString = "[ ";
            for (int i = magicalWaters.size() - 1; i > 0; i--) {
                returnString = returnString + magicalWaters.get(i).toString() + ", ";
            }
            returnString = returnString + magicalWaters.get(0).toString() + " ]";
            return returnString;
        } else {
            return "";
        }
    }
}
