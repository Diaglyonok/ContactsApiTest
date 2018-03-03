package com.github.diaglyonok.contactsapi;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by diaglyonok on 02.03.18.
 */


public class DownloadFilesTask extends AsyncTask<Integer, Void, JSONObject> {

    public static String num = "40";
    public static final String API_URL = "https://randomuser.me/api/?results=";


    public AsyncResponse delegate;

    protected JSONObject doInBackground(Integer... nums) {

        JSONObject jsonObject = null;
        try {
            jsonObject = getJSONObjectFromURL(API_URL + num);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject;
    }

    protected void onProgressUpdate(Void... voids) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(JSONObject result) {
        JSONArray array = null;
        ArrayList<User> allUsers = new ArrayList<>();
        try {
            array = result.getJSONArray("results");
            for (int i = 0; i < (array != null ? array.length() : 0); i++){
                User user = new User(
                        array.getJSONObject(i).get("gender").toString(),
                        array.getJSONObject(i).getJSONObject("picture").get("large").toString(),
                        array.getJSONObject(i).getJSONObject("name").get("title").toString(),
                        array.getJSONObject(i).getJSONObject("name").get("first").toString(),
                        array.getJSONObject(i).getJSONObject("name").get("last").toString()
                        );
                allUsers.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        delegate.processFinish(allUsers);
    }


    private static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        Log.i(MainActivity.LOG_TAG, "JSON: " + jsonString);

        return new JSONObject(jsonString);
    }
}


