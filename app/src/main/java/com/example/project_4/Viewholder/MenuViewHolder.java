package com.example.project_4.Viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView txtMenuType;
    Button buttonType;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenuType = itemView.findViewById(R.id.textView6);
        buttonType = itemView.findViewById(R.id.button2);

    }

    @Override
    public void onClick(View view) {

    }
}
