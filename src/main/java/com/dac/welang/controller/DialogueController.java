package com.dac.welang.controller;



import java.util.ArrayList;
import java.util.List;
import com.dac.welang.util.DialogueSpliter;
import com.dac.welang.model.Dialogue;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dac.welang.model.Conversation;

import com.dac.welang.model.Notification;
import com.dac.welang.repo.DialogueRepository;
import com.dac.welang.repo.UserAccountRepository;
import com.dac.welang.service.NotificationService;


@Controller
public class DialogueController {
	String titles[] = {
		"Going to University conversation",
		"First day at work",
		"Global warming",
		"Finding a job",
		"Weather"
	};
	
	String titlesKh[] = {
			"ខ្ញុំចូលចិត្ដហែលទឹក",
			"ទៅជួបលោកវេជ្ជបណ្ឌិត",
			"អញ្ជើញពួកម៉ាកទៅពិសារបាយខាងក្រៅ"
		};
	
	String titlesFr[] = {
			"présentation",
			"situation à l’agence immobilière",
			"cours particuliers",
			"Acheter une cravate",
			"A l’agence de location"
		};
	
	@Autowired
	DialogueRepository dialogueRepository;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private UserAccountRepository userRepository;
	
	@RequestMapping(value = "/getContentA", method = RequestMethod.GET)
	@ResponseBody
	public String getContentA(@RequestParam Long id, @RequestParam int index, @RequestParam Long talkWith) {
		Conversation conversation = dialogueRepository.getConversationById(id);
		
		//Create message for user b
		
		if(conversation.getContentA().size()<=index){
			return "===Done===";
		}
		String toId = userRepository.getUserEmailById(talkWith);
		if(conversation.getContentA().size()<=index){
			notificationService.notifyDialogue(new Notification("===Done===", -99L),toId);
		}else{
			notificationService.notifyDialogue(new Notification(conversation.getContentB().get(index), -99L),toId);
		}	
		
		return conversation.getContentA().get(index);
	}
	
	@RequestMapping(value = "/searchDialogueByTag", method = RequestMethod.GET)
	@ResponseBody
	public String searchByLanguageTagAndKeyword(@RequestParam String language,@RequestParam String tag, @RequestParam String keyword) {
		//English
		/*List<Conversation> conversations = new ArrayList<>();
		for(int i=1; i<6; i++){
			DialogueSpliter ds = new DialogueSpliter("src/main/resources/static/text/text" + i + ".txt");
			conversations.add(new Conversation(titles[i-1], "tag:" + i, ds.getContentA(), ds.getContentB()));
		}
		Dialogue dialogue = new Dialogue("English", conversations);
		dialogueRepository.save(dialogue);
		//Khmer
		conversations = new ArrayList<>();
		for(int i=1; i<4; i++){
			DialogueSpliter ds = new DialogueSpliter("src/main/resources/static/text/text_kh_" + i + ".txt");
			conversations.add(new Conversation(titlesKh[i-1], "tag:" + i, ds.getContentA(), ds.getContentB()));
		}
		Dialogue dialogue1 = new Dialogue("Cambodian", conversations);
		dialogueRepository.save(dialogue1);
		//French
		conversations = new ArrayList<>();
		for(int i=1; i<6; i++){
			DialogueSpliter ds = new DialogueSpliter("src/main/resources/static/text/text_fr_" + i + ".txt");
			conversations.add(new Conversation(titlesFr[i-1], "tag:" + i, ds.getContentA(), ds.getContentB()));
		}
		dialogue = new Dialogue("French", conversations);
		dialogueRepository.save(dialogue);*/
	
		
		return new JSONArray(dialogueRepository.getDialogueTitle(language, new PageRequest(0, 5))).toString();
	}
	
}
