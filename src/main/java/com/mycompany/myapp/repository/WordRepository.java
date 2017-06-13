package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Word;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Word entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordRepository extends JpaRepository<Word,Long> {
    
}
