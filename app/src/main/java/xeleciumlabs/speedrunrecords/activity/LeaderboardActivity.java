package xeleciumlabs.speedrunrecords.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import xeleciumlabs.speedrunrecords.data.Run;

public class LeaderboardActivity extends Activity {

    private static final String TAG = DataActivity.class.getSimpleName();

    private TextView mGameTitleHeader;
    private ListView mItemList;

    private RunAdapter mRunAdapter;

    private String mLeaderboardLink;

    private ArrayList<Run> mRuns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        mGameTitleHeader = (TextView)findViewById(R.id.gameTitle);
        mGameTitleHeader.setText(getIntent().getStringExtra("GAME_TITLE"));
        mGameTitleHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateLeaderboard();
            }
        });

        mItemList = (ListView)findViewById(R.id.itemList);
        mRuns = new ArrayList<Run>();
        mRunAdapter = new RunAdapter(LeaderboardActivity.this, mRuns);
        mItemList.setAdapter(mRunAdapter);
        mItemList.setOnItemClickListener(runClickListener);

        mLeaderboardLink = getIntent().getStringExtra("LEADERBOARD_LINK");
        populateLeaderboard();
    }

    private void populateLeaderboard() {

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

                                run.setGameTitle("");
                                run.setRunTime(object.getJSONObject("times").getInt("primary_t"));
                                JSONObject runner = object.getJSONArray("players").getJSONObject(0);
                                if (runner.getString("rel").equals("user")) {
                                    run.setRunUserId(runner.getString("id"));
                                }
                                else {
                                    //If "rel" is set to "guest", the field is "name" instead of "id"
                                    run.setRunUserId("Guest");
                                }

                                if (object.isNull("videos")) {
                                    run.setVideoLink("");
                                }
                                else {
                                    run.setVideoLink(object.getJSONObject("videos").getJSONArray("links").getJSONObject(0).getString("uri"));
                                }

                                mRuns.add(run);
                            }
                            LeaderboardActivity.this.sendBroadcast(new Intent("xeleciumlabs.speedrunrecords.updateview"));

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
                        Log.e(TAG, "NullPointerException caught: ", e);
                    }
                }
            });
        }
        Log.d(TAG, "End of call");

        //add parsing functions to make 

    }
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
            String url = mRuns.get(i).getVideoLink();
            if (url.isEmpty()) {
                Toast.makeText(LeaderboardActivity.this, "This run does not have a video attached.", Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        }
    };
}
