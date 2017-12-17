package app.movie.tutorial.com.rest;

import java.util.List;

import app.movie.tutorial.com.model.GitHubUserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubApiService {

    @GET("users/{username}")
    Call<GitHubUserResponse> getUser(@Path("username") String username);

    @GET("users/{username}/followers")
    Call<List<GitHubUserResponse>> getUserFollowers(@Path("username") String username);
}
