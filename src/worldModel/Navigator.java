package worldModel;

import java.util.LinkedList;
import java.util.List;

import battleFieldModel.Player;
import battleFieldModel.Point;

/**
 * The user controlled object in the world that the user plays as.
 * 
 * @author Cody Mingus, edits by Jorge Vergara
 * 
 */
public class Navigator {

	private Player player;
	private Point location;
	private Point leeway;
	private LinkedList<Item> inventory;

	/**
	 * Creates a new navigator with a full health player, no location, no money
	 * and standard speed.
	 * 
	 */
	public Navigator() {
		player = new Player();
		location = null;
		setLeeway(new Point(16, 16));
		inventory = new LinkedList<Item>();
	}

	/**
	 * @return The Player used for battle by this Navigator
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the Navigator's location coordinates to those of the passed Point.
	 * 
	 * @param p
	 *            the new location of this Navigator
	 */
	public void setLocation(Point p) {
		location = p;
	}

	/**
	 * @return the current x and y coordinates of this Player
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @return the List of Items this Navigator has.
	 */
	public List<Item> getInventory() {
		return inventory;
	}

	/**
	 * @param i
	 *            the Item being added to the inventory. null is allowed (design
	 *            decision)
	 */
	public void addItem(Item i) {
		inventory.add(i);
	}

	/**
	 * @param leeway
	 *            the new Leeway value for this Navigator
	 */
	public void setLeeway(Point leeway) {
		this.leeway = leeway;
	}

	/**
	 * @return this Navigators Leeway values.
	 */
	public Point getLeeway() {
		return leeway;
	}

}
