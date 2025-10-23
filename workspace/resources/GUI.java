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
   public GUI(Solitaire game){
	    this.game= game;
        //Create and set up the window.
        setTitle("Solitaire");
        setSize(850,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ArrayList<String> suitTypes = new ArrayList<String>();
		suitTypes.add("Hearts");
		suitTypes.add("Diamonds");
		suitTypes.add("Clubs");
		suitTypes.add("Spades");

		ArrayList<String> valueTypes = new ArrayList<String>();
		valueTypes.add("1");
		valueTypes.add("2");
		valueTypes.add("3");
		valueTypes.add("4");
		valueTypes.add("5");
		valueTypes.add("6");
		valueTypes.add("7");
		valueTypes.add("8");
		valueTypes.add("9");
		valueTypes.add("10");
		valueTypes.add("11");
		valueTypes.add("12");
		valueTypes.add("13");

		ArrayList<Card> cards = new ArrayList<Card>();

		for (int i = 0; i < suitTypes.size(); i++) {
			for (int j = 0; j < valueTypes.size(); j++) {
				Card card = new Card(j+1, Card.Suit.valueOf(suitTypes.get(i)));
				card.setMaximumSize(new Dimension(110, 180));
				card.setOpaque(false);
				cards.add(card);
			}
		}

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

        JPanel topArea = new JPanel();
        topArea.setOpaque(false);
        topArea.setLayout(new BoxLayout(topArea, BoxLayout.X_AXIS));
        topArea.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED.darker()));

        JPanel bottomArea = new JPanel();
        bottomArea.setOpaque(false);
        bottomArea.setLayout(new GridLayout(1,2));
        bottomArea.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLUE.darker()));
        bottomArea.setPreferredSize(new Dimension(850,250));
       
        topArea.setPreferredSize(new Dimension(850,350));

		JPanel leftSide = new JPanel();
        leftSide.setOpaque(false);
        leftSide.setLayout(new GridLayout(1,2));
        leftSide.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.YELLOW.darker()));
        leftSide.setPreferredSize(new Dimension(100,250));

		JPanel midSide = new JPanel();
        midSide.setOpaque(false);
        midSide.setLayout(new GridLayout(1,2));
        midSide.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.GRAY.darker()));
        midSide.setPreferredSize(new Dimension(450,250));

		bottomArea.add(leftSide, 0, 0);
		bottomArea.add(midSide, 0, 1);

        backArea.add(topArea, 0,0);
        backArea.add(bottomArea, 0, 1);
        this.add(backArea);

        /*******
        * This is just a test to make sure images are being read correctly on your machine. Please replace
        * once you have confirmed that the card shows up properly. The code below should allow you to play the solitare
        * game once it's fully created.
        */

		int currentCardnumber = 52;

		ArrayList<ArrayList<Card>> columns = new ArrayList<ArrayList<Card>>();
		int rows = 7;

        for (int i = 1; i <= rows; i++) {
			columns.add(new ArrayList<Card>());
            for (int j = 1; j <= i; j++) {
                int randomInt = (int) Math.floor(Math.random() * currentCardnumber);
				columns.get(i-1).add(cards.get(randomInt));
				cards.remove(randomInt);
				currentCardnumber--;
            }
        }

		JLayeredPane col1 = new JLayeredPane();
		JLayeredPane col2 = new JLayeredPane();
		JLayeredPane col3 = new JLayeredPane();
		JLayeredPane col4 = new JLayeredPane();
		JLayeredPane col5 = new JLayeredPane();
		JLayeredPane col6 = new JLayeredPane();
		JLayeredPane col7 = new JLayeredPane();
 
        //This is the offset for computing the origin for the next label.
        int offset = 10;

		for (int i = 0; i < 1; i++) {
			Card card = columns.get(0).get(i);
			col1.add(card, 0);
		}

		for (int i = 0; i < 2; i++) {
			Card card = columns.get(1).get(i);

			if (i < 1) {
				card.hide();
			}

			if (i > 0) {
			card.setBounds(0, offset*i, card.getWidth(), card.getHeight());
			}

			col2.add(card, 0);
		}

		for (int i = 0; i < 3; i++) {
			Card card = columns.get(2).get(i);

			if (i < 2) {
				card.hide();
			}

			if (i > 0) {
			card.setBounds(0, offset*i, card.getWidth(), card.getHeight());
			}

			col3.add(card,0);
		}

		for (int i = 0; i < 4; i++) {
			Card card = columns.get(3).get(i);

			if (i < 3) {
				card.hide();
			}

			if (i > 0) {
			card.setBounds(0, offset*i, card.getWidth(), card.getHeight());
			}

			col4.add(card, 0);
		}

		for (int i = 0; i < 5; i++) {
			Card card = columns.get(4).get(i);

			if (i < 4) {
				card.hide();
			}

			if (i > 0) {
			card.setBounds(0, offset*i, card.getWidth(), card.getHeight());
			}

			col5.add(card, 0);
		}

		for (int i = 0; i < 6; i++) {
			Card card = columns.get(5).get(i);

			if (i < 5) {
				card.hide();
			}

			if (i > 0) {
			card.setBounds(0, offset*i, card.getWidth(), card.getHeight());
			}

			col6.add(card, 0);
		}

		for (int i = 0; i < 7; i++) {
			Card card = columns.get(6).get(i);

			if (i < 6) {
				card.hide();
			}

			if (i > 0) {
			card.setBounds(0, offset*i, card.getWidth(), card.getHeight());
			}

			col7.add(card, 0);
		}

		ArrayList<JLayeredPane> colList = new ArrayList<JLayeredPane>();
		colList.add(col1);
		colList.add(col2);
		colList.add(col3);
		colList.add(col4);
		colList.add(col5);
		colList.add(col6);
		colList.add(col7);

		topArea.add(colList.get(0));
		topArea.add(colList.get(1));
		topArea.add(colList.get(2));
		topArea.add(colList.get(3));
		topArea.add(colList.get(4));
		topArea.add(colList.get(5));
		topArea.add(colList.get(6));

		offset = 40;
		JLayeredPane draw = new JLayeredPane();
		JLayeredPane drawed = new JLayeredPane();
		for (int i = 0; i < 3; i++) {
			Card card = cards.get(i);
			card.setBounds(offset*i, 0, card.getWidth(), card.getHeight());
			drawed.add(card, 0);
			cards.remove(i);
			card.show();
		}

		ArrayList<JLayeredPane> doneCols = new ArrayList<JLayeredPane>();
		JLayeredPane heartsDone = new JLayeredPane();
		JLayeredPane diamondsDone = new JLayeredPane();
		JLayeredPane clubsDone = new JLayeredPane();
		JLayeredPane spadesDone = new JLayeredPane();

		heartsDone.setBounds(0, 0, 110, 180);
		diamondsDone.setBounds(0, 0, 110, 180);
		clubsDone.setBounds(0, 0, 110, 180);
		spadesDone.setBounds(0, 0, 110, 180);
		heartsDone.add (new Card(100, Card.Suit.Spades), 0);
		diamondsDone.add (new Card(100, Card.Suit.Spades), 0);
		clubsDone.add (new Card(100, Card.Suit.Spades), 0);
		spadesDone.add (new Card(100, Card.Suit.Spades), 0);
		doneCols.add(heartsDone);
		doneCols.add(diamondsDone);
		doneCols.add(clubsDone);
		doneCols.add(spadesDone);

		for (int i = 0; i < cards.size(); i++) {
			Card card;
			int randomInt = (int) Math.floor(Math.random() * cards.size());
			card = cards.get(randomInt);
			draw.add(card, 0);
			card.hide();
			i--;
			cards.remove(randomInt);
		}

		midSide.add(doneCols.get(0), 0);
		midSide.add(doneCols.get(1), 1);
		midSide.add(doneCols.get(2), 2);
		midSide.add(doneCols.get(3), 3);
		leftSide.add(draw, 0);
		leftSide.add(drawed, 1);
		// bottomArea.add(draw, 1);


        this.setVisible(true);
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
