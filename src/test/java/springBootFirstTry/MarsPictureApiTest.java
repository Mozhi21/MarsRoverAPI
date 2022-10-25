package springBootFirstTry;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import springBootFirstTry.response.MarsPictureApiResponse;

public class MarsPictureApiTest {


	@Test
	public void SmallTest() {
		RestTemplate rt = new RestTemplate();
		
		ResponseEntity<MarsPictureApiResponse> resp = rt.getForEntity("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY", MarsPictureApiResponse.class);
		System.out.println(resp.getBody());
	}
}
