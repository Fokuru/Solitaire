import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Collections;

public class Solitaire {
	ArrayList<ArrayList<JLayeredPane>> columnsInPlay = new ArrayList<ArrayList<JLayeredPane>>();
	ArrayList<JLayeredPane> doneCols = new ArrayList<JLayeredPane>();
	Queue<Card> deck = new LinkedList();
	ArrayList<String> suitTypes = new ArrayList<String>();
	ArrayList<String> valueTypes = new ArrayList<String>();
	ArrayList<JLayeredPane> drawArray = new ArrayList<JLayeredPane>();
	ArrayList<JLayeredPane> drawedArray = new ArrayList<JLayeredPane>();
	ArrayList<JLayeredPane> colList = new ArrayList<JLayeredPane>();
	int firstClick = -1;
	int secondClick = -1;
	Card placeHolder1;
	Card placeHolder2;
	Boolean won=false;

	public ArrayList<ArrayList<JLayeredPane>> setUp() {
		ArrayList<Card> cards = new ArrayList<Card>();


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
				Card card = new Card(j + 1, Card.Suit.valueOf(suitTypes.get(i)));
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
				columns.get(i - 1).add(cards.get(randomInt));
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

		// This is the offset for computing the origin for the next label.
		int offset = 10;

		JLayeredPane[] cols = {col1, col2, col3, col4, col5, col6, col7};
		for (int c = 0; c < cols.length; c++) {
			ArrayList<Card> colCards = columns.get(c); // use column c, not 1
			for (int i = 0; i < colCards.size(); i++) {
				Card card = colCards.get(i);
				card.setBounds(0, offset * i, card.getWidth(), card.getHeight());
				if (i < colCards.size() - 1) card.setVisible(false); // hide all but top
				cols[c].add(card, 0);
			}                
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
			card.setBounds(offset * i, 0, card.getWidth(), card.getHeight());
			drawed.add(card, i);
			cards.remove(i);
			card.setVisible(true);
		}

		JLayeredPane heartsDone = new JLayeredPane();
		JLayeredPane diamondsDone = new JLayeredPane();
		JLayeredPane clubsDone = new JLayeredPane();
		JLayeredPane spadesDone = new JLayeredPane();

		heartsDone.setBounds(0, 0, 110, 180);
		diamondsDone.setBounds(0, 0, 110, 180);
		clubsDone.setBounds(0, 0, 110, 180);
		spadesDone.setBounds(0, 0, 110, 180);
		heartsDone.add(new Card(100, Card.Suit.Spades), 0);
		diamondsDone.add(new Card(100, Card.Suit.Spades), 0);
		clubsDone.add(new Card(100, Card.Suit.Spades), 0);
		spadesDone.add(new Card(100, Card.Suit.Spades), 0);
		doneCols.add(heartsDone);
		doneCols.add(diamondsDone);
		doneCols.add(clubsDone);
		doneCols.add(spadesDone);

		for (int i = 0; i < cards.size(); i++) {
			Card card;
			int randomInt = (int) Math.floor(Math.random() * cards.size());
			card = cards.get(randomInt);
			draw.add(card, 0);
			card.setVisible(false);
			i--;
			cards.remove(randomInt);
		}

		drawArray.add(draw);
		drawedArray.add(drawed);
		columnsInPlay.add(colList);
		columnsInPlay.add(doneCols);
		if (drawArray.get(0).getComponentCount() > 0) {
			columnsInPlay.add(drawArray);
		}
		if (drawArray.get(0).getComponentCount() > 0) {
			columnsInPlay.add(drawedArray);
		}

		for (int i = 0; i < draw.getComponents().length; i++) {
			deck.add((Card) draw.getComponent(i));
		}

		return columnsInPlay;

	}

	public ArrayList<ArrayList<JLayeredPane>> getColumnsInPlay() {
		return columnsInPlay;
	}

	private JLayeredPane findPane(int pm) {
		if (pm >= 0 && pm <= 6) return colList.get(pm);
		if (pm == 8) return drawedArray.get(0);
		if (pm >= 9 && pm <= 12) return doneCols.get(pm - 9);
		return null;
	}

	private void recordCard(int placeMent) {


		// If placeHolder1 is null, we're recording the first card (source)
		if (placeHolder1 == null) {
			firstClick = placeMent;
			Component comp = null;
			JLayeredPane pane = findPane(placeMent);
			if (pane != null) {
				if (pane.getComponentCount() == 0) {
					// nothing to pick from an empty pane -> no source card
					return;
				} else {
					comp = getTopComponent(pane);
				}
			}

			// still ignore clicks on the sentinel placeholder card when trying to pick a source
			if (comp instanceof Card && isPlaceholder((Card) comp)) {
				return;
			}

			if (comp != null && comp instanceof Card) {
				placeHolder1 = (Card) comp;
				placeHolder1.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
			}
		}

		// If placeHolder1 is not null but placeHolder2 is, we're recording the second card (destination)
		else if (placeHolder2 == null) {
			if (placeMent == 7 || placeMent == 8) {
				placeHolder1.setBorder(null);
				placeHolder1 = null;
				firstClick = -1;

				return;
			}
			secondClick = placeMent;
			Component comp = null;
			JLayeredPane pane = findPane(placeMent);
			if (pane != null) {
				if (pane.getComponentCount() == 0) {
					Card placeholder = new Card(100, Card.Suit.Spades);
					placeholder.setBounds(0, 0, 110, 180);
					placeholder.setVisible(true);
					pane.add(placeholder, 0);
					pane.revalidate();
					pane.repaint();
				}
				comp = getTopComponent(pane);
			}

			if (comp != null && comp instanceof Card) {
				placeHolder2 = (Card) comp;
				placeHolder2.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
			}
		}

		if (placeHolder1 != null && placeHolder2 != null && !placeHolder1.equals(placeHolder2)) {
			moveCard(placeMent);
		}
	}


	// helper: return the top-most card in a layered pane (largest y offset),
	// fallback to last component when bounds are not set.
	private Component getTopComponent(JLayeredPane p) {
		if (p == null || p.getComponentCount() == 0) return null;

		Component topVisible = null;
		int maxYVisible = Integer.MIN_VALUE;
		Component topAny = null;
		int maxYAny = Integer.MIN_VALUE;

		// Find the top-most visible non-placeholder first; also track the top-most card regardless of visibility
		if (colList.contains(p)){
			for (Component c : p.getComponents()) {
			if (!(c instanceof Card)) continue;
			Card card = (Card) c;
			Rectangle r = c.getBounds();
			int y = (r != null) ? r.y : 0;
			if (!isPlaceholder(card)) {
				if (card.isVisible()) {
					if (y >= maxYVisible) {
						maxYVisible = y;
						topVisible = c;
					}
				}
				if (y >= maxYAny) {
					maxYAny = y;
					topAny = c;
				}
			}
		}
		
		}

		// prefer the top-most visible card; if none visible, return the top-most card (even if hidden)
		if (topVisible != null) {
			System.out.println("Top visible card in pane: " + topVisible);
			return topVisible;
		}
		if (topAny != null) {
			System.out.println("No visible cards; top card in pane: " + topAny);
			return topAny;
		}

		// final fallback: any Card (including placeholders)
		for (Component c : p.getComponents()) {
			if (c instanceof Card) return c;
		}

		return null;

		}



	// remove any placeholder cards from a destination pane (before adding a real card)
	private void removePlaceholdersIfAny(JLayeredPane p) {
		if (p == null) return;
		for (int i = p.getComponentCount() - 1; i >= 0; i--) {
			Component c = p.getComponent(i);
			if (c instanceof Card && isPlaceholder((Card) c)) {
				p.remove(i);
			}
		}
		p.revalidate();
		p.repaint();
	}

	public boolean isLegalMove(Card c1, Card c2, int destination) {

		if (c1 == null || c2 == null) return false;
		
		Container parentOf2 = c2.getParent();

		if (parentOf2 == null) return false;

		boolean isDestinationDonePile = doneCols.contains(parentOf2);
		boolean isDestinationColumn = colList.contains(parentOf2);
		boolean isDestinationDrawedPile = drawedArray.contains(parentOf2);
		
		// whatSuit() gets the suit of the card
		// whatValue() gets the value of the card

		// doneCols.add(heartsDone);
		// doneCols.add(diamondsDone);
		// doneCols.add(clubsDone);
		// doneCols.add(spadesDone);
		
		if (isDestinationDonePile) {
			// Check if parentOf2 is the correct done pile for c1
			// So the suit has to match and the value has to be one higher than the top card in the done pile
			// If the done pile is empty (only has placeholder), c1 must be an Ace (value 1)

			if (doneCols.indexOf(parentOf2) == 0) { // heartsDone
				if (c1.whatSuit() != Card.Suit.Hearts) return false;
				if (c1.whatValue() == 1) return true;
				if (c1.whatValue() == c2.whatValue() + 1) return true;
				return false;
			} else if (doneCols.indexOf(parentOf2) == 1) { // diamondsDone
				if (c1.whatSuit() != Card.Suit.Diamonds) return false;
				if (c1.whatValue() == 1) return true;
				if (c1.whatValue() == c2.whatValue() + 1) return true;
				return false;
			} else if (doneCols.indexOf(parentOf2) == 2) { // clubsDone
				if (c1.whatSuit() != Card.Suit.Clubs) return false;
				if (c1.whatValue() == 1) return true;
				if (c1.whatValue() == c2.whatValue() + 1) return true;
				return false;
			} else if (doneCols.indexOf(parentOf2) == 3) { // spadesDone
				if (c1.whatSuit() != Card.Suit.Spades) return false;
				if (c1.whatValue() == 1) return true;
				if (c1.whatValue() == c2.whatValue() + 1) return true;
				return false;
			}

		} else if (isDestinationColumn) {
			// Check if the move to the column is legal
			// So the color has to be opposite and the value has to be one lower than c2
			// If the column is empty (only has placeholder), c1 must be a King (value 13)
			
		} else if (isDestinationDrawedPile) {
			return false;
		}

		/* 		if (deck.contains(c1) && ((c2.getColour()) != (c1.getColour())) && ((c2.getValue()) == (c1.getValue()+1)) && (c2.(functionComfirmingLocation) == (bigDeckLocations))) {
					return true;
				} else if (deck.contains(c1) && (c1.getType == c2.getType) && (c1.getValue == c2.getValue-1) &&(findArea == areaWheretFinishedStacks)){
					return true;
				} else if (deck.contains(c1) && (c1.getValue == 1) && (c2 == (whateverTheBlankDeckSpaceHolds))){
					return true;
				}
		*/


		// find where c1 is

		// find where c2 is
		// say yes or no depending on if it's legal

		// going to central columns the color of the card has to be opposite (red ->
		// black and black -> red)
		// going to final columns color is the same

		return true;
	}

	public ArrayList<ArrayList<JLayeredPane>> moveCard(int placeMent) {
		

		// If either placeholder is null, pick the cards first
		if (placeHolder1 == null || placeHolder2 == null) {
			recordCard(placeMent);
			return columnsInPlay;
		}

		// Do not move placeholders
		if (isPlaceholder(placeHolder1)) {
			placeHolder1.setBorder(null);
			if (placeHolder2 != null) placeHolder2.setBorder(null);
			placeHolder1 = null;
			placeHolder2 = null;
			firstClick = -1;
			secondClick = -1;
			return columnsInPlay;
		}

		boolean legal = isLegalMove(placeHolder1, placeHolder2, placeMent);
		System.out.println("Moving " + placeHolder1 + " to " + placeHolder2 + " legal? " + legal);

		if (legal) {
			Container srcParent = placeHolder1.getParent();
			Container destParent = placeHolder2.getParent();

			// Remove source card
			if (srcParent != null) {
				srcParent.remove(placeHolder1);
				// If source is a layered pile, reflow it and reveal the new top card.
				if (srcParent instanceof JLayeredPane) {
					revealTopAfterRemoval((JLayeredPane) srcParent);
				} else {
					srcParent.revalidate();
					srcParent.repaint();
				}
			}

			// Prepare destination
			if (destParent instanceof JLayeredPane && colList.contains(destParent)) {
				JLayeredPane destPane = (JLayeredPane) destParent;

				// Remove any placeholder
				removePlaceholdersIfAny(destPane);

				int yOffset = destPane.getComponentCount() * 10;
				destPane.add(placeHolder1, Integer.valueOf(destPane.getComponentCount()));
				placeHolder1.setBounds(0, yOffset, placeHolder1.getWidth(), placeHolder1.getHeight());
				placeHolder1.setVisible(true);
				destPane.revalidate();
				destPane.repaint();
			}

			if (destParent instanceof JLayeredPane && doneCols.contains(destParent)) {
				JLayeredPane destPane = (JLayeredPane) destParent;

				// Remove any placeholder
				removePlaceholdersIfAny(destPane);

				destPane.add(placeHolder1, Integer.valueOf(destPane.getComponentCount()));
				placeHolder1.setBounds(0, 0, placeHolder1.getWidth(), placeHolder1.getHeight());
				placeHolder1.setVisible(true);
				destPane.revalidate();
				destPane.repaint();
			}
			
			// Clear selection
			placeHolder1.setBorder(null);
			if (placeHolder2 != null) placeHolder2.setBorder(null);
			placeHolder1 = null;
			placeHolder2 = null;
		}
		else if (placeMent == 7) {
			if (drawArray.get(0).highestLayer() < 1) {
				resetDraw();
				newDraw();
				System.out.println("Resetting draw pile.");
			} else {
				newDraw();
				System.out.println("Drawing new cards.");
			}
		} else {
			placeHolder1.setBorder(null);
			if (placeHolder2 != null) placeHolder2.setBorder(null);
			placeHolder1 = null;
			placeHolder2 = null;
		}

		if (placeMent == 7) {
			if (drawArray.get(0).highestLayer() < 1) {
				resetDraw();
				newDraw();
				System.out.println("Resetting draw pile.");
			} else {
				newDraw();
				System.out.println("Drawing new cards.");
			}
		}
	
		// Handle draw pile logic if needed

		return columnsInPlay;
	}

	// helper to detect the sentinel placeholder without relying on Card.equals
	private boolean isPlaceholder(Card c) {
		if (c == null) return false;
		// fast path: if equals is implemented, use it
		try {
			if (c.equals(new Card(100, Card.Suit.Spades))) return true;
		} catch (Throwable t) {
			// ignore
		}
		// reflection fallback: look for an int field == 100 and an enum field named "Spades"
		try {
			boolean intMatch = false;
			boolean suitMatch = false;
			for (Field f : c.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				Object val = f.get(c);
				if (!intMatch && val instanceof Integer && ((Integer) val) == 100) intMatch = true;
				if (!suitMatch && val != null && val.getClass().isEnum() && val.toString().equals("Spades")) suitMatch = true;
				if (intMatch && suitMatch) return true;
			}
		} catch (Throwable t) {
			// ignore reflection failures
		}
		return false;
	}

	// Preconditions: drawArray is not null
	// Postconditions:
	public void newDraw() {
		for (int i = 0; i < 3 && drawArray.get(0).getComponentCount() > 0; i++) {
			Card card = (Card) drawArray.get(0).getComponent(i);
			card.setBounds(10 * i, 0, card.getWidth(), card.getHeight());
			drawedArray.get(0).add(card, 0);
			drawArray.get(0).remove(0);
			card.setVisible(true);
		}
	}

	public void resetDraw() {
		while (drawedArray.get(0).getComponentCount() > 0) {
			JLayeredPane temp = drawedArray.get(0);
			drawArray.get(0).add(temp.getComponent(0));
			temp.remove(0);

		}
	}

	private void revealTopAfterRemoval(JLayeredPane p) {
		if (p == null) return;

		// If the pane became empty, add the sentinel placeholder (100, Spades)
		if (p.getComponentCount() == 0) {
			Card placeholder = new Card(100, Card.Suit.Spades);
			placeholder.setBounds(0, 0, 100, 145);
			placeholder.setVisible(true);
			p.add(placeholder, 0);
			p.revalidate();
			p.repaint();
			return;
		}

		// Find the top-most non-placeholder card (highest y). Prefer non-placeholder cards.
		Card toReveal = null;
		int maxY = Integer.MIN_VALUE;
		for (Component c : p.getComponents()) {
			if (!(c instanceof Card)) continue;
			Card card = (Card) c;
			if (isPlaceholder(card)) continue;
			Rectangle r = card.getBounds();
			int y = (r != null) ? r.y : 0;
			if (y >= maxY) {
				maxY = y;
				toReveal = card;
			}
		}

		if (toReveal != null) {
			final Card finalCard = toReveal;
			// Ensure face-up and visible, and clear any selection border on the EDT
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						finalCard.show(); // set isReversed = false (face up)
					} catch (Throwable t) {
						// ignore if show() can't be called for some reason
					}
					finalCard.setVisible(true);
					finalCard.setBorder(null);
					p.revalidate();
					p.repaint();
				}
			});
		} else {
			// nothing to reveal; still refresh the pane
			SwingUtilities.invokeLater(() -> { p.revalidate(); p.repaint(); });
		}
	}

	//Precondition: Legal moves is working as such the final decks only contain the cards  the same suit
	public void checkWin() {
		int done=0;
		for (int i=0; i<3; i++){
			if (doneCols.get(i).getComponentCount()==13){
				done++;
			}
		}
		if (done==4){
			won=true;
		}
			
	}

}
