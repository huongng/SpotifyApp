package com.example.huongnguyen.spotifyapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;

public class MainActivity extends AppCompatActivity implements ConnectionStateCallback {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String CLIENT_ID = "cf4e2e814e1648c4b600533e57a4d77c";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String REDIRECT_URI = "huongnguyen-login://callback";
    private static final int REQUEST_CODE = 1337;
    private static AuthenticationResponse response;

    public static final String TAG = "MainActivity";

    public static final String EXTRA_MESSAGE_STRING = "AccessToken";

    public static String accessToken;

    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login(); // <<<

        gridView = (GridView) findViewById(R.id.options_list_grid);
        gridView.setAdapter(new GridAdapter(this, values, images));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(view.getContext(), AmbientActivity.class);
                        //startActivity(ambientIntent);
                        break;
                    case 1:
                        intent = new Intent(view.getContext(), WorkoutActivity.class);
                        //startActivity(workoutIntent);
                        break;
                    case 2:
                        intent = new Intent(view.getContext(), DinnerActivity.class);
                        //startActivity(dinnerIntent);
                        break;
                    case 3:
                        intent = new Intent(view.getContext(), RoadTripActivity.class);
                        //startActivity(roadTripIntent);
                        break;
                    case 4:
                        intent = new Intent(view.getContext(), StudyActivity.class);
                        //startActivity(studyIntent);
                        break;
                    default:
                        intent = new Intent(view.getContext(), SleepActivity.class);
                        //startActivity(sleepIntent);
                        break;
                }
                intent.putExtra(EXTRA_MESSAGE_STRING, accessToken);
                startActivity(intent);
            }
        });
    }

    private void login() {
        final AuthenticationRequest request = new AuthenticationRequest.Builder(
                CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
                .setScopes(new String[]{"user-read-private",
                        "playlist-read", "playlist-read-private", "streaming"})
                .build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Log.d(TAG, "accessToken " + response.getAccessToken());
                accessToken = response.getAccessToken();
            }
        }
    }

    String[] values = {
            "Ambient",
            "Cardio",
            "Dinner",
            "Road trip",
            "Study",
            "Sleep"
    };
    int[] images = {
            R.drawable.ambient,
            R.drawable.cardio,
            R.drawable.dinner,
            R.drawable.roadtrip,
            R.drawable.study,
            R.drawable.sleep
    };

    @Override
    public void onLoggedIn() {
        Toast.makeText(MainActivity.this, "" + accessToken, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Error error) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }
}
