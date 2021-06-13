package com.mobile.areasselectapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
public class AreaTextAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<AreaEntity> texts = new ArrayList<>();
    private int selectedPosition = -1;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetInvalidated();
    }

    // 参数包含 Context时，放在首位
    public AreaTextAdapter(Context context, ArrayList<AreaEntity> texts) {
        inflater = LayoutInflater.from(context);
        if (texts == null || texts.size() == 0) {
            return;
        }
        this.texts.addAll(texts);
    }

    @Override
    public int getCount() {
        return texts.size();
    }

    public void setData(ArrayList<AreaEntity> texts) {
        this.texts.clear();
        this.texts.addAll(texts);
        selectedPosition = -1;
        notifyDataSetChanged();
    }

    @Override
    public AreaEntity getItem(int position) {
        return texts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lv_text, parent,
                    false);

            ViewHolder holder = new ViewHolder();
            holder.textTv = (TextView) convertView.findViewById(R.id.tv_text_ll);
            convertView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        // 设置选中效果
        holder.textTv.setSelected(selectedPosition == position);
        holder.textTv.setText(texts.get(position).getAreaName());

        return convertView;
    }

    private static class ViewHolder {
        public TextView textTv;
    }
}
