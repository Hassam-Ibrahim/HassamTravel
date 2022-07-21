package com.example.hassamtouragency.model;

public class PackageModel {
    private String package_des;
    private String package_img;
    private String package_name;
    private String package_price;

    public PackageModel(String package_des, String package_img, String package_name, String package_price) {
        this.package_des = package_des;
        this.package_img = package_img;
        this.package_name = package_name;
        this.package_price = package_price;
    }

    public String getPackage_des() {
        return package_des;
    }

    public void setPackage_des(String package_des) {
        this.package_des = package_des;
    }

    public String getPackage_img() {
        return package_img;
    }

    public void setPackage_img(String package_img) {
        this.package_img = package_img;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }
}
