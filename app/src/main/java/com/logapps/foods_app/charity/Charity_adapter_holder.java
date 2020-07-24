package com.logapps.foods_app.charity;

import com.logapps.foods_app.person.All_Posts;
import com.logapps.foods_app.rest.Rest_my_donates;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Charity_adapter_holder extends FragmentPagerAdapter {

    public Charity_adapter_holder(@NonNull FragmentManager fm) {
        super(fm);
    }
    public Fragment getItem(int position){

        switch (position){
            case 0:
                All_Posts all_posts = new All_Posts();
                return all_posts;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 1;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "All Donates";
            default:
                return null;
        }
    }
}
