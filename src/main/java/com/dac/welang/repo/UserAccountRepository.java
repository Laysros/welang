package com.dac.welang.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dac.welang.model.UserAccount;

public interface UserAccountRepository extends 
JpaRepository<UserAccount, Integer> {
	@Query("select u from UserAccount u where u.username like %:name% and u.id not in :ids")
	List<UserAccount> getUserByName(@Param("name")String name, @Param("ids") List<Long> ids);
	
	@Query("select u from UserAccount u")
	List<UserAccount> getAllUsers();
	
	@Query("select distinct u from UserAccount u where u.id = :id")
	UserAccount getUserById(@Param("id")Long id);
	
	@Query("select u.username from UserAccount u where u.id = :id")
	String getUserNameById(@Param("id")Long id);
	
	@Query("select id from UserAccount u where u.email = :email")
	Long getUserIdByEmail(@Param("email")String email);
	
	@Query("select id from UserAccount u where u.email = :email and u.userSettings.dateOfBirth = :dateOfBirth")
	Long getUserIdByEmailAndDB(@Param("email")String email, @Param("dateOfBirth")Date dateOfBirth);
	
	@Query("select password from UserAccount u where u.email = :email")
	String getUserPasswordByEmail(@Param("email")String email);

	
	@Query("select distinct u from UserAccount u where u.email = :email")
	UserAccount getCurrentUser(@Param("email")String email);
	
	
	@Query("select friends from UserAccount u where u.email = :email")
	List<UserAccount> getFriendsOfUserEmail(@Param("email")String email);
	
	
	@Query("select email from UserAccount u where u.id = :id")
	String getUserEmailById(@Param("id")Long id);
	
	@Query("select email from UserAccount u where u.userKey = :key")
	String getEmailByKey(@Param("key") String key);
	
	
	@Query("select u from UserAccount u where u.userSettings.nativeLanguage = :language and u.id not in :ids")
	List<UserAccount> getUserByNative(@Param("language")String language, @Param("ids") List<Long> ids);
	
	
	@Query("select u from UserAccount u where u.userSettings.nativeLanguage = :language and u.id not in :ids and u.username like %:name% ")
	List<UserAccount> getUserByNameAndNative(@Param("name")String name,@Param("language")String language, @Param("ids") List<Long> ids);
	
	@Query("select u from UserAccount u join u.userSettings.foreignLanguages f where f.language = :language and  u.id not in :ids")
	List<UserAccount> getUserByForeignLanguage(@Param("language")String language, @Param("ids") List<Long> ids);

	
	@Query("select u from UserAccount u join u.userSettings.foreignLanguages f where f.language = :language and  u.id not in :ids and u.username like %:name%")
	List<UserAccount> getUserByNameAndForeignLanguage(@Param("name")String name,@Param("language")String language, @Param("ids") List<Long> ids);
	
	@Query("select u from UserAccount u join u.userSettings.foreignLanguages f where f.language = :language and u.userSettings.nativeLanguage = :nativeLanguage and u.id not in :ids and u.username like %:name% ")
	List<UserAccount> getUserByNameAndNativeAndForeignLanguage(@Param("name")String name,@Param("nativeLanguage")String nativeLanguage,@Param("language")String learning, @Param("ids") List<Long> ids);
	
	
	
	@Query("select u from UserAccount u where u.id in :ids")
	List<UserAccount> getFriendsById(@Param("ids") List<Long> ids);

	@Query("select u from UserAccount u where u.id not in :ids")
	List<UserAccount> getEligibleFriends(@Param("ids") List<Long> ids);
	
	@Modifying
	@Transactional
	@Query("update UserAccount u set u.password = :password  where u.id = :id ")
	int resetPassword(@Param("id")Long id,@Param("password")String password );
	
	@Modifying
	@Transactional
	@Query("update UserAccount u set u.password = :password  where u.email = :email and u.userKey = :key")
	int resetPasswordByEmail(@Param("email")String email,@Param("key")String key, @Param("password")String password );

	@Modifying
	@Transactional
	@Query("update UserAccount u set u.password = :confirmPass  where u.email = :email and u.password = :oldPass ")
	int changePassword(@Param("email")String email, @Param("oldPass")String oldPass, @Param("confirmPass")String confirmPass);

	@Query("select id from UserAccount u where u.userKey = :key")
	Long checkResetKey(@Param("key") String key);

	@Modifying
	@Transactional
	@Query("update UserAccount u set u.userKey = :userKey  where u.email = :email ")
	int resetUserKey(@Param("email")String email,@Param("userKey")String userKey );
	
}

