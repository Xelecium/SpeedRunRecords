package xeleciumlabs.speedrunrecords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xeleciumlabs.speedrunrecords.R;
import xeleciumlabs.speedrunrecords.data.Run;

/**
 * Created by Xelecium on 8/16/2015.
 */
public class RunAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Run> mRuns;

    //Constructor
    public RunAdapter(Context context, ArrayList<Run> runs) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mRuns = runs;
    }

    @Override
    public int getCount() {
        return mRuns.size();
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
            holder.gameTitle = (TextView)convertView.findViewById(R.id.gameTitle);
            holder.runTime = (TextView)convertView.findViewById(R.id.runTime);
            holder.runUser = (TextView)convertView.findViewById(R.id.runUser);

            holder.viewPosition = position;
            convertView.setTag(holder); //Tag for the RecyclerView
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        Run run = mRuns.get(position);
        holder.gameTitle.setText(run.getGameTitle());
        int time = run.getRunTime();
        holder.runTime.setText(String.valueOf(time));
        holder.runUser.setText(run.getRunUserId());

        return convertView;
    }

    //ViewHolder for recycling
    private static class ViewHolder {;
        TextView gameTitle;
        TextView runTime;
        TextView runUser;

        int viewPosition;
    }
}
