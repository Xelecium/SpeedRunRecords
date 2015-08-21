package xeleciumlabs.speedrunrecords;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by Xelecium on 8/19/2015.
 */
public class GameAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private Game[] mGames;

    public GameAdapter (Context context, Game[] games) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mGames = games;
    }
    @Override
    public int getCount() {
//        return mGames.length;
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        //if view is not yet populated
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.game_list_item, parent, false);

            holder = new ViewHolder();
            holder.gameTitle = (TextView)convertView.findViewById(R.id.gameTitle);

            holder.viewPosition = position;
            convertView.setTag(holder); //Tag for the RecyclerView
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        Game game = mGames[position];
//        holder.gameTitle.setText(game.getGameName());
        holder.gameTitle.setText("TITLES!!!!");

        return convertView;
    }

    //ViewHolder for recycling
    private static class ViewHolder {
        TextView gameTitle;

        int viewPosition;
    }

    private boolean isNetworkAvailable() {
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
