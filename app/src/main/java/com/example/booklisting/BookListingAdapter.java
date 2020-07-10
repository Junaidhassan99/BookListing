package com.example.booklisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BookListingAdapter extends ArrayAdapter<BookListingDataType>  {
    Context context;
    int resource;


    public BookListingAdapter(@NonNull Context context, int resource, @NonNull List<BookListingDataType> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        convertView=layoutInflater.inflate(resource,parent,false);

        TextView title=convertView.findViewById(R.id.title);
        title.setText(getItem(position).title);

        TextView author=convertView.findViewById(R.id.author);
        author.setText("By "+getItem(position).author);

        ImageView imageUrl=convertView.findViewById(R.id.imageUrl);
        //Loading image using Picasso
      //  Log.e("String",getItem(position).getImageUrl());

    //   String s="https://books.google.com/books/content?id=D_FsAAAAMAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api";
      // String s="https://www.shutterstock.com/image-photo/studio-shot-lovely-woman-wears-halloween-1523214461";
      // String s="https://i.ytimg.com/vi/rLoeAzpIkyo/hqdefault.jpg";
        //String s=getItem(position).getImageUrl();

        String imageUrlString=getItem(position).getImageUrl();
        imageUrlString=imageUrlString.substring(0, 4) + "s" + imageUrlString.substring(4, imageUrlString.length());





        Picasso.get().load(imageUrlString).into(imageUrl);







        return convertView;
    }

}
