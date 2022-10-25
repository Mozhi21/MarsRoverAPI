package springBootFirstTry.web;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springBootFirstTry.dto.HomeDto;
import springBootFirstTry.repository.PreferencesRepository;
import springBootFirstTry.response.MarsPictureApiResponse;
import springBootFirstTry.service.MarsPictrureAPIService;

/**
 * The home controller of the program
 * there is no "model" created by author,
 * model is created and maintained by STS.
 * @author 123456
 *
 */
@Controller
public class HomeCotroller {

  @Autowired // managed by spring boot, listener
  private MarsPictrureAPIService roverService;

  
  @GetMapping("/") //pick up by form method ="get" action="" id ='fromRoverType' in index.html
  public String getHomeView (ModelMap model,Long userId, Boolean createUser) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	 
	  HomeDto homeDto = createDefaultHomeDto(userId);
	  
	  if(Boolean.TRUE.equals(createUser) && userId == null) {
		  homeDto=roverService.save(homeDto);
	  } else {
		  homeDto = roverService.findByUserId(userId);// if database have saved the preference.
		  if(homeDto == null) {
			  homeDto = createDefaultHomeDto(userId);
		  }
	  }
	  	 
	  MarsPictureApiResponse roverData = roverService.getRoverData(homeDto);
	  model.put("roverData", roverData);
	  model.put("homeDto", homeDto);
	  // put the name of the chosen rover
	  model.put("validCameras",roverService.getValidCameras().get(homeDto.getMarsApiRoverData()));
	  // reset homeDto if rememberPreferences is not true
	  if ( !Boolean.TRUE.equals(homeDto.getRememberPreferences()) && userId != null) {
		  HomeDto defaultHomeDto = createDefaultHomeDto(userId);
		  roverService.save(defaultHomeDto);
	  }
	  	
	  return"index";
  }
  //mapping to <form method ="get"> in index.html, get back from database 
  // using userId and push it into model.
  // returned redirect ,homeDto would be picked up by STS.
  @PostMapping("/")
  public String postHomeView (HomeDto homeDto) {
	  homeDto = roverService.save(homeDto);
	  //System.out.println(homeDto);
	  return "redirect:/?userId=" +homeDto.getUserId();
  }

  @PostMapping("/savedPreferences")
  @ResponseBody
  public  HomeDto getSavedPreferences(Long userId) {
	  if(userId != null)
		  return roverService.findByUserId(userId);
	  else
		  return createDefaultHomeDto(userId);
  }
  
  /**
   * create defaultHomeDto when web is initialized.
   * @param userId
   * @return homeDto
   */
  private HomeDto createDefaultHomeDto(Long userId) {
	  HomeDto homeDto = new HomeDto();	  
	  homeDto.setMarsApiRoverData("opportunity"); // set default
	  homeDto.setMarsSol(1); // set default
	  homeDto.setUserId(userId); // userId is not reset
	return homeDto;
}

}
