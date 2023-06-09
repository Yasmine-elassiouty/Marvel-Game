package model.effects;

import model.world.Champion;

public class Dodge extends Effect 
{
	public Dodge(int duration) 
	{
		super("Dodge", duration, EffectType.BUFF);
	}
	
	public void apply(Champion c) 
	{
		int s=c.getSpeed();
		c.setSpeed((int)(s*1.05));
	}

	public void remove(Champion c) 
	{
		int s=c.getSpeed();
		c.setSpeed((int)(s/1.05));
	}
}

