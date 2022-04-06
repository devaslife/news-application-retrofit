package com.example.krnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.GridLayoutAnimationController;
import android.widget.Button;
import android.widget.Toast;

import com.example.krnews.Models.NewsApiResponse;
import com.example.krnews.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1, b2, b3, b4, b5, b6;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news articles...");
        dialog.show();

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Search " + query);
                dialog.show();
                // i need to make object of class Request
                RequestManger requestManger = new RequestManger(MainActivity.this);
                requestManger.getNewsHeadlines(listener, "science", query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);

        // i need to make object of class Request
        RequestManger requestManger = new RequestManger(this);
        requestManger.getNewsHeadlines(listener, "science", null);

    }

    // i need to use this method to add the data in recyclerView
    public final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            // i need to test the api data if found or not
            if (list.isEmpty()) {
                Toast.makeText(MainActivity.this, "not found ... you have problem", Toast.LENGTH_SHORT).show();
            } else {
                ShowNews(list);// use the method show
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "not found ... you have problem", Toast.LENGTH_SHORT).show();
        }
    };

    private void ShowNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // make object of adapter
        adapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    // this is to interface for click the card view to intent the activity with Serializable
    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class).putExtra("data", headlines));
    }

    // this method to click the buttons and get category data
    @Override
    public void onClick(View view) {

        Button button = (Button) view;
        String category = button.getText().toString();

        dialog.setTitle("Fetched news articles of " + category);
        dialog.show();

        // i need to make object of class Request
        RequestManger requestManger = new RequestManger(this);
        requestManger.getNewsHeadlines(listener, category, null);

    }
}