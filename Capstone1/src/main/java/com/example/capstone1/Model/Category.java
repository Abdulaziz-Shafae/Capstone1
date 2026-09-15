package com.example.capstone1.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "The id can not be empty")
    @NotBlank(message = "The id can not be blank")
    @Pattern(regexp = "^\\d{3}$", message = "The ID must be three numbers")
    private String ID;

    @NotEmpty(message = "The name can not be empty")
    @NotBlank(message = "The name can not be blank")
    @Size(min=4 , message = "The name length must be more than 3")
    private String name;
}
