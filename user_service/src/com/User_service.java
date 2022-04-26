package com;
import model.UserService;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


//For JSON
import com.google.gson.*;


//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Moniter")

public class User_service {
	
	
	UserService userObj = new UserService();
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	//register user
	public String insertUser(@FormParam("moniter_no") String moniter_no,
	@FormParam("customer_id") String customer_id,
	@FormParam("unit_amount") String unit_amount,
	@FormParam("previous_unit") String previous_unit,
	@FormParam("current_unit") String current_unit,
	@FormParam("monthly_amount") String monthly_amount
	) {
	String output = userObj.insertUser(moniter_no,customer_id,unit_amount,previous_unit,current_unit,monthly_amount);
	return output;
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readUserDetails()//view all users
	{
		return userObj.readUserDetails();
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUser(String userData)//update user
	{
		//Convert the input string to a JSON object
		 JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();
		//Read the values from the JSON object
		 String bill_no = userObject.get("bill_no").getAsString();
		 String moniter_no = userObject.get("moniter_no").getAsString();
		 String customer_id = userObject.get("customer_id").getAsString();
		 String unit_amount = userObject.get("unit_amount").getAsString();
		 String previous_unit = userObject.get("previous_unit").getAsString();
		 String current_unit = userObject.get("current_unit").getAsString();
		 String monthly_amount = userObject.get("monthly_amount").getAsString();
		 String output = userObj.EditUserDetails(bill_no, moniter_no, customer_id, unit_amount, previous_unit, current_unit, monthly_amount);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteUser(String userData)//delete users
	{
	//Convert the input string to an XML document
	Document doc = Jsoup.parse(userData, "", Parser.xmlParser());

	//Read the value from the element <userId>
	String billno = doc.select("bill_no").text();
	String output = userObj.deleteUser(billno);
	return output;
	}
	@GET
	@Path("/getUserbyID/{billNO}")//view a specific user
	@Produces(MediaType.TEXT_HTML)
	public String UserProfileDetails(@PathParam("billno") String billNO) {

		return userObj.fetchUser(billNO);
	}
	
}

