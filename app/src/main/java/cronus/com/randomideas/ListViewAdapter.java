package cronus.com.randomideas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final String[] mDataset;

    public ListViewAdapter(Context context, String[] dataset) {
        mContext = context;
        mDataset = dataset;
    }

    @Override
    public int getCount() {
        return mDataset.length;
    }

    @Override
    public String getItem(int position) {
        return mDataset[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String ideas = mDataset[position];

        int flagResId = mContext.getResources().getIdentifier("0", "drawable", mContext.getPackageName());
        viewHolder.mTextView.setText(ideas);
        viewHolder.mTextView.setCompoundDrawablesWithIntrinsicBounds(flagResId, 0, 0, 0);

        return convertView;
    }

    private static class ViewHolder {
        public TextView mTextView;
    }
}