package me.androidbox.busbymovies.network;

import java.util.List;

import me.androidbox.busbymovies.models.PopularMovies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by steve on 2/18/17.
 */

public interface MovieAPIService {
    /* Get a list of the current popular movies on TMDb. This list updates daily */
    @GET("movie/popular")
    Call<PopularMovies> getPopular(@Query("api_key") String apikey);


}
