package engine;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyPanel extends JFrame implements ActionListener{
	JTextField TB1;
	JTextField  TB2;
	Image ImageBackground ; 

	//JButton Submit1;
	JButton next;
	MyPanel() 
	{
		this.setSize(2000,1000);
		//this.setLayout(new GridLayout(1,2));
		ImageBackground = new ImageIcon ("Background1.jpg").getImage();
		setLayout(null);

		//getContentPane().setBackground(Color.blue);;

		//JLabel label1 = new JLabel(); 
		//JLabel label2 = new JLabel();
		//label1.setSize(250,52);
		//label2.setSize(250,52);
		//Submit1 = new JButton();
		// Submit2 = new JButton();


		/* 
		 Submit1.setSize(100, 50);		
		 Submit1.setLocation(30, 600);
		 Submit1.setText("Submit");
		 Submit1.setForeground(Color.green);
		 Submit1.setBackground(Color.black);
		 Submit1.setFocusable(false);
		 Submit1.setLocation(70, 500);
		 //Submit1.addActionListener(this);
		 */
		//		 Submit2.setSize(100, 50);		
		//		 Submit2.setLocation(850, 830);
		//		 Submit2.setText("Submit");
		//		 Submit2.setForeground(Color.green);
		//		 Submit2.setBackground(Color.black);
		//		 Submit2.setFocusable(false);
		//		 Submit2.addActionListener(this);


		//		label1.setText("First Player");
		//		label1.setHorizontalTextPosition(JLabel.CENTER);
		//		label1.setForeground(Color.red);
		//		label1.setFont(new Font("MY Boli",Font.PLAIN,50));
		//		label2.setText("Second Player");
		//		label2.setHorizontalTextPosition(JLabel.CENTER);
		//		label2.setForeground(Color.red);
		//		label2.setFont(new Font("MY Boli",Font.PLAIN,50));


		TB1 = new JTextField(); 
		TB1.setFont(new Font("Consolas",Font.BOLD,30));
		TB1.setLocation(380,825);
		TB1.setOpaque(true);
		TB1.setSize(300,50);
		TB1.setBorder(new LineBorder(Color.white,1));
		TB1.setBackground(Color.white);
		TB1.setForeground(Color.black);
		TB1.setCaretColor(Color.blue);
		//TB1.setText("Enter your name");

		TB2 = new JTextField();
		TB2.setSize(300, 50);
		TB2.setFont(new Font("Consolas",Font.BOLD,30));
		TB2.setLocation(1120, 825);
		TB2.setBackground(Color.white);
		TB2.setForeground(Color.black);
		TB2.setCaretColor(Color.blue);
		//TB2.setText("Enter your name");

		this.getContentPane().add(TB1);
		this.getContentPane().add(TB2);
		//label1.add(Submit1);
		//this.add(Submit2);

		//this.add(label1,BorderLayout.CENTER);
		//this.add(label2,BorderLayout.CENTER);
		setVisible(true);
		ImageIcon image = new ImageIcon ("MarvelLogo.jpg") ;
		setIconImage(image.getImage());
		setTitle("Marvel Infinity War");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Icon icon = new ImageIcon("submit.jpg");
		JButton next = new JButton(icon);
		next.setBounds(1539, 828, 200, 50);
		next.addActionListener(this);

		add(next);

		revalidate();
		repaint();
	}
	public void actionPerformed(ActionEvent e) {





		if(TB1.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "First Player should enter their name!","Error",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			if(TB2.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Second Player should enter their name!","Error",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				if(e.getSource()== next)
					next.setVisible(false);
				new	ChampionPick(TB1.getText(),TB2.getText());
				this.dispose();
			}


		}








	}




	public void paint(Graphics g)
	{ 
		Graphics2D g2D =(Graphics2D) g ; 
		g2D.drawImage(ImageBackground, 0, 0, null) ;

	}



	public static void main(String[] args) {
		new MyPanel();
	}
	//@Override
	//public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub

}






