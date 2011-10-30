package worldModel;

import battleFieldModel.Point;

/**
 * Thrown when a Navigator tries to move to WorldTile that doesn't exist or is
 * occupied by a shop.
 * 
 * @author Cody Mingus, edits by Jorge Vergara
 * 
 */
public class InvalidMoveException extends Exception {

	/**
	 * Added to make the compiler happy!
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param point
	 *            the point that was invalid
	 */
	public InvalidMoveException(Point point) {
		super("Cannot move to (" + point.row + "," + point.col + ")");
	}

}
