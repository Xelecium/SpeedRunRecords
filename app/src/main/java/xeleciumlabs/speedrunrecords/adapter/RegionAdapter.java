package xeleciumlabs.speedrunrecords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xeleciumlabs.speedrunrecords.R;
import xeleciumlabs.speedrunrecords.data.Region;

/**
 * Created by Xelecium on 8/24/2015.
 */
public class RegionAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Region> mRegions;


    public RegionAdapter(Context context, ArrayList<Region> regions) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mRegions = regions;
    }

    @Override
    public int getCount() {
        return mRegions.size();
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
            convertView = mInflater.inflate(R.layout.region_list_item, parent, false);

            holder = new ViewHolder();
            holder.regionName = (TextView)convertView.findViewById(R.id.regionName);

            holder.viewPosition = position;
            convertView.setTag(holder); //Tag for the RecyclerView
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        Region region = mRegions.get(position);
        holder.regionName.setText(region.getRegionName());

        return convertView;
    }

    //ViewHolder for recycling
    private static class ViewHolder {
        TextView regionName;

        int viewPosition;
    }
}
