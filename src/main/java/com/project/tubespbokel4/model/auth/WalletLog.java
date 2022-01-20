package com.project.tubespbokel4.model.auth;

public class WalletLog {
    private int id;
    private int user_req_id;
    private int wallet;
    private WalletLogStatus status;
    private User member;

    public WalletLog(){}

    public WalletLog(int id, int user_req_id, int wallet, WalletLogStatus status) {
        this.id = id;
        this.user_req_id = user_req_id;
        this.wallet = wallet;
        this.status = status;
        member = new User();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_req_id() {
        return user_req_id;
    }

    public void setUser_req_id(int user_req_id) {
        this.user_req_id = user_req_id;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public WalletLogStatus getStatus() {
        return status;
    }

    public void setStatus(WalletLogStatus status) {
        this.status = status;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }
}
