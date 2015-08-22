package xeleciumlabs.speedrunrecords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xeleciumlabs.speedrunrecords.R;
import xeleciumlabs.speedrunrecords.data.Series;

/**
 * Created by Xelecium on 8/22/2015.
 */
public class SeriesAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Series> mSeries;

    public SeriesAdapter (Context context, ArrayList<Series> series) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mSeries = series;
    }
    @Override
    public int getCount() {
        return mSeries.size();
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
            convertView = mInflater.inflate(R.layout.series_list_item, parent, false);

            holder = new ViewHolder();
            holder.seriesTitle = (TextView)convertView.findViewById(R.id.seriesTitle);

            holder.viewPosition = position;
            convertView.setTag(holder); //Tag for the RecyclerView
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        Series series = mSeries.get(position);
        holder.seriesTitle.setText(series.getSeriesName());

        return convertView;
    }

    //ViewHolder for recycling
    private static class ViewHolder {
        TextView seriesTitle;

        int viewPosition;
    }
}
