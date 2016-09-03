package com.dac.welang.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dac.welang.model.ChatContent;

public interface ChatContentRepository extends 
JpaRepository<ChatContent, Integer> {
	@Query("select c from ChatContent c where ( c.fromId = :fromId and c.toId = :toId ) or ( c.fromId = :toId and c.toId = :fromId )")
	List<ChatContent> getLastChatContent(@Param("fromId")Long fromId, @Param("toId")Long toId);
	
	@Query("select c from ChatContent c where ( c.fromId = :fromId and c.toId = :toId ) or ( c.fromId = :toId and c.toId = :fromId )")
	List<ChatContent> getLast30ChatContent(@Param("fromId")Long fromId, @Param("toId")Long toId , Pageable pageable);
	
	
	@Query("select distinct fromId from ChatContent c where c.toId = ?1")
	List<Long> getFriendIdChatWithUserId1(Long id);
	
	@Query("select distinct toId from ChatContent c where c.fromId = ?1")
	List<Long> getFriendIdChatWithUserId2(Long id);
	
	@Query("select count(*) from ChatContent c where ( c.fromId = :fromId and c.toId = :toId ) or ( c.fromId = :toId and c.toId = :fromId )")
	int countChat(@Param("fromId")Long fromId, @Param("toId")Long toId);

	@Query("select count(*) from ChatContent c where c.fromId = :fromId and c.toId = :toId and c.seen = false")
	Integer countUnseenMessagefromUserId(@Param("fromId")Long fromId, @Param("toId")Long toId);
	
	@Modifying
	@Transactional
	@Query("update ChatContent c set c.seen = true where c.fromId = :fromId and c.toId = :toId")
	int setSeenMessage(@Param("toId")Long toId, @Param("fromId")Long fromId);

	
	
}


