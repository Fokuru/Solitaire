import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
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
	Boolean won = false;
	int score = 0;

	// Preconditions: none
	// Postconditions: initializes the game state and returns the initial columnsInPlay
	public ArrayList<ArrayList<JLayeredPane>> setUp() {

		columnsInPlay = new ArrayList<ArrayList<JLayeredPane>>();
		doneCols = new ArrayList<JLayeredPane>();
		suitTypes = new ArrayList<String>();
		valueTypes = new ArrayList<String>();
		deck = new LinkedList();
		drawArray = new ArrayList<JLayeredPane>();
		drawedArray = new ArrayList<JLayeredPane>();
		colList = new ArrayList<JLayeredPane>();
		firstClick = -1;
		secondClick = -1;
		placeHolder1 = null;
		placeHolder2 = null;
		won = false;

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

		JLayeredPane[] cols = { col1, col2, col3, col4, col5, col6, col7 };
		for (int c = 0; c < cols.length; c++) {
			ArrayList<Card> colCards = columns.get(c); // use column c, not 1
			for (int i = 0; i < colCards.size(); i++) {
				Card card = colCards.get(i);
				card.setBounds(0, offset * i, card.getWidth(), card.getHeight());
				if (i < colCards.size() - 1)
					card.setVisible(false); // hide all but top
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

	// Preconditions: none
	// Postconditions: returns the current columnsInPlay
	public ArrayList<ArrayList<JLayeredPane>> getColumnsInPlay() {
		return columnsInPlay;
	}

	// helper: find the pane corresponding to a placement index
	// Preconditions: pm is a valid placement index
	// Postconditions: returns the corresponding JLayeredPane, or null if not found
	private JLayeredPane findPane(int pm) {
		if (pm >= 0 && pm <= 6)
			return colList.get(pm);
		if (pm == 8)
			return drawedArray.get(0);
		if (pm >= 9 && pm <= 12)
			return doneCols.get(pm - 9);
		return null;
	}

	// Record the source and destination cards for a move
	// Preconditions: placeMent is the index of the clicked area
	// Postconditions: placeHolder1 and placeHolder2 are set as appropriate
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
					if (colList.contains(pane)) {
						comp = getBottomVisibleInColumn(pane);
					} else {
						comp = getTopComponent(pane);
					}
				}
			}

			// still ignore clicks on the sentinel placeholder card when trying to pick a
			// source
			if (comp instanceof Card && isPlaceholder((Card) comp)) {
				return;
			}

			if (comp != null && comp instanceof Card) {
				placeHolder1 = (Card) comp;
				placeHolder1.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
			}
		}

		// If placeHolder1 is not null but placeHolder2 is, we're recording the second
		// card (destination)
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
	// Preconditions: cardPane is not null
	// Postconditions: returns the top-most Card component in the pane, or null if none
	private Component getTopComponent(JLayeredPane cardPane) {
		if (cardPane == null || cardPane.getComponentCount() == 0)
			return null;

		Component topVisible = null;
		int maxYVisible = Integer.MIN_VALUE;
		Component topAny = null;
		int maxYAny = Integer.MIN_VALUE;

		// Find the top-most visible non-placeholder first; also track the top-most card
		// regardless of visibility
		if (colList.contains(cardPane)) {
			for (Component c : cardPane.getComponents()) {
				if (!(c instanceof Card))
					continue;
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

		// prefer the top-most visible card; if none visible, return the top-most card
		// (even if hidden)
		if (topVisible != null) {
			System.out.println("Top visible card in pane: " + topVisible);
			System.out.println("this card is " + ((Card) topVisible).isReversed);
			return topVisible;
		}
		if (topAny != null) {
			System.out.println("No visible cards; top card in pane: " + topAny);
			return topAny;
		}

		// final fallback: any Card (including placeholders)
		for (Component c : cardPane.getComponents()) {
			if (c instanceof Card)
				return c;
		}

		return null;

	}

	// helper: return the bottom-most visible card in a layered pane (largest y offset)
	// Preconditions: cardPane is not null
	// Postconditions: returns the bottom-most visible Card component in the pane, or null if none
	private Component getBottomVisibleInColumn(JLayeredPane pane) {
    if (pane == null || pane.getComponentCount() == 0) return null;
    Component bottom = null;
    int maxY = Integer.MIN_VALUE;
    for (Component c : pane.getComponents()) {
        if (!(c instanceof Card)) continue;
        Card card = (Card) c;
        if (isPlaceholder(card)) continue;
        if (!card.isVisible()) continue;
        Rectangle r = c.getBounds();
        int y = (r != null) ? r.y : 0;
        if (y > maxY) {
            maxY = y;
            bottom = c;
        }
    }
    // fallback to the existing logic if nothing matched
    return (bottom != null) ? bottom : getTopComponent(pane);
}

	// remove any placeholder cards from a destination pane (before adding a real
	// card)
	// Preconditions: cardPane is not null
	// Postconditions: any placeholder cards in cardPane are removed
	private void removePlaceholdersIfAny(JLayeredPane cardPane) {
		if (cardPane == null)
			return;
		for (int i = cardPane.getComponentCount() - 1; i >= 0; i--) {
			Component c = cardPane.getComponent(i);
			if (c instanceof Card && isPlaceholder((Card) c)) {
				cardPane.remove(i);
			}
		}
		cardPane.revalidate();
		cardPane.repaint();
	}

	// Preconditions: c1 is the moving card, c2 is the top card of the destination pile
	// Postconditions: returns true if the move is legal, false otherwise
	public boolean isLegalMove(Card c1, Card c2, int destination) {

		if (c1 == null || c2 == null)
			return false;

		Container parentOf2 = c2.getParent();

		if (parentOf2 == null)
			return false;

		boolean isDestinationDonePile = doneCols.contains(parentOf2);
		boolean isDestinationColumn = colList.contains(parentOf2);
		boolean isDestinationDrawedPile = drawedArray.contains(parentOf2);

		// if the pile the card is being placed into is...
		if (isDestinationDonePile) {//from the finishing piles
			// Check if parentOf2 is the correct done pile for c1
			// So the suit has to match and the value has to be one higher than the top card
			// in the done pile
			// If the done pile is empty (only has placeholder), c1 must be an Ace (value 1)

			if (doneCols.indexOf(parentOf2) == 0) { // heartsDone
				if (c1.whatSuit() != Card.Suit.Hearts)
					return false;
				if (c1.whatValue() == 1)
					return true;
				if (c1.whatValue() == c2.whatValue() + 1)
					return true;
				return false;
			} else if (doneCols.indexOf(parentOf2) == 1) { // diamondsDone
				if (c1.whatSuit() != Card.Suit.Diamonds)
					return false;
				if (c1.whatValue() == 1)
					return true;
				if (c1.whatValue() == c2.whatValue() + 1)
					return true;
				return false;
			} else if (doneCols.indexOf(parentOf2) == 2) { // clubsDone
				if (c1.whatSuit() != Card.Suit.Clubs)
					return false;
				if (c1.whatValue() == 1)
					return true;
				if (c1.whatValue() == c2.whatValue() + 1)
					return true;
				return false;
			} else if (doneCols.indexOf(parentOf2) == 3) { // spadesDone
				if (c1.whatSuit() != Card.Suit.Spades)
					return false;
				if (c1.whatValue() == 1)
					return true;
				if (c1.whatValue() == c2.whatValue() + 1)
					return true;
				return false;
			}
		}
		boolean sameColour = false;// get if it's the same colour

		if (((c1.whatSuit() == Card.Suit.Spades) || (c1.whatSuit() == Card.Suit.Clubs))
				&& ((c2.whatSuit() == Card.Suit.Diamonds) || (c2.whatSuit() == Card.Suit.Hearts))) {
			sameColour = false;
		}
		if (((c1.whatSuit() == Card.Suit.Diamonds) || (c1.whatSuit() == Card.Suit.Hearts))
				&& ((c2.whatSuit() == Card.Suit.Spades) || (c2.whatSuit() == Card.Suit.Clubs))) {
			sameColour = false;
		} else if (((c1.whatSuit() == Card.Suit.Spades) || (c1.whatSuit() == Card.Suit.Clubs))
				&& ((c2.whatSuit() == Card.Suit.Spades) || (c2.whatSuit() == Card.Suit.Clubs))) {
			sameColour = true;
		} else if (((c1.whatSuit() == Card.Suit.Diamonds) || (c1.whatSuit() == Card.Suit.Hearts))
				&& ((c2.whatSuit() == Card.Suit.Diamonds) || (c2.whatSuit() == Card.Suit.Hearts))) {
			sameColour = true;
		}
		if (isDestinationColumn) {// a column
			if (sameColour == false && (c1.whatValue() == c2.whatValue() - 1)) {
				System.out.println("legal move to column");
				score += 50;
				return true;
			} else if ((isPlaceholder(placeHolder2)) && (c1.whatValue() == 13)) {
				return true;
			}
			// So the color has to be opposite and the value has to be one lower than c2
			// If the column is empty (only has placeholder), c1 must be a King (value 13)
			//from the draw pile
		} else if (isDestinationDrawedPile) {

			System.out.println("illegal move to drawed pile");
			return false;//then it's illegal
		}


		return false;
	}

	// Preconditions: placeMent is the index of the clicked area
	// Postconditions: performs the move if legal, returns updated columnsInPlay
	public ArrayList<ArrayList<JLayeredPane>> moveCard(int placeMent) {

		checkWin();
		if (won) {
			return columnsInPlay;
		}

		if (placeMent == 7) {
			if (drawArray.get(0).getComponentCount() < 3) {
				resetDraw();
				newDraw();
				System.out.println("Resetting draw pile.");
			} else {
				System.out.println("Drawing new cards.");
				newDraw();
			}
		}

		// If either placeholder is null, pick the cards first
		if (placeHolder1 == null || placeHolder2 == null) {
			recordCard(placeMent);
			return columnsInPlay;
		}

		// Do not move placeholders
		if (isPlaceholder(placeHolder1)) {
			placeHolder1.setBorder(null);
			if (placeHolder2 != null)
				placeHolder2.setBorder(null);
			placeHolder1 = null;
			placeHolder2 = null;
			firstClick = -1;
			secondClick = -1;
			return columnsInPlay;
		}

		JLayeredPane srcParent = (JLayeredPane) placeHolder1.getParent();
		Container destParent = placeHolder2.getParent();
		ArrayList<Card> movingCards = new ArrayList<Card>();
		boolean legal = false;
		Card bottomCard = new Card(100, Card.Suit.Spades);

		if (colList.contains(srcParent) && colList.contains(destParent)) {
			movingCards = movingCards(srcParent);
			if(movingCards.size() > 0) {
				bottomCard = movingCards.get(movingCards.size() - 1);

				System.out.println("Moving cards: " + movingCards);
				System.out.println("This is at 0 " + movingCards.get(0));
				System.out.println("This is at size-1 " + movingCards.get(movingCards.size() - 1));
				System.out.println("Size of moving cards: " + movingCards.size());
				
				System.out.println("Bottom card: " + bottomCard);
				legal = isLegalMove(bottomCard, placeHolder2, placeMent);
			} else {
				legal = isLegalMove(placeHolder1, placeHolder2, placeMent);
			}
			
		} else {
			legal = isLegalMove(placeHolder1, placeHolder2, placeMent);
		}


		 
		System.out.println("Moving " + placeHolder1 + " to " + placeHolder2 + " legal? " + legal);

		if (legal) {
			placeHolder1.setBorder(null);
			placeHolder2.setBorder(null);

			// Prepare list of cards to move (already computed earlier as 'movingCards')
			// If movingCards was empty, fall back to single card
			if (movingCards == null || movingCards.isEmpty()) {
				movingCards = new ArrayList<>();
				movingCards.add(placeHolder1);
			}

			// Remove any placeholder in destination (for column or done piles)
			if (destParent instanceof JLayeredPane) {
				removePlaceholdersIfAny((JLayeredPane) destParent);
			}

			// If moving between two columns: move the entire sequence, preserving order.
			if (drawedArray.contains(srcParent) && colList.contains(destParent)) {
				System.out.println("Moving from drawed pile to column.");
				Card c = placeHolder1;  // always a single card

				JLayeredPane srcPane = drawedArray.get(0);
				JLayeredPane destPane = (JLayeredPane) destParent;

				// remove placeholder if any
				removePlaceholdersIfAny(destPane);

				// compute offset
				int yOffset = destPane.getComponentCount() * 10;

				srcPane.remove(c);
				destPane.add(c, Integer.valueOf(destPane.getComponentCount()));
				c.setBounds(0, yOffset, c.getWidth(), c.getHeight());
				c.setVisible(true);

				// show next card in drawed pile (if needed)
				revealTopAfterRemoval(srcPane);

				placeHolder1 = null;
				placeHolder2 = null;

				destPane.revalidate();
				destPane.repaint();
			} else if (srcParent != null && destParent instanceof JLayeredPane && colList.contains(srcParent)
					&& colList.contains(destParent)) {

				Collections.reverse(movingCards);

				JLayeredPane srcPane = (JLayeredPane) srcParent;
				JLayeredPane destPane = (JLayeredPane) destParent;

				// compute starting y offset on destination (accounting for placeholders that were removed)
				int destCount = 0;
				for (Component c : destPane.getComponents()) {
					if (c instanceof Card && !isPlaceholder((Card) c)) destCount++;
				}
				int yOffset = destCount * 10;

				// preserve order: movingCards[0] should be the top-most card of the moved stack,
				// and the last element should be the bottom-most.
				for (Card moving : movingCards) {
					// remove from src (safe removal)
					srcPane.remove(moving);
					// add to dest at the next layer index so it stacks correctly
					destPane.add(moving, Integer.valueOf(destPane.getComponentCount()));
					moving.setBounds(0, yOffset, moving.getWidth(), moving.getHeight());
					moving.setVisible(true);
					moving.setBorder(null);
					yOffset += 10;
				}

				// refresh both panes
				destPane.revalidate();
				destPane.repaint();

				revealTopAfterRemoval(srcPane);

				// clear selection
				placeHolder1 = null;
				placeHolder2 = null;
			}

			// moving to a done-pile (single-card only)
			else if (destParent instanceof JLayeredPane && doneCols.contains(destParent)) {
				JLayeredPane destPane = (JLayeredPane) destParent;
				removePlaceholdersIfAny(destPane);

				// only move the single selected card to the done pile
				if (!movingCards.isEmpty()) {
					Card toMove = movingCards.get(0); // should be the single card
					if (srcParent instanceof JLayeredPane)
						((JLayeredPane) srcParent).remove(toMove);
					destPane.add(toMove, Integer.valueOf(destPane.getComponentCount()));
					toMove.setBounds(0, 0, toMove.getWidth(), toMove.getHeight());
					toMove.setVisible(true);
					toMove.setBorder(null);
				}

				if (srcParent instanceof JLayeredPane)
					revealTopAfterRemoval((JLayeredPane) srcParent);

				destPane.revalidate();
				destPane.repaint();

				placeHolder1 = null;
				placeHolder2 = null;
			}

			// Clear selection

		} else if (placeMent == 7) {
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
			if (placeHolder2 != null)
				placeHolder2.setBorder(null);
			placeHolder1 = null;
			placeHolder2 = null;
		}

		// Handle draw pile logic if needed

		return columnsInPlay;
	}

	// helper to detect the sentinel placeholder without relying on Card.equals
	// Preconditions: c is not null
	// Postconditions: returns true if c is the placeholder card, false otherwise
	private boolean isPlaceholder(Card c) {
		if (c == null)
			return false;
		// fast path: if equals is implemented, use it
		try {
			if (c.equals(new Card(100, Card.Suit.Spades)))
				return true;
		} catch (Throwable t) {
			// ignore
		}
		// reflection fallback: look for an int field == 100 and an enum field named
		// "Spades"
		try {
			boolean intMatch = false;
			boolean suitMatch = false;
			for (Field f : c.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				Object val = f.get(c);
				if (!intMatch && val instanceof Integer && ((Integer) val) == 100)
					intMatch = true;
				if (!suitMatch && val != null && val.getClass().isEnum() && val.toString().equals("Spades"))
					suitMatch = true;
				if (intMatch && suitMatch)
					return true;
			}
		} catch (Throwable t) {
			// ignore reflection failures
		}
		return false;
	}

	// Preconditions: drawArray is not null
	// Postconditions: Adds at most 3 new cards to the drawed deck
	public void newDraw() {
		System.out.println("Cards in draw pile: " + drawArray.get(0).getComponentCount());
		System.out.println("Cards in drawed pile: " + drawedArray.get(0).getComponentCount());
		
		int amountToDraw = 3;
		if (drawArray.get(0).getComponentCount() < 3) {
			System.out.println("k");
			amountToDraw = drawedArray.get(0).getComponentCount();
			System.out.println(drawArray.get(0).getComponentCount());
			System.out.println("Less than 3 cards in draw pile, adjusting amount to draw to " + amountToDraw);
		}
		
		for (int k = 0; k < amountToDraw; k++) {
				Card card = (Card) drawArray.get(0).getComponent(0);
				card.setBounds(80 + k * -40, 0, card.getWidth(), card.getHeight());
				card.show();
				drawedArray.get(0).add(card, drawedArray.get(0).getComponentCount(), 0);
	
		}

		drawedArray.get(0).revalidate();
		drawedArray.get(0).repaint();
		drawArray.get(0).revalidate();
		drawArray.get(0).repaint();

		System.out.println("Cards in draw pile: " + drawArray.get(0).getComponentCount());
		System.out.println("Cards in drawed pile: " + drawedArray.get(0).getComponentCount());

		if (drawArray.get(0).getComponentCount() == 0) {
			drawArray.get(0).add(new Card(100, Card.Suit.Spades), 0);
			drawArray.get(0).revalidate();
			drawArray.get(0).repaint();
		}

	}

	//Moves all cards that have been drawm back into the deck and shuffles them
	// Preconditions: drawedArray and drawArray are not null
	// Postconditions: all cards from drawedArray are moved back to drawArray and shuffled
	public void resetDraw() {

		if (isPlaceholder((Card) getTopComponent(drawArray.get(0)))) {
			drawArray.get(0).remove(getTopComponent(drawArray.get(0)));
			drawArray.get(0).revalidate();
			drawArray.get(0).repaint();
		}

		System.out.println("e");
		while (drawedArray.get(0).getComponentCount() > 0) {
			drawArray.get(0).add(drawedArray.get(0).getComponent(0));

		}

		for (int i = 0; i < drawArray.get(0).getComponentCount(); i++) {
			System.out.print(drawArray.get(0).getComponent(i));
		}

		for (int j = 0; j < drawArray.get(0).getComponentCount(); j++) {
			Card card = (Card) drawArray.get(0).getComponent(j);
			card.setBounds(0, 0, card.getWidth(), card.getHeight());
			card.hide();
		}

		ArrayList<Card> toShuffle = new ArrayList<Card>();
		for (int i = 0; i < drawArray.get(0).getComponentCount(); i++) {
			Card card = (Card) drawArray.get(0).getComponent(i);
			toShuffle.add(card);
		}

		for (int i = 0; toShuffle.get(0).getComponentCount() > 0; i++) {
			int randomInt = (int)(Math.random() * drawArray.get(0).getComponentCount());
			Card card = (Card) drawArray.get(0).getComponent(randomInt);
			drawArray.get(0).remove(card);
			i--;
			toShuffle.remove(card);
			drawArray.get(0).add(card, 0);
		}

		drawedArray.get(0).revalidate();
		drawedArray.get(0).repaint();
		drawArray.get(0).revalidate();
		drawArray.get(0).repaint();
	}

	// Reveals the top card of one of the seven piles after the last non-reversed card is removed
	// Precondition: cardPane is one of the seven column panes
	// Postcondition: the top-most non-placeholder card is made visible and face-up
	private void revealTopAfterRemoval(JLayeredPane cardPane) {
		if (cardPane == null)
			return;

		// If the pane became empty, add the sentinel placeholder (100, Spades)
		if (cardPane.getComponentCount() == 0) {
			Card placeholder = new Card(100, Card.Suit.Spades);
			placeholder.setBounds(0, 0, 100, 145);
			placeholder.setVisible(true);
			cardPane.add(placeholder, 0);
			cardPane.revalidate();
			cardPane.repaint();
			return;
		}
		
		//Reveals the top card of one of the seven piles after the last non-reversed card is removed

		// Find the top-most non-placeholder card (highest y). Prefer non-placeholder
		// cards
					Card toReveal = null;
		int maxY = Integer.MIN_VALUE;
		for (Component c : cardPane.getComponents()) {
			if (!(c instanceof Card))
				continue;
			Card card = (Card) c;
			if (isPlaceholder(card))
				continue;
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
					cardPane.revalidate();
					cardPane.repaint();
				}
			});
		} else {
			// nothing to reveal; still refresh the pane
			SwingUtilities.invokeLater(() -> {
				cardPane.revalidate();
				cardPane.repaint();
			});
		}
	}

	// Checks if the game has been won
	// Precondition: Legal moves is working as such the final decks only contain the
	// cards the same suit
	// Postcondition: If all done piles have 13 cards, won is set to true
	public void checkWin() {
		int done = 0;
		for (int i = 0; i < 3; i++) {
			if (doneCols.get(i).getComponentCount() == 13) {
				done++;
			}
		}
		if (done == 4) {
			won = true;
		}

	}

	// Given a source container, returns the list of cards to be moved (from the source)
	// Precondition: source contains placeHolder1 as one of its components
	// Postcondition: returns the list of cards to be moved (at least placeHolder1)
	public ArrayList<Card> movingCards(Container source) {
		placeHolder1.setBorder(null);
		ArrayList<Card> movingCards = new ArrayList<>();
		if (!(source instanceof JLayeredPane)) {
			// nothing fancy for non-layered sources
			System.out.println("Source is not a JLayeredPane");
			movingCards.add(placeHolder1);
			return movingCards;
		}

		System.out.println("Source is a JLayeredPane");

		JLayeredPane pane = (JLayeredPane) source;

	//Returns what cards are to be moved		// If there's no non-placeholder visible card, fall back
		Card bottomComp = (Card) getBottomVisibleInColumn(pane);
		if (!(bottomComp instanceof Card)) {
			System.out.println("Bottom component in source pane is not a Card (probably empty pane with placeholder)");
			movingCards.add(placeHolder1);
			return movingCards;
		}

		// find the component index of placeHolder1 so we only move the stack starting at it
		boolean startAdding = false;
		for (int i = 0; i < pane.getComponentCount(); i++) {
			Component comp = pane.getComponent(i);
			if (!(comp instanceof Card)) continue;
			Card c = (Card) comp;
			if (isPlaceholder(c)) continue;
			// if we've reached the selected card, start collecting from here
			if (comp == placeHolder1) {
				startAdding = true;
			}
			if (!startAdding) continue;
			if (c.isReversed) {
				// hidden card below selection — shouldn't happen for a valid selection; stop
				continue;
			}
			movingCards.add(c);
		}

		// if nothing was collected (defensive), at least move the single selected card
		if (movingCards.isEmpty()) {
			movingCards.add(placeHolder1);
		}

		return movingCards;
	}

}
