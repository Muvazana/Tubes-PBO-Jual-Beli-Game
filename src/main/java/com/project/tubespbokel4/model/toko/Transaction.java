package com.project.tubespbokel4.model.toko;

import com.project.tubespbokel4.model.auth.User;
import com.project.tubespbokel4.model.auth.UserStore;

public class Transaction {
    private int id;
    private int user_member_id;
    private int product_id;
    private int total_price;
    private String note;
    private String redeeom_code;
    private int product_rate;
    private TransactionStatus status;
    private Product product;
    private User member;
    private UserStore seller;

    public Transaction(){
        this.product = new Product();
        this.member = new User();
        this.seller = new UserStore();
    }

    public Transaction(int id, int user_member_id, int product_id, int total_price, String note, String redeeom_code, int product_rate, TransactionStatus status) {
        this.id = id;
        this.user_member_id = user_member_id;
        this.product_id = product_id;
        this.total_price = total_price;
        this.note = note;
        this.redeeom_code = redeeom_code;
        this.product_rate = product_rate;
        this.status = status;
        this.product = new Product();
        this.member = new User();
        this.seller = new UserStore();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_member_id() {
        return user_member_id;
    }

    public void setUser_member_id(int user_member_id) {
        this.user_member_id = user_member_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRedeeom_code() {
        return redeeom_code;
    }

    public void setRedeeom_code(String redeeom_code) {
        this.redeeom_code = redeeom_code;
    }

    public int getProduct_rate() {
        return product_rate;
    }

    public void setProduct_rate(int product_rate) {
        this.product_rate = product_rate;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getSimpleNameStatus(){
        return switch (this.status){
            case member_request -> "Member Request";
            case member_cancel -> "Member Cancel";
            case seller_approved -> "Seller Approved";
            case seller_rejected -> "Seller Rejected";
            case done -> "done";
        };
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public UserStore getSeller() {
        return seller;
    }

    public void setSeller(UserStore seller) {
        this.seller = seller;
    }
}
