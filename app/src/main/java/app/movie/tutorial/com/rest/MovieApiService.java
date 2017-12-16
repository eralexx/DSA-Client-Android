package app.movie.tutorial.com.rest;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.Call;
import app.movie.tutorial.com.model.MovieResponse;


public interface MovieApiService {

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> getMovieSearch(@Query("query") String queryString, @Query("api_key") String apiKey);
}
