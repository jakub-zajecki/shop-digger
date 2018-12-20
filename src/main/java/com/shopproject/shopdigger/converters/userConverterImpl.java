package com.shopproject.shopdigger.converters;

import com.shopproject.shopdigger.dto.UserDto;
import com.shopproject.shopdigger.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class userConverterImpl implements userConverter{


    @Override
    public User convert(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        user.setLogin(userDto.getLogin());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(DigestUtils.md5Hex(userDto.getPassword()));
        user.setToken(UUID.randomUUID().toString());
        user.setMail(userDto.getMail());
        return user;
    }

    @Override
    public UserDto convertDto(User customer) {
        return null;
    }
}