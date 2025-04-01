package com.utkarsh.datedemo;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DateAdapter extends BaseAdapter {

    private Context context;
    private List<DateModel> dateList;

    public DateAdapter(Context context, List<DateModel> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    @Override
    public int getCount() {
        return dateList.size();
    }

    @Override
    public Object getItem(int position) {
        return dateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_list_item, parent, false);
            holder = new ViewHolder();
            holder.checkBox = convertView.findViewById(R.id.checkBox);
            holder.dateText = convertView.findViewById(R.id.dateText);
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DateModel model = dateList.get(position);

        holder.dateText.setText(model.getDate());
        holder.checkBox.setChecked(model.isChecked());

        // Handle checkbox click
        View finalConvertView = convertView;
        holder.checkBox.setOnClickListener(v -> {
            model.setChecked(holder.checkBox.isChecked());
            updateBackground(finalConvertView, model);
        });

        // Handle image click
        View finalConvertView1 = convertView;
        holder.imageView.setOnClickListener(v -> {
            model.setImageClicked(!model.isImageClicked());
            updateBackground(finalConvertView1, model);
        });

        // Apply the background color initially
        updateBackground(convertView, model);

        return convertView;
    }

    // ✅ Method to apply the correct background with a horizontal gradient
    private void updateBackground(View view, DateModel model) {
        if (model.isChecked() && model.isImageClicked()) {
            // Horizontal gradient (green → blue)
            GradientDrawable gradient = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,   // Gradient from left to right
                    new int[]{0xFF4CAF50, 0xFF2196F3}         // Green → Blue
            );
            view.setBackground(gradient);
        } else if (model.isChecked()) {
            view.setBackgroundColor(0xFF4CAF50);  // Green
        } else if (model.isImageClicked()) {
            view.setBackgroundColor(0xFF2196F3);  // Blue
        } else {
            view.setBackgroundColor(0xFFFFFFFF);  // White (default)
        }
    }

    static class ViewHolder {
        CheckBox checkBox;
        TextView dateText;
        ImageView imageView;
    }
}
