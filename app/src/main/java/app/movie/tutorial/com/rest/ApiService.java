package app.movie.tutorial.com.rest;

import app.movie.tutorial.com.model.Chat;
import app.movie.tutorial.com.model.Game;
import app.movie.tutorial.com.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("UserManagement/Login/{EmailOrUsername}/{Password}")
    Call<Integer> Login(@Path("EmailOrUsername") String Email, @Path("Password") String Password);

    @GET("UserManagement/Register/{Email}/{Username}/{Password}")
    Call<Integer> Register(@Path("Email") String Email,@Path("Username") String Username, @Path("Password") String Password);

    @GET("Game/JoinQueue/{EmailOrUsername}")
    Call<Integer> JoinQueue(@Path("EmailOrUsername") String Email);

    @GET("Game/AttemptToGetGame/{EmailOrUsername}")
    Call<Game> AttemptToGetGame(@Path("EmailOrUsername") String Email);

    @GET("Game/DestroyGame/{EmailOrUsername}")
    Call<Integer> DestroyGame(@Path("EmailOrUsername") String Email);

    @GET("Game/Move/{EmailOrUsername}/{Move}")
    Call<Game> Move(@Path("EmailOrUsername") String Email, @Path("Move") Character move);

    @GET("Game/GetRandomGame")
    Call<Game> GetTestGame();

    @GET("ChatWindow/GetAllMessages")
    Call<Chat> GetAllMessages();

    @GET("ChatWindow/AddMessage/{EmailOrUsername}/{input}")
    Call<Integer> Move(@Path("EmailOrUsername") String Email, @Path("input") String input);

    @GET("UserManagement/GetUserInfo/{EmailOrUsername}")
    Call<User> GetUserData(@Path("EmailOrUsername") String Email);

    @GET("UserManagement/GetUserGames/{EmailOrUsername}")
    Call<String> GetUserGames(@Path("EmailOrUsername") String Email);

    @GET("UserManagement/GetUserWins/{EmailOrUsername}")
    Call<String> GetUserWins(@Path("EmailOrUsername") String Email);


}
