package com.example.astronomyforkids.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.astronomyforkids.R;

import java.util.ArrayList;

public class TopicAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<String> topicList;

    /**
     * Конструктор адапатера тем ініціалізує зміні
     * @param context
     * @param topicList
     */
    public TopicAdapter(Context context, ArrayList<String> topicList) {
        this.topicList = topicList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return topicList.size();
    }

    @Override
    public Object getItem(int i) {
        return topicList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Задає значеня в список тем
     * @param i
     * @param view
     * @param viewGroup
     * @return view
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = layoutInflater.inflate(R.layout.item_list_view, viewGroup, false);
        }
        ((TextView) view.findViewById(R.id.textViewItem)).setText(topicList.get(i));
        return view;
    }
}
