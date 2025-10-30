import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
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
import java.util.LinkedList;
import java.util.PriorityQueue;

import javax.swing.JLayeredPane;

public class Solitaire {
	ArrayList<ArrayList<JLayeredPane>> columnsInPlay = new ArrayList<ArrayList<JLayeredPane>>();
	ArrayList<JLayeredPane> doneCols = new ArrayList<JLayeredPane>();
	Queue<Card> deck = new LinkedList();
	ArrayList<String> suitTypes = new ArrayList<String>();
	ArrayList<String> valueTypes = new ArrayList<String>();
	ArrayList<Card> cards = new ArrayList<Card>();
	ArrayList<JLayeredPane> drawArray = new ArrayList<JLayeredPane>();
	ArrayList<JLayeredPane> drawedArray = new ArrayList<JLayeredPane>();
	ArrayList<JLayeredPane> colList = new ArrayList<JLayeredPane>();

	public ArrayList<ArrayList<JLayeredPane>> setUp() {
			suitTypes.add("Hearts");
			suitTypes.add("Diamonds");
			suitTypes.add("Clubs");
			suitTypes.add("Spades");
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

			for (int i = 0; i < suitTypes.size(); i++) {
				for (int j = 0; j < valueTypes.size(); j++) {
					Card card = new Card(j+1, Card.Suit.valueOf(suitTypes.get(i)));
					card.setMaximumSize(new Dimension(110, 180));
					card.setOpaque(false);
					cards.add(card);
				}
			}
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

		colList.add(col1);
		colList.add(col2);
		colList.add(col3);
		colList.add(col4);
		colList.add(col5);
		colList.add(col6);
		colList.add(col7);

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


		drawArray.add(draw);
		drawedArray.add(drawed);
		columnsInPlay.add(colList);
		columnsInPlay.add(doneCols);
		if(drawArray.get(0).getComponentCount()>0){
		columnsInPlay.add(drawArray);
		}
		if(drawArray.get(0).getComponentCount()>0){
		columnsInPlay.add(drawedArray);
		}

		for (int i = 0; i < draw.getComponents().length; i++) {
			deck.add((Card) draw.getComponent(i));
		}

		return columnsInPlay;

		}

		public ArrayList<ArrayList<JLayeredPane>>  getColumnsInPlay() {
			return columnsInPlay;
		}
	
	//the part of your program that's in charge of game rules goes here.
	public void newDraw(){
		for (int i = 0; i < 3; i++) {
			Card card = cards.get(i);
			card.setBounds(10*i, 0, card.getWidth(), card.getHeight());
			drawedArray.get(0).add(card, 0);
			cards.remove(i);
			card.show();
		}
	}

	public void resetDraw(){
		  while (drawedArray.get(0).getComponentCount() > 0){
			JLayeredPane temp = drawedArray.get(0);
			drawArray.get(0).add(temp.getComponent(0));
			temp.remove(0);

		  }
	}

}
