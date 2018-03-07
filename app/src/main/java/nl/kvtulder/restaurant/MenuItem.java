package nl.kvtulder.restaurant;

import java.io.Serializable;


public class MenuItem implements Serializable{

    // variables
    private String name;
    private String description;
    private String imageUrl;
    private int price;
    private String category;

    public MenuItem(String name, String description, String imageUrl, int price, String category) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    // getters:
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}

