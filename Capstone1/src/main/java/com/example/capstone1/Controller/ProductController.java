package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

     private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProduct(){
        return ResponseEntity.status(200).body(productService.getProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product , Errors errors){

        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        int result= productService.addProduct(product);

        if(result==0){
            return ResponseEntity.status(400).body(new ApiResponse( "The id in taken" ) );
        }

        if(result==1){
            return ResponseEntity.status(200).body(new ApiResponse( "Product added" ) );
        }

        return ResponseEntity.status(400).body(new ApiResponse( "No matching category" ) );

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id,@RequestBody @Valid Product product , Errors errors){

        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        int result= productService.updateProduct(id,product);


        if(result==-1){
            return ResponseEntity.status(400).body(new ApiResponse( "Product not found" ) );
        }

        if(result==0){
            return ResponseEntity.status(400).body(new ApiResponse( "The new id in taken" ) );
        }

        if(result==1){
            return ResponseEntity.status(200).body(new ApiResponse( "Product updated" ) );
        }

        return ResponseEntity.status(400).body(new ApiResponse( "No matching category" ) );

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){

        int result= productService.deleteProduct(id);

        if(result==-1){
            return ResponseEntity.status(400).body(new ApiResponse( "Product not found" ) );
        }

        return ResponseEntity.status(200).body(new ApiResponse( "Product deleted" ) );

    }
}
