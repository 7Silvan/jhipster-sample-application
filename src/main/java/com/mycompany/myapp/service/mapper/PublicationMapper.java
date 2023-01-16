package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CellModel;
import com.mycompany.myapp.domain.Publication;
import com.mycompany.myapp.service.dto.CellModelDTO;
import com.mycompany.myapp.service.dto.PublicationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Publication} and its DTO {@link PublicationDTO}.
 */
@Mapper(componentModel = "spring")
public interface PublicationMapper extends EntityMapper<PublicationDTO, Publication> {
    @Mapping(target = "cellModel", source = "cellModel", qualifiedByName = "cellModelId")
    PublicationDTO toDto(Publication s);

    @Named("cellModelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CellModelDTO toDtoCellModelId(CellModel cellModel);
}
