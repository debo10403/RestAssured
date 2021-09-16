package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.APIResource;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination extends Utils{
	
	TestDataBuild data = new TestDataBuild();
	RequestSpecification res;
	//ResponseSpecification resspec;
	Response response;
	 static String place_id;

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		 
		res=given().spec(requestSpecification())
		.body(data.addPlacePayLoad(name, language, address));

	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		
		//constructor will be called with value of resource which we pass
		APIResource resourceAPI = APIResource.valueOf(resource);
		System.out.println(resourceAPI);
		
		//resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if (method.equalsIgnoreCase("POST"))
		{
		response =res.when().post(resourceAPI.getResource());
				//then().spec(resspec).extract().response();
		}
		else if (method.equalsIgnoreCase("GET")) 
		{
			response =res.when().get(resourceAPI.getResource());
		}
		else if (method.equalsIgnoreCase("DELETE")) 
		{
			response =res.when().delete(resourceAPI.getResource());
		}
		
	}

	@Then("the API call got success with status code {int}")
	public void the_API_call_got_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    
		assertEquals(response.getStatusCode(), 200);
	}

	@And("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
	    // Write code here that turns the phrase above into concrete actions
		
		assertEquals(getJsonPath(response, keyValue), expectedValue);
	}
	
	@And("verify place_Id created maps to {string} using {string}")
	public void verify_place_Id_created_maps_to_using_getPlaceAPI(String expectedName, String resource) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    
		place_id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource, "GET");
		
		String actualname = getJsonPath(response, "name");
		
		assertEquals(actualname, expectedName);
	}
		
	@Given("DeletePlace payload")
	public void deleteplace_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    
		res = given().spec(requestSpecification())
			.body(data.deletePlacePayLoad(place_id));
		
	}
}
