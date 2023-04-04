package model.effects;

import model.abilities.*;
import model.world.*;

public class PowerUp extends Effect 
{
	public PowerUp(int duration) 
	{
		super("PowerUp", duration, EffectType.BUFF);
	}

	public void apply(Champion c) 
	{
		int i=0;
		while(i<(c.getAbilities()).size())
		{
			Ability x=c.getAbilities().get(i);
			
			if(x instanceof DamagingAbility)
			{
				((DamagingAbility)x).setDamageAmount((int)
						(((DamagingAbility)x).getDamageAmount()*1.2));
			}
			
			if(x instanceof HealingAbility)
			{
				((HealingAbility)x).setHealAmount((int)
						(((HealingAbility)x).getHealAmount()*1.2));
			}
			i++;
		}
	}

	public void remove(Champion c) 
	{
		int i=0;

		while(i<(c.getAbilities()).size())
		{
			Ability x=c.getAbilities().get(i);
			
			if(x instanceof DamagingAbility)
			{
				((DamagingAbility)x).setDamageAmount((int)
						(((DamagingAbility)x).getDamageAmount()/1.2));
			}
			
			if(x instanceof HealingAbility)
			{
				((HealingAbility)x).setHealAmount((int)
						(((HealingAbility)x).getHealAmount()/1.2));
			}
			i++;
		}
	}
}
