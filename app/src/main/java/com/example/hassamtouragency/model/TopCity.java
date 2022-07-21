package com.example.hassamtouragency.model;

public class TopCity {
    private String description;
    private String img_url;
    private String place_name;
    private Double lat,lng;

    public TopCity(String description, String img_url, String place_name, Double lat, Double lng) {
        this.description = description;
        this.img_url = img_url;
        this.place_name = place_name;
        this.lat = lat;
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

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
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
}
