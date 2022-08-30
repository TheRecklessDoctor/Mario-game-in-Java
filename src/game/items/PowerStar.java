package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Capable;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Printable;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;
import game.actions.ConsumeItemAction;

/**
 * Magical Item
 */
public class PowerStar extends SpecialItems implements Printable, Capable {

    /**
     * integer variable for the number of turns before disappearing
     */
    int numTurns;
    /**
     * new instance of display to print number of turns remaining
     */
    private static Display display = new Display();

    /**
     * constructor for power star
     */
    public PowerStar(){
        super("Power Star",'*',true);
        this.addAction( new ConsumeItemAction(this, Status.INVINCIBLE));
    }

    /**
     * setter for number of turns till power star disappears
     * @param numTurns
     */
    public void setNumTurns(int numTurns){
        this.numTurns = numTurns;
    }

    //getters


    /**
     *getter for number of turns
     * @return int var showing number of turns remaining
     */
    public int getNumTurns(){
        return numTurns;
    }


    //methods

    /**
     * method to increment, calculate and display number of turns remaining
     * @param location instance of Location to remove item from that location if 10 turns have passed
     */
    public void tick(Location location){
        this.setNumTurns(this.getNumTurns()+1);
        display.println("\nPower Star - " + (10 -this.getNumTurns()) + " turns remaining");
        if(this.getNumTurns() == 10){
            location.removeItem(this);
        }
    }

    /**
     * method to increment, calculate and display number of turns remaining but this is used if the item is already in the actor's inventory
     * @param location
     * @param actor The actor carrying this Item.
     */
    public void tick(Location location, Actor actor){
        this.setNumTurns(this.getNumTurns()+1);
        display.println("\nPower Star in Inventory - " + (10 -this.getNumTurns()) + " turns remaining");
        if(this.getNumTurns() == 10){
            actor.removeItemFromInventory(this);
        }
    }

    /**
     * getter for increase in hit points
     * @return hit points to increase
     */
    @Override
    public int getHpToHeal() {
        return 200;
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
