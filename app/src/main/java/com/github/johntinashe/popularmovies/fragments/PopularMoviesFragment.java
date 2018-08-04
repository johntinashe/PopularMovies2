package com.github.johntinashe.popularmovies.fragments;


import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.johntinashe.popularmovies.MovieDetailActivity;
import com.github.johntinashe.popularmovies.R;
import com.github.johntinashe.popularmovies.data.PopularMoviesViewModel;
import com.github.johntinashe.popularmovies.model.Movie;
import com.github.johntinashe.popularmovies.utils.Utils;
import com.squareup.picasso.Picasso;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.ex.loadmore.SimpleLoadMoreViewCreator;
import net.idik.lib.slimadapter.ex.loadmore.SlimMoreLoader;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("WeakerAccess")
public class PopularMoviesFragment extends Fragment {

    private PopularMoviesViewModel mModel;

    @BindView(R.id.recycler_view_popular_movies)
    RecyclerView mRecyclerView;

    public PopularMoviesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        ButterKnife.bind(this, mView);
        mRecyclerView.setHasFixedSize(true);
        mModel = ViewModelProviders.of(this).get(PopularMoviesViewModel.class);
        setHasOptionsMenu(true);

        mModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movie> movieArrayList) {
                loadMovies(movieArrayList);
            }
        });
        return mView;
    }

    private void loadMovies(final ArrayList<Movie> movieArrayList) {

        final String imageBaseURL = "http://image.tmdb.org/t/p/w500/";
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.movie_item, new SlimInjector<Movie>() {
                    @Override
                    public void onInject(@NonNull final Movie data, @NonNull IViewInjector injector) {
                        injector.with(R.id.movie_poster, new IViewInjector.Action<ImageView>() {
                            @Override
                            public void action(ImageView view) {
                                Picasso.get().load(imageBaseURL + data.getPosterPath()).into(view);
                            }
                        })
                                .clicked(R.id.movie_poster, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                                                Pair.create(v, "posterImage"));

                                        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                                        intent.putExtra("movie", data);
                                        startActivity(intent, options.toBundle());
                                    }
                                });
                    }
                })
                .enableDiff()
                .enableLoadMore(new SlimMoreLoader(getContext(), new SimpleLoadMoreViewCreator(getContext()).setNoMoreHint("...")) {
                    @Override
                    protected void onLoadMore(final Handler handler) {
                        mModel.loadMore();
                        if (getActivity() != null) {
                            mModel.getNewMovies().observe(getActivity(), new Observer<ArrayList<Movie>>() {
                                @Override
                                public void onChanged(@Nullable ArrayList<Movie> movieArrayList) {
                                    handler.loadCompleted(movieArrayList);
                                }
                            });
                        }
                    }

                    @Override
                    protected boolean hasMore() {
                        return mModel.pageNumber <= mModel.totalPages && Utils.isNetworkAvailable(Objects.requireNonNull(getContext()));
                    }

                })
                .attachTo(mRecyclerView);
        slimAdapter.updateData(movieArrayList);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_refresh) {
            mModel.refresh();
            mModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Movie> movieArrayList) {
                    loadMovies(movieArrayList);
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
