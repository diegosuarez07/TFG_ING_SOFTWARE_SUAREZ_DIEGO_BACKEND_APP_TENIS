package com.siglo21.tfg.mapper;

import com.siglo21.tfg.dto.request.UserRequestDto;
import com.siglo21.tfg.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User fromRequest(UserRequestDto requestDto);
}


