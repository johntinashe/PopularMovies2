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
import com.github.johntinashe.popularmovies.data.TopRatedViewModel;
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
public class TopRatedFragment extends Fragment {

    private TopRatedViewModel mModel;

    @BindView(R.id.recycler_view_top_movies)
    RecyclerView mRecyclerView;


    public TopRatedFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_top_rated, container, false);
        ButterKnife.bind(this, mView);
        mRecyclerView.setHasFixedSize(true);
        setHasOptionsMenu(true);
        loadData();
        return mView;
    }

    private void loadData() {
        mModel = ViewModelProviders.of(this).get(TopRatedViewModel.class);
        mModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movie> movieArrayList) {
                getMovies(movieArrayList);
            }
        });
    }

    private void getMovies(ArrayList<Movie> movies) {

        final String imageBaseURL = "http://image.tmdb.org/t/p/w185/";
        SlimAdapter slimAdapter=  SlimAdapter.create()
                .register(R.layout.movie_item, new SlimInjector<Movie>() {
                    @Override
                    public void onInject(@NonNull final Movie data, @NonNull IViewInjector injector) {
                        injector.with(R.id.movie_poster, new IViewInjector.Action<ImageView>() {
                            @Override
                            public void action(ImageView view) {
                                Picasso.get().load(imageBaseURL+data.getPosterPath()).into(view);
                            }
                        })
                        .clicked(R.id.movie_poster, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                                        Pair.create(v,"posterImage"));

                                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                                intent.putExtra("movie",data);
                                startActivity(intent,options.toBundle());
                            }
                        });
                    }
                })
                .enableDiff()
                .enableLoadMore(new SlimMoreLoader(getContext(), new SimpleLoadMoreViewCreator(getContext()).setNoMoreHint("...")) {
                    @Override
                    protected void onLoadMore(final Handler handler) {
                        mModel.loadMore();
                        if(getActivity() != null)
                        mModel.getNewMovies().observe(getActivity(), new Observer<ArrayList<Movie>>() {
                            @Override
                            public void onChanged(@Nullable ArrayList<Movie> movieArrayList) {
                                handler.loadCompleted(movieArrayList);
                            }
                        });
                    }

                    @Override
                    protected boolean hasMore() {
                        return  mModel.pageNumber <= mModel.totalPages && Utils.isNetworkAvailable(Objects.requireNonNull(getContext())) ;
                    }
                })
                .attachTo(mRecyclerView);
            slimAdapter.updateData(movies);
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
                    getMovies(movieArrayList);
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
