package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class to display a suitable monologue depending on which actors ar instantiated
 */
public class Monologue {

    /**
     * Picks random value in a range.
     */
    protected Random rand = new Random();

    /**
     * A singleton monologue instance
     */
    private static Monologue instance;

    /**
     * Get the singleton instance of monologue
     * @return Monologue singleton instance
     */
    public static Monologue getInstance(){
        if(instance == null){
            instance = new Monologue();
        }
        return instance;
    }

    /**
     * Empty Constructor
     */
    private Monologue(){
    }

    /**
     * Creates an array with sentences which Actor will say, sentences are to be said randomly where certain sentences will not be said if conditions are fulfilled.
     * @param actor Actor which speaks
     * @return A sentence randomly, depending on if conditions are fulfilled.
     */
    public String getMonologue(Actor actor){
        int num;
        ArrayList<String> monologue = new ArrayList<>();

        monologue.add("Dear Mario, I'll be waiting for you...");
        monologue.add("Never gonna give you up!");
        monologue.add("Release me, or I will kick you!");
        monologue.add("What was that sound? Oh, just a fire.");
        monologue.add("Princess Peach! You are formally invited... to the creation of my new kingdom!");
        monologue.add("Never gonna let you down!");
        monologue.add("Wrrrrrrrrrrrrrrrryyyyyyyyyyyyyy!!!!");
        monologue.add("Mugga mugga!");
        monologue.add("Ugha ugha... (Never gonna run around and desert you...)");
        monologue.add("Ooga-Chaka Ooga-Ooga!");
        monologue.add("Never gonna make you cry!");
        monologue.add("Koopi koopi koopii~!");
        monologue.add("Pam pam pam!");
        monologue.add("Slsstssthshs~! (Never gonna say goodbye~)");
        monologue.add("Ohmnom nom nom nom.");
        monologue.add("You might need a wrench to smash Koopa's hard shells.");
        monologue.add("The Princess is depending on you! You are our only hope.");
        monologue.add("Being imprisoned in these walls can drive a fungus crazy :(");
        monologue.add("You better get back to finding the Power Stars.");

        if (actor instanceof PrincessPeach) {
            int min = 0;
            int max = 3;
            num = rand.nextInt(max - min) + min;
        } else if (actor instanceof Bowser) {
            int min = 3;
            int max = 7;
            num = rand.nextInt(max - min) + min;
        } else if (actor instanceof Goomba) {
            int min = 7;
            int max = 10;
            num = rand.nextInt(max - min) + min;
        } else if (actor instanceof Koopa) {
            int min = 10;
            int max = 12;
            num = rand.nextInt(max - min) + min;
        }
        else if (actor instanceof FlyingKoopa) {
            int min = 10;
            int max = 13;
            num = rand.nextInt(max - min) + min;
        }
        else if (actor instanceof PiranhaPlant) {
            int min = 13;
            int max = 15;
            num = rand.nextInt(max - min) + min;
        }
        else {
            int min = 15;
            num = rand.nextInt(monologue.size() - min) + min;
        }
        return actor + ": \"" + monologue.get(num) + "\"";
    }

    /**
     * Returns a monologue suitable to be displayed in the menu
     * @param display the I/O object to which messages may be written
     * @param actor The actor which speaks
     */
    public void execute(Display display, Actor actor){
        display.println(getMonologue(actor));
    }
}
