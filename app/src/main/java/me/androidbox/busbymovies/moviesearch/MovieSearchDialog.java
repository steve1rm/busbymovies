package me.androidbox.busbymovies.moviesearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.androidbox.busbymovies.R;

/**
 * Created by steve on 9/30/17.
 */

public class MovieSearchDialog extends DialogFragment {
    @BindView(R.id.etMovieName) EditText movieName;
    @BindView(R.id.etMovieYear) EditText movieYear;

    private MovieSearchListener movieSearchListener;

    public MovieSearchDialog() {
    }

    public static MovieSearchDialog newInstance() {
        return new MovieSearchDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.movie_search, container);

        ButterKnife.bind(MovieSearchDialog.this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);

        movieSearchListener = (MovieSearchListener)getTargetFragment();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnSearch)
    public void searchMovies() {
        if(!TextUtils.isEmpty(movieName.getText().toString())) {
            movieSearchListener.onMovieSearch(
                    movieName.getText().toString(),
                    convertYearToInt(movieYear.getText().toString()));

            dismiss();
        }
        else {
            Toast.makeText(getActivity(), "Enter a name of a movie before searching", Toast.LENGTH_SHORT).show();
        }
    }

    private int convertYearToInt(final String year) {
        return (year.isEmpty()) ? 0 : Integer.valueOf(year);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnClear)
    public void clearSearch() {
        movieName.getText().clear();
        movieYear.getText().clear();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnClose)
    public void closeDialog() {
        dismiss();
    }
}
