package com.android.githubrepomanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddRepoActivity extends AppCompatActivity {

    private EditText ownerEditText;
    private EditText repoEditText;
    private Button addBtn;
    private Retrofit retrofit;
    private GitRepoInterface gitRepoInterface;
    private DBHelper dbHelper;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repo);

        ownerEditText = findViewById(R.id.owner_edittext);
        repoEditText = findViewById(R.id.repo_name_edittext);
        addBtn = findViewById(R.id.add_btn);
        progressBar = findViewById(R.id.progress_bar);

        dbHelper = new DBHelper(this);

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(" https://api.github.com/repos/")
                .build();

        gitRepoInterface = retrofit.create(GitRepoInterface.class);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                addBtn.setEnabled(false);
                if (!ownerEditText.getText().toString().equals("")&& !repoEditText.getText().toString().equals("")){
                    gitRepoInterface.getRepo(getString(R.string.bearer_token), ownerEditText.getText().toString(),repoEditText.getText().toString()).enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            progressBar.setVisibility(View.GONE);
                            addBtn.setEnabled(true);
                            if(response.body()!=null) {
                                String url = response.body().getAsJsonObject().get("html_url").toString().replace("\"", "");
                                dbHelper.addRepo(new Repo(ownerEditText.getText().toString(), repoEditText.getText().toString(), response.body().getAsJsonObject().get("description").toString(), url));
                                Toast.makeText(AddRepoActivity.this, "Repo added to Android storage", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                Toast.makeText(AddRepoActivity.this, "Owner or Repo not found! Enter correct details", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            Log.d("testing", t.toString());
                        }
                    });
                }
                else {
                    Toast.makeText(AddRepoActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper = null;
        gitRepoInterface = null;
        progressBar = null;
    }
}