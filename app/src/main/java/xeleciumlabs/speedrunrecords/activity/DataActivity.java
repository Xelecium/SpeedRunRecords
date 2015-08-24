package xeleciumlabs.speedrunrecords.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
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
import xeleciumlabs.speedrunrecords.adapter.PlatformAdapter;
import xeleciumlabs.speedrunrecords.adapter.RunAdapter;
import xeleciumlabs.speedrunrecords.adapter.SeriesAdapter;
import xeleciumlabs.speedrunrecords.data.APIData;
import xeleciumlabs.speedrunrecords.data.SRR;
import xeleciumlabs.speedrunrecords.data.Game;

/**
 * Created by Xelecium on 8/19/2015.
 */
public class DataActivity extends Activity {

    private static final String TAG = DataActivity.class.getSimpleName();


    private EditText mSearchBar;
    private ListView mGameList;

    private ProgressBar mProgressBar;
    private ImageView mRefreshImageView;

    private ArrayList mData;
    private BaseAdapter mAdapter;

    private String mApiUrl;
    private String mItemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        mSearchBar = (EditText)findViewById(R.id.searchBar);
        mGameList = (ListView)findViewById(R.id.gameList);

        mItemType = getIntent().getStringExtra(SRR.ACTIVITY_EXTRA);

        mData = new ArrayList();

        switch (mItemType) {
            case SRR.SERIES_DATA_TYPE:
                mApiUrl = SRR.API_BASE_URL + SRR.API_SERIES;
                mAdapter = new SeriesAdapter(DataActivity.this, mData);
                break;
            case SRR.GAME_DATA_TYPE:
                mApiUrl = SRR.API_BASE_URL + SRR.API_GAMES;
                mAdapter = new GameAdapter(DataActivity.this, mData);
                break;
            case SRR.PLATFORM_DATA_TYPE:
                mApiUrl = SRR.API_BASE_URL + SRR.API_PLATFORMS;
                mAdapter = new PlatformAdapter(DataActivity.this, mData);
                break;
            case SRR.REGION_DATA_TYPE:
                mApiUrl = SRR.API_BASE_URL + SRR.API_REGIONS;
//                mAdapter = new RegionAdapter(DataActivity.this, mData);
                break;
            case SRR.RUN_DATA_TYPE:
                mApiUrl = SRR.API_BASE_URL + SRR.API_RUNS;
                mAdapter = new RunAdapter(DataActivity.this, mData);
                break;
            case SRR.USER_DATA_TYPE:
                mApiUrl = SRR.API_BASE_URL + SRR.API_USERS;
//                mAdapter = new UserAdapter(DataActivity.this, mData);
                break;
            default: break;
        }

        mGameList.setAdapter(mAdapter);

        //Gets JSON data of type mItemType, from mApiUrl, and stores it in mData
        APIData.getData(this, mData, mApiUrl, mItemType);
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
            mAdapter.notifyDataSetChanged();
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
                    Toast.makeText(DataActivity.this, "No JSON Response", Toast.LENGTH_LONG);
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
                                    mAdapter.notifyDataSetChanged();
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

            mData.add(game);
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
