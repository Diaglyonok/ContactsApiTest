package com.github.diaglyonok.contactsapi;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by diaglyonok on 02.03.18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<User> users;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView ivAvatar;
        TextView tvName;
        TextView tvNum;
        CardView cv;



        public ViewHolder(View v) {
            super(v);
            ivAvatar = v.findViewById(R.id.iv_avatar);
            tvName = v.findViewById(R.id.tv_name_surname);
            tvNum = v.findViewById(R.id.tv_num);
            cv = v.findViewById(R.id.card);

        }
    }

    public RecyclerViewAdapter(ArrayList<User> users) {
        this.users = users;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        // create a new view
        View v = LayoutInflater.from(context)
                .inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User u = users.get(position);
        String sex = u.getSex();
        String name = u.getName().substring(0, 1).toUpperCase() + u.getName().substring(1);
        String surname =  u.getSurName().substring(0, 1).toUpperCase() + u.getSurName().substring(1);
        holder.tvName.setText(String.format("%s, %s %s", u.getTitle(), name, surname));
        holder.tvNum.setText(String.format(Locale.UK,"%d.", (position + 1)));
        Picasso.with(context)
                .load(u.getUrlImage())
                .transform(sex.equals("male")? new MaskTransformation(context, R.drawable.star) : new MaskTransformation(context, R.drawable.heart))//*/
                .placeholder(R.drawable.person_icon)
                .into(holder.ivAvatar);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return users.size();
    }
}