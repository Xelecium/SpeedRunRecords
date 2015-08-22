package xeleciumlabs.speedrunrecords;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Xelecium on 8/19/2015.
 */
public class GameAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Game> mGames;

    public GameAdapter (Context context, ArrayList<Game> games) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mGames = games;
    }
    @Override
    public int getCount() {
        return mGames.size();
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

        Game game = mGames.get(position);
        holder.gameTitle.setText(game.getGameName());

        return convertView;
    }

    //ViewHolder for recycling
    private static class ViewHolder {
        TextView gameTitle;

        int viewPosition;
    }
}
