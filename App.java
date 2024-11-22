import java.util.Scanner;
import java.sql.*;

class Account {
    private int account_no;
    private String name;
    private int balance;
    
    public int getAccount_no() {
        return account_no;
    }

    public void setAccount_no(int account_no) {
        this.account_no = account_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account [account_no=" + account_no + ", name=" + name + ", balance=" + balance + "]";
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Account(String name) {
        this.name = name;
    }
}

public class App {

    public static void createAccount(String name) {
        Account a = new Account(name);
        String url = "jdbc:mysql://localhost:3306/db";
        String query = "Insert into account (name) values ('" + a.getName() + "')";
        try {
            Connection con = DriverManager.getConnection(url, "root", "krati12@");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Account created for " + a.getName());
        } catch (Exception e) {
        	System.out.println("Server Issue");
            System.out.println(e);
        }
    }
    
    public static void withDrawl(int an, int amount) {
        String url = "jdbc:mysql://localhost:3306/db";
        String query = "Update account set balance = balance - " + amount + " where account_no = " + an + ";";
        try {
            Connection con = DriverManager.getConnection(url, "root", "kratti12@");
            Statement st = con.createStatement();
            int affect = st.executeUpdate(query);
            
            if (affect == 0) {
                System.out.println("Error: Account number " + an + " not found.");
            } else {
                System.out.println("WithDrawl Successful");
            }
        } catch (Exception e) {
        	System.out.println("WithDrawl Failed");
            System.out.println(e);
        }
    }
    
    public static void deposite(int an, int amount) {
        String url = "jdbc:mysql://localhost:3306/db";
        String query = "Update account set balance = balance + " + amount + " where account_no = " + an + ";";
        try {
            Connection con = DriverManager.getConnection(url, "root", "krati12@");
            Statement st = con.createStatement();
            int affect = st.executeUpdate(query);
            
            if (affect == 0) {
                System.out.println("Error: Account number " + an + " not found.");
            } else {
                System.out.println("Deposite Successful");
            }
        } catch (Exception e) {
        	System.out.println("Deposite Failed");
            System.out.println(e);
        }
    }
    
    public static void balance(int an) {
        String url = "jdbc:mysql://localhost:3306/db";
        String query = "Select * from account where account_no = " + an;
        try {
            Connection con = DriverManager.getConnection(url, "root", "kratti12@");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            if(rs.next()) {
            	System.out.println(rs.getString(2) + " Balance : " + rs.getInt(3));
            }
        } catch (Exception e) {
        	System.out.println("Server Failed");
            System.out.println(e);
        }
    }
    
    public static void transaction(int an , int r_an , int amount) {
        String url = "jdbc:mysql://localhost:3306/db";
        String query1 = "Update account set balance = balance - " + amount + " where account_no = " + an + ";";
        String query2 = "Update account set balance = balance + " + amount + " where account_no = " + r_an + ";";
        try {
            Connection con = DriverManager.getConnection(url, "root", "krati12@");
            Statement st = con.createStatement();
            int affected1 = st.executeUpdate(query1);
            int affected2 = st.executeUpdate(query2);
            
            if (affected1 == 0 || affected2 ==0 ) {
                System.out.println("Error: Account number " + an + " not found.");
            } else {
                System.out.println("Transaction Successful");
            }
        } catch (Exception e) {
        	System.out.println("Deposite Failed");
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("For Create Account press : c");
        System.out.println("For WithDrawl press : w");
        System.out.println("For Desposi press : d");
        System.out.println("For Transaction press : t");
        System.out.println("To Check Balance press : b");

        // Read a single character input
        String task = sc.nextLine();
        

        switch (task) {
            case "c": {
                System.out.print("Enter Account Holder name: ");
                String name = sc.nextLine(); 
                createAccount(name);
                break; 
            }
            case "w":{
            	System.out.print("Enter Account number: ");
            	int an = sc.nextInt();
            	System.out.print("Enter Amount: ");
            	int amount = sc.nextInt();
            	withDrawl(an , amount);
            	break;
            }
            case "d":{
            	System.out.print("Enter Account number: ");
            	int an = sc.nextInt();
            	System.out.print("Enter Amount: ");
            	int amount = sc.nextInt();
            	deposite(an , amount);
            	break;
            }
            case "b":{
            	System.out.print("Enter Account number: ");
            	int an = sc.nextInt();
            	balance(an);
            	break;
            }
            case "t":{
            	System.out.print("Enter your Account_no : ");
            	int an = sc.nextInt();
            	System.out.print("Enter reciever's Account_no : ");
            	int r_an = sc.nextInt();
            	System.out.print("Enter Amount : ");
            	int amount = sc.nextInt();
            	transaction(an , r_an , amount);
            	break;
            }
            default:
                System.out.println("I am default, if something wrong");
        }
        sc.close();
    }
    
}
