package com.example.gatewayservice.mapper;

import com.example.commonapi.model.User;
import com.example.gatewayservice.model.UserRDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserRDto map(User user)
    {
        if(user!=null)
        {
            UserRDto userRDto = new UserRDto();
            userRDto.setUsername(user.getUsername());
            userRDto.setFullName(user.getFullName());
            userRDto.setDob(user.getDob());
            userRDto.setAddress(user.getAddress());
            userRDto.setPassword(user.getPassword());
            userRDto.setEmail(user.getEmail());
            userRDto.setGender(user.getGender());
            userRDto.setMobileNo(user.getMobileNo());
            return userRDto;
        }
        return null;
    }
}
