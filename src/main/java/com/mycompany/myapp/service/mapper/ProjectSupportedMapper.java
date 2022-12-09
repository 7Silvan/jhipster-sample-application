package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CellModel;
import com.mycompany.myapp.domain.ProjectSupported;
import com.mycompany.myapp.service.dto.CellModelDTO;
import com.mycompany.myapp.service.dto.ProjectSupportedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectSupported} and its DTO {@link ProjectSupportedDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectSupportedMapper extends EntityMapper<ProjectSupportedDTO, ProjectSupported> {
    @Mapping(target = "cellModel", source = "cellModel", qualifiedByName = "cellModelId")
    ProjectSupportedDTO toDto(ProjectSupported s);

    @Named("cellModelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CellModelDTO toDtoCellModelId(CellModel cellModel);
}
