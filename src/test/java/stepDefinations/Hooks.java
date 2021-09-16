package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		//write a code that will give you placeId
		//execute this code only when placeId is null
		
		StepDefination m = new StepDefination();
		
		if(StepDefination.place_id==null)
		{
			m.add_Place_Payload_with("Shetty", "French", "asia");
			m.user_calls_with_http_request("AddPlaceAPI", "POST");
			m.verify_place_Id_created_maps_to_using_getPlaceAPI("Shetty", "getPlaceAPI");
		}
	}
}
