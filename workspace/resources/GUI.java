package resources;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;


public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	Solitaire game;
   public GUI(Solitaire game){
	   this.game= game;
        //Create and set up the window.
       setTitle("Solitaire");
       setSize(900,700);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
		
	

       // this supplies the background
       try {
		System.out.println(getClass().toString());
		Image blackImg = ImageIO.read(getClass().getResource("background.jpg"));
		setContentPane(new ImagePanel(blackImg));

       }catch(IOException e) {
    	   e.printStackTrace();
       }
    
	   //sets the layout type
		this.setLayout(new FlowLayout());	   

	   //Creates the panels
	   JPanel deck = new JPanel();
	   JPanel cardStacks = new JPanel();
	   JPanel suits = new JPanel();

	   //Creates the borders around the panels
	   deck.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
	   cardStacks.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
	   suits.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
	   
	   
	   //sets the sizes of panels
	   deck.setPreferredSize(new Dimension(440,350));
	  suits.setPreferredSize(new Dimension(440 ,350));
	cardStacks.setPreferredSize(new Dimension(900,350));

	   //Makes panels transparents
	   deck.setOpaque(false);
	   cardStacks.setOpaque(false);
	   suits.setOpaque(false);
	
	   	//adds panels to the JFrame
	   this.add(deck );
	   this.add(suits);
	   this.add(cardStacks);



    //Creates the cards and sets their sizes
	Stack<Card> cards = new Stack<Card>();
	Card a = new Card(5, Card.Suit.Diamonds);
	Card b = new Card(4, Card.Suit.Diamonds);
	Card c = new Card(3, Card.Suit.Diamonds);
	Card d = new Card(2, Card.Suit.Diamonds);
	a.setPreferredSize(new Dimension(50,100));
	b.setPreferredSize(new Dimension(50,100));
	c.setPreferredSize(new Dimension(50,100));
	d.setPreferredSize(new Dimension(50,100));

	//Adds cards to the stack
       cards.add(a);    
	   cards.add(b); 
	   cards.add(c); 
	   cards.add(d); 

	//Draws the LayeredPane with the cards in the cardStacks container
	   cardStacks.add(drawPile(cards));

	   	//Makes components visible
        this.setVisible(true);
    }

	//Takes in stack of cards with all values being non-void propery declared cards
	//Returns a JLayeredPane which shows the cards
	public JLayeredPane drawPile(Stack<Card> stackIn) {
    Object cards[];
    cards = stackIn.toArray();
	JLayeredPane p = new JLayeredPane();
	int i=cards.length*11;
	for (int j=cards.length-1; j>=0;j--){
		((Card)cards[j]).setBounds(0,i,75,100);
		p.add((Card)cards[j]);
		i-=11;
	}
	p.setPreferredSize(new Dimension(300, 200));
	 p.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.YELLOW));
	return p;
}


	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
