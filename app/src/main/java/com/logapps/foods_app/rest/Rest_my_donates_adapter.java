package com.logapps.foods_app.rest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.logapps.foods_app.R;
import com.logapps.foods_app.UserClick;
import com.logapps.foods_app.person.All_Posts;
import com.logapps.foods_app.person.All_donates_class;
import com.logapps.foods_app.person.my_posts_fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class Rest_my_donates_adapter extends RecyclerView.Adapter<Rest_my_donates_adapter.ViewHolder> {

    private Rest_my_donates mContext;
    public List<Rest_donates_calss> data = Collections.emptyList();
    Rest_donates_calss current;
    public String TAG = "taaaaaaaag";
    private UserClick lOnClickListener;




//    public All_donates_adapter(UserClick listener) {
//        lOnClickListener = listener ;
//    }


    public Rest_my_donates_adapter(UserClick listener) {

        lOnClickListener = listener;

    }

    public void setUsersData(List<Rest_donates_calss> recipesIn, Rest_my_donates rest_my_donates) {

        data = recipesIn;
        mContext = rest_my_donates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Rest_my_donates_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.donate_raw;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new Rest_my_donates_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Rest_my_donates_adapter.ViewHolder holder, int position) {

        final Rest_my_donates_adapter.ViewHolder holder1 = (Rest_my_donates_adapter.ViewHolder) holder ;
        current = data.get(position);

        holder1.name.setText(current.getName());
        holder1.details.setText(current.getDetails());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {


        CardView cardView ;
        TextView name , details ;
        ImageView imageView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.card);
            name = (TextView)itemView.findViewById(R.id.card_name);
            details = (TextView)itemView.findViewById(R.id.card_details);
            imageView = (ImageView) itemView.findViewById(R.id.card_img);
        }
    }
}
