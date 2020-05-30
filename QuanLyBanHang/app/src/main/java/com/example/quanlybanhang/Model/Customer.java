package com.example.quanlybanhang.Model;

public class Customer {
    private int productImg;
    private Integer id;
    private String name;
    private String product;
    private String date;
    private Integer amount;

    public Customer(String name, String product, String date, Integer amount) {
        this.name = name;
        this.product = product;
        this.date = date;
        this.amount = amount;
    }

    public Customer(int id, String name, String product, String date, Integer amount) {
        this.id = id;
        this.name = name;
        this.product = product;
        this.date = date;
        this.amount = amount;
    }

    public Customer() {
    }

    public int getProductImg() {
        return productImg;
    }

    public void setProductImg(int productImg) {
        this.productImg = productImg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}