package com.github.diaglyonok.contactsapi.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.github.diaglyonok.contactsapi.controllers.MainController;
import com.github.diaglyonok.contactsapi.R;
import com.github.diaglyonok.contactsapi.data.model.User;
import com.github.diaglyonok.contactsapi.ui.helpers.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by diaglyonok on 02.03.18.
 * https://github.com/Diaglyonok
 * diaglyonok@yandex.ru
 */
//View class
public class MainActivity extends AppCompatActivity{

    private ProgressBar progressBar;
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init progress bar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //Init MainController
        controller = new MainController(this); //Link to context of MainActivity as argument
        //Starting downloading contacts
        controller.startDownloading();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Including refresh button to AppBar menu
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                //Refreshing all contacts with controller.
                controller.refreshAll();
                break;
            default:
                break;
        }
        return true;
    }

    /*Method for toggle progress bar VISIBLE or INVISIBLE
      //*/
    public void progressVisible(Boolean visible) {
        if (visible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }

    //Method that ends downloading in view object (set users to RecyclerView)
    public void setRecyclerView(ArrayList<User> users){
        progressVisible(false);

        //Setting RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(users);
        recyclerView.setAdapter(adapter);
    }
}
