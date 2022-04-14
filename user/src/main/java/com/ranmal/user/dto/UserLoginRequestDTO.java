package com.ranmal.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDTO {

    @NotNull(message = "Email can not be Null")
    @Email(message = "Not a Valid Email Address")
    private String email;

    @NotNull(message = "Password can not be Null")
    private String hashCredential;

}
