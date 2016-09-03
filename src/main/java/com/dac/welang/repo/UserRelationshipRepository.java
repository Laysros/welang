package com.dac.welang.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.dac.welang.model.UserRelationship;

public interface UserRelationshipRepository extends 
JpaRepository<UserRelationship, Integer> {
	@Query("select addFrom from UserRelationship ur where ur.addTo = :addTo and ur.confirmed = false ")
	List<Long> getFriendRequests(@Param("addTo")Long addTo);
	
	@Query("select addFrom from UserRelationship ur where ur.addTo = :addTo")
	List<Long> getRequestedFriends(@Param("addTo")Long addTo);
	
	@Query("select addTo from UserRelationship ur where ur.addFrom = :addFrom")
	List<Long> getAddedFriends(@Param("addFrom")Long addFrom);
	
	@Modifying
	@Query("update UserRelationship u set u.confirmed = 1, u.confirmedDate = CURRENT_DATE where u.addTo = ?1")
	int updateFriendship(Long ownerId);
	
	@Query("select count(addFrom) from UserRelationship ur where ur.addTo = :addTo and ur.confirmed = false ")
	int getNumberFriendRequest(@Param("addTo")Long addTo);

	@Query("select ur from UserRelationship ur where ur.addTo = ?1 and ur.addFrom = ?2")
	UserRelationship getCurrentFriendShip(Long ownerId, Long friendId);
}