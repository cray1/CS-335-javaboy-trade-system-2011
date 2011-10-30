package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import battleFieldModel.BattleField;
import battleFieldModel.Bomb;
import battleFieldModel.Enemy;
import battleFieldModel.Harmful;
import battleFieldModel.Obstacle;
import battleFieldModel.Player;
import battleFieldModel.Point;
import battleFieldModel.Shockwave;

/**
 * A KeyEventedPanel for interacting with a BattleField. Consists of a JPanel on
 * top for showing Player health, 3x6 array of Tile graphics, and graphics for
 * the player and enemies. Graphics are presented in the form of JLabels
 * 
 * @author Cody Mingus
 * 
 */
@SuppressWarnings("serial")
public class BattleView extends MasterViewPanel implements Observer {

	private BattleField bf;
	private Player p;
	private JPanel field;
	private JLabel health;
	private JLabel player;
	private HashMap<Enemy, JLabel> enemies;
	private HashMap<Harmful, JLabel> harmfuls;

	/**
	 * Starts a new battle with the passed player.
	 * 
	 * @param m
	 *            the MasterView this BattleView is in
	 * @param b
	 *            the BattleField to show
	 */
	public BattleView(MasterView m, BattleField b) {
		super(m);
		this.p = b.getPlayer();
		bf = b;
		bf.addObserver(this);
		this.setLayout(new FlowLayout());
		setUpNorthernHalf();
		setUpGrid();
		addKeyListener(new BattleListener());
		setFocusable(true);
		bf.start();
		// requestFocusInWindow(); // required
	}

	private void setUpGrid() {
		field = new JPanel();
		field.setLayout(null);
		field.setPreferredSize(new Dimension(640, 300)); // required.

		// Initialize variables
		harmfuls = new HashMap<Harmful, JLabel>();
		enemies = new HashMap<Enemy, JLabel>();
		player = new JLabel(new ImageIcon("images/player.png"));
		player.setLocation(20 + 100 * bf.getPlayerLocation().col,
				60 * bf.getPlayerLocation().row);
		player.setSize(100, 100);

		// Create the field
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				JLabel temp = new JLabel(new ImageIcon("images/blueTile.png"));
				temp.setLocation(20 + 100 * c, 60 * r + 25);
				temp.setSize(100, 100);
				field.add(temp);
				field.setComponentZOrder(temp, 0);
			}
			for (int c = 3; c < 6; c++) {
				JLabel temp = new JLabel(new ImageIcon("images/redTile.png"));
				temp.setLocation(20 + 100 * c, 60 * r + 25);
				temp.setSize(100, 100);
				field.add(temp);
				field.setComponentZOrder(temp, 0);
			}
		}

		JLabel temp = new JLabel();
		field.add(temp);
		field.setComponentZOrder(temp, 0);

		// Add the player
		Iterator<Obstacle> itr = bf.obstacles();
		while (itr.hasNext()) {
			Obstacle o = itr.next();
			if (o instanceof Player) {
				field.add(player);
				field.setComponentZOrder(player, 1);
			} else {
				Enemy e = (Enemy) o;
				enemies.put(e, new JLabel(new ImageIcon("images/enemy.png")));
				enemies.get(e).setLocation(20 + 100 * e.getLocation().col,
						60 * e.getLocation().row);
				enemies.get(e).setSize(100, 100);
				enemies.get(e).setDoubleBuffered(true);
				field.add(enemies.get(e));
				field.setComponentZOrder(enemies.get(e), 1);
			}
		}
		JLabel fake = new JLabel();
		field.add(fake);
		field.setComponentZOrder(fake, 1);

		this.add(field, BorderLayout.SOUTH);
	}

	private void setUpNorthernHalf() {
		health = new JLabel("" + p.getHealth());
		health.setFont(new Font("Arial", Font.PLAIN, 36));
		this.add(health);
	}

	private class BattleListener implements KeyListener {

		public void keyPressed(KeyEvent arg0) {
		}

		public void keyReleased(KeyEvent arg0) {
			int keyCode = arg0.getKeyCode();
			if (keyCode == KeyEvent.VK_UP) {
				p.moveUp();
			} else if (keyCode == KeyEvent.VK_DOWN) {
				p.moveDown();
			} else if (keyCode == KeyEvent.VK_LEFT) {
				p.moveLeft();
			} else if (keyCode == KeyEvent.VK_RIGHT) {
				p.moveRight();
			} else if (keyCode == KeyEvent.VK_Z) {
				p.shoot();
			} else if (keyCode == KeyEvent.VK_X) {
				bf.pause();
			} else {

			}
		}

		public void keyTyped(KeyEvent arg0) {
		}

	}

	public void update(Observable arg0, Object arg1) {
		if (arg1 != null) {
			if (arg1 instanceof Player) {
				Point x = p.getLocation();
				if (x == null) {
					field.remove(player);
					repaint();
				} else {
					player.setLocation(20 + 100 * x.col, 60 * x.row);
				}
			} else if (arg1 instanceof Enemy) {
				Enemy e = (Enemy) arg1;
				Point x = e.getLocation();
				if (x == null) {
					field.remove(enemies.get(e));
					enemies.remove(e);
					this.repaint();
				} else {
					enemies.get(e).setLocation(20 + 100 * x.col, 60 * x.row);
				}
			} else {
				Harmful h = (Harmful) arg1;
				Point x = h.getLocation();
				if (harmfuls.containsKey(h)) {
					if (x == null) {
						field.remove(harmfuls.get(h));
						harmfuls.remove(h);
						this.repaint();
					} else {
						harmfuls.get(h).setLocation(20 + 100 * x.col,
								60 * x.row);
					}
				} else {
					if (x == null) {
						// Do nothing
					} else {
						JLabel temp = null;
						if (h instanceof Bomb) {
							temp = new JLabel(new ImageIcon("images/bomb.png"));
						} else if (h instanceof Shockwave) {
							temp = new JLabel(new ImageIcon(
									"images/shockwave.png"));
						} else {
							temp = new JLabel(
									new ImageIcon("images/bullet.png"));
						}
						temp.setSize(100, 100);
						temp.setLocation(20 + 100 * x.col, 60 * x.row);
						// temp.setDoubleBuffered(true);
						field.add(temp);
						field.setComponentZOrder(temp, 2);
						harmfuls.put(h, temp);
						addFakeHarmful();
					}
				}
			}
			health.setText("" + p.getHealth());
		} else {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			m.changeView(Views.PREVIOUS, null);
		}
		this.repaint();
	}

	private void addFakeHarmful() {
		JLabel temp = new JLabel();
		field.add(temp);
		field.setComponentZOrder(temp, 2);
	}

	public String toString() {
		return "BATTLE";
	}

}
