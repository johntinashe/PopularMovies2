package com.github.johntinashe.popularmovies.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.johntinashe.popularmovies.MovieDetailActivity;
import com.github.johntinashe.popularmovies.R;
import com.github.johntinashe.popularmovies.data.FavoritesViewModel;
import com.github.johntinashe.popularmovies.database.MovieEntry;
import com.github.johntinashe.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("WeakerAccess")
public class FavoritesFragment extends Fragment {

    @BindView(R.id.favorites_recycler_view)
    RecyclerView mRecyclerView;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        ButterKnife.bind(this,view);

        mRecyclerView.setHasFixedSize(true);
        getFavorites();
        return view;
    }

    private void getFavorites() {
        FavoritesViewModel viewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        viewModel.getMovies().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntry> movieEntries) {
                setAdapter(movieEntries);
            }
        });
    }


    private void setAdapter(List<MovieEntry> movieEntryList) {

        final String imageBaseURL = "http://image.tmdb.org/t/p/w342/";

        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.movie_item, new SlimInjector<MovieEntry>() {
                    @Override
                    public void onInject(@NonNull final MovieEntry movie, @NonNull IViewInjector injector) {
                        injector.with(R.id.movie_poster, new IViewInjector.Action<ImageView>() {
                            @Override
                            public void action(ImageView view) {
                                Picasso.get().load(imageBaseURL + movie.getPoster())
                                        .error(R.drawable.avatar).into(view);
                            }
                        }).clicked(R.id.movie_poster, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Movie mov = new Movie();

                                mov.setTitle(movie.getTitle());
                                mov.setOriginalLanguage(movie.getLanguage());
                                mov.setBackdropPath(movie.getBackdrop());
                                mov.setPosterPath(movie.getPoster());
                                mov.setReleaseDate(movie.getDate());
                                mov.setOverview(movie.getPlot());
                                mov.setVoteAverage(movie.getRating());
                                mov.setId(movie.getId());

                                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                                intent.putExtra("movie",mov);
                                startActivity(intent);
                            }
                        });
                    }
                })
                .enableDiff()
                .attachTo(mRecyclerView);
        slimAdapter.updateData(movieEntryList);

    }

}
