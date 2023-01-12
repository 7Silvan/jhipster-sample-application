package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CellModel;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CellModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CellModelRepository extends JpaRepository<CellModel, String>, JpaSpecificationExecutor<CellModel> {
    @Query("select cellModel from CellModel cellModel where cellModel.user.login = ?#{principal.username}")
    List<CellModel> findByUserIsCurrentUser();
}
