/*
 * Harjoitustyö, Mikko Jokinen TVT16SPO
*/

import javafx.application.Application;
import javafx.scene.* ; // Scene, Group, Node, etc.
import javafx.scene.text.* ; // Text, Font
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.* ;
import javafx.scene.control.Button ;
import javafx.geometry.* ; // Pos, Insets
import javafx.scene.image.Image;
import java.util.HashMap ;
import javafx.event.ActionEvent;


public class BlackJack extends Application 
{
    Shoe shoe;
    Bet bet = new Bet();
    CalculatorEngine calculator = new CalculatorEngine();
    Card card;
    Card dealer_card;
    
    StackPane root_stack = new StackPane();
    Text start_info =  new Text(" Set your bet and press DEAL to start ");
   
    Group player_cards = new Group();
    Group dealer_cards = new Group();
    Group all_cards = new Group();
    
    int card_index = 0;
    int dealerCardIndex = 0;
    
    
    @Override
    public void start( Stage stage)
    {
        stage.setTitle("BlackJack Game");
        
        // Load card images and insert them into HashMap
        ImageStore.card_back = new Image("playing_cards_images/card_back.png");
        ImageStore.card_faces = new HashMap<>();
        String[] file_names = { "hearts", "diamonds", "spades", "clubs"};
        
        for( int suit_index = 0; suit_index < 4; suit_index++)
        {
            for ( int card_rank = 1; card_rank < 14; card_rank++)
            {
                String img_file_name = "playing_cards_images/" + file_names [suit_index] + card_rank + ".png";
                Image card_face_image = new Image (img_file_name);
                String key_for_image = file_names[suit_index] + card_rank;
                ImageStore.card_faces.put( key_for_image, card_face_image);
            }
        }
        
        bet.setBalance(100);
        Text textBalance = new Text( "BALANCE = " + bet.getBalance());
        Text textBet = new Text( "BET = " + bet.getBet());
        Text textNotification = new Text(" GOOD LUCK! ");
        textNotification.setFont(new Font(24));
        
        // Create buttons for the game
        Button buttonDeal = new Button("DEAL");
        Button buttonHit = new Button("HIT");
        Button buttonStand = new Button("STAND");
        Button buttonDoubleDown = new Button("DOUBLE DOWN");
        Button buttonIncreaseBet = new Button("BET 5");
        
        Text valueOfPlayersHand = new Text("Your hands value = " + calculator.getValueForPlayer());
        Text valueOfDealersHand = new Text("Dealers hands value = " + calculator.getValueForDealer());
        
        shoe = new Shoe();
        shoe.shuffle();
        buttonHit.setDisable(true);
        buttonStand.setDisable(true);
        buttonDoubleDown.setDisable(true);
        buttonIncreaseBet.setDisable(false);
        
        // DEAL FUNCTIONALITY STARTS
        buttonDeal.setOnAction( (ActionEvent event) ->
        {
            if(bet.getBet() == 0)
            {
                start_info.setText("Please set your bet first");
            }
            else
            {
                // Remove info text before first hand
                if(start_info != null)
                {
                    root_stack.getChildren().remove(start_info);
                    start_info = null;
                }
                textNotification.setText(" GOOD LUCK! ");
                
                buttonDeal.setDisable(false);
                buttonHit.setDisable(false);
                buttonStand.setDisable(false);
                buttonDoubleDown.setDisable(false);
                buttonIncreaseBet.setDisable(false);
                
                calculator.dealButtonPressed();
                // Clear the list for players and dealers cards
                player_cards.getChildren().clear();
                dealer_cards.getChildren().clear();
                
                for( card_index = 0; card_index < 2; card_index++)
                 {
                    card = shoe.getCard();
                    double cardPosX =  40 + (Card.CARD_WIDTH/2) * card_index;
                    double cardPosY = 350;
                    card.setCardPosition(cardPosX, cardPosY);
                    player_cards.getChildren().add(card);
                    if(card.getRank() > 10)
                    {
                       calculator.calculateValueForPlayer(10);
                    }
                    else{
                        calculator.calculateValueForPlayer(card.getRank());
                    }
                    valueOfPlayersHand.setText("Your hands value = " + calculator.getValueForPlayer());
                 }
                for(dealerCardIndex = 0; dealerCardIndex < 2; dealerCardIndex++)
                {
                    dealer_card = shoe.getCard();
                    double cardPosX =  40 + (Card.CARD_WIDTH/2) * dealerCardIndex;
                    double cardPosY = 50;
                    dealer_card.setCardPosition(cardPosX, cardPosY);
                    dealer_cards.getChildren().add(dealer_card);
                    if(dealer_card.getRank() > 10)
                    {
                       calculator.calculateValueForDealer(10);
                    }
                    else{
                        calculator.calculateValueForDealer(dealer_card.getRank());
                    }
                    if(dealerCardIndex == 1)
                    {
                        dealer_card.turnCardFaceDown();
                    }
                }
                valueOfDealersHand.setText("Dealers hands value = HIDDEN");
                buttonDeal.setDisable(true);
                buttonIncreaseBet.setDisable(true);
                if(calculator.isBJ(calculator.getValueForPlayer()))
                {
                    bet.setBalance(bet.getBet()*2.5);
                    bet.setBet(0);
                    textBalance.setText("BALANCE = " + bet.getBalance());
                    textBet.setText("BET = " + bet.getBet());
                    buttonHit.setDisable(true);
                    buttonStand.setDisable(true);
                    buttonDoubleDown.setDisable(true);
                    buttonIncreaseBet.setDisable(false);
                    buttonDeal.setDisable(false);
                    textNotification.setText("PLAYER GETS BLACKJACK");
                }
            }
        });
        // DEAL FUNCTIONALITY ENDS
        
        // HIT FUNCTIONALITY STARTS
        buttonHit.setOnAction( (ActionEvent event) ->
        {
            card = shoe.getCard();
            double cardPosX =  40 + (Card.CARD_WIDTH/2) * card_index;
            double cardPosY = 350;
            card.setCardPosition(cardPosX, cardPosY);
            
            player_cards.getChildren().add(card);
            card_index++;
            if(card.getRank() > 10)
            {
               calculator.calculateValueForPlayer(10);
            }
            else{
                calculator.calculateValueForPlayer(card.getRank());
            }
            valueOfPlayersHand.setText("Your hands value = " + calculator.getValueForPlayer());
            if(calculator.getValueForPlayer() > 21)
            {
                buttonDeal.setDisable(false);
                buttonHit.setDisable(true);
                buttonStand.setDisable(true);
                buttonDoubleDown.setDisable(true);
                buttonIncreaseBet.setDisable(false);
                bet.setBet(0);
                textBet.setText("BET = " + bet.getBet());
                textNotification.setText(" PLAYER BUST! ");
            }
        });
        // HIT FUNCTIONALITY ENDS
        
        // STAND FUNCTIONALITY STARTS
        buttonStand.setOnAction( (ActionEvent event) ->
        {
            buttonDeal.setDisable(true);
            buttonHit.setDisable(true);
            buttonStand.setDisable(true);
            buttonDoubleDown.setDisable(true);
            buttonIncreaseBet.setDisable(true);
            dealer_card.turnCardFaceUp();
            do{
                dealer_card = shoe.getCard();
                double cardPosX =  40 + (Card.CARD_WIDTH/2) * dealerCardIndex;
                double cardPosY = 50;
                dealer_card.setCardPosition(cardPosX, cardPosY);
                dealer_cards.getChildren().add(dealer_card);
                dealerCardIndex++;
                if(dealer_card.getRank() > 10)
                {
                   calculator.calculateValueForDealer(10);
                }
                else{
                    calculator.calculateValueForDealer(card.getRank());
                }
            }while(calculator.getValueForDealer() <= 16);
                
            if(calculator.getValueForDealer() > 21) {
                bet.setBalance(bet.getBet()*2);
                bet.setBet(0);
                textBalance.setText("BALANCE = " + bet.getBalance());
                textBet.setText("BET = " + bet.getBet());
                textNotification.setText("DEALER BUST! PLAYER WINS!");
                buttonDeal.setDisable(false);
                buttonHit.setDisable(true);
                buttonStand.setDisable(true);
                buttonDoubleDown.setDisable(true);
                buttonIncreaseBet.setDisable(false);
            }
            if(calculator.getValueForDealer() == 21)
                {
                    bet.setBet(0);
                    textBalance.setText("BALANCE = " + bet.getBalance());
                    textBet.setText("BET = " + bet.getBet());
                    buttonDeal.setDisable(false);
                    buttonHit.setDisable(true);
                    buttonStand.setDisable(true);
                    buttonDoubleDown.setDisable(true);
                    buttonIncreaseBet.setDisable(false);
                    textNotification.setText("DEALER GETS BLACKJACK!");
                }
            if(calculator.getValueForPlayer() > calculator.getValueForDealer()) {
                bet.setBalance(bet.getBet()*2);
                bet.setBet(0);
                textBalance.setText("BALANCE = " + bet.getBalance());
                textBet.setText("BET = " + bet.getBet());
                buttonDeal.setDisable(false);
                buttonIncreaseBet.setDisable(false);
                textNotification.setText("PLAYER WINS");
            }
            if(calculator.getValueForPlayer() < calculator.getValueForDealer() && calculator.getValueForDealer() < 21) {
                bet.setBet(0);
                textBalance.setText("BALANCE = " + bet.getBalance());
                textBet.setText("BET = " + bet.getBet());
                buttonDeal.setDisable(false);
                buttonIncreaseBet.setDisable(false);
                textNotification.setText("DEALER WINS");
            }
            if(calculator.getValueForPlayer() == calculator.getValueForDealer() && calculator.getValueForDealer() < 21) {
                bet.setBalance(bet.getBet());
                bet.setBet(0);
                textBalance.setText("BALANCE = " + bet.getBalance());
                textBet.setText("BET = " + bet.getBet());
                buttonDeal.setDisable(false);
                buttonIncreaseBet.setDisable(false);
                textNotification.setText("PUSH!");
            }
            valueOfDealersHand.setText("Dealers hands value = " + calculator.getValueForDealer());
        });
        // STAND FUNCTIONALITY ENDS
        
        // DOUBLE DOWN FUNCTIONALITY STARTS
        buttonDoubleDown.setOnAction( (ActionEvent event) ->
        {
            bet.doubleDown();
            textBet.setText("BET = " + bet.getBet());
            textBalance.setText("BALANCE = " + bet.getBalance());
            
            Card hitCard = shoe.getCard();
            double cardPosX =  40 + (Card.CARD_WIDTH/2) * card_index;
            double cardPosY = 350;
            hitCard.setCardPosition(cardPosX, cardPosY);
            
            player_cards.getChildren().add(hitCard);
            card_index++;
            
            calculator.calculateValueForPlayer(hitCard.getRank());
            valueOfPlayersHand.setText("Your hands value = " + calculator.getValueForPlayer());
            buttonHit.setDisable(true);
            buttonStand.setDisable(true);
            buttonDoubleDown.setDisable(true);
            buttonIncreaseBet.setDisable(true);
            if(calculator.getValueForPlayer() > 21)
            {
                buttonDeal.setDisable(false);
                buttonHit.setDisable(true);
                buttonStand.setDisable(true);
                buttonDoubleDown.setDisable(true);
                buttonIncreaseBet.setDisable(false);
                bet.setBet(0);
                textBet.setText("BET = " + bet.getBet());
            }
            
        });
        // DOUBLE DOWN FUNCTIONALITY ENDS
        
        // INCREASE BET FUNCTIONALITY STARTS
        buttonIncreaseBet.setOnAction( (ActionEvent event) ->
        {
            bet.setBet(5);
            textBet.setText("BET = " + bet.getBet());
            textBalance.setText("BALANCE = " + bet.getBalance());
        });
        // INCREASE BET FUNCTIONALITY ENDS
        
        // GRAPHICAL COMMANDS
        HBox paneForButtons =  new HBox(16);
        HBox paneForHandValues = new HBox(40);
        paneForHandValues.getChildren().addAll(valueOfPlayersHand, valueOfDealersHand);
        paneForHandValues.setAlignment(Pos.CENTER);
        paneForHandValues.setPadding(new Insets(0,0,-510,0));
        paneForButtons.getChildren().addAll(buttonDeal, buttonHit, buttonStand, buttonDoubleDown, buttonIncreaseBet, textBalance, textBet, textNotification);
        paneForButtons.setAlignment(Pos.CENTER);
        paneForButtons.setPadding (new Insets(0, 0, 20, 0));
        
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(paneForButtons);
        
        all_cards.setManaged(false);
        all_cards.getChildren().addAll(player_cards, dealer_cards);
        start_info.setFont(new Font(24));
        
        root_stack.getChildren().addAll(paneForHandValues,borderPane, all_cards, start_info);
        root_stack.setBackground(null);
        
        Scene scene = new Scene(root_stack, 910, 700);
        scene.setFill(Color.DARKGREEN);
        
        stage.setScene(scene);
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
