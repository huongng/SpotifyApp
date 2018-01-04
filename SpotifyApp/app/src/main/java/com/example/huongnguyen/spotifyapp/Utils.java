package com.example.huongnguyen.spotifyapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by huongnguyen on 10/10/17.
 */

public final class Utils {

    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static Event fetchPlaylistData(String requestUrl, String accessToken) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url, accessToken);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        return extractPlaylistFromJson(jsonResponse);
    }

    public static Track fetchTrackData(String requestUrl, String accessToken) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url, accessToken);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        return extractTrackFromJson(jsonResponse);
    }

    private static Track extractTrackFromJson(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            String imgUrl = baseJsonResponse.getString("images");
            String name  = baseJsonResponse.getString("name");
            String artist = baseJsonResponse.getString("artist");
            String uri = baseJsonResponse.getString("uri");

            return new Track(name, artist, imgUrl, uri);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }

    private static Event extractPlaylistFromJson(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            JSONArray tracksArray = baseJsonResponse.getJSONObject("tracks")
                    .getJSONArray("items");
            ArrayList<PlaylistTrack> tracks = new ArrayList<>();
            // Extract data from json and store
            for (int i = 0; i < tracksArray.length(); i++) {
                ArrayList<String> artist = new ArrayList<>();
                JSONObject trackInfo = tracksArray.getJSONObject(i)
                        .getJSONObject("track");

                // Artists
                JSONArray artistsList = trackInfo.getJSONArray("artists");
                for (int j = 0; j < artistsList.length(); j++) {
                    artist.add(j, artistsList.getJSONObject(j).getString("name"));
                }

                // Track name
                String name = trackInfo.getString("name");

                // Track uri
                String uri = trackInfo.getString("uri");

                tracks.add(new PlaylistTrack(name, artist, uri));
            }
            return new Event(tracks);
        }catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }

        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url, String accessToken) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            String authorization = "Bearer " + accessToken;
            urlConnection.addRequestProperty("Authorization", authorization);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
