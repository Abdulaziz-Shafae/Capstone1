package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryService categoryService;

    ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts(){
        return products;
    }

    public int addProduct(Product product){
        
        for(int i=0 ; i<products.size() ; i++){
            if(product.getID().equalsIgnoreCase(products.get(i).getID())){
                return 0;
            }
        }

        for(int i=0 ; i<categoryService.getCategories().size() ; i++){
            if( product.getCategoryID().equalsIgnoreCase(categoryService.getCategories().get(i).getID())){
                products.add(product);
                return 1;
            }
        }

        return 2;
    }

    public int updateProduct(String id ,Product product){

        for(int y=0 ; y<products.size() ; y++){

            if(products.get(y).getID().equalsIgnoreCase(id)){

                if( !products.get(y).getID().equalsIgnoreCase(product.getID())){
                    for(int i=0 ; i<products.size() ; i++){
                        if(product.getID().equalsIgnoreCase(products.get(i).getID())){
                            return 0;
                        }
                    }
                }

                for(int i=0 ; i<categoryService.getCategories().size() ; i++){

                    if( product.getCategoryID().equalsIgnoreCase(categoryService.getCategories().get(i).getID())){
                        products.set(y,product);
                        return 1;
                    }

                }
                return 2;
            }
        }

        return -1;

    }

    public int deleteProduct(String id){

        for(int y=0 ; y<products.size() ; y++){

            if(products.get(y).getID().equalsIgnoreCase(id)){
                products.remove(y);
                return 1;
            }
        }

        return -1;

    }


}
