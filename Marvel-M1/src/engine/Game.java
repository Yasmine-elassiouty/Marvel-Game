package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.abilities.*;
import model.effects.*;
import model.world.*;
import exceptions.*;

public class Game 
{
	private static ArrayList<Champion> availableChampions = new ArrayList<Champion>();
	private static ArrayList<Ability> availableAbilities = new ArrayList<Ability>();
	private Player firstPlayer;
	private Player secondPlayer;
	private Object[][] board;
	private PriorityQueue turnOrder;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private final static int BOARDWIDTH = 5;
	private final static int BOARDHEIGHT = 5;

	public Game(Player first, Player second) 
	{
		firstPlayer = first;
		secondPlayer = second;
		board = new Object[BOARDWIDTH][BOARDHEIGHT];
		turnOrder = new PriorityQueue(6);
		placeChampions();
		placeCovers();
		prepareChampionTurns();
	}

	public static void loadAbilities(String filePath) throws IOException 
	{
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Ability a = null;
			AreaOfEffect ar = null;
			switch (content[5]) 
			{
			case "SINGLETARGET":
				ar = AreaOfEffect.SINGLETARGET;
				break;
			case "TEAMTARGET":
				ar = AreaOfEffect.TEAMTARGET;
				break;
			case "SURROUND":
				ar = AreaOfEffect.SURROUND;
				break;
			case "DIRECTIONAL":
				ar = AreaOfEffect.DIRECTIONAL;
				break;
			case "SELFTARGET":
				ar = AreaOfEffect.SELFTARGET;
				break;
			}

			Effect e = null;
			if (content[0].equals("CC")) 
			{
				switch (content[7]) 
				{
				case "Disarm":
					e = new Disarm(Integer.parseInt(content[8]));
					break;
				case "Dodge":
					e = new Dodge(Integer.parseInt(content[8]));
					break;
				case "Embrace":
					e = new Embrace(Integer.parseInt(content[8]));
					break;
				case "PowerUp":
					e = new PowerUp(Integer.parseInt(content[8]));
					break;
				case "Root":
					e = new Root(Integer.parseInt(content[8]));
					break;
				case "Shield":
					e = new Shield(Integer.parseInt(content[8]));
					break;
				case "Shock":
					e = new Shock(Integer.parseInt(content[8]));
					break;
				case "Silence":
					e = new Silence(Integer.parseInt(content[8]));
					break;
				case "SpeedUp":
					e = new SpeedUp(Integer.parseInt(content[8]));
					break;
				case "Stun":
					e = new Stun(Integer.parseInt(content[8]));
					break;
				}
			}

			switch (content[0]) 
			{
			case "CC":
				a = new CrowdControlAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), e);
				break;
			case "DMG":
				a = new DamagingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			case "HEL":
				a = new HealingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			}
			availableAbilities.add(a);
			line = br.readLine();
		}
		br.close();
	}

	public static void loadChampions(String filePath) throws IOException 
	{
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) 
		{
			String[] content = line.split(",");
			Champion c = null;
			switch (content[0]) 
			{
			case "A":
				c = new AntiHero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;

			case "H":
				c = new Hero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			case "V":
				c = new Villain(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			}

			c.getAbilities().add(findAbilityByName(content[8]));
			c.getAbilities().add(findAbilityByName(content[9]));
			c.getAbilities().add(findAbilityByName(content[10]));
			availableChampions.add(c);
			line = br.readLine();
		}
		br.close();
	}

	private static Ability findAbilityByName(String name) 
	{
		for (Ability a : availableAbilities) 
			if (a.getName().equals(name))
				return a;
		return null;
	}

	public void placeCovers() 
	{
		int i = 0;
		while (i < 5) 
		{
			int x = ((int) (Math.random() * (BOARDWIDTH - 2))) + 1;
			int y = (int) (Math.random() * BOARDHEIGHT);

			if (board[x][y] == null) 
			{
				board[x][y] = new Cover(x, y);
				i++;
			}
		}
	}

	public void placeChampions() 
	{
		int i = 1;
		for (Champion c : firstPlayer.getTeam()) 
		{
			board[0][i] = c;
			c.setLocation(new Point(0, i));
			i++;
		}
		i = 1;
		for (Champion c : secondPlayer.getTeam()) 
		{
			board[BOARDHEIGHT - 1][i] = c;
			c.setLocation(new Point(BOARDHEIGHT - 1, i));
			i++;
		}
	}

	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public Object[][] getBoard() {
		return board;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}

	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}

	public static int getBoardwidth() {
		return BOARDWIDTH;
	}

	public static int getBoardheight() {
		return BOARDHEIGHT;
	}

	public Champion getCurrentChampion()
	{
		return (Champion) turnOrder.peekMin();
	}

	public Player checkGameOver()
	{
		if (firstPlayer.getTeam().size()==0) 
			return secondPlayer; 
		else if ((secondPlayer.getTeam().size()==0))
			return firstPlayer;
		else
			return null;
	}

	public void move(Direction d)throws NotEnoughResourcesException , UnallowedMovementException
	{
		if ((getCurrentChampion().getCondition()== Condition.ROOTED ))
		{
			throw new UnallowedMovementException();
		}
		int x=getCurrentChampion().getCurrentActionPoints();

		if (x<1)
			throw new NotEnoughResourcesException();
		else
		{
			Champion c=getCurrentChampion();
			helperMove(d,c);
		}
	}

	public void helperMove(Direction d, Champion c)throws UnallowedMovementException
	{
		int x=getCurrentChampion().getCurrentActionPoints();
		if (d==Direction.UP)
		{
			Point old= (Point)c.getLocation();
			Point New=new Point();
			New.setLocation(old.getX()+1,old.getY());
			if (New.getX()== BOARDHEIGHT)
			{
				throw new UnallowedMovementException();
			}

			else 
			{
				if (board[(int) New.getX()][(int) New.getY()]==null)
				{
					c.setLocation(New);
					board[(int) New.getX()][(int) New.getY()]=c;
					board[(int) old.getX()][(int) old.getY()]=null;
					getCurrentChampion().setCurrentActionPoints(x-1);
				}
				else
					throw new UnallowedMovementException();	
			}
		}

		else if (d==Direction.DOWN)
		{
			Point old= (Point)c.getLocation();
			Point New=new Point();
			New.setLocation(old.getX()-1,old.getY());
			if (New.getX()== -1)
			{
				throw new UnallowedMovementException();
			}

			else if (board[(int) New.getX()][(int) New.getY()]==null)
			{
				c.setLocation(New);
				board[(int) New.getX()][(int) New.getY()]=c;
				board[(int) old.getX()][(int) old.getY()]=null;
				getCurrentChampion().setCurrentActionPoints(x-1);
			}
			else
				throw new UnallowedMovementException();	
		}

		else if (d==Direction.LEFT)
		{
			Point old= (Point)c.getLocation();
			Point New=new Point();
			New.setLocation(old.getX(),old.getY()-1);
			if (New.getY()== -1)
			{
				throw new UnallowedMovementException();
			}

			else if ((board[(int) New.getX()][(int) New.getY()])==null)
			{
				c.setLocation(New);
				board[(int) New.getX()][(int) New.getY()]=c;
				board[(int) old.getX()][(int) old.getY()]=null;
				getCurrentChampion().setCurrentActionPoints(x-1);
			}
			else
				throw new UnallowedMovementException();	
		}

		else if (d==Direction.RIGHT)
		{
			Point old= (Point)c.getLocation();
			Point New=new Point();
			New.setLocation(old.getX(),old.getY()+1);
			if (New.getY()== BOARDWIDTH)
			{
				throw new UnallowedMovementException();
			}

			else if (board[(int) New.getX()][(int) New.getY()]==null)
			{
				c.setLocation(New);
				board[(int) New.getX()][(int) New.getY()]=c;
				board[(int) old.getX()][(int) old.getY()]=null;
				getCurrentChampion().setCurrentActionPoints(x-1);
			}
			else
				throw new UnallowedMovementException();	
		}
	}

	public static int Manhattan (Point a , Point b)
	{
		//The Manhattan Distance between two points (X1, Y1) and (X2, Y2) 
		//is given by |X1 � X2| + |Y1 � Y2|.

		return (int)((Math.abs(a.getY() - b.getY())) + (Math.abs(a.getX() - b.getX())));
	}

	public void castAbility(Ability a) throws NotEnoughResourcesException, AbilityUseException, CloneNotSupportedException
	{
		if(a.getCastArea()!= AreaOfEffect.TEAMTARGET &&
			a.getCastArea()!= AreaOfEffect.SELFTARGET &&
			a.getCastArea()!= AreaOfEffect.SURROUND)
		{
			throw new AbilityUseException();
		}

		Champion c=getCurrentChampion();
		if (c.getCurrentActionPoints()<a.getRequiredActionPoints() 
				|| c.getMana()<a.getManaCost())
			throw new NotEnoughResourcesException();

		if(a.getCurrentCooldown()>0)
			throw new AbilityUseException();

		int now=0;
		while (now<c.getAppliedEffects().size())
		{
			if (c.getAppliedEffects().get(now) instanceof Silence)
				throw new AbilityUseException();
			now++;
		}
		castAbility1Helper(a);
		c.setCurrentActionPoints(c.getCurrentActionPoints()-a.getRequiredActionPoints());
		c.setMana(c.getMana()-a.getManaCost());
		a.setCurrentCooldown(a.getBaseCooldown());
		
	}

	public void castAbility1Helper(Ability a) throws CloneNotSupportedException, AbilityUseException
	{
		Champion c=getCurrentChampion();
		AreaOfEffect z =a.getCastArea();
		ArrayList <Damageable> validTargets=new ArrayList <Damageable>() ;
		int i=0;

		if (z==AreaOfEffect.TEAMTARGET)
		{
			if (a instanceof DamagingAbility)
			{
				if (returnPlayer(c)==firstPlayer)
				{
					while(i<secondPlayer.getTeam().size())
					{
						Champion target=secondPlayer.getTeam().get(i);
						if (Manhattan(c.getLocation(),target.getLocation())<= a.getCastRange())
							validTargets.add(target);
						i++;
					}	
				}
				else
				{
					while(i<firstPlayer.getTeam().size())
					{
						Champion target=firstPlayer.getTeam().get(i);
						if (Manhattan(c.getLocation(),target.getLocation())<= a.getCastRange())
							validTargets.add(target);
						i++;
					}	
				}	
			}

			else if (a instanceof HealingAbility)
			{
				if (returnPlayer(c)==secondPlayer)
				{
					while(i<secondPlayer.getTeam().size())
					{
						Champion target=secondPlayer.getTeam().get(i);
						if (Manhattan(c.getLocation(),target.getLocation())<= a.getCastRange())
							validTargets.add(target);
						i++;
					}	
				}
				else
				{
					while(i<firstPlayer.getTeam().size())
					{
						Champion target=firstPlayer.getTeam().get(i);
						if (Manhattan(c.getLocation(),target.getLocation())<= a.getCastRange())
							validTargets.add(target);
						i++;
					}	
				}	
			}

			else if (a instanceof CrowdControlAbility)
			{
				if (((CrowdControlAbility) a).getEffect().getType()==EffectType.BUFF)
				{
					if (returnPlayer(c)==secondPlayer)
					{
						while(i<secondPlayer.getTeam().size())
						{
							Champion target=secondPlayer.getTeam().get(i);
							if (Manhattan(c.getLocation(),target.getLocation())<= a.getCastRange())
								validTargets.add(target);
							i++;
						}	
					}
					else
						if (returnPlayer(c)==firstPlayer)
						{
							while(i<firstPlayer.getTeam().size())
							{
								Champion target=firstPlayer.getTeam().get(i);
								if (Manhattan(c.getLocation(),target.getLocation())<= a.getCastRange())
									validTargets.add(target);
								i++;
							}	
						}	
				}
				else 
				{
					if (returnPlayer(c)==firstPlayer)
					{
						while(i<secondPlayer.getTeam().size())
						{
							Champion target=secondPlayer.getTeam().get(i);
							if (Manhattan(c.getLocation(),target.getLocation())<= a.getCastRange())
								validTargets.add(target);
							i++;
						}	
					}
					else if (returnPlayer(c)==secondPlayer)
					{
						while(i<firstPlayer.getTeam().size())
						{
							Champion target=firstPlayer.getTeam().get(i);
							if (Manhattan(c.getLocation(),target.getLocation())<= a.getCastRange())
								validTargets.add(target);
							i++;
						}	
					}
				}	
			}	

			a.execute(validTargets);
			for (Damageable target : validTargets) {
				if(isDead(target))
					removeDead(target);
			}

			return;
		}

		else if(z==AreaOfEffect.SELFTARGET)
		{
			validTargets.add(c);
			a.execute(validTargets);
		}	

		else if (z==AreaOfEffect.SURROUND)
		{
			if (a instanceof DamagingAbility)
			{
				Point champ= c.getLocation();
				ArrayList<Damageable> targets= validSurroundTargetsDamaging(champ);
				for (Damageable damageable : targets) 
				{
					if (damageable instanceof Champion)
					{
						for (Effect effect : ((Champion)damageable).getAppliedEffects()) 
						{
							if(effect instanceof Shield)
							{
								((Champion)damageable).getAppliedEffects().remove(effect);
								effect.remove((Champion) damageable);
								return;
							}

						}
					}
				}
				a.execute(validSurroundTargetsDamaging(champ));
				for (Damageable target : validSurroundTargetsDamaging(champ)) {
					if(isDead(target))
						removeDead(target);
				}
				return;
			}	
			else if(a instanceof HealingAbility)
			{
				Point champ= c.getLocation();
				a.execute(validSurroundTargetsHealing(champ));
				return;
			}	
			else if(a instanceof CrowdControlAbility)
			{
				if (((CrowdControlAbility) a).getEffect().getType()==EffectType.BUFF)
				{
					Point champ= c.getLocation();
					a.execute(validSurroundTargetsHealing(champ));
					for (Damageable target : validSurroundTargetsHealing(champ)) {
						if(isDead(target))
							removeDead(target);
					}
					return;
				}
				else if (((CrowdControlAbility) a).getEffect().getType()==EffectType.DEBUFF)
				{
					Point champ= c.getLocation();
					a.execute(validSurroundTargetsDebuff(champ));
					for (Damageable target : validSurroundTargetsDebuff(champ)) {
						if(isDead(target))
							removeDead(target);
					}
					return;
				}
			}
			a.execute(validTargets);
			for (Damageable target : validTargets) {
				if(isDead(target))
					removeDead(target);
			}
			return;
		}
	}

	public void castAbility(Ability a, Direction d) throws CloneNotSupportedException, AbilityUseException, NotEnoughResourcesException
	{
		if(a.getCastArea()!= AreaOfEffect.DIRECTIONAL)
			{
				throw new AbilityUseException();
			}
		
		Champion c=getCurrentChampion();
		if (c.getCurrentActionPoints()<a.getRequiredActionPoints() 
				|| c.getMana()<a.getManaCost())
			throw new NotEnoughResourcesException();
		if(a.getCurrentCooldown()>0)
			throw new AbilityUseException();

		for (Effect effect : c.getAppliedEffects()) {
			if(effect instanceof Silence)
				throw new AbilityUseException();
		}
		ArrayList <Damageable> validTargets=new ArrayList <Damageable>() ;
		Point old= (Point)c.getLocation();
		int x = old.x;
		int y = old.y;
		for(int i=0 ; i<a.getCastRange();i++)
		{
			if (d == Direction.UP)
				x++;
			if (d== Direction.DOWN)
				x--;
			if (d== Direction.RIGHT)
				y++;
			if (d== Direction.LEFT)
				y--;

			Point New = new Point(x,y);
			if(withinBoard(New))
			{
				if (a instanceof HealingAbility || (a instanceof CrowdControlAbility && ((CrowdControlAbility) a).getEffect().getType()==EffectType.BUFF ))
				{
					if (board [x][y] instanceof Champion && isFriendly(c,(Champion)board [x][y] ))
					{
						validTargets.add((Damageable) board [x][y]);
					}

				}
				else if(a instanceof DamagingAbility)
				{
					if(board [x][y] instanceof Champion && !isFriendly(c,(Champion)board [x][y] ))
					{	
						validTargets.add((Damageable) board [x][y]);
						for (Effect effect : ((Champion)board [x][y]).getAppliedEffects())
						{
							if (effect instanceof Shield)
							{	effect.remove((Champion)board [x][y]);
							c.getAppliedEffects().remove(effect);
							validTargets.remove((Champion)board [x][y]);
							}
						}
					}

					if(board [x][y] instanceof Cover)
					{
						validTargets.add((Damageable) board [x][y]);
					}

				}
				else if (a instanceof CrowdControlAbility && ((CrowdControlAbility) a).getEffect().getType()==EffectType.DEBUFF )
				{
					if(board [x][y] instanceof Champion && !isFriendly(c,(Champion)board [x][y] ))
					{
						validTargets.add((Damageable) board [x][y]);
					}
				}
			}
		}
		a.execute(validTargets);
		for (Damageable target : validTargets) 
		{
			if(target.getCurrentHP()<=0)
				removeDead(target);
		}
		c.setCurrentActionPoints(c.getCurrentActionPoints()-a.getRequiredActionPoints());
		c.setMana(c.getMana()-a.getManaCost());
		a.setCurrentCooldown(a.getBaseCooldown());
	}

	public void castAbility(Ability a, int x, int y) throws AbilityUseException, CloneNotSupportedException, NotEnoughResourcesException, InvalidTargetException
	{
		if(a.getCastArea()!= AreaOfEffect.SINGLETARGET)
		{
			throw new AbilityUseException();
		}
		Champion c=getCurrentChampion();
		if (c.getCurrentActionPoints()<a.getRequiredActionPoints() || c.getMana()<a.getManaCost())
			throw new NotEnoughResourcesException();		
		if(a.getCurrentCooldown()>0)
			throw new AbilityUseException();

		for (Effect effect : c.getAppliedEffects()) 
		{
			if (effect instanceof Silence)
				throw new AbilityUseException();
		}
		ArrayList <Damageable> validTargets=new ArrayList <Damageable>() ;
		Point New = new Point(x,y);
		if(board[x][y]==null || !withinBoard(New) )
			throw new InvalidTargetException();

		if(Manhattan(c.getLocation(),New)> a.getCastRange())
			throw new AbilityUseException();

		Damageable d = (Damageable) board[x][y];
		if(a instanceof HealingAbility)
		{
			if (d instanceof Cover || (d instanceof Champion && !isFriendly(c,(Champion)d)))
				throw new InvalidTargetException();
			else validTargets.add(d);
		}
		if(a instanceof DamagingAbility)
		{
			if (d instanceof Champion && isFriendly(c,(Champion)d) )
				throw new InvalidTargetException();
			else
			{
				Champion ll=(Champion)d;
				for(int g=0;g<ll.getAppliedEffects().size();g++)
				{
					if (ll.getAppliedEffects().get(g) instanceof Shield)
					{
						c.setCurrentActionPoints(c.getCurrentActionPoints()-a.getRequiredActionPoints());
						c.setMana(c.getMana()-a.getManaCost());
						a.setCurrentCooldown(a.getBaseCooldown());
						return;
					}
				}
			}
			validTargets.add(d);
		}
		if(a instanceof CrowdControlAbility)
		{	
			if(d instanceof Cover)
				throw new InvalidTargetException("Can't do CrowdControlAbility on a cover");

			if (((CrowdControlAbility) a).getEffect().getType()==EffectType.BUFF)
			{
				if ( d instanceof Champion  && !isFriendly(c,(Champion)d))
					throw new InvalidTargetException();
				validTargets.add(d);	
			}
			if (((CrowdControlAbility) a).getEffect().getType()==EffectType.DEBUFF)
			{
				if (isFriendly(c,(Champion)d))
					throw new InvalidTargetException();
				validTargets.add(d);	
			}
		}
		a.execute(validTargets);
		for (Damageable target : validTargets) {
			if(isDead(target))
				removeDead(target);
		}
		c.setCurrentActionPoints(c.getCurrentActionPoints()-a.getRequiredActionPoints());
		c.setMana(c.getMana()-a.getManaCost());
		a.setCurrentCooldown(a.getBaseCooldown());
	}

	public boolean isFriendly(Champion c, Champion d)
	{	
		if(((firstPlayer.getTeam().contains(c)) && (firstPlayer.getTeam().contains(d)))
				|| ((secondPlayer.getTeam().contains(c)) && (secondPlayer.getTeam().contains(d))))

			return true;
		else
			return false;
	}

	public static boolean isDead(Damageable d)
	{
		if (d instanceof Champion)
		{
			Champion c = (Champion) d;
			if (c.getCondition()== Condition.KNOCKEDOUT || c.getCurrentHP()<=0)
			{
				return true;
			}
			else
				return false;
		}
		else if (d instanceof Cover)
		{
			Cover c = (Cover) d;
			if (c.getCurrentHP()<=0)
			{
				return true;
			}
			else
				return false;
		}

		return false;
	}

	public void removeFromBoard(Damageable d)
	{
		board [d.getLocation().x][d.getLocation().y] = null ;
	}

	public Player returnPlayer(Champion c)
	{
		firstPlayer.getTeam();
		for (int i=0; i<firstPlayer.getTeam().size();i++)
		{
			if (c==firstPlayer.getTeam().get(i))
				return firstPlayer;
			else if (c==secondPlayer.getTeam().get(i))
				return secondPlayer;
		}
		return null;
	}

	public static boolean withinBoard (Point p)
	{
		int x = (int) p.getX();
		int y = (int) p.getY();
		if (x >= BOARDHEIGHT || x < 0 || y >= BOARDWIDTH || y < 0 )
		{
			return false;
		}
		else 
			return true;
	}


	public ArrayList<Damageable> validSurroundTargetsDamaging(Point p)
	{
		Champion c=getCurrentChampion();
		ArrayList<Damageable> damageables= new ArrayList<Damageable> ();
		int x = (int) p.getX() ;
		int y = (int) p.getY() ;
		Point New1 = new Point(x+1,y-1);
		Point New2 = new Point(x+1,y);
		Point New3 = new Point(x+1,y+1);
		Point New4 = new Point(x,y-1);
		Point New5 = new Point(x,y+1);
		Point New6 = new Point(x-1,y-1);
		Point New7 = new Point(x-1,y);
		Point New8 = new Point(x-1,y+1);

		if ( withinBoard(New1) )
		{
			if (board [(int) New1.getX()] [(int) New1.getY()] instanceof Damageable)
				if ( board [(int) New1.getX()] [(int) New1.getY()] instanceof Champion)
				{	
					if (isFriendly(c,(Champion)board [(int) New1.getX()] [(int) New1.getY()]) ==false)
						damageables.add((Damageable) board [(int) New1.getX()] [(int) New1.getY()]);
				}

				else
				{
					damageables.add((Damageable) board [(int) New1.getX()] [(int) New1.getY()]);
				}

		}
		if ( withinBoard(New2) )
		{
			if (board [(int) New2.getX()] [(int) New2.getY()] instanceof Damageable)
				if ( board [(int) New2.getX()] [(int) New2.getY()] instanceof Champion)
				{	
					if ( isFriendly(c,(Champion)board [(int) New2.getX()] [(int) New2.getY()]) ==false)
						damageables.add((Damageable) board [(int) New2.getX()] [(int) New2.getY()]);
				}else
				{
					damageables.add((Damageable) board [(int) New2.getX()] [(int) New2.getY()]);
				}
		}
		if ( withinBoard(New3) )
		{	
			if (board [(int) New3.getX()] [(int) New3.getY()] instanceof Damageable)
				if ( board [(int) New3.getX()] [(int) New3.getY()] instanceof Champion)
				{	
					if ( isFriendly(c,(Champion)board [(int) New3.getX()] [(int) New3.getY()])  ==false)
						damageables.add((Damageable) board [(int) New3.getX()] [(int) New3.getY()]);
				}else
				{
					damageables.add((Damageable) board [(int) New3.getX()] [(int) New3.getY()]);
				}
		}

		if ( withinBoard(New4) )
		{
			if (board [(int) New4.getX()] [(int) New4.getY()] instanceof Damageable)
				if ( board [(int) New4.getX()] [(int) New4.getY()] instanceof Champion)
				{	
					if ( isFriendly(c,(Champion)board [(int) New4.getX()] [(int) New4.getY()])  ==false)
						damageables.add((Damageable) board [(int) New4.getX()] [(int) New4.getY()]);
				}else
				{
					damageables.add((Damageable) board [(int) New4.getX()] [(int) New4.getY()]);
				}
		}
		if ( withinBoard(New5) )
		{
			if (board [(int) New5.getX()] [(int) New5.getY()] instanceof Damageable)
				if ( board [(int) New5.getX()] [(int) New5.getY()] instanceof Champion)
				{	
					if ( isFriendly(c,(Champion)board [(int) New5.getX()] [(int) New5.getY()])  ==false)
						damageables.add((Damageable) board [(int) New5.getX()] [(int) New5.getY()]);
				}else
				{
					damageables.add((Damageable) board [(int) New5.getX()] [(int) New6.getY()]);
				}
		}
		if ( withinBoard(New6) )
		{if (board [(int) New6.getX()] [(int) New6.getY()] instanceof Damageable)
			if ( board [(int) New6.getX()] [(int) New6.getY()] instanceof Champion)
			{	
				if ( isFriendly(c,(Champion)board [(int) New6.getX()] [(int) New6.getY()]) ==false )
					damageables.add((Damageable) board [(int) New6.getX()] [(int) New6.getY()]);
			}else
			{
				damageables.add((Damageable) board [(int) New6.getX()] [(int) New6.getY()]);
			}
		}
		if ( withinBoard(New7) )
		{
			if (board [(int) New7.getX()] [(int) New7.getY()] instanceof Damageable)
				if (board [(int) New7.getX()] [(int) New7.getY()] instanceof Damageable)
					if ( board [(int) New7.getX()] [(int) New7.getY()] instanceof Champion)
					{	
						if ( isFriendly(c,(Champion)board [(int) New7.getX()] [(int) New7.getY()]) ==false )
							damageables.add((Damageable) board [(int) New7.getX()] [(int) New7.getY()]);
					}else
					{
						damageables.add((Damageable) board [(int) New7.getX()] [(int) New7.getY()]);
					}
		}
		if ( withinBoard(New8) )
		{if (board [(int) New8.getX()] [(int) New8.getY()] instanceof Damageable)
			if ( board [(int) New8.getX()] [(int) New8.getY()] instanceof Champion)
			{	
				if ( isFriendly(c,(Champion)board [(int) New8.getX()] [(int) New8.getY()]) ==false )
					damageables.add((Damageable) board [(int) New8.getX()] [(int) New8.getY()]);
			}else
			{
				damageables.add((Damageable) board [(int) New8.getX()] [(int) New8.getY()]);
			}
		}

		return damageables;
	}

	public ArrayList<Damageable> validSurroundTargetsHealing(Point p)
	{
		Champion c=getCurrentChampion();
		ArrayList<Damageable> targets= new ArrayList<Damageable> ();
		int x = (int) p.getX() ;
		int y = (int) p.getY() ;
		Point New1 = new Point(x+1,y-1);
		Point New2 = new Point(x+1,y);
		Point New3 = new Point(x+1,y+1);
		Point New4 = new Point(x,y-1);
		Point New5 = new Point(x,y+1);
		Point New6 = new Point(x-1,y-1);
		Point New7 = new Point(x-1,y);
		Point New8 = new Point(x-1,y+1);

		if ( withinBoard(New1) )
		{
			if (board [(int) New1.getX()] [(int) New1.getY()] instanceof Champion && isFriendly(c,(Champion)board[(int) New1.getX()] [(int) New1.getY()])==true   )
			{ 
				targets.add((Damageable) board [(int) New1.getX()] [(int) New1.getY()]);
			}
		}if ( withinBoard(New2) )
		{
			if (board [(int) New2.getX()] [(int) New2.getY()] instanceof Champion && isFriendly(c,(Champion)board[(int) New2.getX()] [(int) New2.getY()]) ==true  )
			{ 
				targets.add((Damageable) board [(int) New2.getX()] [(int) New2.getY()]);
			}
		}if ( withinBoard(New3) )
		{
			if (board [(int) New3.getX()] [(int) New3.getY()] instanceof Champion && isFriendly(c,(Champion)board[(int) New3.getX()] [(int) New3.getY()])  ==true )
			{ 
				targets.add((Damageable) board [(int) New3.getX()] [(int) New3.getY()]);
			}
		}if ( withinBoard(New4) )
		{
			if (board [(int) New4.getX()] [(int) New4.getY()] instanceof Champion && isFriendly(c,(Champion)board[(int) New4.getX()] [(int) New4.getY()])  ==true )
			{ 
				targets.add((Damageable) board [(int) New4.getX()] [(int) New4.getY()]);
			}
		}if ( withinBoard(New5) )
		{
			if (board [(int) New5.getX()] [(int) New5.getY()] instanceof Champion && isFriendly(c,(Champion)board[(int) New5.getX()] [(int) New5.getY()])==true   )
			{ 
				targets.add((Damageable) board [(int) New5.getX()] [(int) New5.getY()]);
			}
		}if ( withinBoard(New6) )
		{
			if (board [(int) New6.getX()] [(int) New6.getY()] instanceof Champion && isFriendly(c,(Champion)board[(int) New6.getX()] [(int) New6.getY()]) ==true  )
			{ 
				targets.add((Damageable) board [(int) New6.getX()] [(int) New6.getY()]);
			}
		}if ( withinBoard(New7) )
		{
			if (board [(int) New7.getX()] [(int) New7.getY()] instanceof Champion && isFriendly(c,(Champion)board[(int) New7.getX()] [(int) New7.getY()])  ==true )
			{ 
				targets.add((Damageable) board [(int) New7.getX()] [(int) New7.getY()]);
			}
		}if ( withinBoard(New8) )
		{
			if (board [(int) New8.getX()] [(int) New8.getY()] instanceof Champion && isFriendly(c,(Champion)board[(int) New8.getX()] [(int) New8.getY()])  ==true )
			{ 
				targets.add((Damageable) board [(int) New8.getX()] [(int) New8.getY()]);
			}
		}
		return targets;
	}

	public ArrayList<Damageable> validSurroundTargetsDebuff(Point p)
	{
		Champion c=getCurrentChampion();
		ArrayList<Damageable> damageables= new ArrayList<Damageable> ();
		int x = (int) p.getX() ;
		int y = (int) p.getY() ;
		Point New1 = new Point(x+1,y-1);
		Point New2 = new Point(x+1,y);
		Point New3 = new Point(x+1,y+1);
		Point New4 = new Point(x,y-1);
		Point New5 = new Point(x,y+1);
		Point New6 = new Point(x-1,y-1);
		Point New7 = new Point(x-1,y);
		Point New8 = new Point(x-1,y+1);

		if ( withinBoard(New1) )
		{
			if (board [(int) New1.getX()] [(int) New1.getY()] instanceof Damageable)
				if ( board [(int) New1.getX()] [(int) New1.getY()] instanceof Champion)
				{	
					if (isFriendly(c,(Champion)board [(int) New1.getX()] [(int) New1.getY()]) ==false)
						damageables.add((Damageable) board [(int) New1.getX()] [(int) New1.getY()]);
				}

		}
		if ( withinBoard(New2) )
		{
			if (board [(int) New2.getX()] [(int) New2.getY()] instanceof Damageable)
				if ( board [(int) New2.getX()] [(int) New2.getY()] instanceof Champion)
				{	
					if ( isFriendly(c,(Champion)board [(int) New2.getX()] [(int) New2.getY()]) ==false)
						damageables.add((Damageable) board [(int) New2.getX()] [(int) New2.getY()]);
				}
		}
		if ( withinBoard(New3) )
		{	
			if (board [(int) New3.getX()] [(int) New3.getY()] instanceof Damageable)
				if ( board [(int) New3.getX()] [(int) New3.getY()] instanceof Champion)
				{	
					if ( isFriendly(c,(Champion)board [(int) New3.getX()] [(int) New3.getY()])  ==false)
						damageables.add((Damageable) board [(int) New3.getX()] [(int) New3.getY()]);
				}
		}

		if ( withinBoard(New4) )
		{
			if (board [(int) New4.getX()] [(int) New4.getY()] instanceof Damageable)
				if ( board [(int) New4.getX()] [(int) New4.getY()] instanceof Champion)
				{	
					if ( isFriendly(c,(Champion)board [(int) New4.getX()] [(int) New4.getY()])  ==false)
						damageables.add((Damageable) board [(int) New4.getX()] [(int) New4.getY()]);
				}
		}
		if ( withinBoard(New5) )
		{
			if (board [(int) New5.getX()] [(int) New5.getY()] instanceof Damageable)
				if ( board [(int) New5.getX()] [(int) New5.getY()] instanceof Champion)
				{	
					if ( isFriendly(c,(Champion)board [(int) New5.getX()] [(int) New5.getY()])  ==false)
						damageables.add((Damageable) board [(int) New5.getX()] [(int) New5.getY()]);
				}
		}
		if ( withinBoard(New6) )
		{if (board [(int) New6.getX()] [(int) New6.getY()] instanceof Damageable)
			if ( board [(int) New6.getX()] [(int) New6.getY()] instanceof Champion)
			{	
				if ( isFriendly(c,(Champion)board [(int) New6.getX()] [(int) New6.getY()]) ==false )
					damageables.add((Damageable) board [(int) New6.getX()] [(int) New6.getY()]);
			}
		}
		if ( withinBoard(New7) )
		{
			if (board [(int) New7.getX()] [(int) New7.getY()] instanceof Damageable)
				if (board [(int) New7.getX()] [(int) New7.getY()] instanceof Damageable)
					if ( board [(int) New7.getX()] [(int) New7.getY()] instanceof Champion)
					{	
						if ( isFriendly(c,(Champion)board [(int) New7.getX()] [(int) New7.getY()]) ==false )
							damageables.add((Damageable) board [(int) New7.getX()] [(int) New7.getY()]);
					}
		}
		if ( withinBoard(New8) )
		{if (board [(int) New8.getX()] [(int) New8.getY()] instanceof Damageable)
			if ( board [(int) New8.getX()] [(int) New8.getY()] instanceof Champion)
			{	
				if ( isFriendly(c,(Champion)board [(int) New8.getX()] [(int) New8.getY()]) ==false )
					damageables.add((Damageable) board [(int) New8.getX()] [(int) New8.getY()]);
			}
		}

		return damageables;
	}


	private void prepareChampionTurns()
	{
		ArrayList <Champion> T1 =  firstPlayer.getTeam();
		for(int i=0 ; i<T1.size() ; i++)
			turnOrder.insert(T1.get(i)); 
		ArrayList <Champion> T2 =  secondPlayer.getTeam();
		for(int i=0 ; i<T2.size() ; i++)
			turnOrder.insert(T2.get(i)); 
	}

	public void endTurn()
	{ 
		turnOrder.remove();
		if(turnOrder.isEmpty())
			prepareChampionTurns();	
		while(getCurrentChampion().getCondition() == Condition.INACTIVE) 
		{
			Champion c=getCurrentChampion();
			updateEffectsAbilities(c);
			turnOrder.remove();
			if(turnOrder.isEmpty())
				prepareChampionTurns();
			c=getCurrentChampion();
		}

		if (turnOrder.isEmpty())
			prepareChampionTurns();
		Champion c=getCurrentChampion();
		c=getCurrentChampion();
		updateEffectsAbilities(c);
		c.setCurrentActionPoints(c.getMaxActionPointsPerTurn());
	}

	public void updateEffectsAbilities(Champion c)
	{
		for (int i=0; i<c.getAppliedEffects().size();i++) 
		{	
			Effect effect=c.getAppliedEffects().get(i);
			effect.setDuration(effect.getDuration()-1);
			if(effect.getDuration()==0)
			{
				effect.remove(c);
				c.getAppliedEffects().remove(effect);
				i--;
			}
		}
		for (Ability ability : c.getAbilities()) 
		{
			ability.setCurrentCooldown(ability.getCurrentCooldown()-1);	
		}
	}

	public void removeDead(Damageable c) 
	{

		if(isDead(c)&& c instanceof Cover)
		{
			removeFromBoard(c);
		}	
		else 	
			if(isDead(c) && c instanceof Champion) 
			{
				removeFromBoard(c);
				if (firstPlayer.getTeam().contains(c))
					firstPlayer.getTeam().remove(c);
				else
					secondPlayer.getTeam().remove(c);
				ArrayList <Champion> temp = new ArrayList<Champion>();
				while(!turnOrder.isEmpty())
				{
					temp.add((Champion) turnOrder.remove());
				}
				temp.remove(c);
				for (Champion champion : temp) {
					turnOrder.insert(champion);
				}
			}
	}

	public void attack(Direction d) throws InvalidTargetException,NotEnoughResourcesException, ChampionDisarmedException
	{
		Champion c=getCurrentChampion();
		if (c.getCurrentActionPoints()<2)
			throw new NotEnoughResourcesException();
		for (Effect effect : c.getAppliedEffects()) {
			if ( effect instanceof Disarm)
				throw new ChampionDisarmedException();
		}
		Point old = c.getLocation();
		int x= old.x;
		int y= old.y;
		for(int i=0;i<c.getAttackRange();i++)
		{
			if (d == Direction.UP)
				x++;
			if (d== Direction.DOWN)
				x--;
			if (d== Direction.RIGHT)
				y++;
			if (d== Direction.LEFT)
				y--;

			Point New = new Point(x,y);
			if(withinBoard(New)&& board[x][y]!=null)
			{ 
				Damageable target = (Damageable) board[x][y];
				if (target instanceof Cover)
				{
					target.setCurrentHP(target.getCurrentHP()-c.getAttackDamage());
					c.setCurrentActionPoints(c.getCurrentActionPoints()-2);	
					if (target.getCurrentHP()<=0)
					{
						removeFromBoard(target);
					}
					return;
				}
				else if(target instanceof Champion)
				{	
					for (Effect effect : ((Champion) target).getAppliedEffects()) 
					{
						if (effect instanceof Shield)
						{
							effect.remove((Champion) target);
							((Champion) target).getAppliedEffects().remove(effect);
							c.setCurrentActionPoints(c.getCurrentActionPoints()-2);
							return;					
						}
						else if(effect instanceof Dodge)
						{
							int rand=((int) (Math.random() * (1000 - 1))) + 1;
							if (rand%2==0)
							{
								//effect.remove((Champion) target);
								//((Champion) target).getAppliedEffects().remove(((Champion) target).getAppliedEffects().get(i));
								c.setCurrentActionPoints(c.getCurrentActionPoints()-2);
								return;
							}
						}
					}

					int damage= c.getAttackDamage();
					if(c instanceof Hero  && !( target instanceof Hero) ||
							c instanceof AntiHero  && !( target instanceof AntiHero) ||
							c instanceof Villain  && !( target instanceof Villain)
							)
					{
						damage = (int)(damage * 1.5);	
					}
					target.setCurrentHP((int) (target.getCurrentHP()-damage));
					c.setCurrentActionPoints(c.getCurrentActionPoints()-2);	
					if(isDead(target))
						removeDead(target);	
				}
			}
		}
	}


	public void attack2(Direction d) throws InvalidTargetException,NotEnoughResourcesException, ChampionDisarmedException
	{
		Champion c=getCurrentChampion();
		if (c.getCurrentActionPoints()<2)
			throw new NotEnoughResourcesException();
		for (Effect effect : c.getAppliedEffects()) {
			if ( effect instanceof Disarm)
				throw new ChampionDisarmedException();
		}
		helperAttack(c,d);
		c.setCurrentActionPoints(c.getCurrentActionPoints()-2);


	}

	public void helperAttack(Champion c, Direction d)
	{
		if (d==Direction.UP)
		{
			Point old= (Point)c.getLocation();
			Point New=new Point();
			int i=1;
			New.setLocation(old.getX()+c.getAttackRange(),old.getY());
			int distance = Manhattan(old,New);
			while (i<distance)
			{
				New.setLocation(old.getX()+i,old.getY());
				if (New.x>BOARDWIDTH-1)
					return;
				Damageable ff=(Damageable) board[(int) New.getX()][(int) New.getY()];
				if(ff instanceof Champion && validTarget(New)==true)
				{	
					makeAttack(c, New);
					Champion target = (Champion)board[(int) New.getX()][(int) New.getY()];
					if(isDead(target))
						removeDead(target);
					return;
				}
				else if (ff instanceof Cover)
				{	
					makeAttack(c, New);
					Cover K = (Cover)board[(int) New.getX()][(int) New.getY()];

					if(isDead(K))
						removeDead(K);


					return;
				}
				i++;
			}
			return;
		}

		else if (d==Direction.DOWN)
		{
			Point old= (Point)c.getLocation();
			Point New=new Point();
			int i=1;
			New.setLocation(old.getX()-c.getAttackRange(),old.getY());
			int distance = Manhattan(old,New);
			while (i<distance)
			{
				New.setLocation(old.getX()-i,old.getY());
				if (New.x<0)
					return;
				Damageable ff=(Damageable) board[(int) New.getX()][(int) New.getY()];
				if(ff instanceof Champion && validTarget(New)==true)
				{	
					makeAttack(c, New);
					Champion target = (Champion)board[(int) New.getX()][(int) New.getY()];
					if(isDead(target))
						removeDead(target);
					return;
				}
				else if (ff instanceof Cover)
				{	
					makeAttack(c, New);
					Cover K = (Cover)board[(int) New.getX()][(int) New.getY()];
					if(isDead(K))
						removeDead(K);
					return;
				}
				i++;
			}
			return;
		}

		else if (d==Direction.LEFT)
		{
			Point old= (Point)c.getLocation();
			Point New=new Point();
			int i=1;
			New.setLocation(old.getX(),old.getY()-c.getAttackRange());
			int distance = Manhattan(old,New);
			while (i<distance)
			{
				New.setLocation(old.getX(),old.getY()-i);
				if (New.x<0)
					return;
				Damageable ff=(Damageable) board[(int) New.getX()][(int) New.getY()];
				if(ff instanceof Champion && validTarget(New)==true)
				{	
					makeAttack(c, New);
					Champion target = (Champion)board[(int) New.getX()][(int) New.getY()];
					if(isDead(target))
						removeDead(target);
					return;
				}
				else if (ff instanceof Cover)
				{	
					makeAttack(c, New);
					Cover K = (Cover)board[(int) New.getX()][(int) New.getY()];
					if(isDead(K))
						removeDead(K);
					return;
				}
				i++;
			}
			return;
		}

		else if (d==Direction.RIGHT)
		{
			Point old= (Point)c.getLocation();
			Point New=new Point();
			int i=1;
			New.setLocation(old.getX(),old.getY()+c.getAttackRange());
			int distance = Manhattan(old,New);
			while (i<distance)
			{
				New.setLocation(old.getX(),old.getY()+i);
				if (New.x>BOARDHEIGHT-1)
					return;
				Damageable ff=(Damageable) board[(int) New.getX()][(int) New.getY()];
				if(ff instanceof Champion && validTarget(New)==true)
				{	
					makeAttack(c, New);
					Champion target = (Champion)board[(int) New.getX()][(int) New.getY()];
					if(isDead(target))
						removeDead(target);
					return;
				}
				else if (ff instanceof Cover)
				{	
					makeAttack(c, New);
					Cover K = (Cover)board[(int) New.getX()][(int) New.getY()];
					if(isDead(K))
						removeDead(K);
					return;
				}
				i++;
			}
			return;
		}
	}


	public boolean validTarget(Point New)
	{
		Champion c=getCurrentChampion();
		Champion target= (Champion) board[(int) New.getX()][(int) New.getY()];
		target.getAppliedEffects();
		int i=0;
		if (isFriendly(c,target)==true)
			return false;
		else 
		{
			while(i<target.getAppliedEffects().size())
			{
				if(target.getAppliedEffects().get(i) instanceof Shield)
				{
					target.getAppliedEffects().get(i).remove(target);
					target.getAppliedEffects().remove(target.getAppliedEffects().get(i));
					i--;
					return false;
				}

				else if (target.getAppliedEffects().get(i) instanceof Dodge)
				{
					int rand=((int) (Math.random() * (1000 - 1))) + 1;
					if (rand%2==0)
					{
						target.getAppliedEffects().get(i).remove(target);
						target.getAppliedEffects().remove(target.getAppliedEffects().get(i));

						return false;
					}
				}
				i++;
			}
			return true;
		}
	}
	public void makeAttack(Champion c , Point New) 
	{
		Damageable z=(Damageable) board[(int)New.getX()][(int)New.getY()];

		if (z instanceof Champion)
		{
			if ((z instanceof Hero && c instanceof Hero)								
					||(z instanceof Villain && c instanceof Villain)
					||(z instanceof AntiHero && c instanceof AntiHero))
			{
				((Champion) z).setCurrentHP(((Champion) z).getCurrentHP()-c.getAttackDamage());
			}
			else {
				((Champion) z).setCurrentHP(((Champion) z).getCurrentHP()-((int)(c.getAttackDamage()*1.5)));
			}
			if (z.getCurrentHP()<= 0)
				removeDead((Champion)z);
		}
		else if (z instanceof Cover)
		{
			((Cover) z).setCurrentHP(((Cover) z).getCurrentHP()-c.getAttackDamage());
			if (z.getCurrentHP()<=0)
			{
				removeFromBoard(z);
			}
		}
		c.setCurrentActionPoints(c.getCurrentActionPoints()-2);
	}

	public void useLeaderAbility() throws LeaderNotCurrentException, LeaderAbilityAlreadyUsedException
	{
		Champion c = getCurrentChampion();
		Champion d =(Champion)this.getFirstPlayer().getLeader();
		Champion e =(Champion)this.getSecondPlayer().getLeader();
		ArrayList <Champion> targets = new ArrayList <Champion> () ;
		if (c!=d && c!=e)
			throw new LeaderNotCurrentException();
		if (c==d) //first player is leader
			if (firstLeaderAbilityUsed==true)
				throw new LeaderAbilityAlreadyUsedException();
		if (c==e)
			if (secondLeaderAbilityUsed==true)
				throw new LeaderAbilityAlreadyUsedException();
		if (c instanceof Hero) 
		{
			ArrayList<Champion> team= null;
			if (c==d)
				team=firstPlayer.getTeam();
			else
				team=secondPlayer.getTeam();
			for (Champion champion : team) 
			{
				targets.add(champion);
			}
			c.useLeaderAbility(targets);
			if (c==d)
				firstLeaderAbilityUsed=true;
			else
				secondLeaderAbilityUsed= true;
		}
		if (c instanceof Villain)
		{
			ArrayList<Champion> team= null;
			if (c==e)
				team=firstPlayer.getTeam();
			else
				team=secondPlayer.getTeam();

			for (Champion champion : team) 
			{	if(champion.getCurrentHP()<(champion.getMaxHP()*0.3))
				targets.add(champion);
			}
			c.useLeaderAbility(targets);
			if (c==d)
				firstLeaderAbilityUsed=true;
			else
				secondLeaderAbilityUsed= true;
		}
		if (c instanceof AntiHero)
		{
			for (Champion champion : getFirstPlayer().getTeam()) {
				if(champion != d)
					targets.add(champion);
			}
			for (Champion champion : getSecondPlayer().getTeam()) {
				if(champion != e)
					targets.add(champion);
			}
			c.useLeaderAbility(targets);
			if (c==d)
				firstLeaderAbilityUsed=true;
			else
				secondLeaderAbilityUsed= true;
		}
		
	}


}