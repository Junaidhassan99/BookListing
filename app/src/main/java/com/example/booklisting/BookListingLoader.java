package com.example.booklisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class BookListingLoader extends AsyncTaskLoader<ArrayList<BookListingDataType>> {

    String URL_DATA;


    public BookListingLoader(@NonNull Context context,String URL_DATA) {

        super(context);
        this.URL_DATA=URL_DATA;
       // Log.e("MISSION PASS Loader",URL_DATA);

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<BookListingDataType> loadInBackground() {

        URL url =null;

        StringBuilder stringBuilder=new StringBuilder();
        try {
          //  url=new URL("https://www.googleapis.com/books/v1/volumes?q=+subject:internet&maxResults=40");

            url=new URL(URL_DATA);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        try {
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            InputStream inputStream=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("utf-8"));
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);


            String line;
            line=bufferedReader.readLine();
            while (line!=null){
                stringBuilder.append(line);
                line=bufferedReader.readLine();

            }



        } catch (IOException e) {
            e.printStackTrace();
        }

       // Log.e("JSON RESPONSE",stringBuilder.toString());



        return readJson(stringBuilder.toString());
    }


    public ArrayList<BookListingDataType> readJson(String jsonResponse){

        ArrayList<BookListingDataType> bookList=new ArrayList<BookListingDataType>();


        try {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONArray jsonArray=jsonObject.getJSONArray("items");

            Log.e("Array Length ",""+jsonArray.length());


    for (int i = 0; i < jsonArray.length(); i++) {

        JSONObject jsonObjectCount = jsonArray.getJSONObject(i);
        JSONObject jsonObjectPosition1 = jsonObjectCount.getJSONObject("volumeInfo");
        String title = jsonObjectPosition1.getString("title");

        JSONArray jsonArrayPosition2 = jsonObjectPosition1.getJSONArray("authors");
        String author = jsonArrayPosition2.getString(0);

        JSONObject jsonObjectPosition3 = jsonObjectPosition1.getJSONObject("imageLinks");
        String imageUrl = jsonObjectPosition3.getString("thumbnail");

        Log.e("Image Link " + i, imageUrl);

        bookList.add(new BookListingDataType(title, author, imageUrl));


        // Log.e("STRING",author);
        //Log.e("STRING",title);
        //Log.e("WHAT",jsonObjectCount.toString());
    }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bookList;
    }
//
//    public Bitmap getImageUsingUrl(String URL){
//        String urldisplay = URL;
//        Bitmap mIcon11 = null;
//        try {
//            InputStream in = new java.net.URL(urldisplay).openStream();
//            mIcon11 = BitmapFactory.decodeStream(in);
//        } catch (Exception e) {
//            Log.e("Error", e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//
//
//        return mIcon11;

//    }
}
