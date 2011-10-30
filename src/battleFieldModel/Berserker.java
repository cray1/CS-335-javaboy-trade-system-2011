package battleFieldModel;

//change
/**
 * The Berserker Family attacks with Bombs and starts out with 60 points of
 * health.
 * 
 * @author Cody Mingus
 * 
 */
public class Berserker extends Family {

	private int count;

	/**
	 * Attack Produced: Bomb Attack Frequency: 4 moves
	 */
	public Berserker() {
		count = 0;
		canAttack = false;
	}

	@Override
	public Harmful attack() {
		canAttack = false;
		count = 0;
		return new Bomb(BattleFieldSimulation.RED);
	}

	@Override
	public boolean canAttack() {
		if (count == 3)
			canAttack = true;
		count++;
		return canAttack;
	}

	@Override
	public int getStartingHealth() {
		return 60;
	}

}
