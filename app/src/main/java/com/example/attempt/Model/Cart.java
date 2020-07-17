package com.example.attempt.Model;

public class Cart {

    private  String menuid , name ,order_date ,order_time ,price ,quantity ,Restaurant_Phone_number;
    public Cart() {
    }


    public Cart(String menuid, String name, String order_date, String order_time, String price, String quantity, String restaurant_Phone_number) {
        this.menuid = menuid;
        this.name = name;
        this.order_date = order_date;
        this.order_time = order_time;
        this.price = price;
        this.quantity = quantity;
        Restaurant_Phone_number = restaurant_Phone_number;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRestaurant_Phone_number() {
        return Restaurant_Phone_number;
    }

    public void setRestaurant_Phone_number(String restaurant_Phone_number) {
        Restaurant_Phone_number = restaurant_Phone_number;
    }
}
