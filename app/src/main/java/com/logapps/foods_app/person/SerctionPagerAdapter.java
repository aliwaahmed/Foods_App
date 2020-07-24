package com.logapps.foods_app.person;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

class SerctionPagerAdapter extends FragmentPagerAdapter {



    public SerctionPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }


    public Fragment getItem(int position){

                switch (position){
            case 0:
                my_posts_fragment my_posts = new my_posts_fragment();
                return my_posts;
            case 1:
                All_Posts all_posts = new All_Posts();
                return all_posts;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "My Donates";
            case 1:
                return "All Donates";
            default:
                return null;
        }

    }
}
