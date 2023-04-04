package model.world;

import java.util.ArrayList;

public class Villain extends Champion
{

	public Villain(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) 
	{
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}
	
	public void useLeaderAbility(ArrayList<Champion> targets) 
	{
		int i=0;
		while(i<targets.size())
		{
			targets.get(i).setCondition(Condition.KNOCKEDOUT);
			i++;
		}
	}
	

}
