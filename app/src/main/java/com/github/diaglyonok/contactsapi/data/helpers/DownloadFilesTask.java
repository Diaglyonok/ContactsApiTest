package com.github.diaglyonok.contactsapi.data.helpers;

import android.os.AsyncTask;
import android.util.Log;
import com.github.diaglyonok.contactsapi.controllers.MainController;
import com.github.diaglyonok.contactsapi.controllers.interfaces.AsyncResponse;
import com.github.diaglyonok.contactsapi.data.model.User;
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
 * https://github.com/Diaglyonok
 * diaglyonok@yandex.ru
 */


/*
Class, that connects with randomuser api, downloads some new contacts
and send them to MainController, using interface AsyncResponse
// */
public class DownloadFilesTask extends AsyncTask<Integer, Void, JSONObject> {

    //static parameters, that build http request string.
    private static final String num = "40";
    private static final String API_URL = "https://randomuser.me/api/?results=";

    //Object of Class, that implements interface AsyncResponse
    public AsyncResponse delegate;

    //Downloading in background thread.
    protected JSONObject doInBackground(Integer... nums) {

        JSONObject jsonObject = null;
        try {
            //Getting json from URL (api)
            jsonObject = getJSONObjectFromURL(API_URL + num);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject;
    }

    //Execute when downloading progress changed.
    protected void onProgressUpdate(Void... voids) {
        //Progress changing...
    }

    //Execute when downloading ends.
    protected void onPostExecute(JSONObject result) {
        //array for JSONObjects of contacts.
        JSONArray array;
        //Array list of Users as a model.
        ArrayList<User> allUsers = new ArrayList<>();
        try {
            array = result.getJSONArray("results");
            //Creating object for every user and adding it to array list.
            for (int i = 0; i < (array != null ? array.length() : 0); i++){
                User user = new User(
                        array.getJSONObject(i).get("gender").toString(),                         // gender, String
                        array.getJSONObject(i).getJSONObject("picture").get("large").toString(), //url too picture, String
                        array.getJSONObject(i).getJSONObject("name").get("title").toString(),    //title of name (Mr, Ms...), String
                        array.getJSONObject(i).getJSONObject("name").get("first").toString(),    //name, string
                        array.getJSONObject(i).getJSONObject("name").get("last").toString()      //surname, string
                        );
                allUsers.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Using interface data send to MainController.
        delegate.processFinished(allUsers);
    }


    //Method that allows get JSON from URL
    private static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        //String builder for adding lines to string
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        //Adding all lines to string line
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();

        String jsonString = sb.toString();

        //Logging downloaded string
        Log.i(MainController.LOG_TAG, "JSON: " + jsonString);

        return new JSONObject(jsonString);
    }
}


