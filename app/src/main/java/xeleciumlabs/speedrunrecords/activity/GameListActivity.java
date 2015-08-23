package xeleciumlabs.speedrunrecords.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import xeleciumlabs.speedrunrecords.R;
import xeleciumlabs.speedrunrecords.adapter.GameAdapter;
import xeleciumlabs.speedrunrecords.data.APIData;
import xeleciumlabs.speedrunrecords.data.Game;

/**
 * Created by Xelecium on 8/19/2015.
 */
public class GameListActivity extends Activity {

    private static final String TAG = GameListActivity.class.getSimpleName();

    public static final String SERIES_DATA_TYPE = "TYPE_SERIES";
    public static final String GAME_DATA_TYPE = "TYPE_GAME";
    public static final String PLATFORM_DATA_TYPE = "TYPE_PLATFORM";
    public static final String REGION_DATA_TYPE = "TYPE_REGION";
    public static final String RUN_DATA_TYPE = "TYPE_RUN";
    public static final String USER_DATA_TYPE = "TYPE_USER";

    public static final String API_GAME_URL = "http://www.speedrun.com/api/v1/games";

    private EditText mSearchBar;
    private ListView mGameList;

    private ProgressBar mProgressBar;
    private ImageView mRefreshImageView;

    private ArrayList<Game> mGames;
    private GameAdapter mGameAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        mSearchBar = (EditText)findViewById(R.id.searchBar);
        mGameList = (ListView)findViewById(R.id.gameList);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mRefreshImageView = (ImageView)findViewById(R.id.refresh);

        mGames = new ArrayList<>();



//        getGameList();

        mGameAdapter = new GameAdapter(GameListActivity.this, mGames);
        mGameList.setAdapter(mGameAdapter);

        APIData.getData(this, mGames, API_GAME_URL, GAME_DATA_TYPE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mUpdateReceiver, new IntentFilter(APIData.UPDATE_VIEW));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mUpdateReceiver);
    }


    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mGameAdapter.notifyDataSetChanged();
        }
    };

    private void getGameList() {

        String apiKey = "vuoc0473yvpmiiwiaaehcfh9w";
        String apiUrl = "http://www.speedrun.com/api/v1/games";

        if (isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    Toast.makeText(GameListActivity.this, "No JSON Response", Toast.LENGTH_LONG);
                    //alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            getGameData(jsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mGameAdapter.notifyDataSetChanged();
                                }
                            });

                        }
                        else {
//                            alertUserAboutError();
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
        else {
            Toast.makeText(this, "Network is unavailable!",
                    Toast.LENGTH_LONG).show();
        }


    }

    private void getGameData(String jsonData) throws JSONException {
        JSONObject data = new JSONObject(jsonData);
        JSONArray games = data.getJSONArray("data");

        for (int i = 0; i < games.length(); i++) {
            JSONObject jsonGame = games.getJSONObject(i);
            Game game = new Game();

            game.setGameId(jsonGame.getString("id"));

            game.setGameName(jsonGame.getJSONObject("names").getString("international"));
//            JSONObject jsonGameName = jsonGame.getJSONObject("names");
//            game.setGameName(jsonGameName.getString("international"));
            game.setGameWebLink(jsonGame.getString("weblink"));
            game.setGameRelease(jsonGame.getInt("released"));

            game.setGamePlatform(jsonGame.getString("platforms"));

            mGames.add(game);
        }
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }
}
