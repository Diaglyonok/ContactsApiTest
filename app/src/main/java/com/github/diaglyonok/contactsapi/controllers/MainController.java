package com.github.diaglyonok.contactsapi.controllers;

import android.app.Activity;
import android.util.Log;

import com.github.diaglyonok.contactsapi.controllers.interfaces.AsyncResponse;
import com.github.diaglyonok.contactsapi.data.model.User;
import com.github.diaglyonok.contactsapi.data.helpers.DownloadFilesTask;
import com.github.diaglyonok.contactsapi.ui.MainActivity;
import java.util.ArrayList;

/**
 * Created by diaglyonok on 03.03.18.
 * https://github.com/Diaglyonok
 * diaglyonok@yandex.ru
 */

//Class that controls View and Data
public class MainController implements AsyncResponse {
    //Tag for logging.
    public static final String LOG_TAG = "myLog";

    private Activity activity;

    /*Constructor
      Init Main activity
    //*/
    public MainController(MainActivity activity) {
        this.activity = activity;
    }

    //Downloading starts with DownloadFilesTask class.
    public void startDownloading(){
        DownloadFilesTask downloadTask = new DownloadFilesTask();
        /*Initialising context, that helps DownloadFilesTask
        class to call method processFinished
        //*/
        downloadTask.delegate = this;

        //Starting downloading in background
        downloadTask.execute();
    }

    //Renew downloading contacts
    public void refreshAll() {
        ((MainActivity)activity).progressVisible(true);
        startDownloading();
    }


    /*
    Method calls when process of downloading new contact ended.
    Makes MainActivity set RecyclerView.
     */
    @Override
    public void processFinished(ArrayList<User> output) {
        Log.i(LOG_TAG, "download finished");
        ((MainActivity)activity).setRecyclerView(output);
    }
}
