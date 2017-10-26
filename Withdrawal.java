// Withdrawal.java
// Represents a withdrawal ATM transaction
public class Withdrawal extends Transaction
{
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser
   // ---------------------------modified------------------------------v
   private final static int CANCELED = 5;
   private final static int invalidvalue = 4;
   //---------------------------modified-------------------------------^
   // Withdrawal constructor
   public Withdrawal( int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad, CashDispenser atmCashDispenser )
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase );
      // initialize references to keypad and cash dispenser
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   } // end Withdrawal constructor

   // perform transaction
   public void execute()
   {
      boolean cashDispensed = false; // cash was not dispensed yet
      double availableBalance; // amount available for withdrawal

      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();

      // loop until cash is dispensed or the user cancels
      do
      {
         // obtain a chosen withdrawal amount from the user
         amount = displayMenuOfAmounts();

         // check whether user chose a withdrawal amount or canceled
         if ( amount != CANCELED )
         {// get available balance of account involved
            availableBalance =
                    bankDatabase.getAvailableBalance( getAccountNumber() );
// ---------------------------modified------------------------------v
            if ( amount != CANCELED  && amount != invalidvalue)
//---------------------------modified-------------------------------^
               // check whether the user has enough money in the account
               if ( amount <= availableBalance )
               {
                  // check whether the cash dispenser has enough money
                  if ( cashDispenser.isSufficientCashAvailable( amount ) )
                  {
                     // update the account involved to reflect withdrawal
                     bankDatabase.debit( getAccountNumber(), amount );

                     cashDispenser.dispenseCash( amount ); // dispense cash
                     cashDispensed = true; // cash was dispensed

                     // instruct user to take cash
                     screen.displayMessageLine(
                             "\nPlease take your cash now." );
                  } // end if
                  else // cash dispenser does not have enough cash
                     screen.displayMessageLine(
                             "\nInsufficient cash available in the ATM." +
                                     "\n\nPlease choose a smaller amount." );
               } // end if
               else // not enough money available in user's account
               {
                  screen.displayMessageLine(
                          "\nInsufficient funds in your account." +
                                  "\n\nPlease choose a smaller amount." );
               } // end else
         } // end if
// ---------------------------modified------------------------------v
         else if(amount == CANCELED)// User input cancel
         {
            screen.displayMessageLine( "\nTransaction Canceled" );
            return; // Return to main menu
         } // end else
         else if(amount == invalidvalue)
         {
            screen.displayMessageLine( "\nInvalidated Amount" );
            screen.displayMessageLine( "Withdrawal failed" );
            return;
         }
         else //User chose cancel option
         {
            screen.displayMessageLine( "\nTransaction Canceled" );
            return; // return to main menu because user canceled
         } // end else
//---------------------------modified-------------------------------^
      } while ( !cashDispensed );

   } // end method execute

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts()
   {
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      // array of amounts to correspond to menu numbers
//---------------------------modified------------------------------v
      int amounts[] = { 0,100,500,1000};

      // loop while no valid choice has been made
      while ( userChoice == 0 )
      {
         // display the menu
         screen.displayMessageLine( "\nWithdrawal Menu:" );
         screen.displayMessageLine( "1 - HKD$100" );
         screen.displayMessageLine( "2 - HKD$500" );
         screen.displayMessageLine( "3 - HKD$1000" );
         screen.displayMessageLine( "4 - Other Value" );
         screen.displayMessageLine( "5 - Cancel" );
         screen.displayMessage( "\nChoose a withdrawal amount: " );

//---------------------------modified------------------------------^
         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch ( input )
         {
            case 1: // if the user chose a withdrawal amount
            case 2: // (i.e., chose option 1, 2, 3, 4 or 5), return the
//---------------------------modified------------------------------v
            case 3: // corresponding amount from amounts array
               userChoice = amounts[ input ]; //Accept user input
               break;
            // displaly the choice
            case 4:screen.displayMessageLine("Accept notes denominations of HKD100, HKD500 and HKD1,000");
               screen.displayMessage("Amount: ");
               double otherValue = keypad.getDoubleInput();
               if(otherValue - (int)otherValue != 0)
                  userChoice = invalidvalue;
               else if( otherValue % 100 == 0) //check the amount can be withdrawal
                  userChoice = (int)otherValue;
               else
                  userChoice = invalidvalue;
               break;
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;
            default: //Nonvalid value
               screen.displayMessageLine(
                       "\nIvalid selection. Try again." );
//---------------------------modified------------------------------^
         } // end switch
      } // end while

      return userChoice; // return withdrawal amount or CANCELED
   } // end method displayMenuOfAmounts
} // end class Withdrawal



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
