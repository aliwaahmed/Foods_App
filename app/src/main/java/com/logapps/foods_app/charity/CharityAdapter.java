package com.logapps.foods_app.charity;

import android.view.View;
import android.view.ViewGroup;

import com.logapps.foods_app.UserClick;
import com.logapps.foods_app.rest.Rest_donates_calss;
import com.logapps.foods_app.rest.Rest_my_donates;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

class CharityAdapter extends RecyclerView.Adapter<CharityAdapter.ViewHolder> {


    private Rest_my_donates mContext;
    public List<Rest_donates_calss> data = Collections.emptyList();
    Rest_donates_calss current;
    public String TAG = "taaaaaaaag";
    private UserClick lOnClickListener;

    public CharityAdapter(FragmentManager supportFragmentManager) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
