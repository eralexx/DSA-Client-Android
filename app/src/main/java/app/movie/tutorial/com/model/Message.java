package app.movie.tutorial.com.model;

public class Message {
     String Time;
     String Content;
     String UserName;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @Override
    public String toString() {
        return (this.Time + " "  + this.UserName+" > " + this.Content);
    }

    public Message(String Content, User User) {
        /*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime localDate = SimpleDateFormat.now();
        this.Time = dtf.format(localDate);
        this.Content = Content;
        this.UserName = User.getUserName();*/
    }
}
