package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CellModel;
import com.mycompany.myapp.domain.CellType;
import com.mycompany.myapp.service.dto.CellModelDTO;
import com.mycompany.myapp.service.dto.CellTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CellType} and its DTO {@link CellTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CellTypeMapper extends EntityMapper<CellTypeDTO, CellType> {
    @Mapping(target = "cellModel", source = "cellModel", qualifiedByName = "cellModelId")
    CellTypeDTO toDto(CellType s);

    @Named("cellModelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CellModelDTO toDtoCellModelId(CellModel cellModel);
}
