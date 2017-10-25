
public class CurrentAccount extends Account
{

    private Keypad keypad;
    private int OverdrawnLimit=10000;//overdrawn limit default 10000
   
    public CurrentAccount
    (int theAccountNumber,int thePIN,double theAvailableBalance,double theTotalBalance)
    {
        super(theAccountNumber,thePIN,theAvailableBalance,theTotalBalance);
        //get constructor from class Account
    }
    public void setOverdrawnLimit(){//method to set overdrawn limit
        OverdrawnLimit = keypad.getInput();
    }
    
    public int getOverdrawnLimit(){//method to get overdrawn limit
        return OverdrawnLimit;
    }
}
