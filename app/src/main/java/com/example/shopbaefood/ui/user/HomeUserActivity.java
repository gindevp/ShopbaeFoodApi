package com.example.shopbaefood.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.shopbaefood.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeUserActivity extends AppCompatActivity {
    Intent intent;
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        mViewPager = findViewById(R.id.viewpageUser);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);

        int pageToDisplay = getIntent().getIntExtra("pageToDisplay",0 ); // 0 là trang mặc định nếu không có dữ liệu được truyền
        mViewPager.setCurrentItem(pageToDisplay);
        switch (pageToDisplay) {
            case 0:
                mBottomNavigationView.getMenu().findItem(R.id.tab1).setChecked(true);
                break;
            case 1:
                mBottomNavigationView.getMenu().findItem(R.id.tab2).setChecked(true);
                break;
            case 2:
                mBottomNavigationView.getMenu().findItem(R.id.tab3).setChecked(true);
                break;

        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.tab1).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.tab2).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.tab3).setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab1:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.tab2:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.tab3:
                    mViewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {

    }
}