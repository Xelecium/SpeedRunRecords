package xeleciumlabs.speedrunrecords.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

import xeleciumlabs.speedrunrecords.R;
import xeleciumlabs.speedrunrecords.adapter.RunAdapter;
import xeleciumlabs.speedrunrecords.data.Run;

public class LeaderboardActivity extends Activity {

    private ListView leaderboardListView;
    private RunAdapter runAdapter;
    private ArrayList<Run> runs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        leaderboardListView = findViewById(R.id.leaderboardList);
        runs = new ArrayList<Run>();
        runAdapter = new RunAdapter(LeaderboardActivity.this, runs);
        leaderboardListView.setAdapter(runAdapter);
        leaderboardListView.setOnItemClickListener(runClickListener);
    }

    private OnItemClickListener runClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    };

}
