package com.example.myapplication.ui.gallery;

public class GalleryItem {
    private String username;
    private String label;
    private String imageUrl;

    public GalleryItem(String username, String label, String imageUrl) {
        this.username = username;
        this.label = label;
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getLabel() {
        return label;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}