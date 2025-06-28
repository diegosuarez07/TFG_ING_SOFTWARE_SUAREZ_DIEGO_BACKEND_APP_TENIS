package com.siglo21.tfg.mapper;

import com.siglo21.tfg.dto.request.UserRequestDto;
import com.siglo21.tfg.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    User fromRequest(UserRequestDto requestDto);
}


