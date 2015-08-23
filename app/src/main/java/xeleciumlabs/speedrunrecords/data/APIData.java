package xeleciumlabs.speedrunrecords.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.BaseAdapter;
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

import xeleciumlabs.speedrunrecords.activity.GameListActivity;

/**
 * Created by Xelecium on 8/22/2015.
 */
public abstract class APIData extends Activity {

    public static final String UPDATE_VIEW = "xeleciumlabs.speedrunrecords.updateview";

    private static Context mContext;
    private static ArrayList mData;
    private static String mDataType;

    private static Intent mUpdateIntent;

    public static void getData(Context context, final ArrayList arrayList, String apiUrl, String dataType) {

        mContext = context;
        final String TAG = context.toString();

        mData = new ArrayList();
        mDataType = dataType;

        mUpdateIntent = new Intent(UPDATE_VIEW);

        String apiKey = "vuoc0473yvpmiiwiaaehcfh9w";

        if (isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Request request, IOException e) {
                    Toast.makeText(mContext, "No JSON Response", Toast.LENGTH_LONG);
                    //alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            parseData(jsonData, arrayList);
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
            Toast.makeText(mContext, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }
    }

    private static void parseData(String jsonData, ArrayList arrayList) throws JSONException {
        //TODO: Add a loop for parsing further pages (provided in "pagination")

        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray data = jsonObject.getJSONArray("data");

        switch(mDataType) {
            case GameListActivity.SERIES_DATA_TYPE:
                parseSeries(data, arrayList);
                break;
            case GameListActivity.GAME_DATA_TYPE:
                parseGame(data, arrayList);
                break;
            case GameListActivity.PLATFORM_DATA_TYPE:
                parsePlatform(data, arrayList);
                break;
            case GameListActivity.REGION_DATA_TYPE:
                parseRegion(data, arrayList);
                break;
            case GameListActivity.RUN_DATA_TYPE:

                break;
            case GameListActivity.USER_DATA_TYPE:

                break;
            default: break;
        }
    }

    private static void parseSeries(JSONArray data, ArrayList arrayList) throws JSONException {
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            Series series = new Series();

            series.setSeriesId(object.getString("id"));
            series.setSeriesName(object.getJSONObject("names").getString("international"));
            series.setSeriesWebLink(object.getString("weblink"));

            arrayList.add(series);
        }
        mContext.sendBroadcast(mUpdateIntent);
    }
    private static void parseGame(JSONArray data, ArrayList arrayList) throws JSONException {
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            Game game = new Game();

            game.setGameId(object.getString("id"));
            game.setGameName(object.getJSONObject("names").getString("international"));
            game.setGameWebLink(object.getString("weblink"));
            game.setGameRelease(object.getInt("released"));
            game.setGamePlatform(object.getString("platforms"));

            arrayList.add(game);
        }
        mContext.sendBroadcast(mUpdateIntent);
    }
    private static void parsePlatform(JSONArray data, ArrayList arrayList) throws JSONException {
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            Platform platform = new Platform();

            platform.setPlatformId(object.getString("id"));
            platform.setPlatformName(object.getString("name"));
            platform.setPlatformRelease(object.getInt("released"));

            arrayList.add(platform);
        }
        mContext.sendBroadcast(mUpdateIntent);
    }
    private static void parseRegion(JSONArray data, ArrayList arrayList) throws JSONException {
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            Region region = new Region();

            region.setRegionId(object.getString("id"));
            region.setRegionName(object.getString("name"));

            arrayList.add(region);
        }
        mContext.sendBroadcast(mUpdateIntent);
    }


    private static boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }
}
