package engine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

import exceptions.*;
import model.abilities.*;
import model.effects.*;
import model.world.*;

public class War extends JFrame  implements ActionListener , KeyListener{
	Game game;
	JPanel board;
	JPanel right;
	JPanel left;
	JPanel bottom;
	JPanel top;
	JButton [][] buttons;
	JTextArea TurnOrder; 
	Direction D ;
	JTextArea currentInfo;
	JButton cast;
	JButton endTurn;
	JButton leaderability1;
	JRadioButton ability1;
	JRadioButton ability2;
	JRadioButton ability3;
	ButtonGroup group;
	int  radioIndex;
	JPanel infos;
	JTextArea restInfos;
	JLabel leader1Used;
	JLabel leader2Used;
	String player1name;
	String player2name;



	public War(Player p1,Player p2) {

		player1name=p1.getName();
		player2name=p2.getName();

		game=new Game(p1,p2);
		setVisible(true);
		this.setSize(2000,1000);
		this.setLayout(new BorderLayout());
		ImageIcon image = new ImageIcon ("MarvelLogo.jpg") ;
		setIconImage(image.getImage());
		setTitle("Marvel Infinity War");
		setDefaultCloseOperation(EXIT_ON_CLOSE);


		////TOP///////
		top=new JPanel();
		top.setPreferredSize(new Dimension(400,50));
		top.setBackground(Color.black);
		this.add(top,BorderLayout.NORTH);

		leaderability1=new JButton();
		leaderability1.setText("Use Leader Ability");
		leaderability1.setPreferredSize(new Dimension(300,50));
		leaderability1.setFont(new Font("Consolas",Font.PLAIN,25));
		leaderability1.setFocusable(false);
		leaderability1.addActionListener(this);
		leaderability1.setBackground(new Color(0,6,92));
		leaderability1.setForeground(new Color(255,187,176));
		top.add(leaderability1);
		
		
		JLabel p2name = new JLabel(p2.getName()+": ");
		for(int i=0;i<p2.getTeam().size();i++)
		{
			p2name.setText(p2name.getText()+ p2.getTeam().get(i).getName()+". ");	
		}
		p2name.setFont(new Font("Consolas",Font.PLAIN,25));
		p2name.setForeground(Color.magenta);
		top.add(p2name);





		////BOTTOM///////
		bottom=new JPanel();
		bottom.setPreferredSize(new Dimension(400,50));
		bottom.setBackground(Color.black);
		this.add(bottom,BorderLayout.SOUTH);
		JLabel p1name = new JLabel(p1.getName()+": ");
		for(int i=0;i<p1.getTeam().size();i++)
		{
			p1name.setText(p1name.getText()+ p1.getTeam().get(i).getName()+". ");	
		}
		p1name.setFont(new Font("Consolas",Font.PLAIN,25));
		p1name.setForeground(Color.magenta);
		bottom.add(p1name);




		////RIGHT/////
		right=new JPanel();
		right.setPreferredSize(new Dimension(400,100));
		right.setBackground(Color.black);
		this.add(right,BorderLayout.EAST);	
		//currentInfo.setBounds(600,50,350,600);
		currentInfo = new JTextArea(); 
		currentInfo.setEditable(false);
		currentInfo.setFont(new Font("Consolas",Font.PLAIN,25));
		
		//currentInfo.setBackground(new Color(0,6,92));
		//currentInfo.setForeground(new Color(255,187,176));
		currentInfo.setBackground(Color.black);
		currentInfo.setForeground(Color.red);
		currentInfo.setFocusable(false);
		
		
		champInfo();
		right.add(currentInfo);
		JScrollPane currentchampinfo =new JScrollPane(currentInfo);
		currentchampinfo.setPreferredSize(new Dimension(375,500));
		currentchampinfo.setFocusable(false);
		right.add(currentchampinfo);
		
		
		endTurn = new JButton();
		endTurn.setText("End Turn");
		endTurn.setPreferredSize(new Dimension(300,50));
		//endTurn.setBounds(70,400,100,45);
		endTurn.setFont(new Font("Consolas",Font.PLAIN,25));
		endTurn.addActionListener(this);
		endTurn.setFocusable(false);
		endTurn.setBackground(new Color(0,6,92));
		endTurn.setForeground(new Color(255,187,176));
		right.add(endTurn);

		
		right.add(leaderability1);
		
		
		leader1Used = new JLabel(); 
		//leader1Used.setEditable(false);
		leader1Used.setFont(new Font("Consolas",Font.PLAIN,20));
		leader1Used.setText(p1.getName()+"'s Leader Ability: Not Used");
		leader1Used.setForeground(new Color(255,187,176));
		leader1Used.setBackground(Color.BLACK);
		right.add(leader1Used);
		
		leader2Used = new JLabel(); 
		//leader2Used.setEditable(false);
		leader2Used.setFont(new Font("Consolas",Font.PLAIN,20));
		leader2Used.setText(p2.getName()+"'s Leader Ability: Not Used");
		leader2Used.setForeground(new Color(255,187,176));
		leader2Used.setBackground(Color.BLACK);
		right.add(leader2Used);
		
		
		////Left////
		left=new JPanel();
		left.setPreferredSize(new Dimension(400,100));
		left.setBackground(Color.black);
		this.add(left,BorderLayout.WEST);	
		left.setLayout(null);

		cast =new JButton();
		cast.setText("CAST ABILITY");
		cast.setBounds(30,400,250,50);
		cast.setFont(new Font("Consolas",Font.BOLD,25));
		cast.setHorizontalAlignment(JButton.CENTER);
		cast.setVerticalAlignment(JButton.CENTER);
		cast.setBackground(new Color(0,6,92));
		cast.setForeground(new Color(255,187,176));
		cast.setFocusable(false);
		cast.addActionListener(this);
		left.add(cast);

		TurnOrder = new JTextArea();
		TURN_ORDER();
		TurnOrder.setBounds(20,40,350,300);
		TurnOrder.setEditable(false);
		TurnOrder.setFont(new Font("Consolas",Font.PLAIN,25));
		TurnOrder.setBackground(Color.black);
		TurnOrder.setForeground(Color.red);
		TurnOrder.setFocusable(false);
		left.add(TurnOrder);

		

		ability1=new JRadioButton();
		ability1.setFocusable(false);
		ability1.setText(game.getCurrentChampion().getAbilities().get(0).getName());
		ability1.setBounds(30, 500, 200,50);
		ability1.setFont(new Font("Consolas",Font.PLAIN,15));
		ability1.setBackground(Color.BLACK);
		ability1.setForeground(new Color(255,187,176));

		left.add(ability1);

		ability2=new JRadioButton();
		ability2.setFocusable(false);
		ability2.setText(game.getCurrentChampion().getAbilities().get(1).getName());
		ability2.setBounds(30, 600, 200, 50);
		ability2.setFont(new Font("Consolas",Font.PLAIN,15));
		ability2.setBackground(Color.BLACK);
		ability2.setForeground(new Color(255,187,176));

		left.add(ability2);

		ability3=new JRadioButton();
		ability3.setFocusable(false);
		ability3.setText(game.getCurrentChampion().getAbilities().get(2).getName());
		ability3.setBounds(30, 700, 200, 50);
		ability3.setFont(new Font("Consolas",Font.PLAIN,15));
		ability3.setBackground(Color.BLACK);
		ability3.setForeground(new Color(255,187,176));

		left.add(ability3);

		group= new ButtonGroup();
		group.add(ability1);
		group.add(ability2);
		group.add(ability3);




		//// BOARD /////
		board=new JPanel();
		board.setLayout(new GridLayout(5,5));

		buttons = new JButton [5][5];
		for (int i=Game.getBoardheight()-1;i>=0;i--)
		{
			for (int j=0 ; j<5 ; j++)
			{
				buttons[i][j]=new JButton();
				buttons[i][j].setFocusable(false);
				buttons[i][j].addActionListener(this);
				//buttons[i][j].addMouseListener(this);
				board.add(buttons[i][j]);
			}
		}

		this.add(board,BorderLayout.CENTER);
		PLACE_BOARD();
		board.setFocusable(true);
		board.requestFocus(); 
		board.addKeyListener(this);

		//////////////////////////////////
		///// REST OF THE CHAMPIONS //////
		//////////////////////////////////
		infos= new JPanel();

		restInfos = new JTextArea();
		restInfos.setEditable(false);
		restInfos.setFont(new Font("Consolas",Font.PLAIN,30));
		restInfos.setBackground(new Color(0,6,92));
		restInfos.setForeground(new Color(255,187,176));
		restInfos.setFocusable(false);
		infos.add(restInfos);


		revalidate();
		repaint();

	}

	public void PLACE_BOARD ()
	{
		for(int i=Game.getBoardheight()-1; i>=0;i-- )
		{
			for (int j=0;j<Game.getBoardwidth();j++)
			{

				if (game.getBoard()[i][j] instanceof Cover)
				{
					Icon f = new ImageIcon("cover2.JPEG");
					buttons[i][j].setToolTipText("");
					buttons[i][j].setIcon(f);

					if (((Cover) game.getBoard()[i][j]).getCurrentHP()>0) {
						buttons[i][j].setText("HP:"
								+ ((Cover) game.getBoard()[i][j])
								.getCurrentHP());
					}
					buttons[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
					buttons[i][j].setVerticalTextPosition(SwingConstants.CENTER);
					buttons[i][j].setForeground(Color.CYAN);
					buttons[i][j].setFont(new Font("Consolas",Font.PLAIN,25));

				}
				else if(game.getBoard()[i][j] instanceof Champion)
				{
					Champion c= (Champion) game.getBoard()[i][j];
					buttons[i][j].setToolTipText(restChampionsInfo(c)+"\nLocation: x="+i+" ,y="+j+"<br></html>" );

					if(c.getName().equals("Captain America"))
					{
						Icon f = new ImageIcon("captain america logo.JPG");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Deadpool"))
					{
						Icon f = new ImageIcon("deadpool logo.PNG");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Dr Strange"))
					{
						Icon f = new ImageIcon("dr strange logo.png");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Electro"))
					{
						Icon f = new ImageIcon("electro logo.JPEG");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Ghost Rider"))
					{
						Icon f = new ImageIcon("ghost rider logo.PNG");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Hela"))
					{
						Icon f = new ImageIcon("hela logo.JPG");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Hulk"))
					{
						Icon f = new ImageIcon("hulk logo.jpeg");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Iceman"))
					{
						Icon f = new ImageIcon("iceman logo.JPG");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Ironman"))
					{
						Icon f = new ImageIcon("ironman logo.PNG");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Loki"))
					{
						Icon f = new ImageIcon("loki logo.JPG");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Quicksilver"))
					{
						Icon f = new ImageIcon("quicksilver logo.jpg");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Spiderman"))
					{
						Icon f = new ImageIcon("spiderman logo.PNG");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Thor"))
					{
						Icon f = new ImageIcon("thor logo.jpg");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Venom"))
					{
						Icon f = new ImageIcon("venom logo.PNG");
						buttons[i][j].setIcon(f);
					}

					else if(c.getName().equals("Yellow Jacket"))
					{
						Icon f = new ImageIcon("yellow jacket logo.JPG");
						buttons[i][j].setIcon(f);
					}
				}
				else{
					Icon f = new ImageIcon("null.JPEG");
					buttons[i][j].setIcon(f);
					buttons[i][j].setText("");
					buttons[i][j].setToolTipText("");


				}
			}
		}
	}

	public void TURN_ORDER()
	{

		TurnOrder.selectAll();
		TurnOrder.replaceSelection("");
		TurnOrder.setText("TURN ORDER:"+"\n");
		ArrayList <Champion> temp = new ArrayList <Champion>();
		while (!game.getTurnOrder().isEmpty())
		{
			Champion c = (Champion) game.getTurnOrder().remove();
			TurnOrder.setText(TurnOrder.getText()+c.getName()+"\n");
			temp.add(c);
		}
		while(!temp.isEmpty())
		{
			Champion c = (Champion) temp.remove(0); 
			game.getTurnOrder().insert(c);
		}

	}
	public void champInfo()
	{
		Champion c = game.getCurrentChampion();
		String info = "" + c.getName()+"\n"      ;
		if (c instanceof Hero)
		{
			info+= "Type:Hero"+"\n" ;

		}
		if (c instanceof Villain)
		{
			info+= "Type:Villain"+"\n" ;

		}
		if (c instanceof AntiHero)
		{
			info+= "Type:AntiHero"+"\n" ;

		}
		info+= "CurrentHP:" + c.getCurrentHP()+"\n";
		info+= "Mana:" + c.getMana()+"\n";
		info+= "Current ActionPoints:" + c.getCurrentActionPoints()+"\n";

		//////// Abilities Info //////////	
		info+= "Abilities:"+"\n";
		for(int i=0; i<c.getAbilities().size();i++)
		{

			Ability a = c.getAbilities().get(i);
			info+= (i+1) + ") "+ a.getName()+"\n" ;

			///////Ability Type /////////

			if(a instanceof CrowdControlAbility)
			{
				info+= "Type:Crowd Control"+"\n" ;
			}
			if(a instanceof HealingAbility)
			{
				info+= "Type:Healing"+"\n" ;
			}
			if(a instanceof DamagingAbility)
			{
				info+= "Type:Damaging"+"\n" ;
			}

			info+="AreaOfEffect:"+a.getCastArea() +"\n" ;
			info+= "Cast Range:" + a.getCastRange()+ "\n";
			info+= "Mana Cost:" + a.getManaCost()+ "\n";
			info+= "Required Action Points:" + a.getRequiredActionPoints()+ "\n";
			info+= "Cast Range:" + a.getCastRange()+ "\n";
			info+= "Current Cooldown:" + a.getCurrentCooldown()+ "\n";
			info+= "Base Cooldown:" + a.getBaseCooldown()+ "\n";
			if(a instanceof CrowdControlAbility)
			{
				info+= "Effect:"+(((CrowdControlAbility) a).getEffect().getName()) +"\n" ;
				info+= "EffectDuration:"+(((CrowdControlAbility) a).getEffect().getDuration()) +"\n" ;
			}
			if(a instanceof HealingAbility)
			{
				info+= "Heal Amount:"+ ((HealingAbility)a).getHealAmount() +"\n" ;
			}
			if(a instanceof DamagingAbility)
			{
				info+= "Damage Amount"+((DamagingAbility)a).getDamageAmount()+"\n" ;
			}
		}

		info+= "__________________\nApplied Effects: \n";
		if(c.getAppliedEffects().size()!=0)
		{
			for(int i =0 ; i<c.getAppliedEffects().size();i++)
			{
				Effect e = c.getAppliedEffects().get(i);
				info+= e.getName() + ", Duration: "+ e.getDuration()+ "\n" ;
			}
		}
		else
		{
			info+= "<NONE> \n";
		}
		info+= "Attack Damage:" + c.getAttackDamage()+"\n";
		info+= "Attack Range:" + c.getAttackRange()+"\n";

		currentInfo.setText(info);
	}


	public String restChampionsInfo(Champion c)
	{
		String info="<html>";

		info+="Name: "+ c.getName()+"<br>";
		info+= "CurrentHP:" + c.getCurrentHP()+"<br>";
		info+= "Mana:" + c.getMana()+"<br>";
		info+= "Speed:" + c.getSpeed()+"<br>";
		info+= "Max ActionPoints per turn: "+c.getMaxActionPointsPerTurn()+"<br>";
		info+= "Attack Damage:" + c.getAttackDamage()+"<br>";
		info+= "Attack Range:" + c.getAttackRange()+"<br>";
		if(c instanceof Hero)
		{
			info+= "Type: Hero"+"<br>";
		}
		if(c instanceof AntiHero)
		{
			info+= "Type: AntiHero<br>";
		}
		if(c instanceof Villain)
		{
			info+= "Type: Villain<br>";
		}
		if(game.getFirstPlayer().getLeader()==c || game.getSecondPlayer().getLeader()==c )
		{
			info+= "Leader<br>";
		}
		else
		{
			info+="Not Leader<br>";
		}
		info+="Applied Effects:<br>";
		if(c.getAppliedEffects().size()!=0){
			for(int i=0;i<c.getAppliedEffects().size();i++)
			{
				Effect e = c.getAppliedEffects().get(i);
				info+= (i+1) +  e.getName()+"<br>";
				info+= "Duration: " +  e.getDuration() +"<br>";

			}
		}
		else{
			info+="-----NONE-----<br>";
		}




		return info; 

	}


	public static void main(String[] args) 
	{

	}

	public void ABILITIES()
	{
		ability1.setText(game.getCurrentChampion().getAbilities().get(0).getName());
		ability1.setBounds(30, 500, 200, 50);
		left.add(ability1);
		ability2.setText(game.getCurrentChampion().getAbilities().get(1).getName());
		ability2.setBounds(30, 600, 200, 50);
		left.add(ability2);
		ability3.setText(game.getCurrentChampion().getAbilities().get(2).getName());
		ability3.setBounds(30, 700, 200, 50);
		left.add(ability3);
	}
	public void GAME_OVER()
	{
		if(game.checkGameOver()!= null)
		{
			Player winner = (Player)game.checkGameOver();
			JOptionPane.showMessageDialog(this, winner.getName()+" WINS","GAME OVER!",JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		}


	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// JOptionPane.showMessageDialog(this, currentInfo.getText());
		//for each champion on the board

		if(e.getSource()==endTurn)
		{
			game.endTurn();
			TURN_ORDER();
			PLACE_BOARD();
			champInfo();
			ABILITIES();
			GAME_OVER();

		}
		if(e.getSource()==leaderability1)
		{
			try {
				game.useLeaderAbility();
				PLACE_BOARD();
				champInfo();
				GAME_OVER();
				if(game.isFirstLeaderAbilityUsed()==true)
				{
					leader1Used.setText(player1name+"'s Leader Ability: Used");
				}
				if(game.isSecondLeaderAbilityUsed()==true)
				{
					leader2Used.setText(player2name+"'s Leader Ability: Used");
				}
				
			} catch (LeaderNotCurrentException e1) {
				JOptionPane.showMessageDialog(this, "Champion is not a leader","Error",JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (LeaderAbilityAlreadyUsedException e1) {
				JOptionPane.showMessageDialog(this, "Leader Ability already used","Error",JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
		
		
		
		if (e.getSource()==cast)
		{
			if (ability1.isSelected()==true)
				radioIndex=0;
			else if (ability2.isSelected()==true)
				radioIndex=1;
			else if (ability3.isSelected()==true)
				radioIndex=2;
			else
				JOptionPane.showMessageDialog(this, "Please Choose the desired Ability","Error",JOptionPane.ERROR_MESSAGE);



			Ability a = game.getCurrentChampion().getAbilities().get(radioIndex);
			if (a.getCastArea()== AreaOfEffect.SELFTARGET || a.getCastArea()== AreaOfEffect.TEAMTARGET || a.getCastArea()== AreaOfEffect.SURROUND)
			{

				try {
					game.castAbility(a);
					PLACE_BOARD();
					champInfo();
					GAME_OVER();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(this, "Not enough resources","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (AbilityUseException e1) {
					JOptionPane.showMessageDialog(this, "" + a.getManaCost() + " mana needed to cast this ability","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			else
				if (a.getCastArea()== AreaOfEffect.DIRECTIONAL)
				{
					String[] options = {"UP", "DOWN", "LEFT", "RIGHT"};
					String n = (String)JOptionPane.showInputDialog(null, "Choose a Direction to Cast the Ability", 
							"Cast Ability Directional", JOptionPane.QUESTION_MESSAGE,null, options, options[0]);

					if(n.equals("UP"))
					{
						try {
							game.castAbility(a,Direction.UP);
							PLACE_BOARD();
							champInfo();
							GAME_OVER();
						} catch (AbilityUseException e1) {
							JOptionPane.showMessageDialog(this, "You cannot use the Abililty " + a.getManaCost() + " mana to cast this ability","Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} catch (NotEnoughResourcesException e1) {
							JOptionPane.showMessageDialog(this, "Not enough resources","Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if(n.equals("DOWN"))
					{
						try {
							game.castAbility(a,Direction.DOWN);
							PLACE_BOARD();
							champInfo();
							GAME_OVER();
						} catch (AbilityUseException e1) {
							JOptionPane.showMessageDialog(this, "You cannot use the Abililty " + a.getManaCost() + " mana to cast this ability","Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} catch (NotEnoughResourcesException e1) {
							JOptionPane.showMessageDialog(this, "Not enough resources","Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if(n.equals("RIGHT"))
					{
						try {
							game.castAbility(a,Direction.RIGHT);
							PLACE_BOARD();
							champInfo();
							GAME_OVER();
						} catch (AbilityUseException e1) {
							JOptionPane.showMessageDialog(this, "You cannot use the Abililty " + a.getManaCost() + " mana to cast this ability","Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} catch (NotEnoughResourcesException e1) {
							JOptionPane.showMessageDialog(this, "Not enough resources","Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					if(n.equals("LEFT"))
					{
						try {
							game.castAbility(a,Direction.LEFT);
							PLACE_BOARD();
							champInfo();
							GAME_OVER();
						} catch (AbilityUseException e1) {
							JOptionPane.showMessageDialog(this, "You cannot use the Abililty " + a.getManaCost() + " mana to cast this ability","Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} catch (NotEnoughResourcesException e1) {
							JOptionPane.showMessageDialog(this, "Not enough resources","Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			if (a.getCastArea()== AreaOfEffect.SINGLETARGET)
			{

				JTextField field1 = new JTextField();
				JTextField field2 = new JTextField();

				Object [] fields = {
						"Choose the target's Location on the board",	
						"x", field1,
						"y", field2
				};

				JOptionPane.showConfirmDialog(null,fields,"Cast Ability SingleTarget",JOptionPane.OK_CANCEL_OPTION);
				String f1text=  field1.getText();
				String f2text=  field2.getText();
				int f1 = Integer.parseInt(f1text);
				int f2 = Integer.parseInt(f2text);

				try {
					game.castAbility(a,f1,f2);
					PLACE_BOARD();
					champInfo();
					GAME_OVER();
				} catch (AbilityUseException e1) {
					JOptionPane.showMessageDialog(this, "You cannot use the Abililty " + a.getManaCost() + " mana to cast this ability","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(this, "Not enough resources","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(this, "Invalid Target","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}


	}

	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==32) //SPACEBAR
		{
			String[] options = {"UP", "DOWN", "LEFT", "RIGHT"};
			String n = (String)JOptionPane.showInputDialog(null, "Choose a Direction to Attack", 
					"attack", JOptionPane.QUESTION_MESSAGE,null, options, options[0]);

			if(n.equals("UP"))
			{
				try {
					game.attack(Direction.UP);
					PLACE_BOARD();
					champInfo();
					GAME_OVER();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(this, "Not Enough Resources","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (ChampionDisarmedException e1) {
					JOptionPane.showMessageDialog(this, "Champion is disarmed","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(this, "Target is not an enemy","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
			if(n.equals("DOWN"))
			{
				try {
					game.attack(Direction.DOWN);
					PLACE_BOARD();
					champInfo();
					GAME_OVER();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(this, "Not Enough Resources","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (ChampionDisarmedException e1) {
					JOptionPane.showMessageDialog(this, "Champion is disarmed","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(this, "Target is not an enemy","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
			if(n.equals("LEFT"))
			{
				try {
					game.attack(Direction.LEFT);
					PLACE_BOARD();
					champInfo();
					GAME_OVER();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(this, "Not Enough Resources","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (ChampionDisarmedException e1) {
					JOptionPane.showMessageDialog(this, "Champion is disarmed","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(this, "Target is not an enemy","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
			if(n.equals("RIGHT"))
			{
				try {
					game.attack(Direction.RIGHT);
					PLACE_BOARD();
					champInfo();
					GAME_OVER();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(this, "Not Enough Resources","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (ChampionDisarmedException e1) {
					JOptionPane.showMessageDialog(this, "Champion is disarmed","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(this, "Target is not an enemy","Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {

		switch(e.getKeyCode())
		{
		case KeyEvent.VK_UP :
			D=Direction.UP;
			try {
				game.move(D);
				PLACE_BOARD();
				champInfo();
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Not Enough Resources","Error",JOptionPane.ERROR_MESSAGE);
			} catch (UnallowedMovementException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Unallowed Movement","Error",JOptionPane.ERROR_MESSAGE);


			}


			break;
		case KeyEvent.VK_DOWN:
			D=Direction.DOWN;
			try {
				game.move(D);
				PLACE_BOARD();
				champInfo();
			} catch (NotEnoughResourcesException e1) {


				JOptionPane.showMessageDialog(this, "Not Enough Resources","Error",JOptionPane.ERROR_MESSAGE);
			} catch (UnallowedMovementException e1) {
				// TODO Auto-generated catch block

				JOptionPane.showMessageDialog(this, "Unallowed Movement","Error",JOptionPane.ERROR_MESSAGE);
			}
			break;
		case KeyEvent.VK_LEFT:
			D=Direction.LEFT;
			try {
				game.move(D);
				PLACE_BOARD();
				champInfo();
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block

				JOptionPane.showMessageDialog(this, "Not Enough Resources","Error",JOptionPane.ERROR_MESSAGE);
			} catch (UnallowedMovementException e1) {
				// TODO Auto-generated catch block

				JOptionPane.showMessageDialog(this, "Unallowed Movement","Error",JOptionPane.ERROR_MESSAGE);
			}
			break;

		case KeyEvent.VK_RIGHT:
			D=Direction.RIGHT;
			try {
				game.move(D);
				PLACE_BOARD();
				champInfo();
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block

				JOptionPane.showMessageDialog(this, "Not Enough Resources","Error",JOptionPane.ERROR_MESSAGE);
			} catch (UnallowedMovementException e1) {
				// TODO Auto-generated catch block

				JOptionPane.showMessageDialog(this, "Unallowed Movement","Error",JOptionPane.ERROR_MESSAGE);
			}
			break;




		}

	}


	@Override
	public void keyTyped(KeyEvent e) {
	}



}
