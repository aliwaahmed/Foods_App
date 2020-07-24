package com.logapps.foods_app.person;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.logapps.foods_app.R;
import com.logapps.foods_app.UserClick;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class All_donates_adapter extends RecyclerView.Adapter<All_donates_adapter.ViewHolder> {

    private my_posts_fragment mContext;
    public List<All_donates_class> data = Collections.emptyList();
    All_donates_class current;
    public String TAG = "taaaaaaaag";
    private UserClick lOnClickListener;




//    public All_donates_adapter(UserClick listener) {
//        lOnClickListener = listener ;
//    }


    public All_donates_adapter(UserClick listener) {
        lOnClickListener = listener;
    }

    public void setUsersData(List<All_donates_class> recipesIn, my_posts_fragment my_posts_fragment ) {
        data = recipesIn;
        mContext = my_posts_fragment;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.donate_raw;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final ViewHolder holder1 = (ViewHolder) holder ;
        current = data.get(position);

        holder1.name.setText(current.getName());
        holder1.details.setText(current.getDetails());

        ((ViewHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(mContext.getContext(), MyPostsDetails_Activity.class);
                i.putExtra("name" , data.get(position).getName());
                i.putExtra("details" , data.get(position).getDetails());
                i.putExtra("donate_address" , data.get(position).getAddress());
                i.putExtra("donate_call" , data.get(position).getPhone());
                i.putExtra("food_image" , data.get(position).getImage());

                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setUsersData(ArrayList<All_donates_class> feeds, All_Posts all_posts) {

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
