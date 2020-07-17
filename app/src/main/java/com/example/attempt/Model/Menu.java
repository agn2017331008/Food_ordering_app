package com.example.attempt.Model;

public class Menu {
    String date ,description,menuid,name,price,time;
            //menu_image;

    public Menu() {
    }

    public Menu(String date, String description, String menuid, String name, String price, String time) {
        this.date = date;
        this.description = description;
       // this.menu_image = menu_image;
        this.menuid = menuid;
        this.name = name;
        this.price = price;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getMenu_image() {
//        return menu_image;
//    }
//
//    public void setMenu_image(String menu_image) {
//        this.menu_image = menu_image;
//    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
