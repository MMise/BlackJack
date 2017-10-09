public class Bet 
{
    private double bet = 0;
    private double balance = 0;
    
    public void setBet(double bet)
    {
        if(balance >= bet)
        {
            balance = balance - bet;
            this.bet = bet;
        }
    }
    public void setBalance(double balance)
    {
        this.balance = this.balance + balance;
    }
    
    public double getBalance()
    {
        return balance;
    }
    
    public double getBet()
    {
        return bet;
    }
    
    public void doubleDown() 
    {
        if(balance >= bet )
        {
            balance = balance - bet;
            bet = bet * 2;
        }
    }
}
