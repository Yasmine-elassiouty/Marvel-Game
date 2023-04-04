package model.abilities;

import java.util.ArrayList;
import engine.*;
import model.effects.*;
import model.world.*;

public class CrowdControlAbility extends Ability 
{
	private Effect effect;

	public CrowdControlAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area, int required,
			Effect effect) 
	{
		super(name, cost, baseCoolDown, castRadius, area, required);
		this.effect = effect;
	}

	public Effect getEffect() 
	{
		return effect;
	}
	public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException
	{
		int i=0;
		
		while(i<targets.size())
		{
			Champion c = (Champion)targets.get(i);
			((Effect)this.effect.clone()).apply((Champion)targets.get(i));
			c.getAppliedEffects().add((Effect)this.effect.clone());
			
			
			i++;
			
		} 
	}
}
