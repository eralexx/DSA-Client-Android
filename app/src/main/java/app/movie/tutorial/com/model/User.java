package app.movie.tutorial.com.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int Id = 0;
    @SerializedName("userName")
    private String UserName = "null";
    @SerializedName("email")
    private String Email = "null";
    @SerializedName("password")
    private String Password = "null";
    @SerializedName("gamesPlayed")
    private int GamesPlayed = 0;
    @SerializedName("gamesWon")
    private int GamesWon = 0;
    @SerializedName("imagePath")
    private String imagePath ="";

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getGamesPlayed() {
        return GamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        GamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return GamesWon;
    }

    public void setGamesWon(int gamesWon) {
        GamesWon = gamesWon;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public User (){

    }
    public User(String UserName, String Email, String Password){
        this.UserName=UserName;
        this.Email=Email;
        this.Password=Password;
    }



}
