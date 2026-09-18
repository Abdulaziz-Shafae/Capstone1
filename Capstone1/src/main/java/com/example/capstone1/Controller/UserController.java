package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.status(200).body( userService.getUsers() );
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user , Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(userService.addUser(user)==0 ){
            return ResponseEntity.status(400).body(new ApiResponse( "The id in taken" ) );
        }
        return ResponseEntity.status(200).body(new ApiResponse("User added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id ,@RequestBody @Valid User user , Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        int result=userService.updateUser(id,user);
        if(result==-1){
            return ResponseEntity.status(400).body(new ApiResponse( "The User not found" ) );
        }
        if(result==0 ){
            return ResponseEntity.status(400).body(new ApiResponse( "The id in taken" ) );
        }

        return ResponseEntity.status(200).body(new ApiResponse("User updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){

        int result=userService.deleteUser(id);
        if(result==-1){
            return ResponseEntity.status(400).body(new ApiResponse( "The User not found" ) );
        }

        return ResponseEntity.status(200).body(new ApiResponse("User deleted"));
    }

    @PutMapping("/{userID}/buy/{productID}/{merchantID}")
    public ResponseEntity<?> buy(@PathVariable String userID,
                                 @PathVariable String productID,
                                 @PathVariable String merchantID) {

        int result = userService.buyProduct(userID, productID, merchantID);

        if (result == -1)
            return ResponseEntity.status(400)
                    .body(new ApiResponse("The User not found"));

        if (result == 2)
            return ResponseEntity.status(400)
                    .body(new ApiResponse("The Product not found"));

        if (result == 4)
            return ResponseEntity.status(400)
                    .body(new ApiResponse("The Merchant not found"));

        if (result == 3)
            return ResponseEntity.status(400)
                    .body(new ApiResponse("The Merchant does not have this product"));

        if (result == 5)
            return ResponseEntity.status(400)
                    .body(new ApiResponse("Insufficient balance"));

        if (result == 6)
            return ResponseEntity.status(400)
                    .body(new ApiResponse("Out of stock"));

        return ResponseEntity.status(200)
                .body(new ApiResponse("Item purchased"));
    }


    @PutMapping("/{userID}/refund/{productID}/{merchantID}")
    public ResponseEntity<?> refund(@PathVariable String userID, @PathVariable String productID, @PathVariable String merchantID) {

        int result = userService.refundProduct(userID, productID, merchantID);

        if (result == -1)
            return ResponseEntity.status(400).body(new ApiResponse("The User not found"));

        if (result == 3)
            return ResponseEntity.status(400).body(new ApiResponse("The product was not found for this merchant"));

        if (result == 5)
            return ResponseEntity.status(400).body(new ApiResponse("The product was not purchased"));

        return ResponseEntity.status(200).body(new ApiResponse("Item refunded"));
    }


    @GetMapping("/get/history/customer/{id}")
    public ResponseEntity<?> getHistoryID(@PathVariable String id){
        ArrayList<String> result = userService.getHistoryID(id);

        if(result == null)
            return ResponseEntity.status(400).body(new ApiResponse("User not found"));

        if(result.isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("The user doesn't have any history"));

        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/get/history/admin/{id}")
    public ResponseEntity<?> getHistory(@PathVariable String id){
        ArrayList<String> result = userService.getHistory(id);

        if(result == null)
            return ResponseEntity.status(400).body(new ApiResponse("user not found or access denied"));

        if(result.isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No history"));

        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/req/admin/{id}")
    public  ResponseEntity<?> RequestAdmin(@PathVariable String id){
        int result= userService.RequestAdmin(id);

        if(result==-1)
            return ResponseEntity.status(400).body(new ApiResponse("User not found"));

        if(result==5)
            return ResponseEntity.status(400).body(new ApiResponse("You are already an admin"));

        if(result==6)
            return ResponseEntity.status(400).body(new ApiResponse("Not qualified yet"));

        if(result==1)
            return ResponseEntity.status(200).body(new ApiResponse("Your Request has been updated"));


        return ResponseEntity.status(200).body(new ApiResponse("Your Request has been submitted"));

    }

    @GetMapping("/get/reqs/admin/{id}")
    public ResponseEntity<?> ReqForAdmShow(@PathVariable String id){
        ArrayList<String> result = userService.ReqForAdmShow(id);

        if(result == null)
            return ResponseEntity.status(400).body(new ApiResponse("User not found or access denied"));

        if(result.isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No Requests"));

        return ResponseEntity.status(200).body(result);
    }

    @PutMapping("/appa/admin/{id}/{Cid}")
    public ResponseEntity<?> ApproveAdmReq(@PathVariable String id ,@PathVariable String Cid){
        int result = userService.ApproveAReq(id , Cid);

        if(result == -1)
            return ResponseEntity.status(400).body(new ApiResponse("User not found"));

        if(result == 5)
            return ResponseEntity.status(400).body(new ApiResponse("Access denied"));

        if(result == 6)
            return ResponseEntity.status(400).body(new ApiResponse("Customer not found"));

        if(result == 7)
            return ResponseEntity.status(400).body(new ApiResponse("The customer didn't request an admin"));
        if(result == 8)
            return ResponseEntity.status(400).body(new ApiResponse("He is an admin"));

        return ResponseEntity.status(200).body(new ApiResponse("The customer is now an admin"));

    }

    @GetMapping("/get/assets/{id}")
    public ResponseEntity<?> getAllAssets(@PathVariable String id){
        ArrayList<String> result = userService.ShowAllAssets(id);

        if(result == null)
            return ResponseEntity.status(400).body(new ApiResponse("User not found or access denied"));

        if(result.isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No assets"));

        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/get/asset/{id}")
    public ResponseEntity<?> getAsset(@PathVariable String id){
        ArrayList<String> result = userService.ShowAsset(id);

        if(result == null)
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));

        if(result.isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No assets"));

        return ResponseEntity.status(200).body(result);
    }


    @GetMapping("/req/discount/{id}")
    public  ResponseEntity<?> RequestDiscount(@PathVariable String id){
        int result= userService.RequestDiscount(id);

        if(result==-1)
            return ResponseEntity.status(400).body(new ApiResponse("User not found"));

        if(result==5)
            return ResponseEntity.status(400).body(new ApiResponse("You are already an admin you have 10%"));

        if(result==6)
            return ResponseEntity.status(400).body(new ApiResponse("Not qualified yet"));

        if(result==1)
            return ResponseEntity.status(200).body(new ApiResponse("Your Request has been updated"));


        return ResponseEntity.status(200).body(new ApiResponse("Your Request has been submitted"));

    }

    @GetMapping("/get/reqs/discount/{id}")
    public ResponseEntity<?> ReqForDisShow(@PathVariable String id){
        ArrayList<String> result = userService.ReqForDisShow(id);

        if(result == null)
            return ResponseEntity.status(400).body(new ApiResponse("User not found or access denied"));

        if(result.isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No Requests"));

        return ResponseEntity.status(200).body(result);
    }

    @PutMapping("/appd/admin/{id}/{Cid}")
    public ResponseEntity<?> ApproveDisReq(@PathVariable String id ,@PathVariable String Cid){
        int result = userService.ApproveDReq(id , Cid);

        if(result == -1)
            return ResponseEntity.status(400).body(new ApiResponse("User not found"));

        if(result == 5)
            return ResponseEntity.status(400).body(new ApiResponse("Access denied"));

        if(result == 6)
            return ResponseEntity.status(400).body(new ApiResponse("Customer not found"));

        if(result == 7)
            return ResponseEntity.status(400).body(new ApiResponse("The customer didn't request a discount"));
        if(result == 8)
            return ResponseEntity.status(400).body(new ApiResponse("He is an admin he have 10%"));

        return ResponseEntity.status(200).body(new ApiResponse("The customer now have 5%"));

    }


}
