package com.example.myblog.custom;

public class Item {
    private int id;
    private String text;
    private int imageResource;

    public Item(int id, String text, int imageResource) {
        this.id = id;
        this.text = text;
        this.imageResource = imageResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
