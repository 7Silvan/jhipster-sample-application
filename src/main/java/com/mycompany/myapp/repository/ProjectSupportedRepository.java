package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProjectSupported;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProjectSupported entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectSupportedRepository extends JpaRepository<ProjectSupported, Long> {}
