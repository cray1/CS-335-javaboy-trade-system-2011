package worldModel;

/**
 * A purchasable beneficial object to Navigators.
 * 
 * @author Cody Mingus
 * 
 */
public interface Item {

	/**
	 * Using the command pattern, an Item will improve the state of the
	 * navigator when used.
	 * 
	 * @param n
	 *            the Navigator receiving the benefit.
	 */
	public void activateEffect(Navigator n);

}
