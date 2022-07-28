package com.example.project_4.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import model.OrderDetail;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "EatNow.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @SuppressLint("Range")
    public List<OrderDetail> getCart(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();

        String[] sqlSelect={"detailId,orderId,menuId,quantity,price"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<OrderDetail> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new OrderDetail(c.getInt(c.getColumnIndex("detailId")),
                                c.getInt(c.getColumnIndex("orderId")),
                                c.getInt(c.getColumnIndex("menuId")),
                                c.getInt(c.getColumnIndex("quantity")),
                                c.getInt(c.getColumnIndex("price"))
                            ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addtoCart(OrderDetail order){

    }

    public void cleanCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }
}
