package app.movie.tutorial.com.rest;

import java.util.List;

import app.movie.tutorial.com.model.GitHubUserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("users/{username}")
    Call<GitHubUserResponse> getUser(@Path("username") String username);

    @GET("/UserManagement/Login/{EmailOrUsername}/{Password}")
    Call<Integer> Login(@Path("EmailOrUsername") String Email, @Path("Password") String Password);

    @GET("users/{username}/followers")
    Call<List<GitHubUserResponse>> getUserFollowers(@Path("username") String username);
}
