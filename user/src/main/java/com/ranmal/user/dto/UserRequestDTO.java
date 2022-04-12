package com.ranmal.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotNull(message = "First Name can not be Null")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "First Name should contain Alphabetic Characters only")
    private String firstName;

    @NotNull(message = "Last Name can not be Null")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Last Name should contain Alphabetic Characters only")
    private String lastName;

    @NotNull(message = "Username can not be Null")
    @Pattern(regexp = "^[a-zA-Z0-9|_]*$", message = "Username should contain Alpha-Numeric Characters amd Underscore Character only")
    private String userName;

    @NotNull(message = "Email can not be Null")
    @Email(message = "Not a Valid Email Address")
    private String email;

    @NotNull(message = "Address can not be Null")
    private String address;

    @NotNull(message = "Password can not be Null")
    private String hashCredential;

}
