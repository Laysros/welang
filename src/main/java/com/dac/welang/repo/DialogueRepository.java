package com.dac.welang.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dac.welang.model.Conversation;
import com.dac.welang.model.Dialogue;

public interface DialogueRepository extends 
JpaRepository<Dialogue, Integer> {
	//@Query("select c.title from Dialogue d JOIN d.conversations c where d.language = :language")
	//List<String> getDialogueTitle(@Param("language")String language, Pageable pageable);
	
	@Query("select d.conversations from Dialogue d where d.language = :language")
	List<Conversation> getDialogues(@Param("language")String language, Pageable pageable);
	
	
	@Query("select c.id, c.title from Dialogue d JOIN d.conversations c where d.language = :language")
	List<?> getDialogueTitle(@Param("language")String language, Pageable pageable);
	
	 
	
	@Query("select c from Dialogue d JOIN d.conversations c where c.id = :id")
	Conversation getConversationById(@Param("id")Long id);
}

