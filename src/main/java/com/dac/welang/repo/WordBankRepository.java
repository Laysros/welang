package com.dac.welang.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dac.welang.model.WordBank;

public interface WordBankRepository extends 
JpaRepository<WordBank, Integer> {
	@Query("select w from WordBank w where w.languageId = :language")
	List<WordBank> getPhrasesByLanguage(@Param("language")String language, Pageable pageable);

	@Query("select w from WordBank w where w.languageId = :language and w.phrase like %:keyword%")
	List<WordBank> searchByKeyword(@Param("language")String language,@Param("keyword") String keyword, Pageable pageable);
	
	@Query("select w from WordBank w where w.languageId = :language and (w.tag like %:tag% or w.phrase like %:keyword%)")
	List<WordBank> searchByLanguageTagAndKeyword(@Param("language")String language,@Param("tag") String tag, @Param("keyword") String keyword, Pageable pageable);
	
}

