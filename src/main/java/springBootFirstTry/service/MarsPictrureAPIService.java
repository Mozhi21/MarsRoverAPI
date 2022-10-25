package springBootFirstTry.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import springBootFirstTry.dto.HomeDto;
import springBootFirstTry.repository.PreferencesRepository;
import springBootFirstTry.response.MarsPhoto;
import springBootFirstTry.response.MarsPictureApiResponse;
/**
 * The service of the the web
 * Get data form front end and pass it to homecontroller though PreferenceRepository.
 * @author 123456
 *
 */
@Service
public class MarsPictrureAPIService {
	
	private static final String API_KEY = "52id3RgqJl5uWJhyCqivZoIIwveleU0Bpzk4xpbr";
	
	@Autowired // managed by spring boot, listener
	private PreferencesRepository preferencesRepo;
	
	private Map<String, List<String>> validCameras = new HashMap<>();
	
	/**
	 * create the list of valid cameras
	 */
	public MarsPictrureAPIService() {
		validCameras.put("opportunity",Arrays.asList("FHAZ","RHAZ","NAVCAM","PANCAM", "MINITES"));
		validCameras.put("curiosity",Arrays.asList("FHAZ","RHAZ","MAST","NAVCAM","CHEMCAM", "MAHLI","MARDI"));
		validCameras.put("spirit",Arrays.asList("FHAZ","RHAZ","NAVCAM","PANCAM", "MINITES"));
	}
	/**
	 * get rover data from front-end
	 * @param homeDto
	 * @return MarsPictureApiRespoce
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public MarsPictureApiResponse getRoverData(HomeDto homeDto) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RestTemplate rt = new RestTemplate();
		
		List<String> apiUrlEnpoints = getApiUrlEnpoints(homeDto);
		List<MarsPhoto> photos = new ArrayList<>(); 
		MarsPictureApiResponse response = new MarsPictureApiResponse();
		
		apiUrlEnpoints.stream()
					  .forEach(url -> {
						  MarsPictureApiResponse apiResponse = rt.getForObject(url, MarsPictureApiResponse.class); 
						  photos.addAll(apiResponse.getPhotos());
					  });
		response.setPhotos(photos);
		return response;
	};
	// use the name of the get_methods to get name of all the camera,
	// grab all getCamera* methods and (if valid) will build a API URL to
	// call in order to fetch pictures for a given rover /camera /Sol.
	public List<String> getApiUrlEnpoints (HomeDto homeDto) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		List<String> urls = new ArrayList<>(); 
		
		Method[] methods = homeDto.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().indexOf("getCamera") > -1 && Boolean.TRUE.equals(method.invoke(homeDto))){// filter out "getcamera" and camera being selected
				String cameraName = method.getName().split("getCamera")[1].toUpperCase();// name in upper-case now
				if (validCameras.get(homeDto.getMarsApiRoverData()).contains(cameraName)){
					urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" +API_KEY +"&camera=" + cameraName);
				}				
			}
		}
// replaced by code up. 		
//		if(Boolean.TRUE.equals(homeDto.getCameraFhaz())) {
//			urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" +API_KEY +"&camera=FHAZ");
//		}
//		if(Boolean.TRUE.equals(homeDto.getCameraChemcan()) && "curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
//			urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" +API_KEY +"&camera=CHEMCAM");
//		}
//		if(Boolean.TRUE.equals(homeDto.getCameraMahli()) && "curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
//			urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" +API_KEY +"&camera=MAHLI");
//		}
//		if(Boolean.TRUE.equals(homeDto.getCameraMardi()) && "curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
//			urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" +API_KEY +"&camera=MARDI");
//		}
//		if(Boolean.TRUE.equals(homeDto.getCameraMast()) && "curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
//			urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" +API_KEY +"&camera=MAST");	
//		}
//		if(Boolean.TRUE.equals(homeDto.getCameraMinites())&& !"curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
//			urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" +API_KEY +"&camera=MINITES");		
//		}
//		if(Boolean.TRUE.equals(homeDto.getCameraNavcam())) {
//			urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" +API_KEY +"&camera=NAVCAM");		
//		}
//		if(Boolean.TRUE.equals(homeDto.getCameraPancam())&& !"curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
//			urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" +API_KEY +"&camera=PANCAM");		
//		}
//		if(Boolean.TRUE.equals(homeDto.getCameraRhaz())) {
//			urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData()+"/photos?sol="+homeDto.getMarsSol()+"&api_key=" +API_KEY +"&camera=RHAZ");		
//		}
		return urls;
	}
	/**
	 * getter of validCameras.
	 * @return
	 */
	public Map<String, List<String>> getValidCameras() {
		return validCameras;
	}

	public HomeDto save(HomeDto homeDto) {
		return preferencesRepo.save(homeDto);
		
	}

	public HomeDto findByUserId(Long userId) {
		return preferencesRepo.findByUserId(userId);
	}
	
}
