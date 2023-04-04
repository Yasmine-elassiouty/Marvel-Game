package model.effects;

import model.world.Champion;

public class SpeedUp extends Effect
{
	public SpeedUp(int duration) 
	{
		super("SpeedUp",duration,EffectType.BUFF);
	}

	public void apply(Champion c) 
	{
		c.setCurrentActionPoints(c.getCurrentActionPoints()+1);
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()+1);
		c.setSpeed((int)(c.getSpeed()*1.15));
	}

	public void remove(Champion c) 
	{
		c.setCurrentActionPoints(c.getCurrentActionPoints()-1);
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()-1);
		c.setSpeed((int)(c.getSpeed()/1.15));
	}
}
