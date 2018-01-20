package app.movie.tutorial.com.model;

/**
 * Created by Roger on 20/01/2018.
 */

public class DataModel {
    String username;
    String content;


    public DataModel(String username, String content) {
        this.username = username;
        this.content = content;

    }

    public String getusername() {
        return username;
    }

    public String getcontent() {
        return content;
    }

}
