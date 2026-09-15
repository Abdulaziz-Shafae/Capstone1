package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchant(){
        return ResponseEntity.status(200).body( merchantService.getMerchants() );
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        if(merchantService.addMerchants(merchant))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant added"));

        return ResponseEntity.status(200).body(new ApiResponse("The id is taken"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id,@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        int result = merchantService.updateMerchants(id,merchant);
        if(result==-1){
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        if(result==0){
            return ResponseEntity.status(400).body(new ApiResponse("The new id in taken"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Merchant updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){


        if(merchantService.deleteMerchants(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }



}
