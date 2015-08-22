package xeleciumlabs.speedrunrecords.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Xelecium on 8/18/2015.
 */
public class RunDetailAdapter extends BaseAdapter {
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
        //Not using RecyclerView, as the dataset is too small to warrant it


        return convertView;
    }
}
