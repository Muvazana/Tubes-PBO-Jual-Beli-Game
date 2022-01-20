package com.project.tubespbokel4.model.auth;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private Roles role;
    private int wallet;
    private String username;
    private String password;

    /**
     * Object User <P>
     * tidak ada input
     */
    public User(){}
    public User(int id, String name, Roles role, int wallet, String username, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.wallet = wallet;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Roles getRole() {
        return role;
    }
    public String getSimpleNameRole(){
        String role = "";
        switch (this.role){
            case admin:
                role =  "Admin";
                break;
            case seller:
                role =  "Seller";
                break;
            case member:
                role =  "Member";
                break;
        }
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
