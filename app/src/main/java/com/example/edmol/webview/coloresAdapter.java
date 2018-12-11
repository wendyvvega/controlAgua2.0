package com.example.edmol.webview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class coloresAdapter extends ArrayAdapter<coloresItem> {
    public coloresAdapter(Context context, ArrayList<coloresItem> coloresList) {
        super(context, 0, coloresList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.color_spinner, parent, false);
        }
        ImageView imgColor = convertView.findViewById(R.id.icono_colores);
        TextView txtColor = convertView.findViewById(R.id.textColor);

        coloresItem currentItem = getItem(position);
        if (currentItem != null) {
            imgColor.setImageResource(currentItem.getColoresImg());
            txtColor.setText(currentItem.getColoresNombres());
        }
        return convertView;
    }
}