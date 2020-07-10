package com.example.booklisting;

import android.graphics.Bitmap;
import android.widget.Button;

public class BookListingDataType {
    String title;
    String author;
    String imageUrl;

    public BookListingDataType(String title, String author,String imageUrl) {
        this.title = title;
        this.author = author;
        this.imageUrl=imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
