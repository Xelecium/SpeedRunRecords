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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xeleciumlabs.speedrunrecords.R;
import xeleciumlabs.speedrunrecords.adapter.RunAdapter;
import xeleciumlabs.speedrunrecords.data.APIData;
import xeleciumlabs.speedrunrecords.data.Run;

/**
 * Created by Xelecium on 8/19/2015.
 */
public class DataActivity extends Activity {

    private static final String TAG = DataActivity.class.getSimpleName();

    private TextView mGameTitleHeader;
    private ListView mItemList;

    private RunAdapter mRunAdapter;

    private String mApiUrl;
    private String mItemType;
    private String mGameTitle;
    private String mLeaderboardLink;

    private ArrayList<Run> mRuns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        mGameTitleHeader = (TextView)findViewById(R.id.gameTitle);

        mItemList = (ListView)findViewById(R.id.itemList);
        mRuns = new ArrayList<Run>();
        mRunAdapter = new RunAdapter(DataActivity.this, mRuns);
        mItemList.setAdapter(mRunAdapter);
        mItemList.setOnItemClickListener(runClickListener);

        //mItemType = getIntent().getStringExtra(SRR.ACTIVITY_EXTRA);
        mGameTitleHeader.setText(getIntent().getStringExtra("GAME_TITLE"));
        mLeaderboardLink = getIntent().getStringExtra("LEADERBOARD_LINK");

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(mLeaderboardLink)
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
                            JSONArray data = jsonObject.getJSONObject("data").getJSONArray("runs");

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject(i).getJSONObject("run");
                                Run run = new Run();

                                run.setGameTitle(mGameTitle);
                                run.setRunTime(object.getJSONObject("times").getInt("primary_t"));
                                run.setRunUserId(object.getJSONArray("players").getJSONObject(0).getString("id"));
                                run.setVideoLink(object.getJSONObject("videos").getJSONObject("links").getString("uri"));

                                mRuns.add(run);
                            }
                            DataActivity.this.sendBroadcast(new Intent("xeleciumlabs.speedrunrecords.updateview"));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRunAdapter.notifyDataSetChanged();
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
                    catch (NullPointerException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }
        Log.d(TAG, "End of call");






//        //Set adapter (inherited from BaseAdapter) based on data type selected
//        switch (mItemType) {
//            case SRR.SERIES_DATA_TYPE:
//                mApiUrl = SRR.API_BASE_URL + SRR.API_SERIES;
//                mAdapter = new SeriesAdapter(DataActivity.this, mData);
//                break;
//            case SRR.GAME_DATA_TYPE:
//                mApiUrl = SRR.API_BASE_URL + SRR.API_GAMES;
//                mAdapter = new GameAdapter(DataActivity.this, mData);
//                break;
//            case SRR.PLATFORM_DATA_TYPE:
//                mApiUrl = SRR.API_BASE_URL + SRR.API_PLATFORMS;
//                mAdapter = new PlatformAdapter(DataActivity.this, mData);
//                break;
//            case SRR.REGION_DATA_TYPE:
//                mApiUrl = SRR.API_BASE_URL + SRR.API_REGIONS;
//                mAdapter = new RegionAdapter(DataActivity.this, mData);
//                break;
//            case SRR.RUN_DATA_TYPE:
//                mApiUrl = SRR.API_BASE_URL + SRR.API_RUNS;
//                mAdapter = new RunAdapter(DataActivity.this, mData);
//                break;
//            case SRR.USER_DATA_TYPE:
//                mApiUrl = SRR.API_BASE_URL + SRR.API_USERS;
//                mAdapter = new UserAdapter(DataActivity.this, mData);
//                break;
//            default: break;
//        }
//        mItemList.setAdapter(mAdapter);
//
//        //Gets JSON data of type mItemType, from mApiUrl, and stores it in mData
//        APIData.getData(this, mData, mApiUrl, mItemType);
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

    //When APIData sends a message saying it's parsed more data, update the adapter with it
    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mRunAdapter.notifyDataSetChanged();
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

    private AdapterView.OnItemClickListener runClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //Take JSON object
            //{ data [ { links [ { rel: "leaderboard", uri:"***" } ] } ] }
        }
    };
}
