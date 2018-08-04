package com.github.johntinashe.popularmovies;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.johntinashe.popularmovies.adapter.SectionsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class HomeActivity extends AppCompatActivity {

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE =100;

    @BindView(R.id.view_pager) ViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout mTabLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setToolbar();

        SectionsAdapter mAdapter = new SectionsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        if (!checkPermissionForReadExtertalStorage()) {
            try {
                requestPermissionForReadExtertalStorage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void setToolbar () {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            setTitle(R.string.app_name);
    }

    private boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    private void requestPermissionForReadExtertalStorage() {
        try {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
