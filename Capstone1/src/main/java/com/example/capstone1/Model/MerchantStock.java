package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "The id can not be empty")
    @NotBlank(message = "The id can not be blank")
    @Pattern(regexp = "^\\d{3}$", message = "The ID must be three numbers")
    private String ID;

    @NotEmpty(message = "The product id can not be empty")
    @NotBlank(message = "The product id can not be blank")
    @Pattern(regexp = "^\\d{3}$", message = "The product id must be three numbers")
    private String productID;

    @NotEmpty(message = "The merchant id can not be empty")
    @NotBlank(message = "The merchant id can not be blank")
    @Pattern(regexp = "^\\d{3}$", message = "The merchant id must be three numbers")
    private String merchantID;


    @NotNull(message = "The stock can not be empty")
//the min 10 will be checked when adding
    private int stock;

}
