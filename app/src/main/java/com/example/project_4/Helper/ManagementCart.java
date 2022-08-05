package com.example.project_4.Helper;


import android.content.Context;

import com.example.project_4.Helper.TinyDB;
import com.example.project_4.Interface.ChangeNumberItemsListener;

import java.util.ArrayList;
import java.util.List;

import model.Menu;
import model.OrderDetail;

public class ManagementCart {
    Context context;
    TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(Menu item) {
        ArrayList<Menu> list = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (!existAlready) {
            list.add(item);
        }
        tinyDB.putListObject("CardList",list);
    }

    public ArrayList<Menu> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void plusNumberFood(ArrayList<Menu> list, int position, ChangeNumberItemsListener changeNumberItemsListener){
        list.get(position).setNumInCart(list.get(position).getNumInCart() + 1);
        tinyDB.putListObject("CartList", list);
        changeNumberItemsListener.change();
    }

    public void minusNumberFood(ArrayList<Menu> list, int position, ChangeNumberItemsListener changeNumberItemsListener){
        if (list.get(position).getNumInCart() == 1) {
            list.remove(position);
        }else{
            list.get(position).setNumInCart(list.get(position).getNumInCart() - 1);
        }
        tinyDB.putListObject("CartList", list);
        changeNumberItemsListener.change();
    }

    public Double getTotal(){
        ArrayList<Menu> list = getListCart();
        double total = 0;
        for(Menu item: list){
            total = total + (item.getPrice() * item.getNumInCart());
        }
        return  total;
    }

    public void cleanCart(){
        tinyDB.clear();
    }
}

