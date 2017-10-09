import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView
{
    private int card_rank;
    private int card_suit;
    
    Image card_faceup_image;
    
    public static final int HEARTS = 1;
    public static final int DIAMONDS = 2;
    public static final int SPADES = 3;
    public static final int CLUBS = 4;
    
    public static final int CARD_WIDTH = 150;
    public static final int CARD_HEIGHT = 150;
    
    public Card (int given_card_rank, int given_card_suit)
    {
        this.card_rank = given_card_rank;
        this.card_suit = given_card_suit;
        
        switch (card_suit) 
        {
           case HEARTS:
               card_faceup_image = ImageStore.card_faces.get( "hearts" + card_rank ) ;
               setImage( card_faceup_image ) ;
               break;
           case DIAMONDS:
               card_faceup_image = ImageStore.card_faces.get( "diamonds" + card_rank ) ;
               setImage( card_faceup_image ) ;
               break;
           case SPADES:
               card_faceup_image = ImageStore.card_faces.get( "spades" + card_rank ) ;
               setImage( card_faceup_image ) ;
               break;
           case CLUBS:
               card_faceup_image = ImageStore.card_faces.get( "clubs" + card_rank ) ;
               setImage( card_faceup_image ) ;
               break;
           default:
               break;
       }
    }
    
    public int getRank()
    {
        return card_rank;
    }
    
    public int getSuit()
    {
        return card_suit;
    }
    
    public void turnCardFaceUp()
    {
        setImage(card_faceup_image);
    }
    
    public void turnCardFaceDown()
    {
        setImage(ImageStore.card_back);
    }
    
    public void setCardPosition(double x, double y)
    {
        setX(x);
        setY(y);
    }
}
