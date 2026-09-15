package com.example.capstone1.Service;

import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    ArrayList<User> users = new ArrayList<>();
    ArrayList<String> history = new ArrayList<>();
    ArrayList<String> requestA = new ArrayList<>();
    ArrayList<String> discount = new ArrayList<>();
    ArrayList<String> requestD = new ArrayList<>();

    private final MerchantStockService merchantStockService;
    private final ProductService productService;
    private final MerchantService merchantService;

    public ArrayList<User> getUsers() {
        return users;
    }

    public int addUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (user.getID().equalsIgnoreCase(users.get(i).getID())) {
                return 0;
            }
        }
        users.add(user);
        return 1;
    }

    public int updateUser(String id, User user) {
        for (int y = 0; y < users.size(); y++) {
            if (id.equalsIgnoreCase(users.get(y).getID())) {
                if (!id.equalsIgnoreCase(user.getID())) {
                    for (int i = 0; i < users.size(); i++) {
                        if (user.getID().equalsIgnoreCase(users.get(i).getID())) {
                            return 0;
                        }
                    }
                }

                user.setBalance(users.get(y).getBalance());
                user.setRole(users.get(y).getRole());
                users.set(y, user);
                return 1;
            }
        }
        return -1;
    }

    public int deleteUser(String id) {
        for (int y = 0; y < users.size(); y++) {
            if (id.equalsIgnoreCase(users.get(y).getID())) {
                users.remove(y);
                return 1;
            }
        }
        return -1;
    }

    public int buyProduct(String userID, String itemID) {

        for (int y = 0; y < users.size(); y++) {

            if (userID.equalsIgnoreCase(users.get(y).getID())) {

                for (int i = 0; i < merchantStockService.getMerchantStocks().size(); i++) {

                    if (itemID.equalsIgnoreCase(merchantStockService.getMerchantStocks().get(i).getID())) {

                        if (merchantStockService.getMerchantStocks().get(i).getStock() == 0) {
                            return 6;
                        }
                        for (int p = 0; p < productService.getProducts().size(); p++) {

                            if (productService.getProducts().get(p).getID().equalsIgnoreCase(
                                    merchantStockService.getMerchantStocks().get(i).getProductID())) {

                                if (users.get(y).getBalance() < productService.getProducts().get(p).getPrice()) {
                                    return 5;
                                }

                                history.add(users.get(y).getID() + " - " + productService.getProducts().get(p));
                                merchantStockService.getMerchantStocks().get(i).setStock(merchantStockService.getMerchantStocks().get(i).getStock() - 1);

                                if(users.get(y).getRole().equalsIgnoreCase("admin")){
                                    users.get(y).setBalance(users.get(y).getBalance() - (productService.getProducts().get(p).getPrice()) * 0.90);
                                }else {

                                    for(int d=0 ; d<discount.size() ; d++ ){
                                        if (discount.get(d).contains(users.get(y).getID())) {
                                            users.get(y).setBalance(users.get(y).getBalance() - (productService.getProducts().get(p).getPrice()) * 0.95);
                                        return 1;
                                        }
                                    }
                                    users.get(y).setBalance(users.get(y).getBalance() - productService.getProducts().get(p).getPrice());
                                }
                                return 1;
                            }

                        }
                    }
                }
                return 3;
            }
        }
        return -1;
    }


    public int refundProduct(String userID, String itemID) {

        for (int y = 0; y < users.size(); y++) {

            if (userID.equalsIgnoreCase(users.get(y).getID())) {

                for (int i = 0; i < merchantStockService.getMerchantStocks().size(); i++) {

                    if (itemID.equalsIgnoreCase(merchantStockService.getMerchantStocks().get(i).getID())) {

                        for (int p = 0; p < productService.getProducts().size(); p++) {

                            if (productService.getProducts().get(p).getID().equalsIgnoreCase(
                                    merchantStockService.getMerchantStocks().get(i).getProductID())) {

                                boolean found=false;
                                for(int h =0 ; h<history.size() ; h++){
                                    if(history.get(h).equalsIgnoreCase(users.get(y).getID() + " - " + productService.getProducts().get(p))){
                                        history.remove(history.get(h));
                                        found=true;
                                    }
                                }

                                if(!found)
                                    return 5;

                                merchantStockService.getMerchantStocks().get(i).setStock(merchantStockService.getMerchantStocks().get(i).getStock() + 1);

                                if(users.get(y).getRole().equalsIgnoreCase("admin")){
                                    users.get(y).setBalance(users.get(y).getBalance() + (productService.getProducts().get(p).getPrice()) * 0.90);
                                }else {

                                    for(int d=0 ; d<discount.size() ; d++ ){
                                        if (discount.get(d).contains(users.get(y).getID())) {
                                            users.get(y).setBalance(users.get(y).getBalance() + (productService.getProducts().get(p).getPrice()) * 0.95);
                                            return 1;
                                        }
                                    }
                                    users.get(y).setBalance(users.get(y).getBalance() + productService.getProducts().get(p).getPrice());
                                }
                                return 1;
                            }

                        }
                    }
                }
                return 3;
            }
        }
        return -1;
    }

    public ArrayList<String> getHistoryID(String id) {

        boolean found=false;
        for(int i =0 ; i< users.size() ; i++ ){
            if(users.get(i).getID().equalsIgnoreCase(id)){
                found=true;
            }
        }
        if(!found) return null;

        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < history.size(); i++) {

            if (history.get(i).startsWith(id + " - ")) {

                result.add(
                        history.get(i).substring((id + " - ").length())
                );
            }
        }

        return result;
    }

    public ArrayList<String> getHistory(String id) {

        for(int i=0 ; i<users.size() ; i++){
            if(users.get(i).getID().equalsIgnoreCase(id)) {
                if (users.get(i).getRole().equalsIgnoreCase("admin")) {
                    return history;
                }

                return null;
            }
        }
        return null;
    }

    public int RequestAdmin(String id){

        boolean found=false;
        for(int i =0 ; i< users.size() ; i++ ){
            if(users.get(i).getID().equalsIgnoreCase(id)){
                if (users.get(i).getRole().equalsIgnoreCase("admin")) {
                    return 5;
                }

                found=true;

            }
        }
        if(!found) return -1;

        ArrayList<String> history = getHistoryID(id);

        if(history.size()<=10){
            return 6;
        }

        for(int i = 0; i< requestA.size() ; i++){
            if(requestA.get(i).contains(id)){

                requestA.set( (requestA.indexOf( requestA.get(i) ) ) , (id + " - " + history.size()) );
                return 1;

            }
        }
            requestA.add(id + " - " + history.size() );

        return 11;

    }

    public ArrayList<String> ReqForAdmShow(String id){

        for(int i=0 ; i<users.size() ; i++){
            if(users.get(i).getID().equalsIgnoreCase(id)) {
                if (users.get(i).getRole().equalsIgnoreCase("admin")) {
                    return requestA;
                }

                return null;
            }
        }
        return null;

    }

    public int ApproveAReq(String id , String Cid){

        boolean found=false;
        for(int i =0 ; i< users.size() ; i++ ){
            if(users.get(i).getID().equalsIgnoreCase(id)){
                if (users.get(i).getRole().equalsIgnoreCase("customer")) {
                    return 5;
                }

                found=true;

            }
        }
        if(!found) return -1;

        found=false;
        for(int i =0 ; i< users.size() ; i++ ){
            if(users.get(i).getID().equalsIgnoreCase(Cid)){

                for(int y = 0; y< requestA.size() ; y++){
                    if(requestA.get(y).contains(Cid)){

                        users.get(i).setRole("admin");
                        found=true;
                        requestA.remove(y);
                        break;
                    }
                }

                if(!found){

                    if(users.get(i).getRole().equalsIgnoreCase("admin")){
                        return 8;
                    }
                    return 7;
                }
            }

        }
        if(found) return 1;

        return 6;
    }

    public ArrayList<String> ShowAllAssets(String id){

        ArrayList<String> result=new ArrayList<>();

        for(int i=0 ; i<users.size() ; i++){
            if(users.get(i).getID().equalsIgnoreCase(id)) {
                if (users.get(i).getRole().equalsIgnoreCase("admin")) {

                    for(int m=0 ; m< merchantService.getMerchants().size() ; m++){
                        double total = 0;
                        for(int ms=0 ; ms < merchantStockService.getMerchantStocks().size() ; ms++){
                            if(merchantStockService.getMerchantStocks().get(ms).getMerchantID().equalsIgnoreCase( merchantService.getMerchants().get(m).getID())){

                                for( int p=0 ; p<productService.getProducts().size() ; p++){
                                    if(productService.getProducts().get(p).getID().equalsIgnoreCase(merchantStockService.getMerchantStocks().get(ms).getProductID())){
                                        total+=productService.getProducts().get(p).getPrice() * merchantStockService.getMerchantStocks().get(ms).getStock();
                                    }
                                }

                            }
                        }
                        result.add("Merchant: " + merchantService.getMerchants().get(m).getName() + ", Asset total: "+ total +" $");

                    }
                    return result;

                }

                return null;
            }
        }

        return null;
    }

    public ArrayList<String> ShowAsset(String id){

        ArrayList<String> result=new ArrayList<>();

        for(int m=0 ; m< merchantService.getMerchants().size() ; m++){

            if(merchantService.getMerchants().get(m).getID().equalsIgnoreCase(id)){
                double total = 0;
                for(int ms=0 ; ms < merchantStockService.getMerchantStocks().size() ; ms++){
                    if(merchantStockService.getMerchantStocks().get(ms).getMerchantID().equalsIgnoreCase( merchantService.getMerchants().get(m).getID())){

                        for( int p=0 ; p<productService.getProducts().size() ; p++){
                            if(productService.getProducts().get(p).getID().equalsIgnoreCase(merchantStockService.getMerchantStocks().get(ms).getProductID())){
                                total+=productService.getProducts().get(p).getPrice() * merchantStockService.getMerchantStocks().get(ms).getStock();
                            }
                        }

                    }
                }
                result.add("Merchant: " + merchantService.getMerchants().get(m).getName() + ", Asset total: "+ total +" $");
                return result;

            }

        }

        return null;

    }


    public int RequestDiscount(String id){

        boolean found=false;
        for(int i =0 ; i< users.size() ; i++ ){
            if(users.get(i).getID().equalsIgnoreCase(id)){
                if (users.get(i).getRole().equalsIgnoreCase("admin")) {
                    return 5;
                }

                found=true;

            }
        }
        if(!found) return -1;

        ArrayList<String> history = getHistoryID(id);

        if(history.size()<=5){
            return 6;
        }

        for(int i = 0; i< requestD.size() ; i++){
            if(requestD.get(i).contains(id)){

                requestD.set( (requestD.indexOf( requestD.get(i) ) ) , (id + " - " + history.size()) );
                return 1;

            }
        }
        requestD.add(id + " - " + history.size() );

        return 11;

    }

    public ArrayList<String> ReqForDisShow(String id){

        for(int i=0 ; i<users.size() ; i++){
            if(users.get(i).getID().equalsIgnoreCase(id)) {
                if (users.get(i).getRole().equalsIgnoreCase("admin")) {
                    return requestD;
                }

                return null;
            }
        }
        return null;

    }

    public int ApproveDReq(String id , String Cid){

        boolean found=false;
        for(int i =0 ; i< users.size() ; i++ ){
            if(users.get(i).getID().equalsIgnoreCase(id)){
                if (users.get(i).getRole().equalsIgnoreCase("customer")) {
                    return 5;
                }

                found=true;

            }
        }
        if(!found) return -1;

        found=false;
        for(int i =0 ; i< users.size() ; i++ ){
            if(users.get(i).getID().equalsIgnoreCase(Cid)){

                if(users.get(i).getRole().equalsIgnoreCase("admin")){
                    return 8;
                }

                for(int y = 0; y< requestD.size() ; y++){

                    if(requestD.get(y).contains(Cid)){

                        discount.add(users.get(i).getID());

                        found=true;
                        requestD.remove(y);
                        break;
                    }
                }

                if(!found){
                    return 7;
                }
            }

        }
        if(found) return 1;

        return 6;
    }

}
