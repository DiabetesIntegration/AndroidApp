package com.example.kbb12.dms.customListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.kbb12.dms.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 3/12/2017.
 */
public class CustomAdapter extends BaseAdapter implements ListAdapter {
    private List<String> items;
    private Context context;
    private ICustomListViewControllerFactory controllerFactory;

    public CustomAdapter(Context context, ICustomListViewControllerFactory controllerFactory) {
        items = new ArrayList<>();
        this.context = context;
        this.controllerFactory=controllerFactory;
    }

    public boolean isEmpty() {
        if(items.size() == 0) {
            return true;
        }
        return false;
    }

    public void clear() {
        items.clear();
    }

    public void addAll(List<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final int pos = position;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list_layout, null);
        }


        TextView name = (TextView) view.findViewById(R.id.item_string);
        name.setText(items.get(position));


        ImageView delete = (ImageView) view.findViewById(R.id.delete_item);
        delete.setOnClickListener(controllerFactory.getCustomListViewController(position));

        return view;
    }


}
