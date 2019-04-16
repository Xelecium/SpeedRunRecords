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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import xeleciumlabs.speedrunrecords.R;
import xeleciumlabs.speedrunrecords.adapter.GameAdapter;
import xeleciumlabs.speedrunrecords.adapter.PlatformAdapter;
import xeleciumlabs.speedrunrecords.adapter.RegionAdapter;
import xeleciumlabs.speedrunrecords.adapter.RunAdapter;
import xeleciumlabs.speedrunrecords.adapter.SeriesAdapter;
import xeleciumlabs.speedrunrecords.adapter.UserAdapter;
import xeleciumlabs.speedrunrecords.data.APIData;
import xeleciumlabs.speedrunrecords.data.SRR;
import xeleciumlabs.speedrunrecords.data.Game;

/**
 * Created by Xelecium on 8/19/2015.
 */
public class DataActivity extends Activity {

    private static final String TAG = DataActivity.class.getSimpleName();


    private EditText mSearchBar;
    private ListView mItemList;

    private ArrayList mData;
    private BaseAdapter mAdapter;

    private String mApiUrl;
    private String mItemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        mSearchBar = (EditText)findViewById(R.id.searchBar);
        mItemList = (ListView)findViewById(R.id.itemList);

        mItemType = getIntent().getStringExtra(SRR.ACTIVITY_EXTRA);

        mData = new ArrayList();

        //Set adapter (inherited from BaseAdapter) based on data type selected
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
                mAdapter = new RegionAdapter(DataActivity.this, mData);
                break;
            case SRR.RUN_DATA_TYPE:
                mApiUrl = SRR.API_BASE_URL + SRR.API_RUNS;
                mAdapter = new RunAdapter(DataActivity.this, mData);
                break;
            case SRR.USER_DATA_TYPE:
                mApiUrl = SRR.API_BASE_URL + SRR.API_USERS;
                mAdapter = new UserAdapter(DataActivity.this, mData);
                break;
            default: break;
        }
        mItemList.setAdapter(mAdapter);

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

    //When APIData sends a message saying it's parsed more data, update the adapter with it
    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mAdapter.notifyDataSetChanged();
        }
    };
}
