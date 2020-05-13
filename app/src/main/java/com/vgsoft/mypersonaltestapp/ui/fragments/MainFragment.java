package com.vgsoft.mypersonaltestapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vgsoft.mypersonaltestapp.R;
import com.vgsoft.mypersonaltestapp.entiti.Movie;
import com.vgsoft.mypersonaltestapp.entiti.MoviePageResult;
import com.vgsoft.mypersonaltestapp.presenters.MovieInformationView;
import com.vgsoft.mypersonaltestapp.presenters.MoviePresenter;
import com.vgsoft.mypersonaltestapp.realm.RealmController;
import com.vgsoft.mypersonaltestapp.ui.adapters.MovieAdapter;
import com.vgsoft.mypersonaltestapp.utility.MovieClickListener;
import com.vgsoft.mypersonaltestapp.utility.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment implements MovieInformationView {
    private static final String TAG = MainFragment.class.getName();
    private static int totalPages;
    private int currentPage;
    private RecyclerView recyclerView;
    private ArrayList<Movie> movieResults = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private MoviePresenter presenter;
    private LinearLayoutManager manager;
    private RealmController db;
    private int scrollPosition;

    public MainFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        db = new RealmController(getActivity());
        recyclerView = view.findViewById(R.id.rv_movies);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        presenter = new MoviePresenter();
        loadSettings();
        presenter.attachView(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)
                        && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (currentPage != totalPages) {
                        presenter.loadPage(currentPage + 1);
                    }

                }
            }
        });

        if (movieResults.size() == 0) {
            presenter.loadPage(1);
        } else {
            setupAdapter(db.getSettings().getPosition());
        }

        return view;
    }

    private void loadSettings() {
        if (db.getSettings() == null) {
            db.saveSettings(1, 1, 0);
        }
        currentPage = db.getSettings().getPage();
        totalPages = db.getSettings().getTotalPages();
    }

    @Override
    public void onMoviePageResultReceived(MoviePageResult moviePageResult, int page) {
        currentPage = page;
        if (db.getSettings() == null) {
            db.saveSettings(totalPages, page, 0);
        } else {

        }
        if (page == 1) {
            movieResults = moviePageResult.getMovieResult();
            totalPages = moviePageResult.getTotalPages();
            db.removeAll();
            for (Movie movie : moviePageResult.getMovieResult()) {
                db.addOrUpdateMovie(movie);
            }
            setupAdapter(0);

        } else {
            List<Movie> movies = moviePageResult.getMovieResult();
            for (Movie movie : movies) {
                movieResults.add(movie);
                movieAdapter.notifyItemInserted(movieResults.size() - 1);
            }
        }

        db.saveSettings(totalPages, page, 0);

    }

    private void setupAdapter(int position) {
        movieAdapter = new MovieAdapter(movieResults, new MovieClickListener() {
            @Override
            public void onMovieClick(Movie movie, int position) {
                db.saveSettings(totalPages, currentPage, position);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(OverviewFragment.class.getName()).replace(R.id.content_frame, OverviewFragment.newInstance(movie)).commit();
            }
        });
        recyclerView.setAdapter(movieAdapter);
        recyclerView.scrollToPosition(position);

    }

    @Override
    public void onError(String error) {
        Utils.makeToast(error);
        if (movieResults.size() == 0) {
            movieResults = db.getMoves(movieResults);
            totalPages = db.getSettings().getTotalPages();
            currentPage = db.getSettings().getPage();
            setupAdapter(movieResults.size());
        } else {
            setupAdapter(movieResults.size());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (manager != null) {
            scrollPosition = manager.findFirstVisibleItemPosition();
            db.saveSettings(totalPages, currentPage, scrollPosition);
        }
    }

}
