package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private final ProductService productService;
    private final MerchantService merchantService;

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public ArrayList<MerchantStock> getMerchantStocks(){
        return merchantStocks;
    }


    public int addMerchantStock(MerchantStock merchantStock){
        for( int i = 0 ; i< merchantStocks.size() ; i++){
            if(merchantStock.getID().equalsIgnoreCase(merchantStocks.get(i).getID())){
                return 0;
            }
        }

        boolean check = false;
        for( int i = 0 ; i< productService.getProducts().size() ; i++){
            if(merchantStock.getProductID().equalsIgnoreCase(productService.getProducts().get(i).getID())){
                check=true;
            }
        }
        if(!check) return 3;

        check = false;
        for(int i = 0; i< merchantService.getMerchants().size() ; i++){
            if(merchantStock.getMerchantID().equalsIgnoreCase(merchantService.getMerchants().get(i).getID())){
                check=true;
            }
        }
        if(!check) return 2;

        check = false;
        if(merchantStock.getStock()>10){
            check=true;
        }
        if(!check) return 4;

        merchantStocks.add(merchantStock);
        return 1;

    }

    public int updateMerchantStock(String id, MerchantStock merchantStock){

        for(int y =0 ; y< merchantStocks.size() ; y++) {

            if(id.equalsIgnoreCase(merchantStocks.get(y).getID())) {

                if(!id.equalsIgnoreCase(merchantStock.getID())) {
                    for (int i = 0; i < merchantStocks.size(); i++) {
                        if (merchantStock.getID().equalsIgnoreCase(merchantStocks.get(i).getID())) {
                            return 0;
                        }
                    }
                }

                boolean check = false;
                for (int i = 0; i < productService.getProducts().size(); i++) {
                    if (merchantStock.getProductID().equalsIgnoreCase(productService.getProducts().get(i).getID())) {
                        check = true;
                    }
                }
                if (!check) return 3;

                check = false;
                for (int i = 0; i < merchantService.getMerchants().size(); i++) {
                    if (merchantStock.getMerchantID().equalsIgnoreCase(merchantService.getMerchants().get(i).getID())) {
                        check = true;
                    }
                }
                if (!check) return 2;

                merchantStock.setStock(merchantStocks.get(y).getStock());

                merchantStocks.set(y, merchantStock);
                return 1;
            }
        }
        return -1;
    }

    public int deleteMerchantStock(String id){

        for(int y =0 ; y< merchantStocks.size() ; y++) {

            if(id.equalsIgnoreCase(merchantStocks.get(y).getID())) {

                merchantStocks.remove(y);
                return 1;
            }
        }
        return -1;
    }

    public int addStock(String productID, String merchantID, int amount) {

        if (amount < 1) {
            return 5;
        }

        boolean productFound = false;
        for (int i = 0; i < productService.getProducts().size(); i++) {
            if (productService.getProducts().get(i).getID().equalsIgnoreCase(productID)) {
                productFound = true;
                break;
            }
        }

        if (!productFound) {
            return 2;
        }

        boolean merchantFound = false;
        for (int i = 0; i < merchantService.getMerchants().size(); i++) {
            if (merchantService.getMerchants().get(i).getID().equalsIgnoreCase(merchantID)) {
                merchantFound = true;
                break;
            }
        }

        if (!merchantFound) {
            return 3;
        }

        for (int i = 0; i < merchantStocks.size(); i++) {

            if (merchantStocks.get(i).getProductID().equalsIgnoreCase(productID)
                    && merchantStocks.get(i).getMerchantID().equalsIgnoreCase(merchantID)) {

                merchantStocks.get(i).setStock(
                        merchantStocks.get(i).getStock() + amount
                );

                return 1;
            }
        }

        return -1;
    }


//this will return all the product that match this merchant
    public ArrayList<Product> getByMerchant(String id){

        ArrayList<Product> result= new ArrayList<>();
        String MID="";

        for(int i=0 ; i<merchantService.getMerchants().size() ; i++){
            if(merchantService.getMerchants().get(i).getID().equalsIgnoreCase(id)){
                MID=merchantService.getMerchants().get(i).getID();
                break;
            }
        }

        if(MID.equalsIgnoreCase("")) {
            return null;
        }


        for(int i = 0; i< merchantStocks.size() ; i++){

            if( merchantStocks.get(i).getMerchantID().equalsIgnoreCase(MID)){
                for(int y = 0 ; y< productService.getProducts().size() ; y++){
                    if(productService.getProducts().get(y).getID().equalsIgnoreCase( merchantStocks.get(i).getProductID() )){
                        result.add(productService.getProducts().get(y));
                    }
                }
            }
        }
        return result;
    }

//this will return the product name and price, merchant name, and id
    public ArrayList<String> getProductInfo(String id) {
        ArrayList<String> result=new ArrayList<>();

        for (int y = 0; y < merchantStocks.size(); y++){

            if(id.equalsIgnoreCase(merchantStocks.get(y).getID())) {
                result.add("ID: "+ id);

                for (int i = 0; i < merchantService.getMerchants().size(); i++) {
                    if(merchantService.getMerchants().get(i).getID().equalsIgnoreCase(merchantStocks.get(y).getMerchantID())){
                        result.add("Merchant Name: " + merchantService.getMerchants().get(i).getName());
                        break;
                    }
                }

                for (int i = 0; i < productService.getProducts().size(); i++) {
                    if(productService.getProducts().get(i).getID().equalsIgnoreCase(merchantStocks.get(y).getProductID())){
                        result.add("Product Name: " + productService.getProducts().get(i).getName());
                        result.add("Product Price: " + productService.getProducts().get(i).getPrice());
                        break;
                    }
                }

                break;
            }
        }
        return result;
    }


}
