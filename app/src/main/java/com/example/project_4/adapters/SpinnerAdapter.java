package com.example.project_4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project_4.R;

import java.util.List;

import model.Site;

public class SpinnerAdapter extends ArrayAdapter<Site> {
    public SpinnerAdapter(@NonNull Context context, int resource, List<Site> objects) {
        super(context, resource, objects);
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
//    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_site, parent, false);
        TextView tvSite = convertView.findViewById(R.id.site);

        Site site = this.getItem(position);
        if(site != null){
            tvSite.setText(site.getAddress());
        }

        return convertView;
    }
}
