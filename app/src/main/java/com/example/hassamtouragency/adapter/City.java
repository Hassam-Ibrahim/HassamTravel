package com.example.hassamtouragency.adapter;

public class City {
    private String name;
    private String description;
    private String imageurl;

    public City(String name, String description, String imageurl) {
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
