package com.github.diaglyonok.contactsapi.ui.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.diaglyonok.contactsapi.R;
import com.github.diaglyonok.contactsapi.data.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by diaglyonok on 02.03.18.
 * https://github.com/Diaglyonok
 * diaglyonok@yandex.ru
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    //View adapter
    private ArrayList<User> users;
    private Context context;

    //View holder (cardView)
    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAvatar;
        private TextView tvName;
        private TextView tvNum;

        //ViewHolder constructor
        ViewHolder(View v) {
            super(v);
            ivAvatar = v.findViewById(R.id.iv_avatar);
            tvName = v.findViewById(R.id.tv_name_surname);
            tvNum = v.findViewById(R.id.tv_num);

        }
    }

    /*RecyclerViewAdapter constructor
        Initialising users
    //*/
    public RecyclerViewAdapter(ArrayList<User> users) {
        this.users = users;
    }

    //OnCreate
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        //Setting view holder
        View v = LayoutInflater.from(context)
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User u = users.get(position);
        String sex = u.getSex();
        String name = u.getName().substring(0, 1).toUpperCase() + u.getName().substring(1);
        String surname =  u.getSurName().substring(0, 1).toUpperCase() + u.getSurName().substring(1);
        holder.tvName.setText(String.format("%s, %s %s", u.getTitle(), name, surname));
        holder.tvNum.setText(String.format(Locale.UK,"%d.", (position + 1)));
        //Using Picasso lib
        Picasso.with(context)
                .load(u.getUrlImage()) //Loading image from url
                .transform(
                        sex.equals("male")?                                 // if sex male
                        new MaskTransformation(context, R.drawable.star) :  // set mask star
                        new MaskTransformation(context, R.drawable.heart))  // else set mask heart
                .placeholder(R.drawable.person_icon)    // default icon
                .into(holder.ivAvatar);  // set mask to imageView
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}