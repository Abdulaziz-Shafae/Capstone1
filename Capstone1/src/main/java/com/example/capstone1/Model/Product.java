package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "The id can not be empty")
    @NotBlank(message = "The id can not be blank")
    @Pattern(regexp = "^\\d{3}$", message = "The id must be three numbers")
    private String ID;


    @NotEmpty(message = "The name can not be empty")
    @NotBlank(message = "The name can not be blank")
    @Size(min=4 , message = "The name length must be more than 3")
    private String name;

    @NotNull(message = "The price can not be empty")
    @Min(value = 0 , message = "The price must be more than 0")
    private double price;

    @NotEmpty(message = "The category id can not be empty")
    @NotBlank(message = "The category id can not be blank")
    @Pattern(regexp = "^\\d{3}$", message = "The category id must be three numbers")
    private String categoryID;




}
