package xeleciumlabs.speedrunrecords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import xeleciumlabs.speedrunrecords.R;

/**
 * Created by Xelecium on 8/16/2015.
 */
public class RunAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    //Constructor
    public RunAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
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
            convertView = mInflater.inflate(R.layout.run_list_item, parent, false);

            holder = new ViewHolder();
            holder.gameArt = (ImageView)convertView.findViewById(R.id.gameArt);
            holder.gameTitle = (TextView)convertView.findViewById(R.id.gameTitle);
            holder.gameDetails = (ListView)convertView.findViewById(R.id.runDetails);
            RunDetailAdapter detailAdapter = new RunDetailAdapter();
            holder.gameDetails.setAdapter(detailAdapter);

            holder.viewPosition = position;
            convertView.setTag(holder); //Tag for the RecyclerView
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        return convertView;
    }

    //ViewHolder for recycling
    private static class ViewHolder {
        ImageView gameArt;
        TextView gameTitle;
        ListView gameDetails;

        int viewPosition;
    }
}
