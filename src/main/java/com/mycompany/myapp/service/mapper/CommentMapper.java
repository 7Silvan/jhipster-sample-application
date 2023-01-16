package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CellModel;
import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.service.dto.CellModelDTO;
import com.mycompany.myapp.service.dto.CommentDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "cellModel", source = "cellModel", qualifiedByName = "cellModelId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    CommentDTO toDto(Comment s);

    @Named("cellModelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CellModelDTO toDtoCellModelId(CellModel cellModel);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
