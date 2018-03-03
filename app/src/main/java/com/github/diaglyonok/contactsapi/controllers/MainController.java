package com.github.diaglyonok.contactsapi.controllers;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.github.diaglyonok.contactsapi.R;
import com.github.diaglyonok.contactsapi.controllers.interfaces.AsyncResponse;
import com.github.diaglyonok.contactsapi.data.model.User;
import com.github.diaglyonok.contactsapi.helpers.DownloadFilesTask;
import com.github.diaglyonok.contactsapi.helpers.RecyclerViewAdapter;
import com.github.diaglyonok.contactsapi.ui.MainActivity;
import java.util.ArrayList;

/**
 * Created by diaglyonok on 03.03.18.
 */

public class MainController implements AsyncResponse {
    public static final String LOG_TAG = "myLog";

    private Context context;
    private Activity activity;

    public MainController(MainActivity activity) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    public void startDownloading(){
        DownloadFilesTask downloadTask = new DownloadFilesTask();
        downloadTask.delegate = this;

        downloadTask.execute();
    }

    public void refreshAll() {
        ((MainActivity)activity).progressVisible(true);
        startDownloading();
    }


    @Override
    public void processFinished(ArrayList<User> output) {
        Log.i(LOG_TAG, "download finished");
        ((MainActivity)activity).progressVisible(false);

        RecyclerView recyclerView = activity.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(output);
        recyclerView.setAdapter(adapter);
    }
}
