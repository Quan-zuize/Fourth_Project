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

import com.example.project_4.model.Site;

public class SpinnerAdapter extends ArrayAdapter<Site> {
    private Site selectedSite;
    public SpinnerAdapter(@NonNull Context context, int resource, List<Site> objects) {
        super(context, resource, objects);
    }

    public Site getSelectedSite() {
        return selectedSite;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_site_selected, parent, false);
        }
        TextView tvSelected = convertView.findViewById(R.id.tv_selected);

        selectedSite = this.getItem(position);
        if(selectedSite != null){
            tvSelected.setText(selectedSite.getAddress());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_site, parent, false);
        }
        TextView tvSite = convertView.findViewById(R.id.site);

        Site site = this.getItem(position);
        if(site != null){
            tvSite.setText(site.getAddress());
        }

        return convertView;
    }
}
