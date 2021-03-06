package com.shopproject.shopdigger.converters;

import com.shopproject.shopdigger.dto.UserDto;
import com.shopproject.shopdigger.model.User;

public interface UserConverter {

    public User convert(UserDto customerDto);

    public UserDto convertDto(User customer);


}
