package com.example.myblog.custom;

public class Item {
    private String id;
    private String title;
    private int imageResource;

    public Item(String id, String title, int imageResource) {
        this.id = id;
        this.title = title;
        this.imageResource = imageResource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
