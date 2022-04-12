package com.ranmal.user.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "unique_email_id",
                columnNames = "email"
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String address;
    private String hashCredential;

}
