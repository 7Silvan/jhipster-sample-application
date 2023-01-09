package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CellType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CellType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CellTypeRepository extends JpaRepository<CellType, Long> {}
