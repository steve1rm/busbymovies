package me.androidbox.busbymovies.data;

import me.androidbox.busbymovies.models.Favourite;
import me.androidbox.busbymovies.models.Results;

/**
 * Created by steve on 3/26/17.
 */

public interface MovieFavouriteModelContract {
    interface InsertListener {
        void onInsertFailed(String errorMessage);
        void onInsertSuccess();
    }
    void insert(Favourite favourite, InsertListener insertListener);

    interface RetrieveListener {
        void onRetrieveFailed(String errorMessage);
        void onRetrievedSuccess(Results<Favourite> favouriteList);
    }
    void retrieve(RetrieveListener retrieveListener);

    interface DeleteListener {
        void onDeleteFailed(String errorMessage);
        void onDeleteSuccess(int rowId);
    }
    void delete(int movieId, DeleteListener deleteListener);

    interface QueryMovieListener {
        void onQueryMovieFailed(String errorMessage);
        void onQueryMovieSuccess(boolean hasMovie);
    }
    void queryMovie(int movieId, QueryMovieListener queryMovieListener);

    void startup();
    void closeDown();
}
