package com.project.tubespbokel4.model.toko;

import com.project.tubespbokel4.model.auth.UserStore;
import javafx.scene.image.Image;

public class Product {
    private int id;
    private int store_id;
    private String name;
    private ProductType type;
    private int stock;
    private int price;
    private String rate;
    private String description;
    private UserStore seller;

    public Product(){}

    public Product(int id, int store_id, String name, ProductType type, int stock, int price, String rate, String description) {
        this.id = id;
        this.store_id = store_id;
        this.name = name;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.rate = rate;
        this.description = description;
        this.seller = new UserStore();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }
    public Image getProductImage(){
        return new Image(getClass().getResource(
                switch (this.type){
                    case wallet_code -> "/assets/icons/wallet-icon.png";
                    case game_code -> "/assets/icons/game-icon.png";
                    case game_item -> "/assets/icons/item-game-icon.png";
                    default -> "/assets/icons/akar-icons-image.png";
                }
        ).toExternalForm());
    }
    public String getSimpleNameType(){
        String type = "";
        switch (this.type){
            case wallet_code:
                type =  "Wallet";
                break;
            case game_code:
                type =  "Game";
                break;
            case game_item:
                type =  "Game Item";
                break;
        }
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserStore getSeller() {
        return seller;
    }

    public void setSeller(UserStore seller) {
        this.seller = seller;
    }
}
