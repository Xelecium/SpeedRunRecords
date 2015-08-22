package xeleciumlabs.speedrunrecords.activity;

import android.app.Activity;
import android.content.Context;
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
import xeleciumlabs.speedrunrecords.adapter.SeriesAdapter;
import xeleciumlabs.speedrunrecords.data.Game;
import xeleciumlabs.speedrunrecords.data.Series;

public class SeriesListActivity extends Activity {

    private static final String TAG = SeriesListActivity.class.getSimpleName();

    private EditText mSearchBar;
    private ListView mSeriesList;

    private ProgressBar mProgressBar;
    private ImageView mRefreshImageView;

    private ArrayList<Series> mSeries;
    private SeriesAdapter mSeriesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_list);

        mSearchBar = (EditText)findViewById(R.id.searchBar);
        mSeriesList = (ListView)findViewById(R.id.seriesList);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mRefreshImageView = (ImageView)findViewById(R.id.refresh);

        mSeries = new ArrayList<>();

        getGameList();

        mSeriesAdapter = new SeriesAdapter(SeriesListActivity.this, mSeries);
        mSeriesList.setAdapter(mSeriesAdapter);
    }

    private void getGameList() {

        String apiKey = "vuoc0473yvpmiiwiaaehcfh9w";
        String apiUrl = "http://www.speedrun.com/api/v1/series";

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
                    Toast.makeText(SeriesListActivity.this, "No JSON Response", Toast.LENGTH_LONG);
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
                                    mSeriesAdapter.notifyDataSetChanged();
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

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonGame = games.getJSONObject(i);
            Series series = new Series();

            series.setSeriesId(jsonGame.getString("id"));

            series.setSeriesName(jsonGame.getJSONObject("names").getString("international"));
            series.setSeriesWebLink(jsonGame.getString("weblink"));

            mSeries.add(series);
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
