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

public class tamanoAdapter extends ArrayAdapter<tamanoItem> {
    public tamanoAdapter(Context context, ArrayList<tamanoItem> tamanoList) {
        super(context, 0, tamanoList);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tamano_spinner, parent, false);
        }
        ImageView imgTamano = convertView.findViewById(R.id.icono_tamano);
        TextView txtTamano = convertView.findViewById(R.id.textTamano);

        tamanoItem currentItem = getItem(position);
        if (currentItem != null) {
            imgTamano.setImageResource(currentItem.getTamanoImg());
            txtTamano.setText(currentItem.getTamanoNombres());
        }
        return convertView;
    }
}