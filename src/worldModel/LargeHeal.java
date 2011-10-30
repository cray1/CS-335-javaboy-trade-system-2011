package worldModel;

/**
 * Sets the navigators speed to a higher value, increasing the rate at which it
 * moves.
 * 
 * @author Cody Mingus
 * 
 */
public class LargeHeal implements Item {

	public void activateEffect(Navigator n) {
		n.getPlayer().receiveDamage(-80);
	}

	public String toString() {
		return this.getClass().getSimpleName();
	}

}
