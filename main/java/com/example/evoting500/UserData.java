package com.example.evoting500;

public class UserData {

    private String fullname;
    private String cnic;
    private String email;
    private String phone;
    private String password;
    private String requestStatus;
    private String HasVoted;

    public String getHasVoted() {
        return HasVoted;
    }

    public void setHasVoted(String hasVoted) {
        HasVoted = hasVoted;
    }

    private String key;

    public UserData(String fullname, String cnic, String email, String phone) {
        this.fullname = fullname;
        this.cnic = cnic;
        this.email = email;
        this.phone = phone;
        this.requestStatus = "Pending";
    }
    public UserData() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
