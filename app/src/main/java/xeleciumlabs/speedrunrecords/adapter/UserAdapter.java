package xeleciumlabs.speedrunrecords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xeleciumlabs.speedrunrecords.R;
import xeleciumlabs.speedrunrecords.data.User;

/**
 * Created by Xelecium on 8/24/2015.
 */
public class UserAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<User> mUsers;

    public UserAdapter(Context context, ArrayList<User> users) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mUsers = users;
    }

    @Override
    public int getCount() {
        return mUsers.size();
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
            holder.userName = (TextView)convertView.findViewById(R.id.userName);

            holder.viewPosition = position;
            convertView.setTag(holder); //Tag for the RecyclerView
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        User user = mUsers.get(position);
        holder.userName.setText(user.getUserName());

        return convertView;
    }

    //ViewHolder for recycling
    private static class ViewHolder {
        TextView userName;

        int viewPosition;
    }
}
