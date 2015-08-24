package xeleciumlabs.speedrunrecords.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import xeleciumlabs.speedrunrecords.R;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final String ACTIVITY_EXTRA = "API_TYPE";
    public static final String SERIES_DATA_TYPE = "TYPE_SERIES";
    public static final String GAME_DATA_TYPE = "TYPE_GAME";
    public static final String PLATFORM_DATA_TYPE = "TYPE_PLATFORM";
    public static final String REGION_DATA_TYPE = "TYPE_REGION";
    public static final String RUN_DATA_TYPE = "TYPE_RUN";
    public static final String USER_DATA_TYPE = "TYPE_USER";


    private Button mSeriesButton;
    private Button mGamesButton;
    private Button mPlatformButton;
    private Button mRegionButton;
    private Button mRunButton;
    private Button mUserButton;

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeriesButton = (Button)findViewById(R.id.seriesButton);
        mSeriesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDataList(SERIES_DATA_TYPE);
            }
        });
        mGamesButton = (Button)findViewById(R.id.gamesButton);
        mGamesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDataList(GAME_DATA_TYPE);
            }
        });
        mPlatformButton = (Button)findViewById(R.id.platformsButton);
        mPlatformButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDataList(PLATFORM_DATA_TYPE);
            }
        });
        mRegionButton = (Button)findViewById(R.id.regionsButton);
        mRegionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDataList(REGION_DATA_TYPE);
            }
        });
        mRunButton = (Button)findViewById(R.id.runsButton);
        mRunButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDataList(RUN_DATA_TYPE);
            }
        });
        mUserButton = (Button)findViewById(R.id.usersButton);
        mUserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDataList(USER_DATA_TYPE);
            }
        });

        mIntent = new Intent(MainActivity.this, DataActivity.class);
    }

    private void startDataList(String dataType) {
        mIntent.putExtra(ACTIVITY_EXTRA, dataType);
        Log.i(TAG, "Going to: " + dataType);
        startActivity(mIntent);
    }

}
