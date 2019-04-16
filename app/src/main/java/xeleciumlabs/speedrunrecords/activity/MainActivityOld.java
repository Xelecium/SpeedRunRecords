//package xeleciumlabs.speedrunrecords.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//
//import java.util.ArrayList;
//
//import xeleciumlabs.speedrunrecords.R;
//import xeleciumlabs.speedrunrecords.adapter.GameAdapter;
//import xeleciumlabs.speedrunrecords.adapter.UserAdapter;
//import xeleciumlabs.speedrunrecords.data.APIData;
//import xeleciumlabs.speedrunrecords.data.Game;
//import xeleciumlabs.speedrunrecords.data.SRR;
//
//public class MainActivityOld {
//
//    public static final String TAG = MainActivity.class.getSimpleName();
//
//    private Button mSeriesButton;
//    private Button mGamesButton;
//    private Button mPlatformButton;
//    private Button mRegionButton;
//    private Button mRunButton;
//    private Button mUserButton;
//
//
//    private EditText searchBar;
//    private Button searchButton;
//    private ListView gameList;
//    private ListView userList;
//
//    private Intent mIntent;
//
//    private String gameApiUrl;
//    private GameAdapter gameAdapter;
//    private ArrayList gameResults;
//
//    private String userApiUrl;
//    private UserAdapter userAdapter;
//    private ArrayList userResults;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        //UI Elements
//        searchBar = findViewById(R.id.searchBar);
//        searchButton = findViewById(R.id.searchButton);
//        gameList = findViewById(R.id.gameResultsList);
//        //userList = findViewById(R.id.userResultsList);
//
//        gameResults = new ArrayList<Game>();
//        //userResults = new ArrayList<User>();
//
//        searchButton.setOnClickListener(searchClickListener);
//
//
//
//        //Adapters for the results ListViews
//        gameAdapter = new GameAdapter(MainActivity.this, gameResults);
//        //gameList.setAdapter(gameAdapter);
//        userAdapter = new UserAdapter(MainActivity.this, userResults);
//        //userList.setAdapter(userAdapter);
//
//
//
////        //Set up each button and its ClickListener
////        mSeriesButton = (Button)findViewById(R.id.seriesButton);
////        mSeriesButton.setOnClickListener(new OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                startDataList(SRR.SERIES_DATA_TYPE);
////            }
////        });
////        mGamesButton = (Button)findViewById(R.id.gamesButton);
////        mGamesButton.setOnClickListener(new OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                startDataList(SRR.GAME_DATA_TYPE);
////            }
////        });
////        mPlatformButton = (Button)findViewById(R.id.platformsButton);
////        mPlatformButton.setOnClickListener(new OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                startDataList(SRR.PLATFORM_DATA_TYPE);
////            }
////        });
////        mRegionButton = (Button)findViewById(R.id.regionsButton);
////        mRegionButton.setOnClickListener(new OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                startDataList(SRR.REGION_DATA_TYPE);
////            }
////        });
////        mRunButton = (Button)findViewById(R.id.runsButton);
////        mRunButton.setOnClickListener(new OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                startDataList(SRR.RUN_DATA_TYPE);
////            }
////        });
////        mUserButton = (Button)findViewById(R.id.usersButton);
////        mUserButton.setOnClickListener(new OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                startDataList(SRR.USER_DATA_TYPE);
////            }
////        });
//
//        //All buttons lead to DataActivity, but with different data
//        //mIntent = new Intent(MainActivity.this, DataActivity.class);
//    }
//
////    @Override
////    protected void onResume() {
////        super.onResume();
////        registerReceiver(updateReceiver, new IntentFilter(APIData.UPDATE_VIEW));
////    }
////
////    @Override
////    protected void onPause() {
////        super.onPause();
////        unregisterReceiver(updateReceiver);
////    }
//
//    private void startDataList(String dataType) {
//        //Assign the data type based on the button pressed and start DataActivity
//        mIntent.putExtra(SRR.ACTIVITY_EXTRA, dataType);
//        Log.i(TAG, "Going to: " + dataType);
//        startActivity(mIntent);
//    }
//
//
////    private BroadcastReceiver updateReceiver = new BroadcastReceiver() {
////        @Override
////        public void onReceive(Context context, Intent intent) {
////
////        }
////    };
//
//    private View.OnClickListener searchClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            String query = searchBar.getText().toString();
//            if (!query.equals("")) {
//                //Sets the API URLs for retrieving data
//                gameApiUrl = SRR.API_BASE_URL + SRR.API_GAMES + query;
//                //userApiUrl = SRR.API_BASE_URL + SRR.API_USERS + query;
//
//                //Gets JSON data from the API URLs, and stores them
//                APIData.getData(MainActivity.this, gameResults, gameApiUrl, SRR.GAME_DATA_TYPE);
//                //APIData.getData(MainActivity.this, userResults, userApiUrl, SRR.USER_DATA_TYPE);
//
//                gameAdapter.notifyDataSetChanged();
//                //userAdapter.notifyDataSetChanged();
//            }
//        }
//    };
//}
