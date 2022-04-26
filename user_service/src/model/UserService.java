package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserService {
	 //A common method to connect to the DB
		private Connection connect()
		 {
		 Connection con = null;
		 try
		 {
		 Class.forName("com.mysql.jdbc.Driver");

		 //Provide the correct details: DBServer/DBName, username, password
		 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", "sokian@981119");
		 }
		 catch (Exception e)
		 {e.printStackTrace();}
		 return con;
		 }
		
		public String insertUser(String card_type, String Name, String cardNo, String exp_date, String cvc, String monthly_amount)
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for inserting."; }
		 // create a prepared statement
		 String query = " insert into payment(`payment_id`,`card_type`,`Name`,`cardNo`,`exp_date`,`cvc`,`monthly_amount`)"
		 + " values (?, ?, ?, ?, ?, ?, ?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, card_type);
		 preparedStmt.setString(3, Name);
		 preparedStmt.setString(4, cardNo);
		 preparedStmt.setString(5, exp_date);
		 preparedStmt.setString(6, cvc);
		 preparedStmt.setString(7, monthly_amount);
		 // execute the statement
		 
		 preparedStmt.execute();
		 con.close();
		 output = "Inserted successfully";
		 }
		 catch (Exception e)
		 {
		 output = "Error while inserting the item.";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }
		
		public String readUserDetails()
		
		{
			 String output = "";
			try
			 {
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database for reading.";
			 }
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>card_type</th>"
			 +"<th>Name</th><th>cardNo</th>"
			 + "<th>exp_date</th>"
			 + "<th>cvc</th>"
			 + "<th>monthly_amount</th>";
			 String query = "select * from user";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 // iterate through the rows in the result set
			 while (rs.next())
			 {
				 String payment_id = Integer.toString(rs.getInt("payment_id"));
				 String card_type = rs.getString("card_type");
				 String Name = rs.getString("Name");
				 String cardNo = rs.getString("cardNo");
				 String exp_date = rs.getString("exp_date");
				 String cvc = rs.getString("cvc");
				 String monthly_amount = rs.getString("monthly_amount");
				 // Add a row into the html table
				 output += "<tr><td>" + card_type + "</td>";
				 output += "<td>" + Name + "</td>";
				 output += "<td>" + cardNo + "</td>";
				 output += "<td>" + exp_date + "</td>"; 
				 output += "<td>" + cvc + "</td>";
				 output += "<td>" + monthly_amount + "</td>";
				 // buttons
				 output += "<input name='user_id' type='hidden' "
				 + " value='" + payment_id + "'>"
				 + "</form></td></tr>";
			 }
			 con.close();
			 // Complete the html table
			 output += "</table>";
			 }
			catch (Exception e)
			 {
				 output = "Error while reading the user details"
				 		+ ".";
				 System.err.println(e.getMessage());
			 }
			return output;
		}
		
		public String EditUserDetails(String payment_id,String card_type,String Name,String cardNo, String exp_date, String cvc, String monthly_amount)
		   {
			   String output = "";
			   try
				   {
				   Connection con = connect();
				   if (con == null)
				   {
					   return "Error while connecting to the database for updating"; 
				   }
				   // create a prepared statement
				   String query = "UPDATE payment SET card_type=?,Name=?,cardNo=?,exp_date=?,cvc=?,monthly_amount=?WHERE payment_id=?";
				   PreparedStatement preparedStmt = con.prepareStatement(query);
				   // binding values
				   preparedStmt.setString(1, card_type);
				   preparedStmt.setString(2, Name);
				   preparedStmt.setString(3, cardNo);
				   preparedStmt.setString(4, exp_date);
				   preparedStmt.setString(5, cvc);
				   preparedStmt.setString(6, monthly_amount);
				   preparedStmt.setInt(7, Integer.parseInt(payment_id));
				   // execute the statement
				   preparedStmt.execute();
				   con.close();
				   output = "Updated successfully";
				   }
			    catch (Exception e)
				{
				   output = "Error while updating the user";
				   System.err.println(e.getMessage());
				}
			    return output;
			    }
		
		public String deleteUser(String payment_id)
		{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{
		return "Error while connecting to the database for deleting.";
		}

		// create a prepared statement
		String query = "delete from payment where payment_id=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);

		// binding values
		preparedStmt.setInt(1, Integer.parseInt(payment_id));

		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "payment Deleted successfully";
		}
		catch (Exception e)
		{
		output = e.toString();
		//System.err.println(e.getMessage());
		}
		return output;
		}
public String fetchUser(String payment_id)
		
		{
			 String output = "";
			try
			 {
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database for reading";
			 }
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>card_type</th>"
			 +"<th>Name</th><th>cardNo</th>"
			 + "<th>exp_date</th>"
			 + "<th>cvc</th>"
			 + "<th>monthly_amount</th></tr>";
			 String query = "select * from payment where payment_id='"+payment_id+"'";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 // iterate through the rows in the result set
			 while (rs.next())
			 { 
				 String paymentid = Integer.toString(rs.getInt("payment_id"));
				 String card_type = rs.getString("card_type");
				 String Name = rs.getString("Name");
				 String cardNo = rs.getString("cardNo");
				 String exp_date = rs.getString("exp_date");
				 String cvc = rs.getString("cvc");
				 String monthly_amount = rs.getString("monthly_amount");
				 // Add a row into the html table
				 
				 output += "<tr><td>" + card_type + "</td>";
				 output += "<td>" + Name + "</td>";
				 output += "<td>" + cardNo + "</td>"; 
				 output += "<td>" + exp_date + "</td>";
				 output += "<td>" + cvc + "</td>";
				 output += "<td>" + monthly_amount + "</td></tr>";
				 // buttons
				 output += "<input name='itemID' type='hidden' "
				 + " value='" + payment_id + "'>"
				 + "</form></td></tr>";
			 }
			 con.close();
			 // Complete the html table
			 output += "</table>";
			 
			 }
			catch (Exception e)
			 {
//				 output = "Error while reading the user details";
				output=e.toString();
				 System.err.println(e.getMessage());
			 }
			return output;
		}
		
		
}
