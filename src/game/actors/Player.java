package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.DrinkWaterAction;
import game.actions.ResetMapAction;
import game.capabilities.Indestructible;
import game.capabilities.Resettable;
import game.capabilities.Status;
import game.grounds.Dirt;
import game.items.Bottle;
import game.items.Coin;
import game.items.Wrench;

import java.util.ArrayList;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable {


	private final Menu menu = new Menu();

	/**
	 * Stores if this Player object has been called to reset itself
	 */
	private boolean reset = false;

	/**
	 * Used in displaying the appropriate messages
	 */
	private static Display display = new Display();
	/**
	 * Stores the total value of all coins collected by Player. Initialised to 0 in its declaration
	 */
	private int wallet = 0;

	/**
	 * Stores the number of turns of the power star after being consumed. Initialised to 0 in its declaration
	 */
	private int numTurnsPowerStar = 0;

	/**
	 * Stores the number of turns of the fire attack after the fire flower is consumed. Initialised to 0 in its declaration
	 */
	private int numTurnsFireAttack = 0;

	private int intrinsicDamage = 5;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		this.registerInstance();
		this.addItemToInventory(new Bottle());
	}

	//setters
	/**
	 * setter for wallet
	 * @param wallet integer that represents and stores value of total of coins collected by the player
	 */
	public void setWallet(int wallet){
		this.wallet = wallet;
	}


	/**
	 * getter for wallet
	 * @return integer that represents the total of coins collected by the player
	 */
	public int getWallet(){
		return this.wallet;
	}

	public int getIntrinsicDamage(){
		return this.intrinsicDamage;
	}

	/**
	 * sets a new value for the default intrinsic damage
	 * @param intrinsicDamage
	 */
	public void setIntrinsicDamage(int intrinsicDamage) {
		this.intrinsicDamage = intrinsicDamage;
	}

	/**
	 * Stuff the player can do.
	 * @param actions List of actions the player can carry out.
	 * @param lastAction The action the player carried out in the previous turn.
	 * @param map The map the actor is on.
	 * @param display Receives inputs from user or prints suitable sentences in the I/O.
	 * @return the next Action in a series if it is not null.
	 * @return options for the user to choose from in the console menu.
	 */
	 @Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		 display.println(this + "'s HP: " + this.printHp() + "\t \t \t \t \t Wallet: " + getWallet());

		 if (lastAction.getNextAction() != null)
			 return lastAction.getNextAction();

		 this.updateWallet();
		 this.invincible(map);
		 this.highGround(map);
		 this.fireAttack();

		 if(!this.getBottle().isEmpty()){
			 actions.add(new DrinkWaterAction(this.getBottle()));
		 }
		 if(!reset){
			 actions.add(new ResetMapAction());
		 }
			// return/print the console menu
		 return menu.showMenu(this, actions, display);
	}

	/**
	 * Updates the wallet
	 */
	public void updateWallet(){
		ArrayList<Item> toRemove = new ArrayList<>();

		for (Item item: this.getInventory() ){
			if (item instanceof Coin){
				toRemove.add(item);
				setWallet(getWallet()+ ((Coin) item).getValue());
			}
		}

		for (Item item: toRemove){
			this.removeItemFromInventory(item);
		}
	}

	/**
	 * Changes to the game map, keeps track and prints suitable descriptions in the console menu when the player is invincible
	 * @param map the map where the player is currently on
	 */
	public void invincible(GameMap map){
		if(this.hasCapability(Status.INVINCIBLE)){
			this.numTurnsPowerStar = this.numTurnsPowerStar + 1;
			display.println("Mario is INVINCIBLE!");
			display.println(this + " consumed Power Star - " + (10 -this.numTurnsPowerStar) + " turns remaining");
			if(this.numTurnsPowerStar == 10){
				this.removeCapability(Status.INVINCIBLE);
				this.numTurnsPowerStar = 0;
			}
		}
		if (this.hasCapability(Status.INVINCIBLE)){
			if(!(map.locationOf(this).getGround() instanceof Indestructible)){
				map.locationOf(this).addItem(new Coin(5));
				map.locationOf(this).setGround(new Dirt());
			}
		}
	}

	/**
	 * Prints a suitable description in the console menu when the player is on higher ground
	 * @param map the map where the player is currently on
	 */
	public void highGround(GameMap map){
		Location currentlocation = map.locationOf(this);
		if (!currentlocation.getGround().canActorEnter(this)){
			display.println("Current High Ground Location: " +  "( " + currentlocation.x() + "," + currentlocation.y() + " ).");
		}
	}

	/**
	 * Keeps track and prints suitable descriptions in the console menu when the player can use fire attack action
	 */
	public void fireAttack(){
		if(this.hasCapability(Status.FIREATTACK)){
			this.numTurnsFireAttack = this.numTurnsFireAttack + 1;
			display.println(this + " consumed Fire Flower - " + (20 -this.numTurnsFireAttack) + " turns remaining for Fire Attack");
			if(this.numTurnsFireAttack == 20){
				this.removeCapability(Status.FIREATTACK);
				this.numTurnsFireAttack = 0;
			}
		}
	}

	/**
	 * Displays small letter m or capital letter M depending on if the status of the actor is TALL.
	 * @return capital letter M if the status of the actor is TALL or small letter m if the status of the player is not TALL.
	 */
	@Override
	public char getDisplayChar(){
		return this.hasCapability(Status.TALL) ? Character.toUpperCase(super.getDisplayChar()): super.getDisplayChar();
	}

	/**
	 * Set reset to true
	 */
	@Override
	public void resetInstance() {
		reset = true;
		this.removeCapability(Status.TALL);
		this.removeCapability(Status.INVINCIBLE);
		this.heal(this.getMaxHp());
		this.numTurnsPowerStar = 0;
		this.numTurnsFireAttack = 0;
	}


	/**
	 * Get bottle
	 * @return bottle if available
	 */public Bottle getBottle(){
		for (Item item: this.getInventory() ){
			if (item instanceof Bottle){
				return ((Bottle) item);
			}
		}
		return null;
	}

	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		 return new IntrinsicWeapon(this.getIntrinsicDamage(),"punches");
	}
}