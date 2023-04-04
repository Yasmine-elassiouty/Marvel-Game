package model.effects;

import model.world.Champion;
import model.abilities.*;

public class Disarm extends Effect 
{

	public Disarm( int duration) 
	{
		super("Disarm", duration, EffectType.DEBUFF);
	}

	public void apply(Champion c) 
	{
		DamagingAbility z = new DamagingAbility("Punch",0,1,1,AreaOfEffect.SINGLETARGET,1,50);
		c.getAbilities().add(z);
	}

	public void remove(Champion c) 
	{
		int i=0;

		while (i<c.getAbilities().size())
		{
			if(c.getAbilities().get(i).getName().equals("Punch")) 
			{
				c.getAbilities().remove(c.getAbilities().get(i));
				break;
			}
			i++;
		}

	}
}








