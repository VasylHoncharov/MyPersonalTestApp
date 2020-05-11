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
import com.vgsoft.mypersonaltestapp.model.Movie;
import com.vgsoft.mypersonaltestapp.model.MoviePageResult;
import com.vgsoft.mypersonaltestapp.presenters.MovieInformationView;
import com.vgsoft.mypersonaltestapp.presenters.MoviePresenter;
import com.vgsoft.mypersonaltestapp.realm.CursorModel;
import com.vgsoft.mypersonaltestapp.realm.RealmController;
import com.vgsoft.mypersonaltestapp.ui.adapters.MovieAdapter;
import com.vgsoft.mypersonaltestapp.utility.EndlessRecyclerViewScrollListener;
import com.vgsoft.mypersonaltestapp.utility.MovieClickListener;

import java.util.List;

public class MainFragment extends BaseFragment implements MovieInformationView {
    private static int totalPages;
    private RecyclerView recyclerView;
    private List<Movie> movieResults;
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
        presenter.attachView(this);
//        movieResults = db.getMoves();
            presenter.loadPage(1);


        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((page + 1) <= totalPages) {
                    presenter.loadPage(page + 1);
                }
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
        return view;
    }

    @Override
    public void onMoviePageResultReceived(MoviePageResult moviePageResult, int page) {
        if (page == 1) {
            movieResults = moviePageResult.getMovieResult();
            totalPages = moviePageResult.getTotalPages();
            movieAdapter = new MovieAdapter(movieResults, new MovieClickListener() {
                @Override
                public void onMovieClick(Movie movie) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().addToBackStack(OverviewFragment.class.getName()).replace(R.id.content_frame, OverviewFragment.newInstance(movie)).commit();
                }
            });
            recyclerView.setAdapter(movieAdapter);
        } else {
            List<Movie> movies = moviePageResult.getMovieResult();
            for (Movie movie : movies) {
                movieResults.add(movie);
                movieAdapter.notifyItemInserted(movieResults.size() - 1);
            }
        }
        db.addCursor(new CursorModel(777, page, totalPages, 0));

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (manager != null) {
            scrollPosition = manager.findFirstVisibleItemPosition();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (manager != null) {
            recyclerView.scrollToPosition(scrollPosition);
        }

    }


}
