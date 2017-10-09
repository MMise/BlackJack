import java.util.ArrayList;
import java.util.Collections;

public class Shoe 
{
    static int DECKS_IN_SHOE = 7;
    static int[] suits = { Card.CLUBS, Card.DIAMONDS, Card.HEARTS, Card.SPADES } ;
    ArrayList<Card> cards_in_shoe = new ArrayList<>();
    
    public Shoe()
    {
        for( int suit_index  = 0; suit_index < 4; suit_index++)
        {
            for ( int card_rank = 1; card_rank < 14; card_rank++)
            {
                for(int deckIndex = 0; deckIndex <= DECKS_IN_SHOE; deckIndex++)
                {
                    add_card( new Card(card_rank, suits[suit_index]));
                }
            }
        }
    }
    
    public void shuffle()
    {
        Collections.shuffle(cards_in_shoe); // shuffles the whole shoe
    }
    
    public void add_card(Card given_card)
    {
        if (cards_in_shoe.size() < 364) // 7 decks 7*52  = 364 is the default shoe size
        {
            cards_in_shoe.add(given_card);
        }
    }
    
    public Card getCard()
    {
        // If there are no cards left in the shoe, return null
        Card card_to_return = null;
        
        if (cards_in_shoe.size() > 0)
        {
            card_to_return = cards_in_shoe.remove(cards_in_shoe.size() - 1);
            // remove() return a reference to the
            // object it removes from the ArrayList
        }
        return card_to_return;
    }
}
