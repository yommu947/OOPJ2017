package atmcasestudy_gui;
//========================Changes Start========================================================
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
//========================Changes End==========================================================
public class ATM extends JFrame
{
    private boolean userAuthenticated; // whether user is authenticated
    private int currentAccountNumber; // current user's account number
    private Screen screen; // ATM's screen
    private Keypad keypad; // ATM's keypad
    private CashDispenser cashDispenser; // ATM's cash dispenser
    private BankDatabase bankDatabase; // account information database

    // constants corresponding to main menu options
    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;

    private static final int TRANSFER = 3;

    private static final int EXIT = 4;

    //========================Changes Start=========================================================
    private BorderLayout borderLayout; // declare layout managers
    private JPanel numPad; // key pad for input
    //create array for saved 15 number and character
    private JButton[] numPad_Button = new JButton[15];
    private JPanel leftSide; // left hand side buttons
    private JButton[] leftSide_Button = new JButton[4];
    private JPanel rightSide; // right hand side buttons
    private JButton[] rightSide_Button = new JButton[4];
    private JPanel monitor;  //the monitor of the ATM
    private JLabel AccountNum_label;
    private JTextField AccountNum_field; // account number with text
    private JLabel AccountPw_label;
    private JPasswordField AccountPw_field; // password with hidden text
    //========================Changes End===========================================================

    // no-argument ATM constructor initializes instance variables
    public ATM()
    {
        userAuthenticated = false; // user is not authenticated to start
        currentAccountNumber = 0; // no current account number to start
        screen = new Screen(); // create screen
        keypad = new Keypad(); // create keypad
        cashDispenser = new CashDispenser(); // create cash dispenser
        bankDatabase = new BankDatabase(); // create account info database

//========================Changes Start=========================================================
        // set frame layout for the whole window
        setLayout(borderLayout = new BorderLayout());

        numPad = new JPanel();
        //set the grid layout for the keypad
        numPad.setLayout( new GridLayout(4,4,2,2) );
        // use for loop to print the key pad
        for(int i = 0; i < numPad_Button.length; i++)
            numPad_Button[i] = new JButton(Integer.toString(i));
        for(int i = 7; i < 10; i++)
            numPad.add(numPad_Button[i]);
        // set the text for the button
        numPad_Button[12].setText("Cancel");
        numPad_Button[12].setBackground(Color.red);
        numPad_Button[12].setOpaque(true);
        //add the word ' Cancel' to the keypad of the JFrame
        numPad.add( numPad_Button[12] );
        //print the number of the keypad
        for(int i = 4; i < 7; i++)
            numPad.add(numPad_Button[i]);
        //add the word ' Clear' to the keypad of the JFrame
        numPad_Button[13].setText("Clear");
        numPad_Button[13].setBackground(Color.yellow);
        numPad_Button[13].setOpaque(true);
        numPad.add( numPad_Button[13] );

        //print the number of the keypad
        for(int i = 1; i < 4; i++)
            numPad.add(numPad_Button[i]);
        //add the word ' Enter' to the keypad of the JFrame
        numPad_Button[14].setText("Enter");
        numPad_Button[14].setBackground(Color.green);
        numPad_Button[14].setOpaque(true);
        numPad.add( numPad_Button[14] );

        // set the text for the button
        numPad_Button[0].setText("0");
        numPad_Button[10].setText(".");
        numPad_Button[11].setText("00");
        //add number '0' to the keypad of the JFrame
        numPad.add(numPad_Button[0]);
        //add character '.' to the keypad of the JFrame
        numPad.add(numPad_Button[10]);
        //add character '00' to the keypad of the JFrame
        numPad.add(numPad_Button[11]);

        leftSide = new JPanel();
        // set layout for left hand side button
        leftSide.setLayout( new GridLayout(4,1) ); // 4 by 1; no gaps
        rightSide = new JPanel();
        rightSide.setLayout( new GridLayout(4,1) );
        // use for loop to print the array of the left and right hand side button
        for(int i = 0; i < leftSide_Button.length && i < rightSide_Button.length; i++){
            leftSide_Button[i] = new JButton();
            // add left hand side button to the JFrame
            leftSide.add(leftSide_Button[i]);
            rightSide_Button[i] = new JButton();
            // add right hand side button to the JFrame
            rightSide.add(rightSide_Button[i]);
        }

        monitor = new JPanel();
        monitor.setLayout( null ); // monitor do not have the layout managers
        AccountNum_label = new JLabel("Account Number: ");
        // using Absolute positioning method to set the position of the GUI component
        AccountNum_label.setBounds(150,200,200,250);
        AccountNum_field = new JTextField();
        AccountNum_field.setBounds(250,316,100,20);
        AccountPw_label = new JLabel("Password: ");
        AccountPw_label.setBounds(187,225,200,250);
        AccountPw_field = new JPasswordField(10);
        AccountPw_field.setBounds(250,341,100,20);
        // add the component to the JFrame
        monitor.add(AccountNum_label);
        monitor.add(AccountNum_field);
        monitor.add(AccountPw_label);
        monitor.add(AccountPw_field);

        // add the component with the border layout
        add(leftSide, borderLayout.WEST);
        add(rightSide, borderLayout.EAST);
        add(numPad, borderLayout.SOUTH);
        add(monitor, borderLayout.CENTER);
        // register event handler for the keypad number and character
        ButtonHandler1 handler1 = new ButtonHandler1();
        for(int i = 0; i < numPad_Button.length; i++)
            numPad_Button[i].addActionListener( handler1 );
        //========================Changes End===========================================================



    } // end no-argument ATM constructor
    //========================Changes Start=========================================================
    private class ButtonHandler1 implements ActionListener{
        //process buttons events
        boolean status = true ;
        public void actionPerformed(ActionEvent event){

            for(int i = 0; i <= 9; i++){
                // return a reference to the event source , compare with the keypad button
                // set the account number field length equal to 5 and less than 5
                if( event.getSource() == numPad_Button[i] && AccountNum_field.getText().length() == 5 &&
                        String.valueOf(AccountPw_field.getPassword()).length() < 5){
                    // temp saved the user input number
                    String temp = String.valueOf(AccountPw_field.getPassword());
                    // set the user input number to the text field
                    AccountPw_field.setText(temp + Integer.toString(i));
                }
                else if( event.getSource() == numPad_Button[i] && AccountNum_field.getText().length() < 5){
                    String temp = AccountNum_field.getText();
                    AccountNum_field.setText(temp + Integer.toString(i));
                }

            }

            if( event.getSource() == numPad_Button[13] && status == true ){ // if the user press the button 'Clear'
                AccountNum_field.setText("");  // set the account number field with space
                AccountPw_field.setText(""); // set the password field with space
            }

            if( event.getSource() == numPad_Button[14] && status == true){ // if the user press the button 'Enter'
                try{
                    int accountNumber = Integer.parseInt( AccountNum_field.getText() ); // input account number
                    int pin = Integer.parseInt(String.valueOf(AccountPw_field.getPassword() )); // input PIN
                    //pass the account number and pin to the bankDatabase, check the validity of them
                    userAuthenticated = bankDatabase.authenticateUser( accountNumber, pin ); //pass the account number and pin
                    currentAccountNumber = accountNumber; // Initialize the account NO to currentAccount number
                }catch(NumberFormatException e){

                }
                if ( userAuthenticated == true){ // if the account exist in the database
                    status = false;
                    performTransactions_GUI();  // run the function performTransactions_GUI

                }
                else{
                    monitor.removeAll(); // remove anything of the screen
                    monitor.repaint(); // refresh the component on the screen
                    // add the component to the screen if the there not exist the user
                    monitor.add(AccountNum_label);
                    monitor.add(AccountNum_field);
                    monitor.add(AccountPw_label);
                    monitor.add(AccountPw_field);
                    // clear the text field
                    AccountNum_field.setText("");
                    AccountPw_field.setText("");
                    // show the error message to the screen
                    JLabel wrong = new JLabel("Account Number or Password incorrect or does not exist");
                    wrong.setBounds(100,300,400,250); // set the position of the error message
                    monitor.add(wrong); // add error message to the screen
                    monitor.revalidate();
                }
            }
            else if( event.getSource() == numPad_Button[12]) // if the user press button 'Cancel', then close the window
            {monitor.removeAll(); // remove anything of the screen
                monitor.repaint(); // refresh the component on the screen
                // add the component to the screen if the there not exist the user
                monitor.add(AccountNum_label);
                monitor.add(AccountNum_field);
                monitor.add(AccountPw_label);
                monitor.add(AccountPw_field);
                // clear the text field
                AccountNum_field.setText("");
                AccountPw_field.setText("");
                // show the error message to the screen
                JLabel logout = new JLabel("Account logged out successfully");
                logout.setBounds(200,300,400,250); // set the position of the error message
                monitor.add(logout); // add error message to the screen
                monitor.revalidate();
                status = true;
                userAuthenticated = false;
            };
        }
    }
    //========================Changes End===========================================================

    // start ATM
   /*public void run()
   {
      // welcome and authenticate user; perform transactions
      while ( true )
      {
         // loop while user is not yet authenticated
         while ( !userAuthenticated )
         {
            screen.displayMessageLine( "\nWelcome!" );
            authenticateUser(); // authenticate user
         } // end while

         performTransactions(); // user is now authenticated
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         screen.displayMessageLine( "\nThank you! Goodbye!" );
      } // end while
   } // end method run*/

    // attempts to authenticate user against database
    private void authenticateUser()
    {
        screen.displayMessage( "\nPlease enter your account number: " );
        int accountNumber = keypad.getInput(); // input account number
        screen.displayMessage( "\nEnter your PIN: " ); // prompt for PIN
        int pin = keypad.getInput(); // input PIN

        // set userAuthenticated to boolean value returned by database
        userAuthenticated =
                bankDatabase.authenticateUser( accountNumber, pin );

        // check whether authentication succeeded
        if ( userAuthenticated )
        {
            currentAccountNumber = accountNumber; // save user's account #
        } // end if
        else
            screen.displayMessageLine(
                    "Invalid account number or PIN. Please try again." );
    } // end method authenticateUser

    // display the main menu and perform transactions
   /*private void performTransactions()
   {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;

      boolean userExited = false; // user has not chosen to exit
      // loop while user has not chosen option to exit system
      while ( !userExited )
      {
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();
         // decide how to proceed based on user's menu selection
         switch ( mainMenuSelection )
         {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY:
            case WITHDRAWAL:
            case TRANSFER:
               // initialize as new object of chosen type
               currentTransaction =
                  createTransaction( mainMenuSelection );
               currentTransaction.execute(); // execute transaction
               break;
            case EXIT: // user chose to terminate session
               screen.displayMessageLine( "\nExiting the system..." );
               userExited = true; // this ATM session should end
               break;
            default: // user did not enter an integer from 1-4
               screen.displayMessageLine(
                  "\nYou did not enter a valid selection. Try again." );
               break;
         } // end switch
      } // end while
   } // end method performTransactions*/

    // display the main menu and return an input selection
   /*private int displayMainMenu()
   {
      screen.displayMessageLine( "\nMain menu:" );
      screen.displayMessageLine( "1 - View my balance" );
      screen.displayMessageLine( "2 - Withdraw cash" );
//========================Changes Starts=========================
      screen.displayMessageLine( "3 - Transfer" );
//========================Charges Ends===========================
      screen.displayMessageLine( "4 - Exit\n" );
      screen.displayMessage( "Enter a choice: " );
      return keypad.getInput(); // return user's selection
   } // end method displayMainMenu*/

    // return object of specified Transaction subclass
   /*private Transaction createTransaction( int type )
   {
      Transaction temp = null; // temporary Transaction variable

      // determine which type of Transaction to create
      switch ( type )
      {
         case BALANCE_INQUIRY: // create new BalanceInquiry transaction
            temp = new BalanceInquiry(
               currentAccountNumber, screen, bankDatabase );
            break;
         case WITHDRAWAL: // create new Withdrawal transaction
            temp = new Withdrawal( currentAccountNumber, screen,
               bankDatabase, keypad, cashDispenser );
            break;
//========================Changes Starts=========================
         case TRANSFER: // create new Transfer transaction
            temp = new Transfer( currentAccountNumber, screen,
               bankDatabase, keypad);
            break;
 //========================Charges Ends===========================
      } // end switch
      return temp; // return the newly created object
   } // end method createTransaction*/


    //========================Changes Start=========================================================
    private void performTransactions_GUI(){
        monitor.removeAll(); // remove the account.no field and password field
        monitor.repaint();
        // check whether the account is Saving or current account
        if( bankDatabase.isSavingAccountOrCurrent( currentAccountNumber ) == true ){
            JLabel AccountType = new JLabel("Saving Account");
            AccountType.setBounds(220,270,400,250); // set the position of the label
            monitor.add(AccountType); // add Label to the screen
        }
        else{
            JLabel AccountType = new JLabel("Current Account");
            AccountType.setBounds(220,270,400,250);
            monitor.add(AccountType);
        }
        //set the position of the label 'Withdrawal','View Balance' and 'Transfer'.
        JLabel withdrawal = new JLabel("Withdrawal");
        withdrawal.setBounds(10,295,200,200);
        JLabel viewBalance = new JLabel("View Balance");
        viewBalance.setBounds(440,180,200,200);
        JLabel transfer = new JLabel("Transfer");
        transfer.setBounds(470,295,200,200);
        // add the label to the monitor
        monitor.add(withdrawal);
        monitor.add(viewBalance);
        monitor.add(transfer);
        monitor.revalidate();

        ButtonHandler2_MainMenu handler2 = new ButtonHandler2_MainMenu();
        leftSide_Button[3].addActionListener( handler2 ); // event handler for withdrawal
        rightSide_Button[2].addActionListener( handler2 );// event handler for view balance
        rightSide_Button[3].addActionListener( handler2 );//event handler for transfer
    }

    private class ButtonHandler2_MainMenu implements ActionListener{
       boolean status = true;
        JTextField transferAccountNum_field = new JTextField(); // transfer account number text field
        JTextField transferAmount_field = new JTextField();	// transfer amount text field
        public void actionPerformed(ActionEvent event) {
            for(int i = 0; i < 10; i++)
                numPad_Button[i].removeActionListener( this );

            if( event.getSource() == numPad_Button[12] ) // if the user press button "Cancel"
                System.exit(0);										// close the window
            if( event.getSource() == leftSide_Button[3] && status == true){	// if the user press button "withdrawal"
                monitor.removeAll();							// remove all component of the monitor
                monitor.repaint();

                // set the new interface of the monitor, add the amount label to the screen and
                // set the position of them
                JLabel amount100_label = new JLabel("$100");
                amount100_label.setBounds(10,70,200,200);
                JLabel amount500_label = new JLabel("$500");
                amount500_label.setBounds(10,180,200,200);
                JLabel amount1000_label = new JLabel("$1000");
                amount1000_label.setBounds(10,295,200,200);
                JLabel otherAmount_label = new JLabel("Other Value");
                otherAmount_label.setBounds(450,180,200,200);
                JLabel back_label = new JLabel("Back to Main Menu");
                back_label.setBounds(415,295,200,200);
                monitor.add(amount100_label);
                monitor.add(amount500_label);
                monitor.add(amount1000_label);
                monitor.add(otherAmount_label);
                monitor.add(back_label);

                monitor.revalidate();
                // remove the original event handler of interface
                leftSide_Button[3].removeActionListener( this );
                rightSide_Button[2].removeActionListener( this );
                rightSide_Button[3].removeActionListener( this );

                // add new event handler to deal with the new button of the withdrawal interface
                ButtonHandler4_Withdrawal handler4 = new ButtonHandler4_Withdrawal();
                leftSide_Button[1].addActionListener( handler4 );
                leftSide_Button[2].addActionListener( handler4 );
                leftSide_Button[3].addActionListener( handler4 );
                rightSide_Button[2].addActionListener( handler4 );
                rightSide_Button[3].addActionListener( handler4 );
                numPad_Button[14].addActionListener( handler4 );
            }

            if( event.getSource() == rightSide_Button[2] && status == true){ // if the user press button 'View balance'
                monitor.removeAll();							// remove the original interface
                monitor.repaint();
                // create the local variables to store the value from the bank database
                double availableBalance = bankDatabase.getAvailableBalance( currentAccountNumber );
                double totalBalance = bankDatabase.getTotalBalance( currentAccountNumber );

                // show the balance of the account
                JLabel availableBalance_label = new JLabel( "Available Balance: " + Double.toString( availableBalance ) );
                JLabel totalBalance_label = new JLabel( "Total Balance: " + Double.toString( totalBalance ) );
                // set the position of the label
                availableBalance_label.setBounds(200,200,400,250);
                totalBalance_label.setBounds(210,250,400,250);
                // add the label to the screen
                monitor.add( availableBalance_label );
                monitor.add( totalBalance_label );

                JLabel back_label = new JLabel("Back to Main Menu");
                back_label.setBounds(10,295,200,200);
                monitor.add(back_label);
                monitor.revalidate();
                // remove the original action listener
                leftSide_Button[3].removeActionListener( this );
                rightSide_Button[2].removeActionListener( this );
                rightSide_Button[3].removeActionListener( this );

                ButtonHandler3_BalanceInquiry handler3 = new ButtonHandler3_BalanceInquiry();
                leftSide_Button[3].addActionListener(handler3);
            }


            if( event.getSource() == rightSide_Button[3] && status == true){ // if the user press button 'Transfer'
                leftSide_Button[3].removeActionListener( this );
                rightSide_Button[2].removeActionListener( this );
                rightSide_Button[3].removeActionListener( this );
                monitor.removeAll();  // remove all the component of the monitor
                monitor.repaint();
                monitor.revalidate();

                //create and set the label of the transfer account and amount
                JLabel transferAccountNum_label = new JLabel("Transfer Account Number: ");
                transferAccountNum_label.setBounds(128,200,200,250);
                JLabel transferAmount_label = new JLabel("Transfer Amount: ");
                transferAmount_label.setBounds(180,250,200,250);
                transferAccountNum_field.setBounds(280,317,100,20);
                transferAmount_field.setBounds(280,367,100,20);

                JLabel back_label = new JLabel("Back to Main Menu");
                back_label.setBounds(10,295,200,200);
                monitor.add(back_label);
                monitor.revalidate();

                // add the label and field to the screen
                monitor.add(transferAccountNum_label);
                monitor.add( transferAccountNum_field);
                monitor.add(transferAmount_label);
                monitor.add(transferAmount_field);

                monitor.revalidate();
                // create a button handler to deal with the button of transfer functions
                ButtonHandler5_Transfer buttonHandler5_Transfer = new ButtonHandler5_Transfer();
                for(int i = 0; i <= 11 ; i++)
                    numPad_Button[i].addActionListener(buttonHandler5_Transfer);
                numPad_Button[12].addActionListener(buttonHandler5_Transfer);
                numPad_Button[13].addActionListener(buttonHandler5_Transfer);
                numPad_Button[14].addActionListener(buttonHandler5_Transfer);
                leftSide_Button[3].addActionListener(buttonHandler5_Transfer);
                rightSide_Button[2].addActionListener(buttonHandler5_Transfer);
                rightSide_Button[3].addActionListener(buttonHandler5_Transfer);
            }
        }
        
        //ButtonHandler5_Transfer is for transfer function
        private class ButtonHandler5_Transfer implements ActionListener{
        	String temp1;
        	String temp2;
        	//status for the numPad_button is being used while using this function
            boolean status = true;
            public void actionPerformed(ActionEvent event){
                for(int i = 0; i <10 ; i++) {
                	// Maximum of Transfer Account Numbers is 5 
                    if( event.getSource() == numPad_Button[i] && transferAccountNum_field.getText().length() < 5 ){
                        //keypad of number
                        temp1 = transferAccountNum_field.getText();
                        // create temp to stored the content of account number text field
                        transferAccountNum_field.setText( temp1 + Integer.toString(i));
                        // the new content combine with old content ,integer will change to string to stored
                    }
                    //for the transfer Amount 
                    else if( event.getSource() == numPad_Button[i] && transferAccountNum_field.getText().length() == 5 &&
                            transferAmount_field.getText().length() < 5){
                        temp2 = transferAmount_field.getText();
                        transferAmount_field.setText( temp2 + Integer.toString(i));

                    }
                }
                 // NumPad_Button for typing "00"
                if(event.getSource() == numPad_Button[11]) {
                    String temp = transferAmount_field.getText();
                    transferAmount_field.setText( temp + "00");
                }
                // NumPad_Button for typing "."
                if(event.getSource() == numPad_Button[10]) {
                    String temp = transferAmount_field.getText();
                    transferAmount_field.setText(temp + ".");
                }
                // LeftSide_Button to back to Main
                if( event.getSource() == leftSide_Button[3] ){
                    leftSide_Button[1].removeActionListener( this );
                    leftSide_Button[2].removeActionListener( this );
                    leftSide_Button[3].removeActionListener( this );
                    rightSide_Button[2].removeActionListener( this );
                    rightSide_Button[3].removeActionListener( this );
                    //remove button which is needed in the transfer function
                    status = false;//change to false as leave the transfer function
                    performTransactions_GUI();// back to Main

                }
                // NumPad_Button for clearing the field
                if( event.getSource() == numPad_Button[13] && status == true){//execute "clear" key if user clicked
                    transferAccountNum_field.setText("");// clear text filed of transfer account number
                    transferAmount_field.setText("");// clear text filed of transfer amount

                }
                
                

                // execute the action, if the user click the "Enter" key
                if( event.getSource() == numPad_Button[14] ){
                    monitor.removeAll(); //clear the screen
                    monitor.repaint();   // repaint the display below
                    // set label object
                    JLabel transferAccountNum_label = new JLabel("Transfer Account Number: ");
                    JLabel confirmtransferAccountNum = new JLabel(transferAccountNum_field.getText());
                    JLabel transferAmount_label = new JLabel("Transfer Amount: ");
                    JLabel confirmtransferAmount = new JLabel(transferAmount_field.getText());
                    JLabel confirmmsg = new JLabel("Confirm");
                    JLabel cancelmsg = new JLabel("Cancel");
                    //set label object size
                    transferAccountNum_label.setBounds(128,200,200,250);
                    confirmtransferAccountNum.setBounds(280,317,100,20);
                    transferAmount_label.setBounds(180,250,200,250);
                    confirmtransferAmount.setBounds(280,367,100,20);
                    confirmmsg.setBounds(480,295,200,200);
                    cancelmsg.setBounds(480,180,200,200);
                    // print label object
                    monitor.add(transferAccountNum_label);
                    monitor.add(confirmtransferAccountNum);
                    monitor.add(confirmtransferAmount);
                    monitor.add(transferAmount_label);
                    monitor.add(confirmmsg);
                    monitor.add(cancelmsg);
                    monitor.revalidate();

                }
                // numPad_Button for confirming the input of the user in transaction.
                if( event.getSource() == rightSide_Button[3] ){
                      //create object "transfer"
                    Transfer transfer = new Transfer( currentAccountNumber, screen, bankDatabase, keypad );
                   // create transferAccountNum to stored transfer account number
                    int transferAccountNum = Integer.parseInt( transferAccountNum_field.getText() );
                    transferAccountNum_field.setText("");
                   // call method from class "transfer" to set transfer account number
                    transfer.setTransferAccountNumber(transferAccountNum);
                   // create transferAmount to stored transfer amount
                    double transferAmount = Double.parseDouble( transferAmount_field.getText() );
                    transferAmount_field.setText("");
                    // call method from class "transfer" to set transfer amount
                    transfer.setTransferAmount(transferAmount);
                    //use if-statement to check the user input is valid
                    //account number is exist and not the logged-in account
                    //transfer amount must greater than 0 and the amount of logged-in account
                    if( transfer.isTransferAccountExist() == true && (transfer.getTransferAccountNumber() != currentAccountNumber) &&
                            transfer.getTransferAmount() > 0 &&
                            transfer.getTransferAmount() <= bankDatabase.getAvailableBalance(currentAccountNumber)){
                         // Start transaction
                    	 //transfer amount to transfer account
                        bankDatabase.transferCredit( transfer.getTransferAccountNumber(), transfer.getTransferAmount());
                         // minus amount of logged-in account
                        bankDatabase.debit( currentAccountNumber, transfer.getTransferAmount());
                        //clear the screen
                        monitor.removeAll();
                        monitor.repaint();
                        status = false; // change to false while leaving transfer function 
                        //create the "Transfer Successful" message
                        JLabel transferSuccessful_label = new JLabel("Transfer Successful");
                        //create the "back_label" indicator
                        JLabel back_label = new JLabel("Back to Main Menu");
                        transferSuccessful_label.setBounds(200,250,200,250); //define place of the message
                        back_label.setBounds(10,295,200,200);//define place of the indicator
                        monitor.add(transferSuccessful_label);// print the message
                        monitor.add(back_label);// print the back to main button message
                        monitor.revalidate();
                    }
                    else{//if check there are something invalid.
                        monitor.removeAll();// clear the monitor
                        monitor.repaint();
                        // transfer account doesn't exist
                        if(transfer.isTransferAccountExist() == false){
                            monitor.removeAll();
                            monitor.repaint();
                            JLabel promptmsg = new JLabel ("Do not have this Transfer Account");
                            promptmsg.setBounds(150,200,300,250);
                            monitor.add(promptmsg);
                        }
                        // transfer account is the logged-in account
                        if(transfer.getTransferAccountNumber() == currentAccountNumber) {
                            monitor.removeAll();
                            monitor.repaint();
                            JLabel promptmsg = new JLabel ("Sorry, You cannot transfer to your own account.");
                            promptmsg.setBounds(150,200,300,250);
                            monitor.add(promptmsg);
                        }
                        // transfer amount is not greater than 0
                         if(transfer.getTransferAmount() < 0) {
                            monitor.removeAll();
                            monitor.repaint();
                            JLabel promptmsg = new JLabel ("The minimum transfer amount is $0.01");
                            promptmsg.setBounds(150,200,300,250);
                            monitor.add(promptmsg);
                        }
                         // transfer amount is greater than the logged-in account has.
                        if(transfer.getTransferAmount() >= bankDatabase.getAvailableBalance(currentAccountNumber)) {
                            monitor.removeAll();
                            monitor.repaint();
                            JLabel promptmsg = new JLabel ("\nInsufficient balance to transfer");
                            promptmsg.setBounds(150,200,300,250);
                            monitor.add(promptmsg);
                        }

                        // if the user input is invalid
                        //create the "Transfer Failure" message
                        JLabel transferFailed = new JLabel("Transfer Failed");
                        JLabel back_label = new JLabel("Back to Main Menu");
                        transferFailed.setBounds(200,250,200,250);//define place of the message
                        back_label.setBounds(10,295,200,200);
                        monitor.add(transferFailed);// print the message
                        monitor.add(back_label);// print the back to main button message
                        monitor.revalidate();
                        //removing the button function when leaving transfer part
                        rightSide_Button[2].removeActionListener( this );
                        rightSide_Button[3].removeActionListener( this );
                    }
                    
                }else if(event.getSource() == rightSide_Button[2]) {
                    monitor.removeAll();
                    monitor.repaint();
                    // if the user input is invalid
                    JLabel transferFailed = new JLabel("Transfer Cancelled");
                    //create the "Transfer Failure" message
                    transferFailed.setBounds(200,250,200,250);//define place of the message
                    monitor.add(transferFailed);// print the message
                    monitor.revalidate();
                    rightSide_Button[2].removeActionListener( this );
                    rightSide_Button[3].removeActionListener( this );
                    JLabel back_label = new JLabel("Back to Main Menu");
                    back_label.setBounds(10,295,200,200);
                    monitor.add(back_label);// print the back to main button message
                }



            }
			private void ATM() {
				// TODO Auto-generated method stub
				
			}
        }
        private class ButtonHandler3_BalanceInquiry implements ActionListener{
            //use to "View Balance"function of GUI
            public void actionPerformed(ActionEvent event){
                if( event.getSource() == leftSide_Button[3] ){
                    // execute the "View Balance"function, if user click the "View Balance" Button
                    leftSide_Button[3].removeActionListener( this );
                    //call the method update the screen
                    performTransactions_GUI();
                }
            }
        }
        private class ButtonHandler4_Withdrawal implements ActionListener{
            //use to "Withdrawal"function of GUI
            Withdrawal withdrawal = new Withdrawal( currentAccountNumber, screen,
                    bankDatabase, keypad, cashDispenser );// create object "withdrawal"
            boolean cashDispensed = false;  // use to check whether the cash is dispensed by user
            double availableBalance = bankDatabase.getAvailableBalance( currentAccountNumber );
            //call method to find available balance of log in account and store in availableBalance
            int amount = 0;//store the amount of money

            private JLabel amountLabel = new JLabel("Amount: ");//create message to prompt user
            private JTextField amountField = new JTextField(10);//create text field to allow user to input data
            boolean status = true ;
            public void actionPerformed(ActionEvent event){
                // use to execute the "Withdrawal" function
                monitor.removeAll();
                monitor.repaint();

                if( event.getSource() == rightSide_Button[3] && status == true){
                    leftSide_Button[1].removeActionListener( this );
                    leftSide_Button[2].removeActionListener( this );
                    leftSide_Button[3].removeActionListener( this );
                    rightSide_Button[2].removeActionListener( this );
                    rightSide_Button[3].removeActionListener( this );
                    //remove button which is needed in the withdrawal function
                    status = false;
                    performTransactions_GUI();
                }

                if( event.getSource() == leftSide_Button[1] && status == true ){
                    // the user select 100 cash
                    amount = 100;
                    check();// call the method to process checking
                    JLabel leave = new JLabel("Exit after 3 second");
                    // create the message to prompt the user that withdrawal is end
                    leave.setBounds(200,300,100,20);// define the place of message
                    monitor.add(leave);// print the message on the screen
                    monitor.revalidate(); // refresh the screen
                    

                }
                if( event.getSource() == leftSide_Button[2] && status == true ){
                    // the user select 500 cash
                    amount = 500;
                    check();
                    JLabel leave = new JLabel("Exit after 3 second");
                    leave.setBounds(200,300,100,20);
                    monitor.add(leave);
                    monitor.revalidate();
                    
                }
                if( event.getSource() == leftSide_Button[3] && status == true ){
                    // the user select 1000 cash
                    amount = 1000;
                    check();
                    JLabel leave = new JLabel("Exit after 3 second");
                    leave.setBounds(200,300,100,20);
                    monitor.add(leave);
                    monitor.revalidate();
                    
                }

                if( event.getSource() == rightSide_Button[2] && status == true){
                    // the user select "other amount" button
                    leftSide_Button[1].removeActionListener( this );
                    leftSide_Button[2].removeActionListener( this );
                    leftSide_Button[3].removeActionListener( this );
                    rightSide_Button[2].removeActionListener( this );
                    rightSide_Button[3].removeActionListener( this );
                    //removing no need button
                    status = false;
                    amountLabel.setBounds(150,200,200,250);
                    amountField.setBounds(250,316,100,20);
                    monitor.add(amountLabel);
                    monitor.add(amountField);
                    ButtonHandler4_Withdrawal2 buttonHandler4_Withdrawal2 = new ButtonHandler4_Withdrawal2();
                    for(int i = 0 ; i <= 9; i++)
                        numPad_Button[i].addActionListener( buttonHandler4_Withdrawal2 );
                    numPad_Button[11].addActionListener(buttonHandler4_Withdrawal2);
                    numPad_Button[13].addActionListener( buttonHandler4_Withdrawal2 );
                    numPad_Button[14].addActionListener( buttonHandler4_Withdrawal2 );

                }

            }
             private class ButtonHandler4_Withdrawal2 implements ActionListener{
                boolean status = true;
                public void actionPerformed(ActionEvent event) {
                    for(int i = 0 ; i <= 9; i++){
                        if( event.getSource() == numPad_Button[i]){//stored input data in text field
                            String temp = amountField.getText();
                            amountField.setText( temp + Integer.toString(i));
                        }

                    }
                    if( event.getSource() == numPad_Button[11] && status == true) {
                        String temp = amountField.getText();
                        amountField.setText(temp + "00");

                    }
                    if( event.getSource() == numPad_Button[14] && status == true){
                        //other value
                        int temp = Integer.parseInt( amountField.getText() );
                        if(temp % 100 == 0){// the amount is divisible by 100
                            amount = temp;
                            check();// call method to process checking
                            JLabel leave = new JLabel("Exit after 3 seconds");
                            // create message to prompt user withdrawal is finish
                            leave.setBounds(200,300,100,20);
                            monitor.add(leave);
                            monitor.revalidate();
                        }
                        else{
                            // the amount can not divide by 100
                            // the program will not run check{}
                            JLabel leave = new JLabel("Exit after 3 seconds");
                            leave.setBounds(200,300,100,20);
                            monitor.add(leave);
                            monitor.revalidate();
                        }

                    }
                    if( event.getSource() == numPad_Button[13] && status == true){//"clear" key
                        amountField.setText( "" );//clear the text field
                    }
                }
            }

            private void check(){
                if ( amount <= availableBalance ){// check whether the logged-in account have enough money
                    if ( cashDispenser.isSufficientCashAvailable( amount ) )// check whether ATM have enough money
                    {
                        // update the account involved to reflect withdrawal
                        bankDatabase.debit( currentAccountNumber, amount );

                        cashDispenser.dispenseCash( amount ); // dispense cash
                        cashDispensed = true; // cash was dispensed

                        JLabel takeCash_label = new JLabel("Please take your cash now");
                        // create the message to prompt the user taking money
                        takeCash_label.setBounds(190,250,400,250);  // define the place of message
                        monitor.add(takeCash_label); // print the message on the screen
                    }
                    else{//Only ATM not have enough money
                        JLabel insufficientCashAvailable = new JLabel("It has not sufficient cash. Sorry");
                        // create the message to prompt the user that ATM not have enough money
                        insufficientCashAvailable.setBounds(100,300,400,250);// define the place of message
                        monitor.add(insufficientCashAvailable);// print the message on the screen
                    }
                }
                else{//the log in account not have enough money
                    JLabel insufficientBalance = new JLabel("You have not sufficient balance. Sorry");
                    // create the message to prompt the user that log in account not have enough money
                    insufficientBalance.setBounds(170,250,400,250);
                    monitor.add(insufficientBalance);
                }
            }

        }
    }
    //========================Changes End===========================================================
} // end class ATM





/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
