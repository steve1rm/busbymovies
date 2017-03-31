package me.androidbox.busbymovies.network;

import java.util.List;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.ResultsTrailer;
import me.androidbox.busbymovies.models.Trailer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by steve on 2/18/17.
 */

public interface MovieAPIService {
    /* Get a list of the current popular movies on TMDb. This list updates daily */
    @GET("movie/popular")
    Observable<Results> getPopular(@Query("api_key") String apikey);

    @GET("movie/top_rated")
    Observable<Results> getTopRatedMovies(@Query("api_key") String apikey);

    /* Return the movie with the matching movie id */
    @GET("movie/{movie_id}")
    Call<Movie> getMovieById(@Path("movie_id") int movie_id, @Query("api_key") String apikey);

    @GET("movie/{movie_id}")
    Observable<Movie> getMovieByIdExt(@Path("movie_id") int movie_id, @Query("api_key") String apikey);

    @GET("movie/{movie_id}/videos")
    Observable<ResultsTrailer<Trailer>> getMovieTrailers(@Path("movie_id") int movie_id, @Query("api_key") String apiKey);

    /* Search for a popular movie */
/*    @GET("movie/popular")
    Call<Results> searchPopular(@Query("api_key") String apikey, @Query("q") String query);*/













































}
