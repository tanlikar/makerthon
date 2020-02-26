package com.example.makerthon.viewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.VisibleForTesting;

import com.example.makerthon.R;

public class customProductGridAdapter extends BaseAdapter {

    private Context mContext;
    private final String[] text;
    private final int[] Imageid;
    private final String[] stockText;

    public customProductGridAdapter(Context c, String[] text,String[] stockText, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.text = text;
        this.stockText = stockText;
    }

    @Override
    public int getCount() {
        return text.length;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.activity_gridview_product, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text_product);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image_product);
            TextView textView1 = grid.findViewById(R.id.grid_text_stock);
            textView.setText(text[i]);
            textView1.setText(stockText[i]);
            imageView.setImageResource(Imageid[i]);
        } else {
            grid = (View) view;
        }

        return grid;
    }
}