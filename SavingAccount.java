
public class SavingAccount extends Account
{

    private Keypad keypad;
    private double InterestRate=0.001;//interest rate default 0.1%

    public SavingAccount
    (int theAccountNumber,int thePIN,double theAvailableBalance,double theTotalBalance)
    {
        super(theAccountNumber, thePIN,  theAvailableBalance, theTotalBalance);
        //get constructor from class Account
    }
    public void setInterestRate(){//method to set interest rate
        InterestRate = keypad.getDoubleInput();
    }
    
    public double getInterestRate(){//method to get interest rate
        return InterestRate;
    }
}
