package model.world;

import java.util.ArrayList;
import model.effects.*;

public class AntiHero extends Champion 
{
	public AntiHero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) 
	{
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);
	}
	
	public void useLeaderAbility(ArrayList<Champion> targets) 
	{
		for(Champion x: targets)
		{
			Stun s= new Stun(2);
			s.apply(x);
			x.getAppliedEffects().add(s);
		}
	}
	
}
