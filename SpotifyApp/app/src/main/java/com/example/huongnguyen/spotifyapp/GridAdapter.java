package com.example.huongnguyen.spotifyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by huongnguyen on 10/8/17.
 */

public class GridAdapter extends BaseAdapter {

    Context context;
    private String [] values;
    private int [] images;
    View view;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, String[] values, int[] images) {
        this.context = context;
        this.values = values;
        this.images = images;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = new View(context);
            view = layoutInflater.inflate(R.layout.single_item, null);

            ImageView imageView = view.findViewById(R.id.playlist_image);
            TextView textView = view.findViewById(R.id.playlist_name);

            imageView.setImageResource(images[i]);
            textView.setText(values[i]);
        }
        return view;
    }
}
