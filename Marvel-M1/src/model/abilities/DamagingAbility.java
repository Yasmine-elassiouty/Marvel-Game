package model.abilities;

import java.util.*;
import model.world.*;

public class DamagingAbility extends Ability 
{
	
	private int damageAmount;
	public DamagingAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area,int required,int damageAmount) {
		super(name, cost, baseCoolDown, castRadius, area,required);
		this.damageAmount=damageAmount;
	}
	
	public int getDamageAmount() 
	{
		return damageAmount;
	}
	
	public void setDamageAmount(int d) 
	{
		this.damageAmount = d;
	}
	public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException
	{
		for(Damageable x:targets)
			x.setCurrentHP(x.getCurrentHP()-getDamageAmount());
	}

}
