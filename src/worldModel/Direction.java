package worldModel;

import battleFieldModel.Point;

/**
 * An 8 way directional system for a two dimensional plane.
 * 
 * @author Cody Mingus
 * 
 */
public enum Direction {

	/**
	 * The direction representing 0 degrees, 360 degrees, and North
	 */
	NORTH,
	/**
	 * The direction representing 45 degrees and North-East
	 */
	NORTH_EAST,
	/**
	 * The direction representing 90 degrees and East
	 */
	EAST,
	/**
	 * The direction representing 135 degrees and South-East
	 */
	SOUTH_EAST,
	/**
	 * The direction representing 180 degrees and South
	 */
	SOUTH,
	/**
	 * The direction representing 225 degrees and South-West
	 */
	SOUTH_WEST,
	/**
	 * The direction representing 270 degrees and West
	 */
	WEST,
	/**
	 * The direction representing 315 degrees and North-West
	 */
	NORTH_WEST;

	/**
	 * Returns a Point next to the passed Point based on the direction passed.
	 * 
	 * @param p
	 *            The original point
	 * @return A point with coordinates one away from the coordinates of the
	 *         passed point in the direction passed.
	 */
	public Point shift(Point p) {
		switch (this) {
		case NORTH:
			return new Point(p.row-1, p.col);
		case NORTH_EAST:
			return new Point(p.row-1, p.col+1);
		case EAST:
			return new Point(p.row, p.col+1);
		case SOUTH_EAST:
			return new Point(p.row+1, p.col+1);
		case SOUTH:
			return new Point(p.row+1, p.col);
		case SOUTH_WEST:
			return new Point(p.row+1, p.col-1);
		case WEST:
			return new Point(p.row, p.col-1);
		case NORTH_WEST:
			return new Point(p.row-1, p.col-1);
		default:
			return null;
		}
	}

	/**
	 * @return The direction opposite of the direction passed.
	 */
	public Direction opposite() {
		return (Direction.values())[(this.ordinal() + 4) % 8];
	}
}
