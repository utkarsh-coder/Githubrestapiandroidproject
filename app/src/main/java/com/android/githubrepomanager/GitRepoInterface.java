package com.android.githubrepomanager;

import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitRepoInterface {

    @GET("{owner}/{repo}")
    Call<JsonElement> getRepo(@Path(value = "owner") String owner, @Path(value = "repo") String repo);

}
