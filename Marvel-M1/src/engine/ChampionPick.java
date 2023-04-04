package engine;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ChampionPick extends JFrame  implements ActionListener{

	//int random= Min + (int)(Math.random() * ((Max - Min) + 1));

	String p1name;
	String p2name;
	Game game;
	JComboBox cb1;
	JComboBox cb2;
	JComboBox cb3;
	JComboBox cb4;
	JComboBox cb5;
	JComboBox cb6;
	JButton Next; 
	JLabel name2;

	JComboBox <Object>leader1;
	JComboBox <Object>leader2;
	JLabel L1;
	JLabel L2;
	JButton start;
	JLabel choose1;
	JLabel choose2;
	ArrayList <Integer> indexes;
	JTextArea leaderInfo;


	//	JCheckBox ch1;
	//	JCheckBox ch2;
	//	JCheckBox ch3;
	//	JCheckBox ch4;
	//	JCheckBox ch5;
	//	JCheckBox ch6;
	//	JCheckBox ch7;
	//	JCheckBox ch8;
	//	JCheckBox ch9;
	//	JCheckBox ch10;
	//	JCheckBox ch11;
	//	JCheckBox ch12;
	//	JCheckBox ch13;
	//	JCheckBox ch14;
	//	JCheckBox ch15;
	//JList list1;

	JPanel p1;
	//	JPanel p2;
	JPanel champions;



	ChampionPick(String p1name,String p2name) 
	{
		this.p1name=p1name;
		this.p2name=p2name;
		setVisible(true);
		champions=new JPanel();
		this.setLayout(new BorderLayout());
		p1=new JPanel();
		this.add(p1,BorderLayout.WEST); 
		p1.setLayout(null);

		String [] champs= {"Captain America","Deadpool","Dr Strange",
				"Electro","Ghost Rider","Hela","Hulk","Iceman","Ironman","Loki",
				"Quicksilver","Spiderman","Thor","Venom","Yellow Jacket"};

		cb1=new JComboBox<String>(champs);
		cb2=new JComboBox<String>(champs);
		cb3=new JComboBox<String>(champs);
		cb4=new JComboBox<String>(champs);
		cb5=new JComboBox<String>(champs);
		cb6=new JComboBox<String>(champs);

		Next = new JButton("Next->>");
		Next.setBounds(200,860,100,45);
		Next.addActionListener(this);
		Next.setForeground(new Color(0,6,92));
		//Next.setFont(new Font("Consolas",Font.BOLD,18));
		p1.add(Next);


		cb1.setBounds(10,150,200,45);
		cb1.setFont(new Font("Consolas",Font.BOLD,18));
		cb1.setBackground(new Color(0,6,92));
		cb1.setForeground(new Color(255,187,176));
		
		cb2.setBounds(10,250,200,45);
		cb2.setFont(new Font("Consolas",Font.BOLD,18));
		cb2.setBackground(new Color(0,6,92));
		cb2.setForeground(new Color(255,187,176));
		
		cb3.setBounds(10,350,200,45);
		cb3.setFont(new Font("Consolas",Font.BOLD,18));
		cb3.setBackground(new Color(0,6,92));
		cb3.setForeground(new Color(255,187,176));
		

		cb4.setFont(new Font("Consolas",Font.BOLD,18));
		cb4.setBounds(10,550,200,45);
		cb4.setBackground(new Color(0,6,92));
		cb4.setForeground(new Color(255,187,176));
		
		cb5.setBounds(10,650,200,45);
		cb5.setFont(new Font("Consolas",Font.BOLD,18));
		cb5.setBackground(new Color(0,6,92));
		cb5.setForeground(new Color(255,187,176));
		
		cb6.setBounds(10,750,200,45);
		cb6.setFont(new Font("Consolas",Font.BOLD,18));
		cb6.setBackground(new Color(0,6,92));
		cb6.setForeground(new Color(255,187,176));
		



		p1.setPreferredSize(new Dimension(350, 800));

		champions=new JPanel();
		champions.setPreferredSize(new Dimension(1000, 800));
		this.add(champions);

		JLabel name1 = new JLabel(p1name);

		name1.setBounds(10,30,200,45);;
		name1.setFont(new Font("Consolas",Font.BOLD,30));
		name1.setForeground(Color.red);
		name1.setBackground(Color.black);
		name2 = new JLabel(p2name);

		name2.setBounds(10,450,200,45);;
		name2.setFont(new Font("Consolas",Font.BOLD,30));
		name2.setForeground(Color.red);
		name2.setBackground(Color.black);
		p1.add(name1);
		p1.add(name2);
		p1.add(cb1);
		p1.add(cb2);
		p1.add(cb3);
		p1.add(cb4);
		p1.add(cb5);
		p1.add(cb6);



		champions.setLayout(new BorderLayout());

		p1.setBackground(new Color(000));

		this.setSize(2000,1000);


		ImageIcon ch =new ImageIcon ("champsList.jpg");
		JLabel champslist =new JLabel(ch) ;
		champions.add(champslist);
		this.getContentPane().add(champions,BorderLayout.CENTER);		


		ImageIcon image = new ImageIcon ("MarvelLogo.jpg") ;
		setIconImage(image.getImage());
		setTitle("Marvel Infinity War");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.add(champions,BorderLayout.CENTER);


		L1 = new JLabel();
		L1.setText("Choose your Leader");
		L2 = new JLabel();
		L2.setText("Choose your Leader");
		L1.setBounds(10,75,300,45);
		L2.setBounds(10,750,300,45);
		L1.setFont(new Font("Consolas",Font.BOLD,25));
		L1.setForeground(Color.yellow);
		L2.setFont(new Font("Consolas",Font.BOLD,25));
		L2.setForeground(Color.yellow);

		leader1 = new JComboBox<Object>();
		leader1.setFont(new Font("Consolas",Font.BOLD,18));
		leader1.setBounds(10,150,200,45);
		leader1.setBackground(new Color(0,6,92));
		leader1.setForeground(new Color(255,187,176));
		
		leader2 = new JComboBox<Object>();
		leader2.setFont(new Font("Consolas",Font.BOLD,18));
		leader2.setBounds(10, 800, 200, 45);
		leader2.setBackground(new Color(0,6,92));
		leader2.setForeground(new Color(255,187,176));
		

		start =new JButton("Start Game");
		start.setBounds(200,860,100,45);
		start.addActionListener(this);
		start.setForeground(new Color(0,6,92));

		
		indexes = new ArrayList <Integer>();
		choose1 = new JLabel();
		choose1.setText("Choose your Champions");
		choose1.setBounds(10,75,300,45);
		choose1.setFont(new Font("Consolas",Font.BOLD,25));
		choose1.setForeground(Color.yellow);
		p1.add(choose1);


		choose2 = new JLabel();
		choose2.setText("Choose your Champions");
		choose2.setBounds(10,500,300,45);
		choose2.setFont(new Font("Consolas",Font.BOLD,25));
		choose2.setForeground(Color.yellow);
		p1.add(choose2);


		leaderInfo = new JTextArea();
		leaderInfo.setBackground(Color.black);
		leaderInfo.setForeground(Color.white);
		leaderInfo.setText("*****************\n–Hero: Removes all \n negative effects from \n the player’s entire \n team and adds an \n Embrace effect\n"+
				" to them which lasts\n for 2 turns.\n"+
				"–Villain: Immediately\neliminates (knocks out)\nall enemy champions \nwith less than 30% \nhealth"+
				"points.\n"+
				"–AntiHero:All champions\non the board except for\nthe leaders of each \nteam will be stunned \n"+
				"for 2 turns.\n****************");
		leaderInfo.setBounds(20, 220, 400, 445);
		leaderInfo.setFont(new Font("Consolas",Font.BOLD,20));
		
		
		
//		cb1.setSelectedIndex(0);
//		cb2.setSelectedIndex(1);
//		cb3.setSelectedIndex(2);
//		cb4.setSelectedIndex(3);
//		cb5.setSelectedIndex(4);
//		cb6.setSelectedIndex(5);




		revalidate();
		repaint();
	}


	public static void main(String[] args) {
		new ChampionPick("test1", "test2");
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()== Next)
		{

			while(!indexes.isEmpty())
			{
				indexes.remove(indexes.get(0));
			}
			indexes.add(cb1.getSelectedIndex());
			indexes.add(cb2.getSelectedIndex());
			indexes.add(cb3.getSelectedIndex());
			indexes.add(cb4.getSelectedIndex());
			indexes.add(cb5.getSelectedIndex());
			indexes.add(cb6.getSelectedIndex());

			for(int i=0;i<indexes.size();i++)
			{
				for(int j=i+1;j<indexes.size();j++)
				{
					if (indexes.get(i)==indexes.get(j))
					{
						JOptionPane.showMessageDialog(this, "Each champion should be chosen only once!","Error",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}

			cb1.setVisible(false);
			cb2.setVisible(false);
			cb3.setVisible(false);
			cb4.setVisible(false);
			cb5.setVisible(false);
			cb6.setVisible(false);
			Next.setVisible(false);
			choose1.setVisible(false);
			choose2.setVisible(false);

			p1.add(L1);
			p1.add(L2);

			name2.setBounds(10,700,200,45);;

			leader1.addItem(cb1.getSelectedItem());
			leader1.addItem(cb2.getSelectedItem());
			leader1.addItem(cb3.getSelectedItem());
			p1.add(leader1);

			leader2.addItem(cb4.getSelectedItem());
			leader2.addItem(cb5.getSelectedItem());
			leader2.addItem(cb6.getSelectedItem());			
			p1.add(leader2);


			p1.add(start);
			p1.add(leaderInfo);




		}
		if(e.getSource()== start)
		{

			try {
				Game.loadAbilities("Abilities.csv");
				Game.loadChampions("Champions.csv");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			Player player1 = new Player(p1name);
			player1.getTeam().add(Game.getAvailableChampions().get(indexes.get(0)));
			player1.getTeam().add(Game.getAvailableChampions().get(indexes.get(1)));
			player1.getTeam().add(Game.getAvailableChampions().get(indexes.get(2)));
			player1.setLeader(Game.getAvailableChampions().get(indexes.get(leader1.getSelectedIndex())));


			Player player2 = new Player(p2name);
			player2.getTeam().add(Game.getAvailableChampions().get(indexes.get(3)));
			player2.getTeam().add(Game.getAvailableChampions().get(indexes.get(4)));
			player2.getTeam().add(Game.getAvailableChampions().get(indexes.get(5)));
			player2.setLeader(Game.getAvailableChampions().get(indexes.get(leader2.getSelectedIndex())+3));

			new War(player1,player2);
			this.dispose();


			//System.out.println(player1.getTeam().get(0).getName());








		}
	}










}
