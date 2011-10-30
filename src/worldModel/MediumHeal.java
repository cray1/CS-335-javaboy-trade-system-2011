package worldModel;

import battleFieldModel.Player;

/**
 * Heals the Player of the Navigator by 50 points.
 * 
 * @author Cody Mingus
 * 
 */
public class MediumHeal implements Item {

	public void activateEffect(Navigator n) {
		Player p = n.getPlayer();
		p.receiveDamage(-50);
	}

	public String toString() {
		return "MediumHeal";
	}

}
