package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Stun extends Effect {

	public Stun(int duration) 
	{
		super("Stun", duration, EffectType.DEBUFF);
	}

	public void apply(Champion c) 
	{
		c.setCondition(Condition.INACTIVE);
	}
	
	public void remove(Champion c) 
	{
		int i=0;

		while(i<c.getAppliedEffects().size())
			{
				if (c.getAppliedEffects().get(i) instanceof Stun)
					return;
				i++;
			}
		i=0;
		
		while(i<c.getAppliedEffects().size())
		{
			if (c.getAppliedEffects().get(i) instanceof Root)
			{
				c.setCondition(Condition.ROOTED);
				return;
			}
			i++;
		}	

		c.setCondition(Condition.ACTIVE);	
	}
}
