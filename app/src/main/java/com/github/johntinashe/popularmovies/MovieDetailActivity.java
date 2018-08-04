package com.github.johntinashe.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.github.johntinashe.popularmovies.data.MovieDetailViewModel;
import com.github.johntinashe.popularmovies.database.MovieEntry;
import com.github.johntinashe.popularmovies.databinding.ActivityMovieDetailBinding;
import com.github.johntinashe.popularmovies.model.Cast;
import com.github.johntinashe.popularmovies.model.Genre;
import com.github.johntinashe.popularmovies.model.Movie;
import com.github.johntinashe.popularmovies.model.Review;
import com.github.johntinashe.popularmovies.model.Trailer;
import com.github.johntinashe.popularmovies.services.AddToFavoritesService;
import com.github.johntinashe.popularmovies.utils.Utils;
import com.squareup.picasso.Picasso;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

@SuppressWarnings("WeakerAccess")
public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.cast_recycler_view)
    RecyclerView mCastRView;
    @BindView(R.id.trailers_recycler_view)
    RecyclerView mTrailersRView;
    @BindView(R.id.reviews_recycler_view)
    RecyclerView mReviewsRView;
    @BindView(R.id.tag_group)
    TagGroup mTagGroup;

    @BindView(R.id.cast_label)
    TextView mCastLabel;
    @BindView(R.id.review_label)
    TextView mReviewLabel;
    @BindView(R.id.trailer_label)
    TextView mTrailerLabel;
    @BindView(R.id.add_to_fav_btn)
    ImageView addToFavBtn;
    @BindView(R.id.nested_scroll)
    NestedScrollView mScrollView;

    private MovieDetailViewModel detailViewModel;
    private Movie movie;
    private boolean isInFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) getSupportActionBar().hide();
        ActivityMovieDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        ButterKnife.bind(this);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager reviewsLayoutManager = new LinearLayoutManager(this);
        mCastRView.setLayoutManager(layoutManager);
        mTrailersRView.setHasFixedSize(true);
        mCastRView.setHasFixedSize(true);
        mReviewsRView.setHasFixedSize(true);
        mTrailersRView.setLayoutManager(linearLayoutManager);
        mReviewsRView.setLayoutManager(reviewsLayoutManager);

        addToFavBtn.setOnClickListener(this);


        detailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        Intent i = getIntent();
        if (i == null) {
            error();
        } else {
            movie = i.getParcelableExtra("movie");
            if (movie != null) {
                binding.setMovie(movie);
                getCast(movie.getId());
                getGenres(movie.getId());
                getTrailers(movie.getId());
                getReviews(movie.getId());
                setFavorite(movie.getId());
            }
        }
    }

    public void getGenres(int movieId) {
        detailViewModel.getGenresLiveData(movieId).observe(this, new Observer<ArrayList<Genre>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Genre> genres) {
                if (genres != null) {
                    setmTagGroup(genres);
                }
            }
        });
    }

    private void getTrailers(int id) {
        detailViewModel.getTrailersLiveData(id).observe(this, new Observer<ArrayList<Trailer>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Trailer> trailers) {
                if (trailers != null)
                    if (trailers.size() == 0) {
                        mTrailerLabel.setText(R.string.error_trailer);
                        return;
                    }
                setTrailers(trailers);
            }
        });

    }

    private void getReviews(int id) {
        detailViewModel.getReviewsLiveData(id).observe(this, new Observer<ArrayList<Review>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Review> reviews) {
                if (reviews != null)
                    if (reviews.size() == 0) {
                        mReviewLabel.setText(R.string.error_reviews);
                        return;
                    }
                setReviews(reviews);
            }
        });
    }

    private void setReviews(ArrayList<Review> reviews) {

        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.review_item, new SlimInjector<Review>() {
                    @Override
                    public void onInject(@NonNull final Review data, @NonNull IViewInjector injector) {
                        injector.with(R.id.review_text_drawable, new IViewInjector.Action<ImageView>() {
                            @Override
                            public void action(ImageView view) {

                                ColorGenerator generator = ColorGenerator.MATERIAL;
                                int color = generator.getColor(data.getAuthor().charAt(0));
                                TextDrawable drawable = TextDrawable.builder()
                                        .buildRound(String.valueOf(data.getAuthor().charAt(0)), color);
                                view.setImageDrawable(drawable);

                            }
                        })
                                .with(R.id.review_author, new IViewInjector.Action<TextView>() {
                                    @Override
                                    public void action(TextView view) {
                                        view.setText(data.getAuthor());
                                    }
                                })
                                .with(R.id.review_content, new IViewInjector.Action<TextView>() {
                                    @Override
                                    public void action(TextView view) {
                                        view.setText(data.getContent());
                                    }
                                });
                    }
                })
                .enableDiff()
                .attachTo(mReviewsRView);
        slimAdapter.updateData(reviews);
    }

    private void setTrailers(ArrayList<Trailer> trailers) {

        final String imageBaseURL = "https://img.youtube.com/vi/";
        final String url = "https://www.youtube.com/watch?v=";
        final String imgSize = "/0.jpg";
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.trailer_item, new SlimInjector<Trailer>() {
                    @Override
                    public void onInject(@NonNull final Trailer data, @NonNull IViewInjector injector) {
                        injector.with(R.id.video_thumb_image, new IViewInjector.Action<ImageView>() {
                            @Override
                            public void action(ImageView view) {
                                Picasso.get().load(imageBaseURL + data.getKey() + imgSize)
                                        .error(R.drawable.avatar).into(view);
                            }
                        }).with(R.id.trailer_name, new IViewInjector.Action<TextView>() {
                            @Override
                            public void action(TextView view) {
                                view.setText(data.getName());
                            }
                        }).clicked(R.id.trailer_share_btn, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareTextUrl(url + data.getKey());
                            }
                        }).clicked(R.id.video_thumb_image, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url + data.getKey()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                try {
                                    if (Utils.isPackageExisted("com.google.android.youtube", getApplicationContext())) {
                                        intent.setPackage("com.google.android.youtube");
                                    }
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + data.getKey())));
                                }
                            }
                        });
                    }
                })
                .enableDiff()
                .attachTo(mTrailersRView);
        slimAdapter.updateData(trailers);
    }


    private void setmTagGroup(ArrayList<Genre> group) {
        List<String> list = new ArrayList<>();

        if (group != null) {
            for (Genre genre : group) {
                list.add(genre.getName());
            }
            mTagGroup.setTags(list);
        }
    }

    private void getCast(int id) {

        detailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        detailViewModel.getCasts(id).observe(this, new Observer<ArrayList<Cast>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Cast> casts) {
                if (casts != null)
                    if (casts.size() == 0) {
                        mCastLabel.setText(R.string.error_cast);
                        return;
                    }
                setData(casts);
            }
        });

    }

    public void setData(ArrayList<Cast> casts) {

        final String imageBaseURL = "http://image.tmdb.org/t/p/w185/";
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.cast_item, new SlimInjector<Cast>() {
                    @Override
                    public void onInject(@NonNull final Cast data, @NonNull IViewInjector injector) {
                        injector.with(R.id.cast_profile_image, new IViewInjector.Action<ImageView>() {
                            @Override
                            public void action(ImageView view) {
                                Picasso.get().load(imageBaseURL + data.getProfile_path())
                                        .error(R.drawable.avatar).into(view);
                            }
                        });
                    }
                })
                .enableDiff()
                .attachTo(mCastRView);
        slimAdapter.updateData(casts);
    }


    private void error() {
        finish();
        Toast.makeText(this, R.string.error_no_movie, Toast.LENGTH_SHORT).show();
    }

    public void addToFav(Movie movie) {
        Intent service = new Intent(this, AddToFavoritesService.class);
        service.setAction("add_to_fav");
        service.putExtra("movie", movie);
        startService(service);
    }

    public void removeFromFavorites(Movie movie) {
        Intent service = new Intent(this, AddToFavoritesService.class);
        service.setAction("remove_from_fav");
        service.putExtra("movie", movie);
        startService(service);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.add_to_fav_btn) {
            if (isInFavorites) {
                removeFromFavorites(movie);
            } else {
                addToFav(movie);
            }
        }

    }

    public void setFavorite(int id) {

        detailViewModel.checkFavorites(id).observe(this, new Observer<MovieEntry>() {
            @Override
            public void onChanged(@Nullable MovieEntry movieEntry) {
                if (movieEntry == null) {
                    addToFavBtn.setImageResource(R.drawable.ic_favorite_border);
                    isInFavorites = false;
                } else {
                    addToFavBtn.setImageResource(R.drawable.ic_favorite);
                    isInFavorites = true;
                }
            }
        });
    }

    private void shareTextUrl(String url) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_trailer));
        share.putExtra(Intent.EXTRA_TEXT, url);

        startActivity(Intent.createChooser(share, getString(R.string.share_link)));
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(getString(R.string.scroll_position),
                new int[]{mScrollView.getScrollX(), mScrollView.getScrollY()});
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray(getString(R.string.scroll_position));
        if (position != null)
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.scrollTo(position[0], position[1]);
                }
            });
    }
}
