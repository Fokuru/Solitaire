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
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;


public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	Solitaire game;
	private JPanel topArea;
	private JPanel leftSide;
	private JPanel rightSide;
   public GUI(Solitaire game){
	    this.game = game;
        //Create and set up the window.
        setTitle("Solitaire");
        setSize(850,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		//Set up the content pane.
       
       // this supplies the background
        try {
			System.out.println(getClass().toString());
			Image blackImg = ImageIO.read(getClass().getResource("woodBackground.png"));
			setContentPane(new ImagePanel(blackImg));

        }catch(IOException e) {
      	    e.printStackTrace();
        }

	    JPanel backArea = new JPanel();
        backArea.setOpaque(false);
        backArea.setLayout(new BoxLayout(backArea, BoxLayout.PAGE_AXIS));
        backArea.setSize(new Dimension(850,600));
        backArea.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.GREEN.darker()));

        this.topArea = new JPanel();
        topArea.setOpaque(false);
        topArea.setLayout(new BoxLayout(topArea, BoxLayout.X_AXIS));
        topArea.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED.darker()));

        JPanel bottomArea = new JPanel();
        bottomArea.setOpaque(false);
        bottomArea.setLayout(new GridLayout(1,2));
        bottomArea.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLUE.darker()));
        bottomArea.setPreferredSize(new Dimension(850,250));
       
        topArea.setPreferredSize(new Dimension(850,350));

		this.leftSide = new JPanel();
        leftSide.setOpaque(false);
        leftSide.setLayout(new GridLayout(1,2));
        leftSide.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.YELLOW.darker()));
        leftSide.setPreferredSize(new Dimension(100,250));

		this.rightSide = new JPanel();
        rightSide.setOpaque(false);
        rightSide.setLayout(new GridLayout(1,2));
        rightSide.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.GRAY.darker()));
        rightSide.setPreferredSize(new Dimension(450,250));

		bottomArea.add(leftSide, 0, 0);
		bottomArea.add(rightSide, 0, 1);

        backArea.add(topArea, 0,0);
        backArea.add(bottomArea, 0, 1);
        backArea.addMouseListener(this);  // Add mouse listener to backArea
        this.add(backArea);

        /*******
        * This is just a test to make sure images are being read correctly on your machine. Please replace
        * once you have confirmed that the card shows up properly. The code below should allow you to play the solitare
        * game once it's fully created.
        *
		*/

		setUp(topArea, leftSide, rightSide);

		

		
		
		
		
		// bottomArea.add(draw, 1);


        this.setVisible(true);
    }

	public void setUp(JPanel topArea, JPanel leftSide, JPanel rightSide) {
		boolean won = game.won;

		if (won == false) {
		ArrayList<ArrayList<JLayeredPane>> colList = game.setUp();
		topArea.add(colList.get(0).get(0), 0);
		topArea.add(colList.get(0).get(1), 1);
		topArea.add(colList.get(0).get(2), 2);
		topArea.add(colList.get(0).get(3), 3);
		topArea.add(colList.get(0).get(4), 4);
		topArea.add(colList.get(0).get(5), 5);
		topArea.add(colList.get(0).get(6), 6);

		rightSide.add(colList.get(1).get(0), 0);
		rightSide.add(colList.get(1).get(1), 1);
		rightSide.add(colList.get(1).get(2), 2);
		rightSide.add(colList.get(1).get(3), 3);


		leftSide.add(colList.get(2).get(0), 0);
		leftSide.add(colList.get(3).get(0), 1);

		this.revalidate();
		this.repaint();
		}
		
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
		int xPosition = arg0.getX();
		int yPosition = arg0.getY();
		//where 0 = col1 -> 6 = col7, 7 = draw, 8 = drawed, 9 = hearts, 
		// 10 = diamonds, 11 = clubs, 12 = spades.
		
		if (((xPosition >= 0 )&& (xPosition <= 120)) && ((yPosition >=0 ) && (yPosition <= 349))){
			game.moveCard(0);
		}
		else if (((xPosition >= 121 )&& (xPosition <= 240)) && ((yPosition >=0 ) && (yPosition <= 349))){
			game.moveCard(1);
		}
		else if (((xPosition >= 241 )&& (xPosition <= 360)) && ((yPosition >=0 ) && (yPosition <= 349))){
			game.moveCard(2);
		}
		else if (((xPosition >= 361 )&& (xPosition <= 480)) && ((yPosition >=0 ) && (yPosition <= 349))){
			game.moveCard(3);
		}
		else if (((xPosition >= 481 )&& (xPosition <= 600)) && ((yPosition >=0 ) && (yPosition <= 349))){
			game.moveCard(4);
		}
		else if (((xPosition >= 601 )&& (xPosition <= 720)) && ((yPosition >=0 ) && (yPosition <= 349))){
			game.moveCard(5);
		}
		else if (((xPosition >= 721 )&& (xPosition <= 850)) && ((yPosition >=0 ) && (yPosition <= 349))){
			game.moveCard(6);
		}
		else if (((xPosition >= 0 )&& (xPosition <= 220)) && ((yPosition >=350 ) && (yPosition <= 600))){
			game.moveCard(7);
		}
		else if (((xPosition >= 221 )&& (xPosition <= 425)) && ((yPosition >=350 ) && (yPosition <= 600))){
			game.moveCard(8);
		}
		else if (((xPosition >= 426 )&& (xPosition <= 530)) && ((yPosition >=350 ) && (yPosition <= 600))){
			game.moveCard(9);
		}
		else if (((xPosition >=  531)&& (xPosition <= 630)) && ((yPosition >=350 ) && (yPosition <= 600))){
			game.moveCard(10);
		}
		else if (((xPosition >= 631 )&& (xPosition <= 740)) && ((yPosition >=350 ) && (yPosition <= 600))){
			game.moveCard(11);
		}
		else if (((xPosition >= 741 )&& (xPosition <= 850)) && ((yPosition >=350 ) && (yPosition <= 600))){
			game.moveCard(12);
		}

		System.out.println("Mouse clicked at " + arg0.getX() + "," + arg0.getY());
		
		// Refresh the display after each move
		//setUp(topArea, leftSide, rightSide);
	
		        
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
