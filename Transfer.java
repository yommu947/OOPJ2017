

public class Transfer extends Transaction {

	
private Keypad keypad; // reference to Keypad
private Screen screen; // reference to Screen
private BankDatabase bankdatabase; // reference to BankDatabase

// declare instance variable
private int transferAccountNumber;
private double transferAmount;


// Transfer constructor
public Transfer(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad) {
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
 
 //check whether the transfer account is exist
 public boolean isTransferAccountExist(){
     for(int i = 0; i < bankdatabase.getAccountsQuantity(); i++){
         if(transferAccountNumber == bankdatabase.getTransferAccountNumber(i))
             return true;
     }
     return false;
 }
 
 //confirmation of the information for transferring by the user
 public boolean TransferConfirmation(){
     screen.displayMessage("Transfer Account Number: "); //display Transfer account Number
     System.out.println(transferAccountNumber); // user input account number again
     screen.displayMessage("Transfer Amount: ");  //display Transfer Amount
     screen.displayDollarAmount(transferAmount);
     System.out.print("\n");
     boolean correctInput = false;
     int option = 0;
     while(!correctInput){ // while loop
         // prompt user input ""Yes" or "No"
         screen.displayMessage("\nConfirm - Press 1\nCancel -  Press 2 ");
         option = keypad.getInput();
         if(option == 1 || option == 2)  //check the input is valid
             correctInput = true;
         else
             screen.displayMessageLine("Error, Please enter again.");
     } // end while loop
     if(option == 1) 
         return true; // user input 1 will return true
     else
         return false; // user input 2 will return false
 }
 
 //Execute the transaction
 public void execute(){
     //user input the transfer account number
     screen.displayMessage("Please Enter the Transfer Account Number: ");
     setTransferAccountNumber();
     
     // check whether the transfer account is exist 
     if(isTransferAccountExist() == false){
         screen.displayMessageLine("Do not have this Transfer Account");
         screen.displayMessageLine("Transfer Failed");
         return;
     }
     
     // check whether the transfer account is current login account  
     if(transferAccountNumber == super.getAccountNumber()){
         screen.displayMessageLine("Sorry, You cannot transfer to your own account.");
         screen.displayMessageLine("Transfer Failed");
         return;
     }
     
     // prompt user input the transfer amount 
     screen.displayMessage("Please Enter the Transfer Amount : ");
     setTransferAmount();
     
     // check whether the amount is valid
     if(transferAmount <= 0){
         screen.displayMessageLine("The minimum transfer amount is $0.01");
         screen.displayMessageLine("Transfer Failed");
         return;
     }
     
     //call method to confirm the data by user input is correct 
     if(TransferConfirmation() == false){
         screen.displayMessageLine("Transfer Cancelled");
         return;
     }
     
     //check whether the user has enough money in the account 
     if(transferAmount > bankdatabase.getTotalBalance(super.getAccountNumber()) ||
             transferAmount > bankdatabase.getAvailableBalance(super.getAccountNumber())){
         screen.displayMessageLine("\ninsufficient balance to transfer");
         screen.displayMessageLine("Transfer Failed");
         return;
     }
     
     //change amount of account, if the transfer is successful
     bankdatabase.transferCredit(transferAccountNumber, transferAmount);
     bankdatabase.debit(super.getAccountNumber(), transferAmount);
     System.out.println("\nTransfer Successful");
     
     
 } //end of execute
 
}// end class transfer

