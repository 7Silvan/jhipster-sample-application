package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CellModel;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.service.dto.CellModelDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CellModel} and its DTO {@link CellModelDTO}.
 */
@Mapper(componentModel = "spring")
public interface CellModelMapper extends EntityMapper<CellModelDTO, CellModel> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    CellModelDTO toDto(CellModel s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
