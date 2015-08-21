package xeleciumlabs.speedrunrecords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String GAME_LIST = "GAME_LIST";

    private Button mGamesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGamesButton = (Button)findViewById(R.id.gamesButton);
        mGamesButton.setOnClickListener(gamesClickListener);
    }

    OnClickListener gamesClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, GameListActivity.class);
            startActivity(intent);
        }
    };

}
