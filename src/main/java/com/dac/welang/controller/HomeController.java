package com.dac.welang.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dac.welang.model.Authority;
import com.dac.welang.model.Language;
import com.dac.welang.model.UserAccount;
import com.dac.welang.model.UserSettings;
import com.dac.welang.repo.ChatContentRepository;
import com.dac.welang.repo.UserAccountRepository;
import com.dac.welang.repo.UserRelationshipRepository;
import com.google.common.collect.Lists;



@Controller
public class HomeController{
    @Autowired
    private UserAccountRepository userRepository;
    @Autowired
	private UserRelationshipRepository userRelationshipRepository;
    @Autowired
	private ChatContentRepository chatRepository;
    
    @Autowired
    private JavaMailSender javaMailService;
    


    
    private Facebook facebook;
   	private ConnectionRepository connectionRepository;

   @Inject
   public HomeController(Facebook facebook, ConnectionRepository connectionRepository) {
       this.facebook = facebook;
	   this.connectionRepository = connectionRepository;
   }

    @RequestMapping(value = "/findfriend", method = RequestMethod.GET)
    public String findFreind(Model model,  @RequestParam Long id){
    	List<Long> friendsReqs = userRelationshipRepository.getRequestedFriends(id);
    	friendsReqs.addAll(userRelationshipRepository.getAddedFriends(id));
    	friendsReqs.add(id);
    	List<UserAccount> friends;
    	if(friendsReqs.size()!=0){
    		friends = userRepository.getEligibleFriends(friendsReqs);	
    	}else{
    		friends  =  userRepository.getAllUsers();
    	}
    	filterLanguage(friends);
    	model.addAttribute("friends", friends);
    	return "find_friend";
    }
    
    @RequestMapping(value = "/register") 
    public String register(UserAccount user, Model model) {
    	model.addAttribute("languages", Arrays.asList(ProfileController.strLanguages));
        return "register"; 
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
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("userAccount") @Valid UserAccount user, BindingResult result, Model model){
    	if (result.hasErrors()) {
    		model.addAttribute("languages", Arrays.asList(ProfileController.strLanguages));
            return "register";
        }    	
    	//user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    	user.setUsername(user.getFirstName() + " " + user.getLastName());
        user.setUserKey(RandomStringUtils.randomAlphanumeric(16));
        user.setAuthorities(Lists.newArrayList(new Authority("ROLE_USER")));
    	try{
    		userRepository.saveAndFlush(user);
    	}catch(Exception e){
    		e.printStackTrace();
    		model.addAttribute("erro", true);
            return "redirect:register?error";
    	}
    	    
    	return "redirect:/login";
    }
    
   
    @RequestMapping(value="/changePassword",method=RequestMethod.POST)
    @ResponseBody
	public String changePassword(@RequestParam String oldPass, @RequestParam String confirmPass){
    	String email = SecurityContextHolder.getContext().getAuthentication().getName();		
    	int id = userRepository.changePassword(email, oldPass, confirmPass);
    	if(id>0){
			return  "OK";	
		}else{
			return "NO";
		}
	}
    
    @RequestMapping(value="/resetPassword",method=RequestMethod.POST)
    @ResponseBody
	public String resetPassword(@RequestParam String email)
	{
		
		Long id = userRepository.getUserIdByEmail(email);
		if(id != null && id>0){
			String key = RandomStringUtils.randomAlphanumeric(20);
			sendEmailWithoutTemplating(userRepository.getUserNameById(id),email,  key);
			userRepository.resetUserKey(email, key);
			return  "OK";	
		}else{
			return "NO";
		}
	}
    
    @RequestMapping("/resetPassword/{resetKey}")
    public String resetPasswordWithLink(@PathVariable(value="resetKey") String key, Model model) {
    	Long id= userRepository.checkResetKey(key);
    	if(id != null && id>0){
    		model.addAttribute("key", key);
    		return "reset_password"; 
    	}
    	return "redirect:/login";	 
    }
    
    @RequestMapping("/resetPassword/submitResetPassword")
    public String submitResetPassword(@RequestParam String key, @RequestParam String confirmPass) {
    	String email = userRepository.getEmailByKey(key);
    	userRepository.resetPasswordByEmail(email, key ,confirmPass);
    	sendEmailToConfirmChangedPassword(email);
    	String newKey = RandomStringUtils.randomAlphanumeric(20);
		userRepository.resetUserKey(email, newKey);		
    	return "redirect:/login";	 
    }
    
    public void sendEmailWithoutTemplating(String username, String email, String key){
    	System.out.println("Sending email...");
    	SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Reset password");
        mailMessage.setText("Hello " + username  +",\n You have asked to reset the password\n. " 
        +" Please go to this link: http://localhost:9999/resetPassword/" + key
        + " to reset your paswword: "
        + "\n\n**Please set your new password now.\nThank you!!");
        javaMailService.send(mailMessage);
        System.out.println("Email Sent!");
    }
    public void sendEmailToConfirmChangedPassword(String email){
    	System.out.println("Sending email...");
    	SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Changed password");
        mailMessage.setText("Hello user,\n You just changed your password");
        javaMailService.send(mailMessage);
        System.out.println("Email Sent!");
    }
    
    
    
    
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String search(@RequestParam String name, @RequestParam String nativeLanguage,String learningLanguage, Model map)
	{
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    	Long id = userRepository.getUserIdByEmail(userEmail);
		List<Long> friendsReqs = userRelationshipRepository.getRequestedFriends(id);
    	friendsReqs.addAll(userRelationshipRepository.getAddedFriends(id));
    	friendsReqs.add(id);
    	System.out.println("GG:" + name + ":G:" + name.equals("") + ":CAT:" + nativeLanguage);
    	
		List<UserAccount> users=new ArrayList<UserAccount>();
		if(nativeLanguage.equals("NONE") && learningLanguage.equals("NONE")){
			users=userRepository.getUserByName(name, friendsReqs);
		}else{
			if(!nativeLanguage.equals("NONE") && !learningLanguage.equals("NONE")){
				users=userRepository.getUserByNameAndNativeAndForeignLanguage(name, nativeLanguage, learningLanguage, friendsReqs);
			}
			else if(!nativeLanguage.equals("NONE"))
			{
				users=userRepository.getUserByNameAndNative(name, nativeLanguage, friendsReqs);
			}
			else if(!learningLanguage.equals("NONE")){
				users=userRepository.getUserByNameAndForeignLanguage(name, learningLanguage, friendsReqs);
			}
		}
		filterLanguage(users);
		map.addAttribute("friends",users);
		map.addAttribute("input",name);
		return  "find_friend";
	}
	
	@RequestMapping("/auto_login")
    public String fb(Model model){    	
    	if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
    	User uf= facebook.userOperations().getUserProfile();
    	String email = uf.getFirstName()+ uf.getLastName()+"@facebook.com";
    	System.out.println("Email:" + email);
    	Long id = userRepository.getUserIdByEmail(email);
    	String password;
    	if( id!=null && id>0){
    		password = userRepository.getUserPasswordByEmail(email);
    	}else{
            //save to database
    		password = RandomStringUtils.randomAlphanumeric(16);
    		UserSettings settings = new UserSettings();
        	List<Language> languages = new ArrayList<Language>();
        	languages.add(new Language("English", 1));
        	settings.setForeignLanguages(languages);
        	settings.setNativeLanguage("English");
        	settings.setDescription(uf.getAbout() + "--More:" + uf.getBio());
        	System.out.println("GG:" + uf);
        	if(uf.getLanguages() != null){
	        	for(Reference ref:uf.getLanguages()){
	        		System.out.println("Language uf:" + ref.getName());
	        	}        	
        	}
        	System.out.println("Bd uf:" + uf.getBirthday());
        	System.out.println("About uf:" + uf.getAbout() + "XX" + uf.getBio());
        	System.out.println("Quote uf:" + uf.getQuotes());
        	if(uf.getBirthday() != null){
        		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        		try {
    				settings.setDateOfBirth(dateFormat.parse(uf.getBirthday()));
    			} catch (ParseException e1) {
    				e1.printStackTrace();
    			}
        	}
        	
        	UserAccount user = new UserAccount();
        	user.setUserSettings(settings);
        	user.setFirstName(uf.getFirstName());
        	user.setLastName(uf.getLastName());
        	user.setUsername(uf.getName());
        	user.setEmail(email);
        	user.setPassword(password);
        	user.setGender(uf.getGender());
        	//user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setUserKey(RandomStringUtils.randomAlphanumeric(16));
            user.setAuthorities(Lists.newArrayList(new Authority("ROLE_USER")));
            try{
        		userRepository.saveAndFlush(user);
        	}catch(Exception e){
        		e.printStackTrace();
        		System.out.println("ddd");
        		model.addAttribute("erro", true);
                return "login";
        	}
    	}
        model.addAttribute("username", email);
        model.addAttribute("password", password);
    	return "facebook_login";
    }
	
    @RequestMapping("/")
    public String chatBox(Model model){
    	String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	Long id = userRepository.getUserIdByEmail(userEmail);
    	List<UserAccount> friends = userRepository.getFriendsOfUserEmail(userEmail);
    	List<Integer> messageCount = new ArrayList<>();    			
    	int messageCountTotal = 0;
    	int tmpCount;
    	for(UserAccount f:friends){
    		tmpCount = chatRepository.countUnseenMessagefromUserId(f.getId(), id);
    		messageCountTotal +=tmpCount;
    		messageCount.add(tmpCount);
    	}
    	System.out.println("Message:" + messageCount);
    	model.addAttribute("messageCount", messageCount);
    	model.addAttribute("messageCountTotal", messageCountTotal);
		model.addAttribute("friends", friends);
    	model.addAttribute("friendNotification", userRelationshipRepository.getNumberFriendRequest(id));
    	model.addAttribute("currentUser", userRepository.getCurrentUser(userEmail));
    	
    	
        
    	
    	return "chat_box";
    }
}

