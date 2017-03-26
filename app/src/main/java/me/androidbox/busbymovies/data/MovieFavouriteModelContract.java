package me.androidbox.busbymovies.data;


import java.util.List;

import me.androidbox.busbymovies.models.Favourite;

/**
 * Created by steve on 3/26/17.
 */

public interface MovieFavouriteModelContract {
    interface InsertListener {
        void onInsertFailed();
        void onInsertSuccess();
    }
    void insert(Favourite favourite, InsertListener insertListener);

    interface RetrieveListener {
        void onRetrieveFailed();
        void onRetrievedSuccess(List<Favourite> favouriteList);
    }
    void retrieve(RetrieveListener retrieveListener);

    interface DeleteListener {
        void onDeleteFailed();
        void onDeleteSuccess();
    }
    void delete(int movieId, DeleteListener deleteListener);

    void startup();
    void closeDown();
}
