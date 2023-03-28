package com.kangjun.common.mapstruct.mapstruct;

import com.kangjun.common.mapstruct.User;
import com.kangjun.common.mapstruct.UserRegisterReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserConverter {
    /**
     * 固定写法：Mappers.getMapper(接口名.class);
     */
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    User toUser(UserRegisterReq req);
}