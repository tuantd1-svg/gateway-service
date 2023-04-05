package com.example.gatewayservice.model;




import com.example.commonapi.model.Address;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserRDto {
    private String username;
    @NotNull(message = "email is required")
    @Email
    private String email;
    @NotNull(message = "password is required")
    private String password;
    @NotNull
    private Date dob;
    private String fullName;
    @NotNull(message = "mobile is required")
    private String mobileNo;

    private String packageId;

    private String gender;
    private Address address;

    private String currency;

    private String chanel;
}
