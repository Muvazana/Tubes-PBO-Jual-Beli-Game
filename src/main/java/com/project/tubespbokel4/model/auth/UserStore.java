package com.project.tubespbokel4.model.auth;

public class UserStore extends User {
    private String storeName;
    private String description;

    public UserStore(){
        super();
    }
    public UserStore(User user, String storeName, String description) {
        super(user.getId(), user.getName(), user.getRole(), user.getWallet(), user.getUsername(), user.getPassword());
        this.storeName = storeName;
        this.description = description;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
