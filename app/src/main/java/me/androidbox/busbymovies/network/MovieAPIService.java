package me.androidbox.busbymovies.network;

import me.androidbox.busbymovies.models.Actor;
import me.androidbox.busbymovies.models.Cast;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;
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
    Observable<Results<Movies>> getPopular(@Query("api_key") String apikey);

    @GET("movie/top_rated")
    Observable<Results<Movies>> getTopRatedMovies(@Query("api_key") String apikey);

    /* Return the movie with the matching movie id */
    @GET("movie/{movie_id}")
    Call<Movie> getMovieById(@Path("movie_id") int movie_id, @Query("api_key") String apikey);

    @GET("movie/{movie_id}")
    Observable<Movie> getMovieByIdExt(@Path("movie_id") int movie_id, @Query("api_key") String apikey);

    @GET("movie/{movie_id}/videos")
    Observable<Results<Trailer>> getMovieTrailers(@Path("movie_id") int movie_id, @Query("api_key") String apiKey);

    /* Get the movie reviews based on the movie id */
    @GET("movie/{movie_id}/reviews")
    Observable<Results<Review>> getMovieReview(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Observable<Cast<Actor>> getMovieActors(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("/search/movie")
    Observable<Results<Movies>> searchMovies(@Query("movieName") String movieName, @Query("movieYear") int movieYear, @Query("api_key") String apiKey);
}
