package com.example.myblog.custom;

public class Item {
    private String id;
    private String title;
    private String slug;
    private String dateText;
    private int imageResource;

    public Item(String slug, String id, String title, String dateText, int imageResource) {
        this.slug = slug;
        this.id = id;
        this.title = title;
        this.imageResource = imageResource;
        this.dateText = dateText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        if(title.length() < 50){
            return title;
        }
        return title.substring(0, 50) + "...";
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

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }
}
