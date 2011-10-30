package worldModel;

import battleFieldModel.Point;

/**
 * Represents one grid space in the World.
 * 
 * @author Cody Mingus
 * 
 */
public class WorldTile implements Comparable<WorldTile> {

	private Point location;

	/**
	 * @param p
	 *            The location of this WorldTile
	 */
	public WorldTile(Point p) {
		location = p;
	}

	/**
	 * @return the Location of this WorldTile
	 */
	public Point getLocation() {
		return location;
	}

	@Override
	public int compareTo(WorldTile other) {
		return (location.row + location.col)
				- (other.location.row + other.location.col);
	}

}
