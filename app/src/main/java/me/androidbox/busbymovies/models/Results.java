package me.androidbox.busbymovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by steve on 2/20/17.
 */
/* Uses Parcelable for serializing the results<Review> for displaying in the dialog */
public class Results<T> implements Parcelable {
    private List<T> results;

    public Results(List<T> data) {
        this.results = data;
    }

    public Results(){}

    public static Results newInstance() {
        return new Results();
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> data) {
        results = data;
    }

    private Results(Parcel src) {
        src.readList(results, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(results);
    }

    public static final Parcelable.Creator<Results> CREATOR
            = new Parcelable.Creator<Results>() {

        @Override
        public Results createFromParcel(android.os.Parcel source) {
            return new Results(source);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[0];
        }
    };
}
