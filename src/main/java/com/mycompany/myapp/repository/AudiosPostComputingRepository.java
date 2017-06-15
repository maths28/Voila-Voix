package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AudiosPostComputing;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AudiosPostComputing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudiosPostComputingRepository extends JpaRepository<AudiosPostComputing,Long> {
    
}
