package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CharacterizationData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CharacterizationData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CharacterizationDataRepository extends JpaRepository<CharacterizationData, Long> {}
