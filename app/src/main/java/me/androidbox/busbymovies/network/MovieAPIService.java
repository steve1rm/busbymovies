package me.androidbox.busbymovies.network;

import me.androidbox.busbymovies.models.PopularMovies;
import me.androidbox.busbymovies.models.Results;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by steve on 2/18/17.
 */

public interface MovieAPIService {
    /* Get a list of the current popular movies on TMDb. This list updates daily */
    @GET("movie/popular")
    Call<Results> getPopular(@Query("api_key") String apikey);

    /* Return the movie with the matching movie id */
    @GET("movie/{movie_id}")
    Call<PopularMovies> getMovie(@Path("movie_id") int movie_id, @Query("api_key") String apikey);

    /* Search for a popular movie */
/*    @GET("movie/popular")
    Call<PopularMovies> searchPopular(@Query("api_key") String apikey, @Query("q") String query);*/
}
