package com.logapps.foods_app.rest;

import com.logapps.foods_app.person.All_Posts;
import com.logapps.foods_app.person.my_posts_fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class RestAdapter extends FragmentPagerAdapter {

    public RestAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    public Fragment getItem(int position){

        switch (position){
            case 0:
                Rest_my_donates restMyDonates = new Rest_my_donates();
                return restMyDonates;
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
