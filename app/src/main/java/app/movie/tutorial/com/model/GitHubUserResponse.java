package app.movie.tutorial.com.model;

import com.google.gson.annotations.SerializedName;

public class GitHubUserResponse {
    @SerializedName("login")
    private String login;
    @SerializedName("avatar_url")
    private String avatar_url;
    @SerializedName("following")
    private int following;
    @SerializedName("public_repos")
    private int public_repos;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }
}
