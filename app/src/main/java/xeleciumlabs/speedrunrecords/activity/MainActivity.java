package xeleciumlabs.speedrunrecords.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import xeleciumlabs.speedrunrecords.R;
import xeleciumlabs.speedrunrecords.data.SRR;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

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

        //Set up each button and its ClickListener
        mSeriesButton = (Button)findViewById(R.id.seriesButton);
        mSeriesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDataList(SRR.SERIES_DATA_TYPE);
            }
        });
        mGamesButton = (Button)findViewById(R.id.gamesButton);
        mGamesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDataList(SRR.GAME_DATA_TYPE);
            }
        });
        mPlatformButton = (Button)findViewById(R.id.platformsButton);
        mPlatformButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDataList(SRR.PLATFORM_DATA_TYPE);
            }
        });
        mRegionButton = (Button)findViewById(R.id.regionsButton);
        mRegionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDataList(SRR.REGION_DATA_TYPE);
            }
        });
        mRunButton = (Button)findViewById(R.id.runsButton);
        mRunButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDataList(SRR.RUN_DATA_TYPE);
            }
        });
        mUserButton = (Button)findViewById(R.id.usersButton);
        mUserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDataList(SRR.USER_DATA_TYPE);
            }
        });

        //All buttons lead to DataActivity, but with different data
        mIntent = new Intent(MainActivity.this, DataActivity.class);
    }

    private void startDataList(String dataType) {
        //Assign the data type based on the button pressed and start DataActivity
        mIntent.putExtra(SRR.ACTIVITY_EXTRA, dataType);
        Log.i(TAG, "Going to: " + dataType);
        startActivity(mIntent);
    }

}
