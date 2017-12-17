package app.movie.tutorial.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GitHubFollowerResponse {
    @SerializedName("results")
    private List<GitHubUser> results;
}
