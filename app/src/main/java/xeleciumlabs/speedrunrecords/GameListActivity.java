package xeleciumlabs.speedrunrecords;

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

/**
 * Created by Xelecium on 8/19/2015.
 */
public class GameListActivity extends Activity {

    private static final String TAG = GameListActivity.class.getSimpleName();

    private EditText mSearchBar;
    private ListView mGameList;

    private ProgressBar mProgressBar;
    private ImageView mRefreshImageView;

    private Game[] mGames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        mSearchBar = (EditText)findViewById(R.id.searchBar);
        mGameList = (ListView)findViewById(R.id.gameList);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mRefreshImageView = (ImageView)findViewById(R.id.refresh);


        getGameList();

        GameAdapter adapter = new GameAdapter(this, mGames);
        mGameList.setAdapter(adapter);
    }

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
                            mGames = getGameData(jsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    updateDisplay();
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

    private Game[] getGameData(String jsonData) throws JSONException {
        JSONObject data = new JSONObject(jsonData);
        JSONArray games = data.getJSONArray("data");

        Game[] gameArray = new Game[games.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonGame = games.getJSONObject(i);
            Game game = new Game();

            game.setGameId(jsonGame.getString("id"));

            JSONObject jsonGameName = jsonGame.getJSONObject("names");
            game.setGameName(jsonGameName.getString("international"));

            game.setGameRelease(jsonGame.getInt("released"));

            game.setGamePlatform(jsonGame.getString("platforms"));

            gameArray[i] = game;
        }
        return gameArray;
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