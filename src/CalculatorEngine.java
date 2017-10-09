public class CalculatorEngine {
    
    private int playersCards = 0;
    private int dealersCards = 0;
    
    public void calculateValueForPlayer(int cardValue){
        if(cardValue == 1){
            playersCards = playersCards + 11;
        }
        else{
            playersCards = playersCards + cardValue;
        }
    }
    public void calculateValueForDealer(int cardValue){
       
        if(cardValue == 1){
            dealersCards =dealersCards + 11;
        }
        else{
            dealersCards = dealersCards + cardValue;
        }
    }
    
    public int getValueForPlayer(){
        return playersCards;
    }
    
    public int getValueForDealer(){
        return dealersCards;
    }
    
    public void dealButtonPressed(){
        playersCards = 0;
        dealersCards = 0;
    }
    
    public boolean isWin(){
        return playersCards > dealersCards;
    }
    
    public boolean isBust(int cardsValues){
        return cardsValues > 21;
    }
    
    public boolean isBJ(int cardsValues){
        return cardsValues == 21;
    }
}
