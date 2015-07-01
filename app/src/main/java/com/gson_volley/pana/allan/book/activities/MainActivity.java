package com.gson_volley.pana.allan.book.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gson_volley.pana.allan.book.R;
import com.gson_volley.pana.allan.book.adapter.RecyclerViewBookAdapter;
import com.gson_volley.pana.allan.book.models.Book;
import com.gson_volley.pana.allan.book.network.GsonRequest;
import com.gson_volley.pana.allan.book.network.VolleyHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String URL = "http://tpbookserver.herokuapp.com/books ";
    private List<Book> bookList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewBookAdapter adapter;

    private String author;

    //GsonRequest gsonRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        adapter = new RecyclerViewBookAdapter(MainActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_book);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if(savedInstanceState != null){
            bookList = (List<Book>) savedInstanceState.getSerializable("books");
            adapter.setBookList( bookList );
        }else {
            setCustomGsonRequest();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            setCustomGsonRequest();
            return true;
        }
        if (id == R.id.action_next) {
            Intent intent = new Intent(this,SubActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("books", (Serializable) bookList);
    }

    public void setCustomGsonRequest(){

        final GsonRequest<Book[]> gsonRequest = new GsonRequest<Book[]>( URL, Book[].class,

                new Response.Listener<Book[]>() {
                    @Override
                    public void onResponse(Book[] books) {
                        bookList  = Arrays.asList(books);
                        adapter.setBookList( bookList );
                        adapter.notifyDataSetChanged();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this,error+"",Toast.LENGTH_LONG).show();
                        Log.d("allan", error+"");
                    }
                });

        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        gsonRequest.setShouldCache(true);
        VolleyHelper.getInstance(this).addToRequestQueue(gsonRequest);
    }






}
