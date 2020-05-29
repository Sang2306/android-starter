package com.example.myblog.retrofit;

public class Article {
    private String uuid;
    private String title;
    private String slug;
    private String publish_date;
    private String html;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title.toUpperCase();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPublish_date() {
        return publish_date.split("T")[0];
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
