// BankDatabase.java
// Represents the bank account information database 

public class BankDatabase
{
   private Account accounts[]; // array of Accounts
   
   //=========================changes starts=========================//
   private SavingAccount saving[];//array for saving account
   private CurrentAccount current[];//array for current account
   
   // no-argument BankDatabase constructor initializes accounts
   public BankDatabase()
   {   
      saving = new SavingAccount[ 3 ];//three saving acount sample
      saving[ 0 ] = new SavingAccount( 12345, 54321, 1000.0, 1200.0);
      saving[ 1 ] = new SavingAccount( 98765, 56789, 200.0, 200.0);
      saving[ 2 ] = new SavingAccount( 13579, 97531, 500.0, 600.0);
      current = new CurrentAccount[ 3 ];//three current acount sample
      current[ 0 ] = new CurrentAccount( 54321, 12345, 1000.0, 1200.0);
      current[ 0 ] = new CurrentAccount( 56789, 98765, 200.0, 200.0);
      current[ 0 ] = new CurrentAccount( 24680, 86420, 500.0, 600.0);
      accounts = new Account[saving.length+current.length];
      //put saving account and current account into the account array
      for(int i = 0, j = 0;i<saving.length+current.length;i++){
         if(i<saving.length){
             accounts[i]=saving[i]; 
        }else{
             accounts[i]=current[j++];
        }
      }
    }

   //=========================changes ends=========================//

   private Account getAccount( int accountNumber )
   {
      // loop through accounts searching for matching account number
      for ( Account currentAccount : accounts )
      {
         // return current account if match found
         if ( currentAccount.getAccountNumber() == accountNumber )
            return currentAccount;
      } // end for

      return null; // if no matching account was found, return null
   } // end method getAccount

   // determine whether user-specified account number and PIN match
   // those of an account in the database
   public boolean authenticateUser( int userAccountNumber, int userPIN )
   {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccount( userAccountNumber );

      // if account exists, return result of Account method validatePIN
      if ( userAccount != null )
         return userAccount.validatePIN( userPIN );
      else
         return false; // account number not found, so return false
   } // end method authenticateUser

   // return available balance of Account with specified account number
   public double getAvailableBalance( int userAccountNumber )
   {
      return getAccount( userAccountNumber ).getAvailableBalance();
   } // end method getAvailableBalance

   // return total balance of Account with specified account number
   public double getTotalBalance( int userAccountNumber )
   {
      return getAccount( userAccountNumber ).getTotalBalance();
   } // end method getTotalBalance

   // credit an amount to Account with specified account number
   public void credit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).credit( amount );
   } // end method credit

   // debit an amount from of Account with specified account number
   public void debit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).debit( amount );
   } // end method debit
} // end class BankDatabase



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
