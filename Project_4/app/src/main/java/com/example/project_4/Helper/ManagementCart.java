package com.example.project_4.Helper;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.project_4.Interface.ChangeNumberItemsListener;

import java.util.ArrayList;

import com.example.project_4.LoginActivity;
import com.example.project_4.R;
import com.example.project_4.Store_dashboardActivity;
import com.example.project_4.databinding.FragmentCartBinding;
import com.example.project_4.model.Menu;
import com.example.project_4.ui.CartFragment;

public class ManagementCart {
    Context context;
    TinyDB tinyDB;
    String key;

    public ManagementCart(Context context, String key) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
        this.key = key;
    }

    public void insertFood(Menu item) {
        ArrayList<Menu> list = getListCart();
        boolean existAlready = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMenu_id() == item.getMenu_id()) {
                list.get(i).setNumInCart(list.get(i).getNumInCart() + item.getNumInCart());
                existAlready = true;
                break;
            }
        }

        if (!existAlready) {
            list.add(item);
        }
        tinyDB.putListObject(key, list);
    }

    public ArrayList<Menu> getListCart() {
        return tinyDB.getListObject(key);
    }

    public void plusNumberFood(ArrayList<Menu> list, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        list.get(position).setNumInCart(list.get(position).getNumInCart() + 1);
        tinyDB.putListObject(key, list);
        changeNumberItemsListener.change();
        Object activity = ((Store_dashboardActivity) this.context);
    }

    public void minusNumberFood(ArrayList<Menu> list, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (list.get(position).getNumInCart() == 1) {
            AlertDialog dialog = new AlertDialog.Builder(this.context)
                    .setMessage("Bạn có muốn xóa khỏi giỏ hàng ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            list.remove(position);
                            tinyDB.putListObject(key, list);
                            changeNumberItemsListener.change();

                        }
                    })
                    .setNegativeButton("No", null)
                    .create();
            dialog.show();
        } else {
            list.get(position).setNumInCart(list.get(position).getNumInCart() - 1);
            tinyDB.putListObject(key, list);
            changeNumberItemsListener.change();
        }
    }

    public Double getTotal() {
        ArrayList<Menu> list = getListCart();
        double total = 0;
        for (Menu item : list) {
            total = total + (item.getPrice() * item.getNumInCart());
        }
        return total;
    }

    public void cleanCart() {
        tinyDB.remove(key);
    }
}

