package com.example.booklisting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookListingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<BookListingDataType>> {

    //Initial URL
    public String URL_DATA="";
    public boolean clicked=false;
    Boolean checkBoxState=false;
    int counter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(!isOnline()){
            Intent no_internet_intent=new Intent(BookListingActivity.this,CheckNetworkConnection.class);
            startActivity(no_internet_intent);
        }


        EditText subject=findViewById(R.id.Subject);
        final Editable editable=subject.getText();



        ImageButton searchButton=findViewById(R.id.Search_Button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                clicked=true;
                counter++;

                View view=findViewById(R.id.line);
                view.setVisibility(View.VISIBLE);


                if(!isOnline()){
                    Intent no_internet_intent=new Intent(BookListingActivity.this,CheckNetworkConnection.class);
                    startActivity(no_internet_intent);
                }

                //initiate a check box
                CheckBox simpleCheckBox = (CheckBox) findViewById(R.id.simpleCheckBox);
                checkBoxState = simpleCheckBox.isChecked();

                Log.e("Check Box ",""+checkBoxState);







                ProgressBar progressBar=findViewById(R.id.pBar);
                progressBar.setVisibility(View.VISIBLE);
                updateSubject(editable.toString());


            }
        });

    }

    //Update URL_DATA
    public void updateSubject(String subject){


        if(!checkBoxState) {
            URL_DATA = "https://www.googleapis.com/books/v1/volumes?q=+subject:" + subject + "&maxResults=10";
        }
        else{
            URL_DATA = "https://www.googleapis.com/books/v1/volumes?q=+subject:" + subject + "&maxResults=40";
        }
        checkBoxState=false;
        URL_DATA=URL_DATA.replace(' ', '+');

        //Log.e("Value",URL_DATA);
        LoaderManager loaderManager=getSupportLoaderManager();
        loaderManager.initLoader(0,null,this);
       // Log.e("MISSION PASS",URL_DATA);

    }


    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
       // Log.e("MISSION PASS",URL_DATA);
        return new BookListingLoader(this,URL_DATA);
    }



    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<BookListingDataType>> loader, ArrayList<BookListingDataType> jsonResponse) {

       // Log.e("JSON RESPONSE",jsonResponse);
        //readJson(jsonResponse);

        ListView listView=findViewById(R.id.listView);

        TextView empty_view=findViewById(R.id.empty_listview);
        listView.setEmptyView(empty_view);


        ProgressBar progressBar=findViewById(R.id.pBar);
        progressBar.setVisibility(View.GONE);


            BookListingAdapter bookListingAdapter = new BookListingAdapter(this, R.layout.books, jsonResponse);
            listView.setAdapter(bookListingAdapter);

       if(clicked&&counter>1) {
           getSupportLoaderManager().restartLoader(0, null, BookListingActivity.this);
           Log.e("Works","Okay");
           clicked=false;

        }


            //Reset Loader
          //  getSupportLoaderManager().restartLoader(0, null, BookListingActivity.this);

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        Log.e("We were there","in Reset");


    }




    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        else {
            return false;
        }
    }
}
