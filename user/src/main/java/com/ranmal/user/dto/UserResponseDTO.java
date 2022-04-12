package com.ranmal.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private int userId;
    private String firstName;
    private String lastName;
    private String userName;

}
