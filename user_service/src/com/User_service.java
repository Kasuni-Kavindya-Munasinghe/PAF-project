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

@Path("/payment")

public class User_service {
	
	
	UserService userObj = new UserService();
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	//register user
	public String insertUser(@FormParam("card_type") String card_type,
	@FormParam("Name") String Name,
	@FormParam("cardNo") String cardNo,
	@FormParam("exp_date") String exp_date,
	@FormParam("cvc") String cvc,
	@FormParam("monthly_amount") String monthly_amount
	) {
	String output = userObj.insertUser(card_type,Name,cardNo,exp_date,cvc,monthly_amount);
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
		 String payment_id = userObject.get("payment_id").getAsString();
		 String card_type = userObject.get("card_type").getAsString();
		 String Name = userObject.get("Name").getAsString();
		 String cardNo = userObject.get("cardNo").getAsString();
		 String exp_date = userObject.get("exp_date").getAsString();
		 String cvc = userObject.get("cvc").getAsString();
		 String monthly_amount = userObject.get("monthly_amount").getAsString();
		 String output = userObj.EditUserDetails(payment_id, card_type, Name, cardNo,exp_date, cvc,  monthly_amount);
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
	String userID = doc.select("payment_id").text();
	String output = userObj.deleteUser(userID);
	return output;
	}
	@GET
	@Path("/getUserbyID/{userId}")//view a specific user
	@Produces(MediaType.TEXT_HTML)
	public String UserProfileDetails(@PathParam("userId") String userId) {

		return userObj.fetchUser(userId);
	}
	
}

