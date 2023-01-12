package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CellModel;
import com.mycompany.myapp.domain.CharacterizationData;
import com.mycompany.myapp.service.dto.CellModelDTO;
import com.mycompany.myapp.service.dto.CharacterizationDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CharacterizationData} and its DTO {@link CharacterizationDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface CharacterizationDataMapper extends EntityMapper<CharacterizationDataDTO, CharacterizationData> {
    @Mapping(target = "cellModel", source = "cellModel", qualifiedByName = "cellModelId")
    CharacterizationDataDTO toDto(CharacterizationData s);

    @Named("cellModelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CellModelDTO toDtoCellModelId(CellModel cellModel);
}
