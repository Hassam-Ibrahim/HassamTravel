package com.example.hassamtouragency;

public class BookingFormModel {
    private String package_name;
    private String package_price;
    private String customer_name;
    private String customer_email;
    private String customer_phone;
    private String customer_message;

    public BookingFormModel(String package_name, String package_price, String customer_name, String customer_email, String customer_phone, String customer_message) {
        this.package_name = package_name;
        this.package_price = package_price;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_phone = customer_phone;
        this.customer_message = customer_message;
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

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_message() {
        return customer_message;
    }

    public void setCustomer_message(String customer_message) {
        this.customer_message = customer_message;
    }
}
