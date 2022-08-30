package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Bowser;
import game.actors.Player;
import game.actors.PrincessPeach;
import game.actors.Toad;
import game.grounds.*;
import game.items.PowerStar;
import game.items.SuperMushroom;
import game.plants.Mature;
import game.plants.Sapling;
import game.plants.Sprout;

/**
 * The main class for the Mario World game.
 * Mario World game created in this class
 *
 * @author Teaching Team
 * @version 1.0
 * @since 04.29.2022
 */
public class Application {

	public static void main(String[] args) {

		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Sprout(), new Sapling(), new Mature(),new Lava(), new WarpPipe(), new HealthFountain(), new PowerFountain());

		List<String> map = Arrays.asList(
				"...................................C......##..........+.........................",
				"....A.......+............+..................#...................................",
				"............................................#...................................",
				"..............................H..............##......................+..........",
				"...............................................#.....................H..........",
				".........................C......................#...............................",
				".................+................................#.......C.....................",
				".................................................##.............................",
				"...................................A.......C....##..............................",
				".........+..........................+...+#____####.................+............",
				".......................................+#__C__###++.............................",
				"........................+.............+#_H___A_###..............................",
				".......................................+#______###........C.....................",
				"........................+........................##.............+...............",
				".................................C.................#............................",
				"....................................................#...........................",
				"..........H........+.................................#..........A...............",
				"......................................................#.........................",
				"....+.........................................+........##.......................");

		List<String> map2 = Arrays.asList(
				"C..LLL#...................+..................H........LLLLLL...............",
				"...LLL#....................................................................",
				".............#......................#......................................",
				".....A......+#.......................#................######....+..........",
				"............+#.................._.....#...............######...............",
				".....H.......#...............+.........#...................................",
				"...........................................................................",
				".............LLL............H................A.............................",
				"............LLLLL.....................................##...................",
				"__....+_....LLLLL..................+..........................+............",
				".............LLL......................___..................................",
				"...................................._...__.................................",
				"...................................LLLL.............................LLLL...",
				"...LLL..............+.............LLLLLL...................H........L..+...",
				"...LLL...........................LLLLLLLL.....#.....................L..+...",
				"...LLL.......###.....A..........L...+....L.....#....................LLLL...",
				"................................................#........+.................");


		GameMap gameMap = new GameMap(groundFactory, map);
		GameMap gameMap2 = new GameMap(groundFactory, map2);
		world.addGameMap(gameMap);
		world.addGameMap(gameMap2);

		MapManager.getInstance().appendMap(gameMap);
		MapManager.getInstance().appendMap(gameMap2);

		Actor mario = new Player("Player", 'm', 100);
		world.addPlayer(mario, gameMap.at(43, 11));



		gameMap.at(43,11).addItem(new SuperMushroom());
		gameMap.at(43,11).addItem(new PowerStar());

		gameMap.at(43, 12).addActor(new Toad());
		gameMap2.at(1, 6).addActor(new PrincessPeach());
		gameMap2.at(0, 6).addActor(new Bowser());

		world.run();

	}
}
