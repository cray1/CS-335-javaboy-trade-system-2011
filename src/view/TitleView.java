package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import battleFieldModel.BattleField;
import battleFieldModel.Player;

/**
 * The screen that shows up when JavaBoy starts. Should have an option for
 * exiting, starting a new game, and a little eye candy.
 * 
 * @author Cody Mingus
 * 
 */
@SuppressWarnings("serial")
public class TitleView extends MasterViewPanel {

	private JButton newGame;
	private JButton exit;

	/**
	 * Creates a new TitleView ready to be viewed.
	 * 
	 * @param m
	 */
	public TitleView(MasterView m) {
		super(m);
		setUpLayout();
		setUpButtons();
	}

	/*
	 * Set up a grid layout and add our eye candy
	 */
	private void setUpLayout() {
		this.setLayout(new GridLayout(3, 1));
		add(new JLabel(new ImageIcon("images/javaBoy.png")));
	}

	/*
	 * Add the JButtons for exiting and starting a new game.
	 */
	private void setUpButtons() {
		newGame = new JButton("New Game");
		newGame.addActionListener(new NewGameListener());
		this.add(newGame);
		exit = new JButton("Exit");
		exit.addActionListener(new ExitListener());
		this.add(exit);
	}

	private class NewGameListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			m.changeView(Views.BATTLE, new BattleField(new Player()));
		}

	}

	private class ExitListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

	}

	public String toString() {
		return "TITLE";
	}

}
