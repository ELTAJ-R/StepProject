package org.eltaj.step.Entities;

import lombok.*;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class User {
    private int id;
    private String name;
    private String surname;
    private String picture;
    private String password;
    private String email;
    private String lastLogin;

}
