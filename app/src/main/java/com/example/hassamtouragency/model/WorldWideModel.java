package com.example.hassamtouragency.model;

public class WorldWideModel {
    private String description;
    private String img_url;
    private String location;
    private String name;
    private Double rating;
    private String short_des;
    private Double lat;
    private Double lng;

    public WorldWideModel(String description, String img_url, String location, String name, Double rating, String short_des, Double lat, Double lng) {
        this.description = description;
        this.img_url = img_url;
        this.location = location;
        this.name = name;
        this.rating = rating;
        this.short_des = short_des;
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getShort_des() {
        return short_des;
    }

    public void setShort_des(String short_des) {
        this.short_des = short_des;
    }
}
