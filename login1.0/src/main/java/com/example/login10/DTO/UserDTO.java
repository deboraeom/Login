package com.example.login10.DTO;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty
    @NotNull
    @Size(min=1, max=30)
    private String name;

    @NotEmpty
    @NotNull
    @Size(min=1, max=30)
    private String email;

    @NotEmpty
    @NotNull
    @Size(min=1, max=30)
    private String password;




    public UserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }
}
