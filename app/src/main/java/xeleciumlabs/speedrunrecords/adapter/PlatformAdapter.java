package xeleciumlabs.speedrunrecords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import xeleciumlabs.speedrunrecords.data.Game;
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
        return null;
    }
}
