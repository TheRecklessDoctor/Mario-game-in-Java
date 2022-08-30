package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.ResetManager;

/**
 * Class for Player to reset game map.
 */
public class ResetMapAction extends Action {

    /**
     * Empty constructor to reset the game map.
     */
    public ResetMapAction() {
    }

    /**
     * Displays a suitable sentence to inform the user that the no more reset game option is available after the game has been reset.
     * @param actor The actor performing the action.
     * @param map The map where the actor performing the action is on.
     * @return a suitable sentence to inform the user that the action is no longer available.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        ResetManager.getInstance().run();
        return "Game has been reset. No more resets available.";
    }

    /**
     * Displays a suitable sentence after the game is reset.
     * @param actor The actor performing the action.
     * @return a suitable sentence after the action is performed.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Reset Game Map. ";
    }

    /**
     * Returns the key used in the menu to trigger this Action.
     * @return "r".
     */
    @Override
    public String hotkey() {
        return "r";
    }
}
