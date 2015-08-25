package xeleciumlabs.speedrunrecords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xeleciumlabs.speedrunrecords.R;
import xeleciumlabs.speedrunrecords.data.Platform;

/**
 * Created by Xelecium on 8/24/2015.
 */
public class PlatformAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Platform> mPlatforms;


    public PlatformAdapter (Context context, ArrayList<Platform> platforms) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mPlatforms = platforms;
    }

    @Override
    public int getCount() {
        return mPlatforms.size();
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
            convertView = mInflater.inflate(R.layout.platform_list_item, parent, false);

            holder = new ViewHolder();
            holder.platformName = (TextView)convertView.findViewById(R.id.platformName);

            holder.viewPosition = position;
            convertView.setTag(holder); //Tag for the RecyclerView
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        Platform platform = mPlatforms.get(position);
        holder.platformName.setText(platform.getPlatformName());

        return convertView;
    }

    //ViewHolder for recycling
    private static class ViewHolder {
        TextView platformName;

        int viewPosition;
    }
}
