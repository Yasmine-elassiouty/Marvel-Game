package model.abilities;

import java.util.ArrayList;
import model.world.*;

public  class HealingAbility extends Ability 
{
	private int healAmount;

	public HealingAbility(String name,int cost, int baseCoolDown, int castRadius, AreaOfEffect area,int required, int healingAmount) {
		super(name,cost, baseCoolDown, castRadius, area,required);
		this.healAmount = healingAmount;
	}

	public int getHealAmount() 
	{
		return healAmount;
	}

	public void setHealAmount(int healAmount) 
	{
		this.healAmount = healAmount;
	}
	
	public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException
	{
		for(Damageable x:targets)
			x.setCurrentHP(x.getCurrentHP()+getHealAmount());
	}

}
