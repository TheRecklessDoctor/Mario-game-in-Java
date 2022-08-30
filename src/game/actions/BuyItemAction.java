package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actors.Player;

/**
 * Action to buy items
 */
public class BuyItemAction extends Action {

    /**
     * actor to buy items from
     */
    Actor buyFromActor;
    /**
     * item to buy
     */
    Item item;
    /**
     * price of item
     */
    int priceOfItem;


    /**
     * constructor for BuyItemAction
     * @param buyFromActor actor to buy from
     * @param item item to be bought
     * @param priceOfItem price of item
     */
    public BuyItemAction(Actor buyFromActor, Item item, int priceOfItem){
        setBuyFromActor(buyFromActor);
        setItem(item);
        setPriceOfItem(priceOfItem);
    }

    //setters

    /**
     * setter for buyFromActor
     * @param buyFromActor actor to buy from
     */
    public void setBuyFromActor(Actor buyFromActor){
        this.buyFromActor = buyFromActor;
    }

    /**
     * setter for item
     * @param item item to buy
     */
    public void setItem(Item item){
        this.item = item;
    }

    /**
     * setter for priceOfItem
     * @param priceOfItem item price
     */
    public void setPriceOfItem(int priceOfItem){
        this.priceOfItem = priceOfItem;
    }

    //methods

    /**
     * actions execute method
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string message showing success
     */
    public String execute(Actor actor, GameMap map){
        int walletValue = ((Player)actor).getWallet();
        if(walletValue>=priceOfItem){
            ((Player)actor).setWallet(walletValue-priceOfItem);
        }else{
            return "You don't have enough coins!";
        }
        if(!(item instanceof WeaponItem)){
            item.togglePortability();
        }

        actor.addItemToInventory(item);

        return this.menuDescription(actor);
    }

    /**
     * returns description of completed action
     * @param actor The actor performing the action.
     * @return string message
     */
    public String menuDescription(Actor actor){
        return actor + " buys " + item +" for $" + priceOfItem;
    }
}

