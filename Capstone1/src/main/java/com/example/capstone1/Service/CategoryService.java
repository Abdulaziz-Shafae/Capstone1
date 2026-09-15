package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getCategories(){
        return categories;
    }

    public boolean addCategories(Category category){

        for(int i =0 ; i<categories.size() ; i++){
            if(categories.get(i).getID().equalsIgnoreCase(category.getID())){
                return false;
            }
        }
        categories.add(category);
        return true;
    }

    public int updateCategories(String id ,Category category){



        for(int i = 0 ; i<categories.size() ; i++){

            if( categories.get(i).getID().equalsIgnoreCase(id)){

                if(!id.equalsIgnoreCase(category.getID())) {

                    for (int y = 0; y < categories.size(); y++) {
                        if (categories.get(y).getID().equalsIgnoreCase(category.getID())) {
                            return 0;
                        }

                    }
                }

                categories.set(i,category);
                return 1;

            }
        }

        return -1;
    }

    public boolean deleteCategories(String id){

        for(int i = 0 ; i<categories.size() ; i++){

            if( categories.get(i).getID().equalsIgnoreCase(id)){
                categories.remove(i);
                return true;

            }
        }

        return false;
    }
}
