package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import battleFieldModel.BattleField;

/**
 * Creates the window JavaBoy is viewed in. This window has a menu bar and
 * controls all keyboard input. Input is usually routed to a KeyEventedPanel.
 * Which is held in the content area of the MasterView.
 * 
 * @author Cody Mingus
 * 
 */
@SuppressWarnings("serial")
public class MasterView extends JFrame {

	private MasterViewPanel currentPanel;
	private Stack<MasterViewPanel> panels;
	private JPanel body;

	/**
	 * Makes a new MasterView
	 * 
	 * @param args
	 *            doesn't matter. They aren't used.
	 */
	public static void main(String[] args) {
		new MasterView();
	}

	/**
	 * Sets up the Application window for JavaBoy. The content area is set to a
	 * new TitleView.
	 */
	public MasterView() {
		this.setLayout(new BorderLayout());
		this.setUpMenuBar();
		this.setDefaultPanel();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(200, 100);
		this.setSize(640, 480);
		this.setVisible(true);
		this.requestFocus();
	}

	private void setUpMenuBar() {
		JMenuBar jmb = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		exit.addActionListener(new ExitListener());
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new AboutListener());
		jmb.add(file);
		jmb.add(about);
		this.add(jmb, BorderLayout.NORTH);
	}

	/*
	 * Sets up the junk that manages the panels being viewed.
	 */
	private void setDefaultPanel() {
		panels = new Stack<MasterViewPanel>();
		currentPanel = new TitleView(this);

		body = new JPanel();
		body.setLayout(new CardLayout());
		body.add(currentPanel, "TITLE");

		panels.push(currentPanel);
		this.add(body, BorderLayout.CENTER);
	}

	private class ExitListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

	}

	private class AboutListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "Cool view by Cody Mingus");
		}

	}

	/**
	 * Switches views based on the passed enum.
	 * 
	 * @param v
	 *            the View to switch to
	 * @param o
	 *            A BattleField if switching to BattleView.
	 */
	public void changeView(Views v, Object o) {
		switch (v) {
		case PREVIOUS:
			CardLayout cl = (CardLayout) body.getLayout();
			JPanel temp = currentPanel;
			panels.pop();
			currentPanel = panels.peek();
			cl.show(body, currentPanel.toString());
			cl.removeLayoutComponent(temp);
			break;
		case BATTLE:
			currentPanel = new BattleView(this, (BattleField) o);
			panels.push(currentPanel);
			body.add(currentPanel, v.name());
			CardLayout cl1 = (CardLayout) body.getLayout();
			cl1.show(body, v.name());
			for (Component c : body.getComponents()) {
				if (c == currentPanel) {
					c.requestFocusInWindow();
				}
			}
			break;
		case TITLE:
			currentPanel = new TitleView(this);
			panels.push(currentPanel);
			body.add(currentPanel, v.name());
			CardLayout cl2 = (CardLayout) body.getLayout();
			cl2.show(body, v.name());
			break;
		default:
			// Do nothing
		}

	}

}
