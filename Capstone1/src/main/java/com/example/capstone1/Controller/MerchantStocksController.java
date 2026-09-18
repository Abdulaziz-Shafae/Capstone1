package com.example.capstone1.Controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class MerchantStocksController {

    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity<?> getSMtocks(){
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMStock(@RequestBody @Valid MerchantStock merchantStock , Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        int result= merchantStockService.addMerchantStock(merchantStock);
        if(result==0)
            return ResponseEntity.status(400).body(new ApiResponse("The id is taken"));
        if(result==2)
            return ResponseEntity.status(400).body(new ApiResponse("No matching merchant"));
        if(result==3)
            return ResponseEntity.status(400).body(new ApiResponse("No matching product"));
        if(result==4)
            return ResponseEntity.status(400).body(new ApiResponse("The stock must be more than 10"));

        return ResponseEntity.status(200).body(new ApiResponse("Merchant stock added"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMStock(@PathVariable String id,@RequestBody @Valid MerchantStock merchantStock , Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        int result= merchantStockService.updateMerchantStock(id , merchantStock);
        if(result==-1)
            return ResponseEntity.status(400).body(new ApiResponse("Merchant stock not found"));
        if(result==0)
            return ResponseEntity.status(400).body(new ApiResponse("The new id is taken"));
        if(result==2)
            return ResponseEntity.status(400).body(new ApiResponse("No matching merchant"));
        if(result==3)
            return ResponseEntity.status(400).body(new ApiResponse("No matching product"));

        return ResponseEntity.status(200).body(new ApiResponse("Merchant stock updated"));

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMStock(@PathVariable String id){

        int result= merchantStockService.deleteMerchantStock(id);
        if(result==-1)
            return ResponseEntity.status(400).body(new ApiResponse("Merchant stock not found"));

        return ResponseEntity.status(200).body(new ApiResponse("Merchant stock deleted"));

    }

    @PutMapping("/add/stock/{productID}/{merchantID}/{amount}")
    public ResponseEntity<?> addStock(@PathVariable String productID, @PathVariable String merchantID, @PathVariable int amount) {

        int result = merchantStockService.addStock(productID, merchantID, amount);

        if (result == 5)
            return ResponseEntity.status(400).body(new ApiResponse("The stock must be more than 0"));

        if (result == 2)
            return ResponseEntity.status(400).body(new ApiResponse("Product ID not found"));

        if (result == 3)
            return ResponseEntity.status(400).body(new ApiResponse("Merchant ID not found"));

        if (result == -1)
            return ResponseEntity.status(400).body(new ApiResponse("The merchant does not have this product"));

        return ResponseEntity.status(200).body(new ApiResponse("Stock added"));
    }

    @GetMapping("/get/merchant/{merchantid}")
    public ResponseEntity<?> getByMerchant(@PathVariable String merchantid){
        ArrayList<Product> result = merchantStockService.getByMerchant(merchantid);

        if (result == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        if (result.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("The merchant dont have any products yet"));
        }

        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/get/stock/{id}")
    public ResponseEntity<?> getProductInfo(@PathVariable String id){
        ArrayList<String> result = merchantStockService.getProductInfo(id);

        if (result.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }

        return ResponseEntity.status(200).body(result);
    }

}
