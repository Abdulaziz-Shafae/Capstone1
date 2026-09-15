package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    public boolean addMerchants(Merchant merchant){

        for(int i =0 ; i<merchants.size() ; i++){
            if(merchants.get(i).getID().equalsIgnoreCase(merchant.getID())){
                return false;
            }
        }
        merchants.add(merchant);
        return true;
    }

    public int updateMerchants(String id ,Merchant merchant){

        for(int i = 0 ; i<merchants.size() ; i++){

            if( merchants.get(i).getID().equalsIgnoreCase(id)){

                if(!id.equalsIgnoreCase(merchant.getID())) {

                    for (int y = 0; y < merchants.size(); y++) {
                        if (merchants.get(y).getID().equalsIgnoreCase(merchant.getID())) {
                            return 0;
                        }

                    }
                }

                merchants.set(i,merchant);
                return 1;

            }
        }

        return -1;
    }

    public boolean deleteMerchants(String id){

        for(int i = 0 ; i<merchants.size() ; i++){

            if( merchants.get(i).getID().equalsIgnoreCase(id)){
                merchants.remove(i);
                return true;

            }
        }

        return false;
    }

}
