package com.vgsoft.mypersonaltestapp.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vgsoft.mypersonaltestapp.R;
import com.vgsoft.mypersonaltestapp.model.Movie;
import com.vgsoft.mypersonaltestapp.model.MovieTrailer;
import com.vgsoft.mypersonaltestapp.model.MovieTrailerResult;
import com.vgsoft.mypersonaltestapp.presenters.TrailerInformationView;
import com.vgsoft.mypersonaltestapp.presenters.TrailerPresenter;
import com.vgsoft.mypersonaltestapp.ui.adapters.TrailerAdapter;
import com.vgsoft.mypersonaltestapp.utility.Utils;

import java.util.ArrayList;


public class OverviewFragment extends BaseFragment implements TrailerInformationView {
    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private TextView mMovieOverview;
    private TextView mMovieReleaseDate;
    private TextView mMovieRating;
    private LinearLayoutManager manager;
    private RecyclerView recyclerView;
    private ArrayList<MovieTrailer> trailerIds;
    private Movie mMovie;
    private TrailerAdapter trailerAdapter;
    private TrailerPresenter presenter;

    public OverviewFragment() {
    }

    public static OverviewFragment newInstance(Movie movie) {
        OverviewFragment f = new OverviewFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);
        f.setArguments(args);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        setRetainInstance(true);
        recyclerView = view.findViewById(R.id.rv_trailers);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        presenter = new TrailerPresenter();
        presenter.attachView(this);

        mMovieTitle = view.findViewById(R.id.movie_activity_title);
        mMoviePoster = view.findViewById(R.id.movie_activity_poster);
        mMovieOverview = view.findViewById(R.id.movie_activity_overview);
        mMovieReleaseDate = view.findViewById(R.id.movie_activity_release_date);
        mMovieRating = view.findViewById(R.id.movie_activity_rating);
        mMovie = (Movie) getArguments().getSerializable("movie");

        presenter.getTrailer(mMovie.getId());
        setContent(mMovie);

        return view;
    }

    private void setContent(Movie movie) {
        Picasso.with(getActivity()).load(Utils.getPosterUri(mMovie.getPosterPath())).into(mMoviePoster);
        mMovieTitle.setText(mMovie.getTitle());
        mMovieOverview.setText(mMovie.getOverview());
        mMovieReleaseDate.setText(mMovie.getReleaseDate());
        mMovieRating.setText(Utils.getRatingString(mMovie.getVoteAverage()));
    }


    @Override
    public void onMovieTrailerResultReceived(MovieTrailerResult movieTrailerResult) {
        if (movieTrailerResult.getTrailerResult() == null
                || movieTrailerResult.getTrailerResult().size() == 0)
            return;
        trailerIds = movieTrailerResult.getTrailerResult();
        trailerAdapter = new TrailerAdapter(trailerIds);
        recyclerView.setAdapter(trailerAdapter);
    }

    @Override
    public void onError(String error) {

    }
}
