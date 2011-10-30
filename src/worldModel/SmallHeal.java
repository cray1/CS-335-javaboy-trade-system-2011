package worldModel;

import battleFieldModel.Player;

/**
 * Heals the Player of the navigator by 20 points
 * 
 * @author Cody Mingus
 * 
 */
public class SmallHeal implements Item {

	public void activateEffect(Navigator n) {
		Player p = n.getPlayer();
		p.receiveDamage(-20);
	}

	public String toString() {
		return "SmallHeal";
	}

}
