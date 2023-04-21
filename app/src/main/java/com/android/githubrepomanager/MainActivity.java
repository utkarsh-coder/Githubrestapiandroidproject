package com.android.githubrepomanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView addIconImageView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    ArrayList<Repo> repoList;
    DBHelper dbHelper;
    private TextView trackTextView;
    private Button addNowBtn;

    public void refreshRecyclerView(){
        repoList =(ArrayList<Repo>) dbHelper.getAllRepo();
        if(repoList.size()<=0){
            recyclerView.setVisibility(View.GONE);
            trackTextView.setVisibility(View.VISIBLE);
            addNowBtn.setVisibility(View.VISIBLE);
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        trackTextView.setVisibility(View.GONE);
        addNowBtn.setVisibility(View.GONE);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, repoList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addIconImageView = findViewById(R.id.add_image_view);
        recyclerView = findViewById(R.id.repo_list);
        trackTextView = findViewById(R.id.track_text_view);
        addNowBtn = findViewById(R.id.add_now_btn);

        dbHelper = new DBHelper(this);

        addIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRepoIntent = new Intent(MainActivity.this, AddRepoActivity.class);
                startActivity(addRepoIntent);
            }
        });

        addNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRepoIntent = new Intent(MainActivity.this, AddRepoActivity.class);
                startActivity(addRepoIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshRecyclerView();
    }
}