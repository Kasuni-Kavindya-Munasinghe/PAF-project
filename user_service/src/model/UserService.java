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

		 //Provide the correct details: DBServer/DBName, account_no, complain
		 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", "sokian@981119");
		 }
		 catch (Exception e)
		 {e.printStackTrace();}
		 return con;
		 }
		
		public String insertUser(String moniter_no, String customer_id, String unit_amount, String previous_unit, String current_unit, String monthly_amount)
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for inserting."; }
		 // create a prepared statement
		 String query = " insert into moniter(`bill_no`,`moniter_no`,`customer_id`,`unit_amount`,`previous_unit`,`current_unit`,`monthly_amount`)"
		 + " values (?, ?, ?, ?, ?, ?, ?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, moniter_no);
		 preparedStmt.setString(3, customer_id);
		 preparedStmt.setString(4, unit_amount);
		 preparedStmt.setString(5, previous_unit);
		 preparedStmt.setString(6, current_unit);
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
			 output = "<table border='1'><tr><th>moniter_no</th>"
			 +"<th>customer_id</th><th>unit_amount</th>"
			 + "<th>previous_unit</th>"
			 + "<th>current_unit</th>"
			 + "<th>monthly_amount</th>";
			 String query = "select * from user";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 // iterate through the rows in the result set
			 while (rs.next())
			 {
				 String bill_no = Integer.toString(rs.getInt("bill_no"));
				 String moniter_no = rs.getString("moniter_no");
				 String customer_id = rs.getString("customer_id");
				 String unit_amount = rs.getString("unit_amount");
				 String previous_unit = rs.getString("previous_unit");
				 String current_unit = rs.getString("current_unit");
				 String monthly_amount = rs.getString("monthly_amount");
				 // Add a row into the html table
				 output += "<tr><td>" + moniter_no + "</td>";
				 output += "<td>" + customer_id + "</td>";
				 output += "<td>" + unit_amount + "</td>";
				 output += "<td>" + previous_unit + "</td>"; 
				 output += "<td>" + current_unit + "</td>";
				 output += "<td>" + monthly_amount + "</td>";
				 // buttons
				 output += "<input name='bill_no' type='hidden' "
				 + " value='" + bill_no + "'>"
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
		
		public String EditUserDetails(String bill_no, String moniter_no, String customer_id, String unit_amount, String previous_unit, String current_unit, String monthly_amount)
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
				   String query = "UPDATE moniter SET moniter_no=?,customer_id=?,unit_amount=?,previous_unit=?,current_unit=?,monthly_amount=?WHERE bill_no=?";
				   PreparedStatement preparedStmt = con.prepareStatement(query);
				   // binding values
				   preparedStmt.setString(1, moniter_no);
				   preparedStmt.setString(2, customer_id);
				   preparedStmt.setString(3, unit_amount);
				   preparedStmt.setString(4, previous_unit);
				   preparedStmt.setString(5, current_unit);
				   preparedStmt.setString(6, monthly_amount);
				   preparedStmt.setInt(7, Integer.parseInt(bill_no));
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
		
		public String deleteUser(String bill_no)
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
		String query = "delete from moniter where bill_no=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);

		// binding values
		preparedStmt.setInt(1, Integer.parseInt(bill_no));

		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Moniter Details Deleted successfully";
		}
		catch (Exception e)
		{
		output = e.toString();
		//System.err.println(e.getMessage());
		}
		return output;
		}
public String fetchUser(String bill_no)
		
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
			 output = "<table border='1'><tr><th>moniter_no</th>"
			 +"<th>customer_id</th><th>unit_amount</th>"
			 + "<th>previous_unit</th>"
			 + "<th>current_unit</th>"
			 + "<th>monthly_amount</th></tr>";
			 String query = "select * from moniter where bill_no='"+ bill_no+"'";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 // iterate through the rows in the result set
			 while (rs.next())
			 { 
				 String billno = Integer.toString(rs.getInt("bill_no"));
				 String moniter_no = rs.getString("moniter_no");
				 String customer_id = rs.getString("customer_id");
				 String unit_amount = rs.getString("unit_amount");
				 String previous_unit = rs.getString("previous_unit");
				 String current_unit = rs.getString("current_unit");
				 String monthly_amount = rs.getString("monthly_amount");
				 // Add a row into the html table
				 
				 output += "<tr><td>" + moniter_no + "</td>";
				 output += "<td>" + customer_id + "</td>";
				 output += "<td>" + unit_amount + "</td>"; 
				 output += "<td>" + previous_unit + "</td>";
				 output += "<td>" + current_unit + "</td>";
				 output += "<td>" + monthly_amount + "</td></tr>";
				 // buttons
				 output += "<input name='itemID' type='hidden' "
				 + " value='" + bill_no + "'>"
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
