package com.test.mlchallenge;

public class ObtenerItems {

    public String title;
    public String id;
    public String thumbnail;
    public Integer price;

    public ObtenerItems() {

    }

    public ObtenerItems(String title, String id, String thumbnail) {
        this.title = title;
        this.id = id;
        this.thumbnail = thumbnail;
        this.price = price;
    }

    public String getName() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return thumbnail;
    }

    public void setFoto(String thumbnail) {
        this.thumbnail = thumbnail;
    }


}
