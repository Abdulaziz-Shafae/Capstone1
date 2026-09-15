package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "The product id can not be empty")
    @NotBlank(message = "The product id can not be blank")
    @Pattern(regexp = "^\\d{3}$", message = "The product id must be three numbers")
    private String ID;

    @NotEmpty(message = "The user name can not be empty")
    @NotBlank(message = "The user name can not be blank")
    @Size(min=6 , message = "The user name length must be more than 5")
    private String username;

    @NotEmpty(message = "The password can not be empty")
    @NotBlank(message = "The password can not be blank")
    @Pattern(regexp = ".*[A-Z].*" , message = "The password must have at least one uppercase")
    @Pattern(regexp = ".*[a-z].*" , message = "The password must have at least one lowercase")
    @Pattern(regexp = ".*\\d.*" , message = "The password must have at least one digit")
    @Size(min=7 , message = "The password length must be more than 6")
    private String password;

    @NotEmpty(message = "The email can not be empty")
    @NotBlank(message = "The email can not be blank")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "The Email must be valid"
    )
    private String email;

    @NotEmpty(message = "The role can not be empty")
    @NotBlank(message = "The role can not be blank")
    @Pattern(regexp = "^(Admin|Customer|admin|customer)$" , message = "The role must be Admin or Customer")
    private String role;

    @NotNull(message = "The balance can not be empty")
    @Min(value = 0, message = "The balance must be more than 0")
    private double balance;
}
