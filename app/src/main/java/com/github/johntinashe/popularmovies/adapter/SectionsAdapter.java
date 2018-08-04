package com.github.johntinashe.popularmovies.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.johntinashe.popularmovies.fragments.FavoritesFragment;
import com.github.johntinashe.popularmovies.fragments.PopularMoviesFragment;
import com.github.johntinashe.popularmovies.fragments.TopRatedFragment;

public class SectionsAdapter extends FragmentPagerAdapter {

    public SectionsAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        switch (position){
            case 0:
                return new PopularMoviesFragment();
            case 1:
                return new TopRatedFragment();
            case 2:
                return new FavoritesFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Most Pop Movies";
            case 1:
                return "Top Rated";
            case 2:
                return "Favorites";
            default:
                return null;
        }
    }
}
