package engine;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView extends JFrame implements ActionListener {

	JButton StartButton = new JButton(); 
	JPanel Players;
	Image ImageBackground ; 
	
	public GameView(){

		Icon icon = new ImageIcon("lets play.jpg");

		StartButton = new JButton (icon);
		StartButton.setBounds(810,870,280,55);
		//StartButton.setText("Let's Play") ;
		StartButton.setFocusable(true);
		//StartButton.setHorizontalAlignment(JButton.CENTER);
		//StartButton.setVerticalAlignment(JButton.BOTTOM);
		//StartButton.setLocation(550,500);
		StartButton.setFont(new Font("My Boli" ,Font.BOLD,25));
		this.getContentPane().add(StartButton);


		/// Button setup ////////	 
		//add(myLabel);

		setTitle("Marvel Infinity War");
		setSize(2000,1000);
		//pack();
		// getContentPane().setBackground(Color.BLACK);
		setLayout(null);
		//setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(StartButton) ;
		StartButton.addActionListener(this);
		ImageIcon image = new ImageIcon ("MarvelLogo.jpg") ;
		ImageBackground = new ImageIcon ("background.jpg").getImage();
		setIconImage(image.getImage());
		//this.getContentPane().add(StartButton);


		revalidate();
		repaint();

	}

	public static void main(String[] args) {
		new GameView();

	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource()== StartButton){
			StartButton.setVisible(false);
			new	MyPanel();
			//this.setVisible(false);
			this.dispose();
		}
	}

	public void paint(Graphics g)
	{ 
		Graphics2D g2D =(Graphics2D) g ; 
		g2D.drawImage(ImageBackground, 0, 0, null) ;

	}

}
