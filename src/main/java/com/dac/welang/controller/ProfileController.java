package com.dac.welang.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dac.welang.model.Language;
import com.dac.welang.model.Notification;
import com.dac.welang.model.UserAccount;
import com.dac.welang.model.UserRelationship;
import com.dac.welang.repo.UserAccountRepository;
import com.dac.welang.repo.UserRelationshipRepository;
import com.dac.welang.service.NotificationService;

@Controller
public class ProfileController {
	@Autowired
	private Environment env;
	@Autowired
	private UserAccountRepository userRepository;
	@Autowired
	private UserRelationshipRepository userRelationshipRepository;
	@Autowired
	private NotificationService notificationService;
	private SimpleDateFormat sdf;
	
	public static String strLanguages[] = {
			"Abkhazian",
			"Afar",
			"Afrikaans",
			"Albanian",
			"Amharic",
			"Arabic",
			"Armenian",
			"Assamese",
			"Aymara",
			"Azerbaijani",
			"Bashkir",
			"Basque",
			"Bengali, Bangla",
			"Bhutani",
			"Bihari",
			"Bislama",
			"Breton",
			"Bulgarian",
			"Burmese",
			"Byelorussian",
			"Cambodian",
			"Catalan",
			"Chinese",
			"Corsican",
			"Croatian",
			"Czech",
			"Danish",
			"Dutch",
			"English",
			"Esperanto",
			"Estonian",
			"Faeroese",
			"Fiji",
			"Finnish",
			"French",
			"Frisian",
			"Gaelic (Scots Gaelic)",
			"Galician",
			"Georgian",
			"German",
			"Greek",
			"Greenlandic",
			"Guarani",
			"Gujarati",
			"Hausa",
			"Hebrew",
			"Hindi",
			"Hungarian",
			"Icelandic",
			"Indonesian",
			"Interlingua",
			"Interlingue",
			"Inupiak",
			"Irish",
			"Italian",
			"Japanese",
			"Javanese",
			"Kannada",
			"Kashmiri",
			"Kazakh",
			"Kinyarwanda",
			"Kirghiz",
			"Kirundi",
			"Korean",
			"Kurdish",
			"Laothian",
			"Latin",
			"Latvian, Lettish",
			"Lingala",
			"Lithuanian",
			"Macedonian",
			"Malagasy",
			"Malay",
			"Malayalam",
			"Maltese",
			"Maori",
			"Marathi",
			"Moldavian",
			"Mongolian",
			"Nauru",
			"Nepali",
			"Norwegian",
			"Occitan",
			"Oriya",
			"Oromo, Afan",
			"Pashto, Pushto",
			"Persian",
			"Polish",
			"Portuguese",
			"Punjabi",
			"Quechua",
			"Rhaeto-Romance",
			"Romanian",
			"Russian",
			"Samoan",
			"Sangro",
			"Sanskrit",
			"Serbian",
			"Serbo-Croatian",
			"Sesotho",
			"Setswana",
			"Shona",
			"Sindhi",
			"Singhalese",
			"Siswati",
			"Slovak",
			"Slovenian",
			"Somali",
			"Spanish",
			"Sudanese",
			"Swahili",
			"Swedish",
			"Tagalog",
			"Tajik",
			"Tamil",
			"Tatar",
			"Tegulu",
			"Thai",
			"Tibetan",
			"Tigrinya",
			"Tonga",
			"Tsonga",
			"Turkish",
			"Turkmen",
			"Twi",
			"Ukrainian",
			"Urdu",
			"Uzbek",
			"Vietnamese",
			"Volapuk",
			"Welsh",
			"Wolof",
			"Xhosa",
			"Yiddish",
			"Yoruba",
			"Zulu"
	};
	/**
	 * Show the index page containing the form for uploading a file.
	 */
	@RequestMapping("/updateProfile")
	public String index(Model model) {
		UserAccount user = userRepository.getCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName());
		Iterator<Language> iter = user.getUserSettings().getForeignLanguages().iterator();
		List<String> languagesProfile = new ArrayList<>();
		List<String> languages = new ArrayList<>();
		languagesProfile.addAll(Arrays.asList(strLanguages));		
		languages.addAll(Arrays.asList(strLanguages));
		model.addAttribute("languagesProfile", languagesProfile);
		while(iter.hasNext()){
			Language lang = iter.next();
		    if(lang.getLevel()==-1){
		        iter.remove();
		    }else{
		    	languages.remove(lang.getLanguage());
		    }
		    
		}
		
		model.addAttribute("languages", languages);
    	model.addAttribute("user", user);
		return "update_profile";
	}

	private void filterLanguage(List<UserAccount> friends){
    	for(UserAccount user:friends){
	    	Iterator<Language> iter = user.getUserSettings().getForeignLanguages().iterator();
			while(iter.hasNext()){
			    if(iter.next().getLevel()==-1){
			        iter.remove();
			    }
			}
    	}
    }
    

	@RequestMapping(value="/updateProfile", method = RequestMethod.POST)
	public String update(@ModelAttribute("user") UserAccount user,@RequestParam("file") MultipartFile file ,Model model,@RequestParam("date") String bdate){
		sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserAccount u=userRepository.getCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName());
		
		Date d1;
		try {
			d1 = sdf.parse(bdate);
			u.getUserSettings().setDateOfBirth(d1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
			try {
				System.out.println("Uploading");
				// Get the filename and build the local file path
				String fileName = u.getId() + "";
				fileName += file.getOriginalFilename();
				//String directory = env.getProperty("uploadedFiles");				
				System.out.println("File name:" + fileName);
				Resource resource = new ClassPathResource("/static/images/");
				System.out.println("Get reousce:" + resource.getFilename());
				String filepath = Paths.get("src/main/resources/static/images/", fileName).toString();

				// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
				System.out.println("File:" + file.getSize());
				if(file.getSize()>0){
					stream.write(file.getBytes());
					u.getUserSettings().setProfileImage(fileName);
				}
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		//file.transferTo(new File(UPLOAD_LOCATION,u.getId().toString()+".png"));
		
		
		
    	System.out.println("Size after:" + user.getUserSettings().getForeignLanguages().size());
    	System.out.println("Language:" + user.getUserSettings().getForeignLanguages());
		u.setGender(u.getGender());
		u.setEmail(user.getEmail());
		u.setUsername(user.getUsername());
		u.getUserSettings().setDescription(user.getUserSettings().getDescription());
		u.getUserSettings().setNativeLanguage(user.getUserSettings().getNativeLanguage());
		u.getUserSettings().setForeignLanguages(user.getUserSettings().getForeignLanguages());
		userRepository.save(u);//Update profile using @query update is better to avoid stackover flow
		model.addAttribute("success","Information Updated Successfully..");
		model.addAttribute("user",u);
		return "redirect:/";
		
	}
	
	
	
	@RequestMapping("/list_friends")
    public String getChatHistory(Model model, @RequestParam Long id){
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		List<UserAccount> users  = userRepository.getFriendsOfUserEmail(userEmail);
		filterLanguage(users);
		model.addAttribute("friends", users);	
    	return "friends";
    }
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadFile(@RequestParam("uploadfile") MultipartFile uploadfile) {

		try {
			// Get the filename and build the local file path
			String filename = uploadfile.getOriginalFilename();
			String directory = env.getProperty("uploadedFiles");
			String filepath = Paths.get(directory, filename).toString();

			// Save the file locally
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			stream.write(uploadfile.getBytes());
			stream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/addFriend", method = RequestMethod.POST)
	@ResponseBody 
	public ResponseEntity<?> addFriend(@RequestParam Long addFrom, @RequestParam Long addTo){
	    String addToUserEmail = userRepository.getUserEmailById(addTo);	    
	    userRelationshipRepository.save(new UserRelationship(addFrom, addTo));
	    notificationService.notifyAddFriend(
		      new Notification(addFrom), // notification object
		      addToUserEmail          
	    );
	    return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/getFriendRequests", method = RequestMethod.POST)
	@ResponseBody 
	public String getFriendRequests(@RequestParam Long userId){
		List<Long> friendsReqs = userRelationshipRepository.getFriendRequests(userId);
		friendsReqs.remove(null);
		if(friendsReqs.size()>0){
			List<UserAccount> users = userRepository.getFriendsById(friendsReqs);
			for(int i=0; i<users.size(); i++){
				users.get(i).setFriends(null);
				users.get(i).setOwners(null);
			}
			return new JSONArray(users).toString();
		}else{
			return "NO";
		}
	}
	
	@RequestMapping(value="/confirmFriendship", method = RequestMethod.POST)
	@ResponseBody 
	public  ResponseEntity<?> confirmFriendship(@RequestParam Long ownerId, @RequestParam Long friendId){
		UserAccount currentUser=userRepository.getCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName());
		UserAccount friend = userRepository.getUserById(friendId);
		friend.getFriends().add(currentUser);
		currentUser.getFriends().add(friend);
		userRepository.save(currentUser);
		userRepository.flush();
		userRepository.save(friend);
		userRepository.flush();
		
		
		UserRelationship f = userRelationshipRepository.getCurrentFriendShip(ownerId, friendId);
		f.setConfirmed(true);
		userRelationshipRepository.save(f);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    System.out.println(auth);
	    if (auth != null){  
	    	System.out.println("not");
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}
}
