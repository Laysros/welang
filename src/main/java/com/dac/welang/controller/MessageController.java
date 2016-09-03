package com.dac.welang.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dac.welang.model.ChatContent;
import com.dac.welang.model.Notification;
import com.dac.welang.model.UserAccount;
import com.dac.welang.repo.ChatContentRepository;
import com.dac.welang.repo.UserAccountRepository;
import com.dac.welang.service.NotificationService;

@Controller
public class MessageController {
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private UserAccountRepository userRepository;
	@Autowired
	private ChatContentRepository chatRepository;
	
	 
	
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> sendMessage(@RequestParam Long fromWhom, @RequestParam Long toWhom, @RequestParam String message) {
		
		String toId = userRepository.getUserEmailById(toWhom);
		
		ChatContent chat = new ChatContent(fromWhom, toWhom, message, new Date(), false);
		
		chatRepository.save(chat);
		notificationService.notifyMessage(new Notification(message, fromWhom, new Date()),toId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@RequestMapping(value = "/contact") 
	 public String register(UserAccount model) {
       return "contact"; 
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String sendContactMessage(@ModelAttribute("userAccount") UserAccount userAccount, @RequestParam("subject") String subject, @RequestParam("message") String message) {
		try {
			notificationService.sendContactMessage(userAccount, subject, message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "redirect:/contact/thank";
	}
	
	
	@RequestMapping("/chat_history")
    public String getChatHistory(Model model, @RequestParam Long id){
		List<Long> friendIds = chatRepository.getFriendIdChatWithUserId1(id);//Who send to this user ->
		List<Long> temp  = chatRepository.getFriendIdChatWithUserId2(id);//Users who this one sent to -<
		temp.remove(null);
	
		
		Collections.reverse(friendIds);
		if(temp!= null && !temp.isEmpty()){			
			Collections.reverse(temp);
			friendIds.addAll(temp);
		}
		friendIds = friendIds.parallelStream().distinct().collect(Collectors.toList());
		
		if(friendIds.size()!=0){
			List<UserAccount> tmpUsers = userRepository.getFriendsById(friendIds);
			List<UserAccount> users = new ArrayList<>();
			for(Long i:friendIds){
				int index = tmpUsers.indexOf(new UserAccount(i));
				if(index>0){
					users.add(tmpUsers.get(index));
				}
			}
			model.addAttribute("friends", users);
		}
    	return "chat_friends";
    }
	
	@RequestMapping("/setSeenMessage")
	@ResponseBody
    public String setSeenMessage(Model model, @RequestParam Long id, @RequestParam Long fromId){
		chatRepository.setSeenMessage(id, fromId);
    	return "OK";
    }
	
	@RequestMapping("/chat_history_content")
	@ResponseBody
    public String getChatHistoryContent(Model model, @RequestParam Long friendId, @RequestParam int nPage){
		UserAccount currentUser = userRepository.getCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName());
		List<ChatContent> contents = new ArrayList<>();
		int countChat = chatRepository.countChat(currentUser.getId(), friendId);
		System.out.println("Number of chat: " + countChat + " X:" + nPage);
    	if(countChat<=30 && nPage==0){
    		contents = chatRepository.getLastChatContent(currentUser.getId(), friendId);
    	}else if(nPage==0){
    		Pageable pLast30Chat = new PageRequest(nPage, 10, Sort.Direction.DESC, "sentDate");
    		contents = chatRepository.getLast30ChatContent(currentUser.getId(), friendId, pLast30Chat);
    		Collections.reverse(contents);
    	}else if(10*nPage<countChat){
    		Pageable pLast30Chat = new PageRequest(nPage, 10, Sort.Direction.DESC, "sentDate");
    		contents = chatRepository.getLast30ChatContent(currentUser.getId(), friendId, pLast30Chat);
    	}
    	return new JSONArray(contents).toString();
    }
	@RequestMapping("/chat_content")
    public String getChatContent(Model model, @RequestParam Long id){
    	UserAccount currentUser = userRepository.getCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName());
    	model.addAttribute("currentUser", currentUser);
    	model.addAttribute("classId", id);
    	List<ChatContent> contents = new ArrayList<>();
    	
    	int countChat = chatRepository.countChat(currentUser.getId(), id);
    	System.out.println("Number of chat: " + countChat);
    	if(countChat<=30){
    		contents = chatRepository.getLastChatContent(currentUser.getId(), id);
    	}else{
    		Pageable pLast30Chat = new PageRequest(0,20, Sort.Direction.DESC, "id");
    		contents = chatRepository.getLast30ChatContent(currentUser.getId(), id, pLast30Chat);
    		Collections.reverse(contents);
    	}    	
    	model.addAttribute("chatContents", contents);
    	System.out.println("IDDD:" + id);
    	return "chat_content";
    }
	
	
}