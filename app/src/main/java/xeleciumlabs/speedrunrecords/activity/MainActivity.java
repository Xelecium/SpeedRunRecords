package xeleciumlabs.speedrunrecords.activity;

import android.app.Activity;
<<<<<<< HEAD
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
=======
import android.content.Intent;
>>>>>>> parent of bd7f712... Updated Search UI
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
<<<<<<< HEAD
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
=======
>>>>>>> parent of bd7f712... Updated Search UI

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import xeleciumlabs.speedrunrecords.R;
<<<<<<< HEAD
import xeleciumlabs.speedrunrecords.adapter.GameAdapter;
import xeleciumlabs.speedrunrecords.data.Game;
=======
>>>>>>> parent of bd7f712... Updated Search UI
import xeleciumlabs.speedrunrecords.data.SRR;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText searchBar;
<<<<<<< HEAD
    //private Button searchButton;

    private ListView gameResults;
    private GameAdapter gameAdapter;
    private ArrayList<Game> games;

=======
    private Intent mIntent;

>>>>>>> parent of bd7f712... Updated Search UI
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        searchBar = findViewById(R.id.searchBar);
        //searchButton = findViewById(R.id.searchButton);
        updateGameList();
    }

    private void updateGameList() {
        gameResults = findViewById(R.id.gameResultsList);
        games = new ArrayList<Game>();
        gameAdapter = new GameAdapter(MainActivity.this, games);
        gameResults.setAdapter(gameAdapter);
        gameResults.setOnItemClickListener(gameClickListener);

        String query = searchBar.getText().toString();
        String encodedQuery = "";
        try {
            encodedQuery = URLEncoder.encode(query,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(MainActivity.this, "Problem encoding query", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        String gameApiUrl = SRR.API_BASE_URL + SRR.API_GAMES + encodedQuery;
        //APIData.getData(MainActivity.this, games, gameApiUrl, SRR.GAME_DATA_TYPE);

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(gameApiUrl)
                    .build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //Toast.makeText(MainActivity.this, "No JSON Response", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            JSONObject jsonObject = new JSONObject(jsonData);
                            JSONArray data = jsonObject.getJSONArray("data");

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);
                                Game game = new Game();

                                game.setGameId(object.getString("id"));
                                game.setGameName(object.getJSONObject("names")
                                        .getString("twitch"));
                                game.setGameWebLink(object.getString("weblink"));
                                game.setGameRelease(object.getInt("released"));
                                game.setGamePlatform(object.getString("platforms"));

                                games.add(game);
                            }
                            MainActivity.this.sendBroadcast(new Intent("xeleciumlabs.speedrunrecords.updateview"));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    gameAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                    catch(IOException e)
                    {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }
        Log.d(TAG, "end of call");
    }

    public void searchOnClick(View view) {
        Toast.makeText(MainActivity.this, "Searching for games...", Toast.LENGTH_LONG).show();
        updateGameList();
    }

    private OnItemClickListener gameClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            gameResults.setVisibility(View.GONE);
            //Take JSON object
                //{ data [ { links [ { rel: "leaderboard", uri:"***" } ] } ] }
            Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
        }
    };

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        else {
            Toast.makeText(this, "Sorry, the network is unavailable.", Toast.LENGTH_LONG).show();
        }

        return isAvailable;
    }


=======
        //Search Bar contents
        searchBar = findViewById(R.id.searchBar);

//        //Set up each button and its ClickListener
//        mSeriesButton = (Button)findViewById(R.id.seriesButton);
//        mSeriesButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startDataList(SRR.SERIES_DATA_TYPE);
//            }
//        });
//        mGamesButton = (Button)findViewById(R.id.gamesButton);
//        mGamesButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startDataList(SRR.GAME_DATA_TYPE);
//            }
//        });
//        mPlatformButton = (Button)findViewById(R.id.platformsButton);
//        mPlatformButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startDataList(SRR.PLATFORM_DATA_TYPE);
//            }
//        });
//        mRegionButton = (Button)findViewById(R.id.regionsButton);
//        mRegionButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startDataList(SRR.REGION_DATA_TYPE);
//            }
//        });
//        mRunButton = (Button)findViewById(R.id.runsButton);
//        mRunButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startDataList(SRR.RUN_DATA_TYPE);
//            }
//        });
//        mUserButton = (Button)findViewById(R.id.usersButton);
//        mUserButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startDataList(SRR.USER_DATA_TYPE);
//            }
//        });

        //All buttons lead to DataActivity, but with different data
        mIntent = new Intent(MainActivity.this, DataActivity.class);
    }

    private void startDataList(String dataType) {
        //Assign the data type based on the button pressed and start DataActivity
        mIntent.putExtra(SRR.ACTIVITY_EXTRA, dataType);
        Log.i(TAG, "Going to: " + dataType);
        startActivity(mIntent);
    }

>>>>>>> parent of bd7f712... Updated Search UI
}
