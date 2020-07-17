package com.example.attempt.Model;

public class Admins {

    String admin_name, admin_password, admin_phoneNumber,admin_image , admin_address;

    public Admins() {
    }

    public Admins(String admin_name, String admin_password, String admin_phoneNumber, String admin_image, String admin_address) {
        this.admin_name = admin_name;
        this.admin_password = admin_password;
        this.admin_phoneNumber = admin_phoneNumber;
        this.admin_image = admin_image;
        this.admin_address = admin_address;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public String getAdmin_phoneNumber() {
        return admin_phoneNumber;
    }

    public void setAdmin_phoneNumber(String admin_phoneNumber) {
        this.admin_phoneNumber = admin_phoneNumber;
    }

    public String getAdmin_image() {
        return admin_image;
    }

    public void setAdmin_image(String admin_image) {
        this.admin_image = admin_image;
    }

    public String getAdmin_address() {
        return admin_address;
    }

    public void setAdmin_address(String admin_address) {
        this.admin_address = admin_address;
    }
}
