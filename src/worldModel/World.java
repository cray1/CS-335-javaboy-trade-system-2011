package worldModel;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import view.Views;
import battleFieldModel.BattleField;
import battleFieldModel.Point;

/**
 * The environment navigators travel in. While traveling, there's a random
 * chance that a battle will start. When this happens, the world will alert the
 * view to change to a BattleView and pass the player inside of the navigator.
 * 
 * @author Cody Mingus, edits by Jorge Vergara
 * 
 */
public class World extends Observable implements Observer {

	private WorldTile[][] tiles;
	private Navigator n;

	/**
	 * Creates a new World for a Navigator to roam around in.
	 * 
	 * @param n
	 *            the Navigator roaming this world.
	 */
	public World(Navigator n) {
		this.n = n;
		tiles = new WorldTile[20][20];
		for (int r = 0; r < 20; r++) {
			for (int c = 0; c < 20; c++) {
				tiles[r][c] = new WorldTile(new Point(r, c));
			}
		}
		n.setLocation(new Point(10,10));
	}

	/**
	 * Moves the Navigator in the specified direction, then notifies Observers.
	 * When moving, there's a 25% chance that a battle will start.
	 * 
	 * @param direction
	 *            The direction this Navigator (n) is traveling.
	 * @throws InvalidMoveException
	 *             thrown if the player attempts to move to a location that
	 *             doesn't not contain a WorldTile.
	 */
	public void move(Direction direction) throws InvalidMoveException {
		Point l = direction.shift(n.getLeeway());
		Point x = new Point(n.getLocation().row, n.getLocation().col);
		Direction d;
		if (l.row > 31) {
			l = new Point(0, l.col);
			d = Direction.SOUTH;
			x = d.shift(x);
		}
		if (l.row < 0) {
			l = new Point(31, l.col);
			d = Direction.NORTH;
			x = d.shift(x);
		}
		if (l.col > 31) {
			l = new Point(l.row, 0);
			d = Direction.EAST;
			x = d.shift(x);
		}
		if (l.col < 0) {
			l = new Point(l.row, 31);
			d = Direction.WEST;
			x = d.shift(x);
		}

		if (x.row > 19 || x.row < 0 || x.col < 0 || x.col > 19) {
			throw new InvalidMoveException(x);
		}
		if (tiles[x.row][x.col] == null) {
			throw new InvalidMoveException(x);
		}
		n.setLocation(x);
		n.setLeeway(l);
		setChanged();
		notifyObservers(n); // update the view
		if (Math.random() <= .02) { // 2% chance of a battle
			BattleField b = new BattleField(n.getPlayer());
			b.addObserver(this);
			setChanged();
			notifyObservers(b);
		} else {
			setChanged();
			notifyObservers();
		}

	}

	/**
	 * A sorted Iterator of WorldTiles about the passed location.
	 * 
	 * @param location
	 *            The center of Iteration. Only WorldTiles within 5 WorldTiles
	 *            of this one will be added to the Iterator.
	 * @return An Iterator of sorted WorldTiles. The rear WorldTile (as close to
	 *         (0,0) as possible) is first and the front WorldTile (as close to
	 *         (n,n) as possible) is last. The Iterator contains the WorldTiles
	 *         inside of the 11x11 area in which the passed location is the
	 *         center.
	 */
	public Iterator<WorldTile> tiles(Point location) {
		if (location == null) {
			throw new NullPointerException();
		} else if (location.row < 0 || location.row >= tiles.length
				|| location.col < 0 || location.col >= tiles[0].length) {
			throw new IllegalArgumentException();
		}
		return new TileIterator(location);
	}

	private class TileIterator implements Iterator<WorldTile> {

		private LinkedList<WorldTile> list;

		public TileIterator(Point p) {
			/*
			 * Grab all WorldTiles within a 11x11 Square (where point p is the
			 * middle)
			 */
			int left = p.col - 5 < 0 ? 0 : p.col - 5;
			int right = p.col + 5 > 19 ? 19 : p.col + 5;
			int top = p.row - 5 < 0 ? 0 : p.row - 5;
			int bottom = p.row + 5 > 19 ? 19 : p.row + 5;
			list = new LinkedList<WorldTile>();
			for (int r = left; r <= right; r++) {
				for (int c = top; c <= bottom; c++) {
					if (tiles[r][c] != null) { // Don't include null WorldTiles
						list.add(tiles[r][c]);
					}
				}
			}
			Collections.sort(list);
		}

		@Override
		public boolean hasNext() {
			return !list.isEmpty();
		}

		@Override
		public WorldTile next() {
			return list.removeFirst();
		}

		@Override
		public void remove() {
			list.removeFirst();
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof BattleField) {
			if (((BattleField) arg).isOver()) {
				Item i = getReward((BattleField) arg);
				if (i == null) {
					setChanged();
					notifyObservers(Views.TITLE);
				} else {
					n.addItem(i);
				}
			}
		}
	}

	private Item getReward(BattleField bf) {
		if (bf.getPlayer().getHealth() < 1) {
			return null;
		} else {
			double chance = Math.random();
			if (chance < .6) {
				return new SmallHeal();
			} else if (chance < .9) {
				return new MediumHeal();
			} else {
				return new LargeHeal();
			}
		}
	}

}
