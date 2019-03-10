Starting Up
Run ATM.main()


Shutting down the system
To shut down the system, log in as the Bank Manager, and select option 7. All data will be saved.


Logging in to Bank Manager
In order to identify yourself as the bank manager, login with the following username and password:
* Username: manager
* Password: password




Enter a number from the list of options listed in the interface to perform tasks that a bank manager is able to perform.


Option 1: Create User
Select this option to create a new user. As the bank manager, you will set the username and password for the new user. One of each type of account (chequing, credit card, line of credit and savings) will automatically be created when a new user is created.


Option 2: Create Account
Select this option to create a new user. You will have to enter the username of the user and select the type of account (chequing, credit card, line of credit or savings) you would want to create for them.


Option 3: Check Alerts
Select this option to see the alerts.txt file which contains alerts every time the amount of any denomination in the ATM goes below 20.


Option 4: Restock Machine
Select this option to restock the number of bills in the ATM. Once you have selected an option of which bill you want to restock, the ATM will now have 100 of those bills in the machine.


Option 5: Undo Transaction 
Select this undo the most recent transaction from a certain user’s account. You would need their username and their account number.


Option 6: Logout
Select this option to logout of the Bank Manager account and go back to the Login interface.


Option 7: Turn Off System
The ATM will shut down if you select this option. To reboot the system, simply run ATM.main() again. The date will increment by one day when you reboot.




Logging into User
Once a bank manager has created a new user, login with the username and password that the bank manager has set to view the user menu.


Enter a number from the list of options listed in the interface to perform tasks that a user is able to perform.


Option 1: Deposit
Select this option to deposit cash or cheque into your primary chequing account.


Option 2: Withdraw
Select this option to withdraw money from the ATM. You will have to specify the account number in which you would like to withdraw money from.


Note that you can only withdraw whole numbers that are multiples of 5 since the ATM only holds $5, $10, $20 and $50 bills.


Option 3: Transfer In
Select this option to transfer in money between your accounts. Note that you cannot transfer out money from a credit card account.


Option 4: Transfer Out
Select this option to transfer out money between your accounts. Note that you cannot transfer out money from a credit card account.


Option 5: Pay Bills
Select this option to pay a bill. You will have to specify the account number to pay from and the name of the payee. Note that you cannot pay a bill from a credit card account.


Option 6: Request Account Creation
Select this option to request the creation of a new account to the bank manager. You will have to specify the type of account you want to create.


Option 7: View Summary of Accounts
Select this option to view a summary of all your accounts. You will be able to see:
* The types of accounts you have
* The account numbers of all the accounts you have
* The balance of each account
* The last transaction performed in each account
* The date in which each account was created
* The net total of all your account balances


Option 8: Change Password
Select this option to change the password of your user account.


Option 9: Logout
Select this option to logout of your user account to go back to the login menu.


Explanation of Text Files


bankmanager.txt
When the system first starts up, BankManager constructor reads from this file to initialise acct_counter which gives accounts unique account numbers.

deposits.txt
Stores the deposits made to the accounts, each line being of the format: “AccountNumber,DepositAmount”

alerts.txt
Writes to this file when the ATM is low on a certain bill. Each line is of the format:
“Alert {Bill number}$ bills are low”

outgoing.txt
Stores Bills paid to outgoing accounts/places. Each line is of the format:
“{Amount} paid to {Name}”
