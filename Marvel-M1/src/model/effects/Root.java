package model.effects;

import model.world.*;

public class Root extends Effect 
{

	public Root( int duration) 
	{
		super("Root", duration, EffectType.DEBUFF);
	}

	public void apply(Champion c) 
	{
		if (c.getCondition()!=Condition.INACTIVE)
		{
			c.setCondition(Condition.ROOTED);
		}
	}
	
	public void remove(Champion c) 
	{
		if (c.getCondition()==Condition.INACTIVE)
			return;
		
		for(int i=0; i<c.getAppliedEffects().size(); i++)
			if (c.getAppliedEffects().get(i) instanceof Root)
			{
				c.setCondition(Condition.ROOTED);
				return;
			}

		c.setCondition(Condition.ACTIVE);
	}
	

}
