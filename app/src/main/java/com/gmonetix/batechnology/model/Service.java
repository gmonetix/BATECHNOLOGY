package com.gmonetix.batechnology.model;

public class Service {

    private String name;
    private String summary;
    private int thumbnail;
    private String link;

    public Service() {

    }

    public Service(String name, String summary, int thumbnail, String link) {
        this.name = name;
        this.summary = summary;
        this.thumbnail = thumbnail;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
