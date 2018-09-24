package com.example.uchiha.imagegallerylayout;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class MultipleResources {
    @SerializedName("name")
    private String name;

    @SerializedName("images")
    private List<String> images = null;

    @SerializedName("colors")
    private List<String> colors = null;

    @SerializedName("sizes")
    private List<String> sizes = null;

    @SerializedName("price")
    private Integer price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
