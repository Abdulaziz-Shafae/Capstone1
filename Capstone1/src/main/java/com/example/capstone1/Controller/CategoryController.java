package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?> getCategory(){
        return ResponseEntity.status(200).body( categoryService.getCategories() );
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        if(categoryService.addCategories(category))
            return ResponseEntity.status(200).body(new ApiResponse("Category added"));

        return ResponseEntity.status(200).body(new ApiResponse("The id is taken"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id,@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        int result = categoryService.updateCategories(id,category);
        if(result==-1){
            return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
        }
        if(result==0){
            return ResponseEntity.status(400).body(new ApiResponse("The new id in taken"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Category updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){


        if(categoryService.deleteCategories(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Category deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
    }



}
