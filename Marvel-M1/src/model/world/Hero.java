package model.world;

import java.util.ArrayList;

import model.effects.*;

public class Hero extends Champion
	{
	public Hero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);
	}

	public void useLeaderAbility(ArrayList<Champion> targets) 
	{
		for (Champion champion : targets)
		{
			for(int i =0;i<champion.getAppliedEffects().size();i++)
			{
					Effect ez=(Effect) champion.getAppliedEffects().get(i);
					if(ez.getType()==EffectType.DEBUFF)
					{
						ez.remove(champion);	
						champion.getAppliedEffects().remove(ez);
						i--;
					}
			}
			
			Embrace embrace = new Embrace(2);
			embrace.apply(champion);
			champion.getAppliedEffects().add(embrace);
		}
	}
}
	

