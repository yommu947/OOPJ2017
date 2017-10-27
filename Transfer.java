/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 *
 * @author WaiHong
 */
public class Transfer extends Transaction{
    
    private Keypad keypad; // reference to keypad
    private Screen screen; // reference to screen
    private BankDatabase bankdatabase; // reference to bankdatabase
	//declare instance variable
    private int transferAccountNumber;
    private double transferAmount;
    
    // Transfer constructor 
    public Transfer(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad){
        super(userAccountNumber, atmScreen, atmBankDatabase); // initialize superclass variables
        keypad = atmKeypad; // initialize references to keypad  
        // get reference
        screen = getScreen();
        bankdatabase = getBankDatabase();     
    } // end Transfer constructor
    
    // input transfer account Number
    public void setTransferAccountNumber(){
        transferAccountNumber = keypad.getInput();
    }
    
    // return account number 
    public int getTransferAccountNumber(){
        return transferAccountNumber;
    }
    
    // input transfer amount
    public void setTransferAmount(){
        transferAmount = keypad.getDoubleInput();
    }
    
    // return Amount
    public double getTransferAmount(){
        return transferAmount;
    }
    
    //check transfer account is exist
    public boolean isTransferAccountExist(){
        for(int i = 0; i < bankdatabase.getAccountsQuantity(); i++){
            if(transferAccountNumber == bankdatabase.getTransferAccountNumber(i))
                return true;
        }
        return false;
    }
    
    //confirm user input correct data
    public boolean confirmToTransfer(){
        screen.displayMessage("Transfer Account Number: "); //display Transfer account Number
        System.out.println(transferAccountNumber); // user input account number again
        screen.displayMessage("Transfer Amount: ");  //display Transfer Amount
        screen.displayDollarAmount(transferAmount);
        System.out.print("\n");
        boolean correctInput = false;
        int option = 0;
        while(!correctInput){ // while loop
            // prompt user input ""Yes" or "No"
            screen.displayMessage("\nYes - Press 1  ;  No - Press 2: ");
            option = keypad.getInput();
            if(option == 1 || option == 2)  //check the input is valid
                correctInput = true;
            else
                screen.displayMessageLine("Wrong Input. Please enter again.");
        } // end while loop
        if(option == 1) 
            return true; // user input 1 will return true
        else
            return false; // user input 2 will return false
    }
    
    // perform transaction
    public void execute(){
        //user input the tansfer account number
        screen.displayMessage("Please Enter the Transfer Account Number: ");
        setTransferAccountNumber();
        
        // check whether the transfer account is exist 
        if(isTransferAccountExist() == false){
            screen.displayMessageLine("Transfer Account doesn't exist.");
            screen.displayMessageLine("Transfer Failure");
            return;
        }
        
        // check whether the transfer account is current login account  
        if(transferAccountNumber == super.getAccountNumber()){
            screen.displayMessageLine("You cannot transfer to your own account.");
            screen.displayMessageLine("Transfer Failure");
            return;
        }
        
        // prompt user input the transfer amount 
        screen.displayMessage("Please Enter the Transfer Amount: ");
        setTransferAmount();
        
        // check whether the amount is valid
        if(transferAmount <= 0){
            screen.displayMessageLine("Transfer Amount should be greater than 0");
            screen.displayMessageLine("Transfer Failure");
            return;
        }
        
        //call method to confirm the data by user input is correct 
        if(confirmToTransfer() == false){
            screen.displayMessageLine("Transfer Cancelled");
            return;
        }
        
        //check whether the user has enough money in the account 
        if(transferAmount > bankdatabase.getTotalBalance(super.getAccountNumber()) ||
                transferAmount > bankdatabase.getAvailableBalance(super.getAccountNumber())){
            screen.displayMessageLine("\nYou have not sufficient balance to transfer");
            screen.displayMessageLine("Transfer Failure");
            return;
        }
        
        //change amount of account, if the transfer is success
        bankdatabase.transferCredit(transferAccountNumber, transferAmount);
        bankdatabase.debit(super.getAccountNumber(), transferAmount);
        System.out.println("\nTransfer Successful");
        
        //For checking transfer account whether receive the amount or not
        BalanceInquiry balanceInquiry = new BalanceInquiry(transferAccountNumber,screen,bankdatabase);
        balanceInquiry.execute();
    } //end executo
}// end class tranfer 
